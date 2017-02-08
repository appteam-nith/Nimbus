package com.nith.appteam.nimbus;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;

public class TestViewHolder extends MaterialLeanBack.ViewHolder {

    protected TextView textView;
    protected ImageView imageView;

    public TestViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
