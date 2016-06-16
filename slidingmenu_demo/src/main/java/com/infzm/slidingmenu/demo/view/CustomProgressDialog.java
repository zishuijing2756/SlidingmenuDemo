/**************************************************************************************
 * [Project] MyProgressDialog [Package] com.lxd.widgets [FileName] CustomProgressDialog.java [Copyright] Copyright 2012
 * LXD All Rights Reserved. [History] Version Date Author Record
 * -------------------------------------------------------------------------------------- 1.0.0 2012-4-27 lxd
 * (rohsuton@gmail.com) Create
 **************************************************************************************/

package com.infzm.slidingmenu.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infzm.slidingmenu.demo.R;

public class CustomProgressDialog extends Dialog {

    private Context                     context              = null;
    private static CustomProgressDialog customProgressDialog = null;
    private static ImageView            close_iv             = null;

    public CustomProgressDialog(Context context){
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }

    public static CustomProgressDialog createDialog(Context context) {
        customProgressDialog = new CustomProgressDialog(context, R.style.customProgressDialog);
        customProgressDialog.setContentView(R.layout.dialog_custom_progress);
        customProgressDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                                                   ViewGroup.LayoutParams.MATCH_PARENT);

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (customProgressDialog == null) {
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

}
