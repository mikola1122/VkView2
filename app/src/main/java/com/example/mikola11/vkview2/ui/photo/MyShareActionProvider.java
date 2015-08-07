package com.example.mikola11.vkview2.ui.photo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;

import com.example.mikola11.vkview2.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyShareActionProvider extends ShareActionProvider {
    private final Context mContext;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public MyShareActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        View v =
                super.onCreateActionView();

        // Set your drawable here
        Drawable icon =
                mContext.getResources().getDrawable(R.drawable.ic_share_white_24dp);

        Class clazz = v.getClass();

        //reflect all of this shit so that I can change the icon
        try {
            Method method = clazz.getMethod("setExpandActivityOverflowButtonDrawable", Drawable.class);
            method.invoke(v, icon);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return v;
    }
}
