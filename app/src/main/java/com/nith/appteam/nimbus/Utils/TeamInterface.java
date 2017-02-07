package com.nith.appteam.nimbus.Utils;

import android.support.v7.widget.CardView;

/**
 * Created by sahil on 7/2/17.
 */

public interface TeamInterface {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);
    int getCount();
}
