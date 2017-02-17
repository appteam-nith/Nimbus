package com.nith.appteam.nimbus.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nith.appteam.nimbus.R;

/**
 * Created by sahil on 9/2/17.
 */

public class EditorView extends ScrollView {
    private static final int EDIT_PADDING_TOP = 10;
    private static final int EDIT_PADDING = 10;

    private LinearLayout allLayout;
    private LayoutInflater inflater;
    private EditText lastEditText;
    private OnFocusChangeListener focusChangeListener;
    private OnKeyListener keyListener;
    private OnClickListener onClickListener;
    private int viewTag = 1;


    public EditorView(Context context) {
        this(context, null);
    }

    public EditorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflater = LayoutInflater.from(context);

        allLayout = new LinearLayout(context);
        allLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(allLayout, layoutParams);

        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                allLayout.removeView(view);
            }
        };

        focusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    lastEditText = (EditText) view;
                }
            }
        };

        keyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    EditText editText = (EditText) view;
                    onBackPress(editText);
                }
                return false;
            }
        };

        EditText e = createEditText("Title", dip2px(10));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        allLayout.addView(e, p);

        EditText content = createEditText("Enter Description", dip2px(10));
        LinearLayout.LayoutParams contentp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        allLayout.addView(content, contentp
        );
        lastEditText = content;

    }

    private EditText createEditText(String hint, int Padding) {
        EditText editText = (EditText) inflater.inflate(R.layout.item_edittext, null);
        editText.setTag(viewTag++);
        editText.setOnFocusChangeListener(focusChangeListener);
        editText.setOnKeyListener(keyListener);
        editText.setPadding(dip2px(EDIT_PADDING_TOP), Padding, dip2px(EDIT_PADDING_TOP), 0);
        editText.setHint(hint);
        return editText;
    }

    private void createImageViewAtIndex(Bitmap bmp, int index) {
        ImageView imageView = (ImageView) inflater.inflate(R.layout.item_image, null);
        imageView.setImageBitmap(bmp);
        int imageHeight = getWidth() * bmp.getHeight() / bmp.getWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, imageHeight);
        imageView.setLayoutParams(lp);
        imageView.setOnClickListener(onClickListener);
        allLayout.addView(imageView, index);
    }

    private void createEditTextAtIndex(String text, int index) {
        EditText e = createEditText("", dip2px(10));
        e.setText(text);
        allLayout.addView(e, index);
    }

    private void onBackPress(EditText editText) {
        int selection = editText.getSelectionStart();
        if (selection == 0) {
            int viewIndex = allLayout.indexOfChild(editText);
            View v = allLayout.getChildAt(viewIndex - 1);

            if (v != null) {
                if (v instanceof EditText) {
                    if ((int) v.getTag() != 1) {
                        String s1 = editText.getText().toString();
                        EditText q = (EditText) v;
                        String s2 = q.getText().toString();
                        allLayout.removeView(editText);
                        q.setText(s1 + s2);
                        q.requestFocus();
                        q.setSelection(s2.length(), s2.length());
                        lastEditText = q;
                    }
                } else if (v instanceof ImageView) {
                    allLayout.removeView(v);
                }

            }
        }

    }

    public void addImage(Bitmap b) {
        String text1 = lastEditText.getText().toString();
        int pos = lastEditText.getSelectionStart();
        String text2 = text1.substring(0, pos).trim();
        int index = allLayout.indexOfChild(lastEditText);

        if (text1.isEmpty() && (int) lastEditText.getTag() != 1) {
            createImageViewAtIndex(b, index);
        } else {
            lastEditText.setText(text2);
            String t = text1.substring(pos).trim();
            if (allLayout.getChildCount() - 1 == index || !t.isEmpty()) {
                createEditTextAtIndex(t, index + 1);
            }
            createImageViewAtIndex(b, index + 1);
            lastEditText.requestFocus();
            lastEditText.setSelection(text2.length(), text2.length());
        }
        hideKeyBoard();
    }

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(lastEditText.getWindowToken(), 0);
    }

    private int dip2px(float dipValue) {
        float m = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public AddTopic buildEditData() {
        String topic="",detail="";

        int num = allLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View itemView = allLayout.getChildAt(index);
            Log.d("index",""+index);
            if (itemView instanceof EditText) {
                EditText item = (EditText) itemView;
                if (index == 0) {
                    topic=item.getText().toString();
                }
                if (index == 2) {
                    detail=item.getText().toString();
                }
                else if(index==1){
                    detail=item.getText().toString();
                }
            }
            /*
            else if (itemView instanceof ImageView) {
                ImageView item = (ImageView) itemView;
            }*/
        }
        AddTopic itemData = new AddTopic(topic,detail);
        return itemData;
    }

    public class AddTopic{
        public String title,detail,imageUrl;

        public AddTopic(String title, String detail) {
            this.title = title;
            this.detail = detail;
        }

        public AddTopic(String title, String detail, String imageUrl) {
            this.title = title;
            this.detail = detail;
            this.imageUrl = imageUrl;
        }
    }
}

