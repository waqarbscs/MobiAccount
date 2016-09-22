package com.OneWindow.sol.MobiAccount.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by waqarbscs on 9/16/2016.
 */
public class NoSelectionEditText extends EditText {
    public NoSelectionEditText(Context context) {
        super(context);
    }

    public NoSelectionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoSelectionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        CharSequence text=getText();
        if(text!=null){
            if(selStart!=text.length()||selEnd!=text.length()){
                setSelection(text.length(),text.length());
            return;
            }

        }
        super.onSelectionChanged(selStart, selEnd);
    }
}
