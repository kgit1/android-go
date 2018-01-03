package com.konggit.mvpexample;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by erik on 03.01.2018.
 */

public class ViewHolderExample {

    public final EditText editText;
    public final TextView textView;

    public ViewHolderExample(EditText editText, TextView textView){

        this.editText = editText;
        this.textView = textView;
    }
}
