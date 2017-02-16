package com.nith.appteam.nimbus;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;

public class EventViewHolder extends MaterialLeanBack.ViewHolder {

    protected TextView textView;
    protected ImageView imageView;
    protected int line;
    public EventViewHolder(View itemView,int line) {
        super(itemView);
        this.line=line;
        textView = (TextView) itemView.findViewById(R.id.eventname);
        imageView = (ImageView) itemView.findViewById(R.id.eventimage);
    }
}
