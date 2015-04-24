package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by csadlo on 4/18/2015.
 */
public class FileHandler {

    private static FileHandler ourInstance = null;
    //private ArrayList<BoardGame> Collection = new ArrayList<BoardGame>();
    private Context context;
    private String username;

    public static FileHandler getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new FileHandler(context);
        }
        return ourInstance;
    }

    private FileHandler(Context context) {
        this.context = context;

        Log.d("Debug", "Creating the File Handler ");
    }

    public void setUser(String username) {

        this.username = username;

        try {
            URL url = new URL("https://boardgamegeek.com/xmlapi2/collection?username=brickedphoneclub");

            new DownloadFilesTask().execute(url);

        } catch (MalformedURLException mue) {
            Log.e("XML Downloader", "malformed url error", mue);
        } catch (SecurityException se) {
            Log.e("XML Downloader", "security error", se);
        }

        //ProcessXML();
    }

    private static void ProcessXML() {

        try {
            //File file = new File(context.getFilesDir() + "/" + "collection.xml");
            File file = new File(Environment.getExternalStorageDirectory() + "/" + "collection.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("item");
            System.out.println("Information of all board games");
            Log.v("XML Parser", "number of board games = " + nodeLst.getLength() );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public ArrayList<BoardGame> getCollection() {
        return Collection;
    }
*/

    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        protected Long doInBackground(URL... urls) {

            try {
                HttpURLConnection connection = (HttpURLConnection)urls[0].openConnection();
                connection.setReadTimeout(5000 /* milliseconds */);
                connection.setConnectTimeout(6000 /* milliseconds */);
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream is = connection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

                br.close();

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
}


