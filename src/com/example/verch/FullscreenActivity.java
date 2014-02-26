package com.example.verch;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class FullscreenActivity extends Activity {
    
	Button mButton;			//Button
	EditText mEdit;			//Text box
	String s_query;		
	View lLayout;			//LinearLayout
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 		//Code for full screen activity
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
        mButton = (Button)findViewById(R.id.button);
        mEdit   = (EditText)findViewById(R.id.fullscreen_content);
        lLayout = (View)findViewById(R.id.linearLayout1);
        
        mButton.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    s_query=mEdit.getText().toString();
                    Intent res=new Intent(FullscreenActivity.this,ResultActivity.class);
                    res.putExtra("query",s_query);
                    startActivity(res);
                    
                }
            });
        
        lLayout.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        hideSoftKeyboard();
                    }
                });
    }

	
	public void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            View yourEditTextHere =(View)findViewById(R.id.fullscreen_content);
			imm.hideSoftInputFromWindow(yourEditTextHere.getWindowToken(), 0);
        }
    }
    
}