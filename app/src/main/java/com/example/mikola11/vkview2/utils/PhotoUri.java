package com.example.mikola11.vkview2.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PhotoUri {

    public PhotoUri() {
    }

    public Uri getLocalBitmapUri(ImageView imageView) {

        // Store image to default external storage directory
        Uri bmpUri = null;
        bmpUri = Uri.fromFile(savePhoto(imageView));

        return bmpUri;
    }

    public File savePhoto(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        File file = null;
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "photo_" + System.currentTimeMillis() + ".jpeg");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void cleanSharedPhoto(List<Uri> sharedPhotoUri) {
        for (int i = 0; i < sharedPhotoUri.size(); i++) {
            (new File(String.valueOf(sharedPhotoUri.get(i)))).delete();
        }
        return;
    }
}
