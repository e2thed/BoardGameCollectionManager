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

public class DownloadImageTask extends AsyncTask<MainCollectionActivity.GameAdapter.BoardGameAndView, Void, MainCollectionActivity.GameAdapter.BoardGameAndView> {

    public DownloadImageTask() {

    }

    protected MainCollectionActivity.GameAdapter.BoardGameAndView doInBackground(MainCollectionActivity.GameAdapter.BoardGameAndView... params) {

        MainCollectionActivity.GameAdapter.BoardGameAndView container = params[0];
        BoardGame BG = container.BG;

        try {
            String imageURL = "http:" + BG.getThumbnail_URL();
            //Log.d("ImageLoader", "Loading "+imageURL);
            InputStream in = (InputStream) new URL(imageURL).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            BG.setThumbnail(bitmap);
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

    protected void onPostExecute(MainCollectionActivity.GameAdapter.BoardGameAndView container) {
        BoardGame BG = container.BG;
        View view = container.view;

        ImageView imgView = (ImageView)view.findViewById(R.id.img_game);
        imgView.setImageBitmap(BG.getThumbnail());
    }
}
