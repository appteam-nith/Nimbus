package com.nith.appteam.nimbus.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.nith.appteam.nimbus.R;

/**
 * Created by jatin on 23/3/17.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {

    public CustomDialogClass(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_customdialog);


    }


    @Override
    public void onClick(View v) {

    }
}
