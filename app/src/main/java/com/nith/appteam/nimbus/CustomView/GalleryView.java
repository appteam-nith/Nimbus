package com.nith.appteam.nimbus.CustomView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nith.appteam.nimbus.R;

/**
 * Created by sahil on 5/3/17.
 */

public class GalleryView extends LinearLayout {
    private LayoutInflater inflater;
    private CardView expandedView,normalView;
    private static  final float PADDING_BELOW=10f;
    private boolean expand=true,collapse;
    private OnClickListener clickListener;
    private ImageView imgVw;

    public GalleryView(Context context) {
        this(context,null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        clickListener=new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand()&&!isCollapse()){
                    setCollapse(true);
                    setExpanded(false);
                    expand(expandedView);
                    imgVw.setImageResource(R.drawable.up);
                }
                else if (isCollapse()&&!isExpand()){
                    setCollapse(false);
                    setExpanded(true);
                    collapse(expandedView);
                    imgVw.setImageResource(R.drawable.down);
                }
            }
        };
        setOrientation(LinearLayout.VERTICAL);
        inflater=LayoutInflater.from(context);
        normalView=addLinearLayout(R.layout.view_normal,0,dip2px(PADDING_BELOW));
        expandedView=addLinearLayout(R.layout.view_expand,0,0);
         expandedView.setVisibility(GONE);

        normalView.setOnClickListener(clickListener);

        imgVw = (ImageView) normalView.findViewById(R.id.expand);

    }

    private CardView addLinearLayout(int layoutId, int paddingTop, int paddingBelow){
        CardView l= (CardView) inflater.inflate(layoutId,null);
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setPadding(0,paddingTop,0,paddingBelow);
        addView(l,p);
        return l;
    }

    private int dip2px(float dipValue) {
        float m = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpanded(boolean expand) {
        this.expand = expand;
    }

    public  void expand(final View v) {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public  void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public void setTextNormalView(String text){
        TextView t= (TextView) normalView.findViewById(R.id.text_normal);
        if(t!=null){
            t.setText(text);
        }
    }

    public void setImageNormalView(String imageUrl){
        AspectRatioImageView imageView= (AspectRatioImageView) normalView.findViewById(R.id.image_head_normal);
        if(imageView!=null){
            Glide.with(getContext()).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(imageView);
        }
    }

    public void setImageExpandedView(String... image){
        AspectRatioImageView image1= (AspectRatioImageView) expandedView.findViewById(R.id.image_expanded_1);
        AspectRatioImageView image2= (AspectRatioImageView) expandedView.findViewById(R.id.image_expanded_2);
        AspectRatioImageView image3= (AspectRatioImageView) expandedView.findViewById(R.id.image_more);
        if(image1!=null&&image2!=null){
            Log.d("hi","hi");
            Glide.with(getContext()).load(image[0]).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(image1);
            Glide.with(getContext()).load(image[1]).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(image2);
            Glide.with(getContext()).load(image[2]).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nimbuslogo).into(image3);
        }
    }

    public void setMoreClickListener(OnClickListener onClickListener){
        if(onClickListener!=null){
            ImageView image1= (ImageView) expandedView.findViewById(R.id.image_more);
            image1.setOnClickListener(onClickListener);
        }
    }
}
