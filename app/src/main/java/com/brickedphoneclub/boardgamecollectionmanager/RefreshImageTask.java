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

public class RefreshImageTask extends AsyncTask<BoardGameAndView, Void, BoardGameAndView> {

    public RefreshImageTask() {

    }

    protected BoardGameAndView doInBackground(BoardGameAndView... params) {

        BoardGameAndView container = params[0];
        SimpleBoardGame BG = container.BG;
        View view = container.view;
        String task = container.task;

        if (task.toLowerCase().contains("thumb")) {     //if the task is thumbnail related
            Log.d("ImageRefreshing", "Refreshing Thumbnail "+BG.getThumbnail_URL());
            ImageView imgView = (ImageView)view.findViewById(R.id.img_game);
            Bitmap thumbnail = BG.getThumbnail();
            if (thumbnail != null)
                Log.d("whateves", "Setting thumbnail bitmap in view");
            //    imgView.setImageBitmap(thumbnail);

        } else {                                        //all other non-thumbnail related tasks
            Log.d("ImageRefreshingTask", "Refreshing Image "+BG.getLargeImage_URL());
            ImageView imgView = (ImageView)view.findViewById(R.id.img_detailImage);
            Bitmap image = BG.getLargeImage();
            if (image != null)
                Log.d("whateves", "Setting image bitmap in view");
                //imgView.setImageBitmap(image);
        }

        return container;
    }

    protected void onPostExecute(BoardGameAndView container) {
        /*
        BoardGame BG = container.BG;
        View view = container.view;
        String task = container.task;
        String imageURL;

        ImageView imgView = (ImageView)view.findViewById(R.id.img_game);

        if (task.toLowerCase().contains("thumb"))
            imgView.setImageBitmap(BG.getThumbnail());
        else
            imgView.setImageBitmap(BG.getImage());
        */
    }
}
