package com.Utopia.utopia.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Joe on 14-9-6.
 */
public class InputDialog extends AlertDialog {

    private String mContent;
    private EditText editText;
    private Button button;
    public InputDialog(Context context) {
        super(context);
    }
    private int mChangeOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_dialog);
        editText = (EditText)findViewById(R.id.input_dialog_edit_text);
        button = (Button) findViewById(R.id.input_dialog_button);
        editText.setText(mContent);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mChangeOne = -1;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent = editText.getText().toString();
                //viewPagerFragment0.addEntry(mContent);
                dismiss();
            }
        });
    }
    public String getContent()
    {
        String temp = mContent;
        mContent = "";
        editText.setText(mContent);
        return temp;
    }
    public void setContent(String content){
        mContent = content;
        editText.setText(mContent);
    }

    public void setChangeOne(int changeOne) {
        mChangeOne = changeOne;
    }

    public int getChangeOne() {
        return mChangeOne;
    }

}
