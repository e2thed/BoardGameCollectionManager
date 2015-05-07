package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by csadlo on 4/18/2015.
 */
public class FileHandler {

    private static FileHandler ourInstance = null;
    private Context context;
    private String username;

    private String collection_filename = "CACHE";
    private File collection_file;
    private BoardGameManager BGM;

    public static FileHandler getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new FileHandler(context);
        }
        return ourInstance;
    }

    private FileHandler(Context context) {
        this.context = context;

        Log.d("Debug", "Creating the File Handler ");

        Log.d("Debug", "Creating the File Handler's handle to the BoardGameManager ");
        BGM = BoardGameManager.getInstance(context);
    }

    public void setUser(String username) {

        this.username = username;
        DownloadFilesTask DFT;

        Log.d("FileHandler","Prepping cache file");

        try {

            collection_file = File.createTempFile(collection_filename, null, context.getCacheDir());
            collection_file.deleteOnExit();

        } catch (NullPointerException npe) {
            Log.e("FileHandler", "Null Pointer Exception", npe);
        } catch (IOException ioe) {
            Log.e("FileHandler", "io error", ioe);
        }

        DFT = new DownloadFilesTask();

        try {

            URL url = new URL("https://boardgamegeek.com/xmlapi2/collection?username="+username+"&own=1");
            DFT.execute(url);

        } catch (MalformedURLException mue) {
            Log.e("XML Downloader", "malformed url error", mue);
        } catch (SecurityException se) {
            Log.e("XML Downloader", "security error", se);
        }

        try {

            DFT.get(3000, TimeUnit.MILLISECONDS);

        } catch (TimeoutException te) {
            Log.e("FileHandler", "Taking too long to download file", te);
        } catch (InterruptedException ie) {
            Log.e("FileHandler", "Taking too long to download file", ie);
        } catch (ExecutionException ee) {
            Log.e("FileHandler", "Taking too long to download file", ee);
        }

        try {

            InputStream is = new FileInputStream(collection_file);
            //Reader isr = new InputStreamReader(is);

            new XMLParserTask().execute((InputStream) is);

        } catch (FileNotFoundException fnfe) {
            Log.e("XML Parser", "missing cached file", fnfe);
        }
    }

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        protected Long doInBackground(URL... urls) {
            boolean connected = false;

            Log.v("DownloadFilesTask", "Attempting to download collection...");

            try {
                HttpURLConnection connection;
                int code = -1;

                for (int retry = 0; retry <= 8 && !connected; retry++) {
                    if (retry > 0) {
                        Log.i("XML Details Parsing", "Attempting connection retry");
                        Thread.sleep(100);  //200 milliseconds
                    }
                    connection = (HttpURLConnection)urls[0].openConnection();
                    connection.setReadTimeout(5000 /* milliseconds */);
                    connection.setConnectTimeout(6000 /* milliseconds */);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        connected = true;
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));

                        Writer writer = new FileWriter(collection_file);
                        BufferedWriter bw = new BufferedWriter(writer);

                        String line;
                        while ((line = br.readLine()) != null) {
                            bw.write(line);
                            bw.newLine();
                        }

                        br.close();
                        bw.close();

                        Reader reader = new FileReader(collection_file);
                        br = new BufferedReader(reader);

                        while ((line = br.readLine()) != null) {
                            //Log.v("Cached file content", line);     //sanity check
                        }

                        br.close();

                        connection.disconnect();
                        break;
                    }
                }

            } catch (IOException ioe) {
                Log.e("XML Downloader", "io error", ioe);
            } catch (SecurityException se) {
                Log.e("XML Downloader", "security error", se);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0L;
        }
    }

    private class XMLParserTask extends AsyncTask <InputStream, Integer, Long> {

        private final String ns = null;

        protected Long doInBackground(InputStream... ins) {

            Log.v("XMLParserTask", "Attempting to parse collection...");

            try {

                Log.d("XMLParserTask","Starting Top Level Parser");
                parseTopLevel(ins[0]);
                Log.d("XMLParserTask","Stopping Top Level Parser");

                String objectIDs = generateStringOfObjectIDs();

                Log.d("XMLParserTask","Starting Details Parser");
                performDetailsSearch(objectIDs);
                Log.d("XMLParserTask","Stopping Details Parser");


            } catch (XmlPullParserException xppe) {
                Log.e("XML Parser", "XmlPullParser error", xppe);
            } catch (IOException ioe) {
                Log.e("XML Parser", "io error", ioe);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0L;
        }

        public void parseTopLevel(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                readTopLevelFeed(parser);
            } finally {
                in.close();
            }
        }

        private void readTopLevelFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            BoardGame BG;

            parser.require(XmlPullParser.START_TAG, ns, "items");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the item tag
                if (name.equals("item")) {
                    BG = readItem(parser);
                    BGM.addBoardGame(BG);
                } else {
                    skip(parser);
                }
            }

            Log.i("BGM size", "Boardgame Collection Size = " + BGM.getCollectionSize());
        }

        // Parses the contents of an item. If it encounters a name, yearpublished, or thumbnail tag, hands them off
        // to their respective "read" methods for processing. Otherwise, skips the tag.
        private BoardGame readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, ns, "item");
            String boardgamename = null;
            int objectid = -1;
            int yearpublished = -1;
            String image_link = null;
            String thumbnail_link = null;

            String result = parser.getAttributeValue(ns, "objectid");
            if (result != null) {
                objectid = Integer.parseInt(result);
                //Log.d("XML Parser", "found boardgame with objectid=" + objectid);
            }

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("name")) {
                    boardgamename = readName(parser);
                } else if (name.equals("yearpublished")) {
                    yearpublished = readYearPublished(parser);
                } else if (name.equals("image")) {
                    image_link = readImageLink(parser);
                } else if (name.equals("thumbnail")) {
                    thumbnail_link = readThumbnailLink(parser);
                } else {
                    skip(parser);
                }
            }

            BoardGame BG;
            if (yearpublished == -1 || image_link == null || thumbnail_link == null)
                BG = new BoardGame(objectid, boardgamename);
            else
                BG = new BoardGame(objectid, boardgamename, Integer.toString(yearpublished), image_link, thumbnail_link);


            if (objectid != -1)
                BG.setObjectId(objectid);

            if (yearpublished != -1)
                BG.setYearPublished(""+yearpublished);

            if (thumbnail_link != null)
                BG.setThumbnail_URL(thumbnail_link);

            if (image_link != null)
                BG.setImage_URL(image_link);


            return BG;
        }

        //This handles skipping the XML tags that we don't care about
        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }


        public String generateStringOfObjectIDs() {

            BoardGame BG;
            String list_of_objectIDs = new String();
            ArrayList<BoardGame> BG_List = BGM.getBgList();

            for(int i = 0; i<BG_List.size(); i++)
            {
                BG = BG_List.get(i);
                if (i >= 1) list_of_objectIDs += ",";
                list_of_objectIDs += BG.getObjectId();    //concatenate ObjectID to the list
            }

            return list_of_objectIDs;
        }

        public void performDetailsSearch(String ObjectIDs) {

            Log.v("XML Details Parser", "Attempting to download/parse details...");

            //Now use the objectid to grab the XML details page and parse that
            //int objectid = (int) BG.getObjectId();
            boolean connected = false;

            try {
                //URL url = new URL("https://boardgamegeek.com/xmlapi/boardgame/" + (Integer.toString(objectid)) + "?stats=1");
                URL url = new URL("https://boardgamegeek.com/xmlapi/boardgame/" + ObjectIDs + "?stats=1");

                String tmp = new String("https://boardgamegeek.com/xmlapi/boardgame/" + ObjectIDs + "?stats=1");
                Log.d("URL for details", tmp);

                HttpURLConnection connection;
                int code = -1;

                for (int retry = 0; retry <= 8 && !connected; retry++) {
                    if (retry > 0) {
                        Log.i("XML Details Parsing", "Attempting connection retry");
                        Thread.sleep(200);  //200 milliseconds
                    }
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(5000 /* milliseconds */);
                    connection.setConnectTimeout(6000 /* milliseconds */);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        connected = true;
                        InputStream is = connection.getInputStream();
                        parseDetailsSearchResult(is);
                        is.close();
                        connection.disconnect();
                        break;
                    }
                }

            } catch (MalformedURLException mue) {
                Log.e("XML Details Parser", "mue error", mue);
            } catch (IOException ioe) {
                Log.e("XML Details Parser", "io error", ioe);
            } catch (SecurityException se) {
                Log.e("XML Details Parser", "security error", se);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void parseDetailsSearchResult(InputStream in) throws XmlPullParserException, IOException {
            String name;
            String result;
            int objectID;
            BoardGame BG;

            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();

                parser.require(XmlPullParser.START_TAG, ns, "boardgames");
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    name = parser.getName();
                    // Starts by looking for the item tag
                    if (name.equals("boardgame")) {

                        result = parser.getAttributeValue(ns, "objectid");
                        if (result != null) {
                            objectID = Integer.parseInt(result);
                            BG = BGM.getBoardGameById(objectID);
                            readBoardgameDetails(BG, parser);
                        }

                    } else {
                        skip(parser);
                    }
                }

            } finally {
                in.close();
            }
        }

        private void readBoardgameDetails(BoardGame BG, XmlPullParser parser) throws XmlPullParserException, IOException {
            int value = -1;
            String temp;
            ArrayList<String> ListOfMechanics = new ArrayList<String>();
            ArrayList<String> ListOfCategories = new ArrayList<String>();

            parser.require(XmlPullParser.START_TAG, ns, "boardgame");

            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("minplayers")) {
                    value = readTagIntValue(parser, "minplayers");
                    BG.setMinPlayers(value);
                } else if (name.equals("maxplayers")) {
                    value = readTagIntValue(parser, "maxplayers");
                    BG.setMaxPlayers(value);
                } else if (name.equals("minplaytime")) {
                    value = readTagIntValue(parser, "minplaytime");
                    BG.setMinPlayTime(value);
                } else if (name.equals("maxplaytime")) {
                    value = readTagIntValue(parser, "maxplaytime");
                    BG.setMaxPlayTime(value);
                } else if (name.equals("age")) {
                    value = readTagIntValue(parser, "age");
                    BG.setMinAge(value);
                } else if (name.equals("boardgamemechanic")) {
                    temp = readTagStringValue(parser, "boardgamemechanic");
                    //Log.d("FOUND", "mechanic "+temp);
                    ListOfMechanics.add(new String(temp));
                } else if (name.equals("boardgamecategory")) {
                    temp = readTagStringValue(parser, "boardgamecategory");
                    //Log.d("FOUND", "category "+temp);
                    ListOfCategories.add(new String(temp));
                } else if (name.equals("statistics")) {
                    float f_value = parseStatistics(parser, "bayesaverage");
                    BG.setRating(f_value);
                /*} else if (name.equals("average")) {
                    float f_value = readTagFloatValue(parser, "average");
                    BG.setRating(f_value);*/
                } else {
                    skip(parser);
                }
            }

            //Once we're done parsing this boardgame
            String[] array = new String[ListOfMechanics.size()];
            ListOfMechanics.toArray(array);
            BG.setBoardGameMechanic(array);
            array = new String[ListOfCategories.size()];
            ListOfCategories.toArray(array);
            BG.setBoardGameCategory(array);
        }

        //Parses the deeper nested statistics portion.
        //This may appear a little confusing but it's just a nested while loop to access the deep rating info
        private float parseStatistics(XmlPullParser parser, String tagname) throws XmlPullParserException, IOException  {
            float value = -1;
            String name;

            parser.require(XmlPullParser.START_TAG, ns, "statistics");
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                name = parser.getName();
                if (name.equals("ratings")) {
                    parser.require(XmlPullParser.START_TAG, ns, "ratings");
                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        name = parser.getName();
                        if (name.equals("bayesaverage")) {
                            value = readTagFloatValue(parser, "bayesaverage");
                        } else {
                            skip(parser);
                        }

                    }
                } else {
                    skip(parser);
                }

            }
            return value;
        }

        // For the tags <name>, objectid, <yearpublished>, <thumbnail>, etc, and extracts their text values.
        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }

        // Processes board game <name> tags in the item.
        private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "name");
            String name = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "name");
            return name;
        }

        // Processes board game <name> tags in the item.
        private int readObjectID(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "objectid");
            int objectid = Integer.parseInt(readText(parser));
            parser.require(XmlPullParser.END_TAG, ns, "objectid");
            return objectid;
        }

        // Processes board game <yearpublished> tags in the item.
        private int readYearPublished(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "yearpublished");
            int year = Integer.parseInt(readText(parser));
            parser.require(XmlPullParser.END_TAG, ns, "yearpublished");
            return year;
        }

        // Processes board game <image> tags in the item.
        private String readImageLink(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "image");
            String link = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "image");
            return link;
        }

        // Processes board game <thumbnail> tags in the item.
        private String readThumbnailLink(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "thumbnail");
            String link = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "thumbnail");
            return link;
        }

        // Processes board game tags in the item. (used by the details parser)
        private int readTagIntValue(XmlPullParser parser, String tagname) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, tagname);
            int value = Integer.parseInt(readText(parser));
            parser.require(XmlPullParser.END_TAG, ns, tagname);
            return value;
        }

        // Processes board game tags in the item. (used by the details parser)
        private String readTagStringValue(XmlPullParser parser, String tagname) throws IOException, XmlPullParserException {
            String value;
            parser.require(XmlPullParser.START_TAG, ns, tagname);
            value = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, tagname);
            return value;
        }

        // Processes board game tags in the item. (used by the details parser)
        private float readTagFloatValue(XmlPullParser parser, String tagname) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, tagname);
            float value = Float.parseFloat(readText(parser));
            parser.require(XmlPullParser.END_TAG, ns, tagname);
            return value;
        }

    }

    public static class Item {
        public final String boardgame_name;
        public final int objectid;
        public final int year_published;
        public final String thumbnail_link;

        private Item(String name, int objectid, int year, String link) {
            this.boardgame_name = name;
            this.objectid = objectid;
            this.year_published = year;
            this.thumbnail_link = link;
        }
    }

}


