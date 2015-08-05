package com.brickedphoneclub.boardgamecollectionmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<BoardGameAndView, Void, BoardGameAndView> {

    public DownloadImageTask() {

    }

    protected BoardGameAndView doInBackground(BoardGameAndView... params) {

        BoardGameAndView container = params[0];
        SimpleBoardGame BG = container.BG;
        String task = container.task;
        String imageURL;

        try {

            if (task.toLowerCase().contains("thumb")) {
                if (BG.getThumbnail_URL() == null || BG.getThumbnail_URL().isEmpty())
                    return container;   //short circuit
                imageURL = "http:" + BG.getThumbnail_URL();
                Log.d("DownloadImageTask", "Downloading Thumbnail " + imageURL);
            } else {
                if (BG.getLargeImage_URL() == null || BG.getLargeImage_URL().isEmpty())
                    return container;   //short circuit
                imageURL = "http:" + BG.getLargeImage_URL();
                Log.d("DownloadImageTask", "Downloading Large Image " + imageURL);
            }

            InputStream in = (InputStream) new URL(imageURL).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            if (task.toLowerCase().contains("thumb"))
                BG.setThumbnail(bitmap);
            else
                BG.setLargeImage(bitmap);

            in.close();
            return container;
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(BoardGameAndView container) {
 /*
        BoardGame BG = container.BG;
        View view = container.view;
        String task = container.task;

        if (task.toLowerCase().contains("thumb")) {
            ImageView imgView = (ImageView) view.findViewById(R.id.img_game);
            imgView.setImageBitmap(BG.getThumbnail());
        }
        //else
        //    imgView.setImageBitmap(BG.getImage());
*/
    }
}
