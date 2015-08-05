package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

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
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by csadlo on 4/18/2015.
 */
public class SimpleFileHandler {

    private static SimpleFileHandler ourInstance = null;
    private Context context;
    private String username;

    private String collection_filename = "CACHE";
    private File collection_file;
    private BoardGameManager BGM;

    public static SimpleFileHandler getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SimpleFileHandler(context);
        }
        return ourInstance;
    }

    private SimpleFileHandler(Context context) {
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
}


