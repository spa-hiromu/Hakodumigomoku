package com.example.hakodumigomoku.opengl;

import android.os.Bundle;

import com.example.hakodumigomoku.BaseActivity;

public class GameActivity extends BaseActivity{
    MyGLView my_gl_view;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        my_gl_view = new MyGLView(this);
        setContentView(my_gl_view);
    }
    protected void onResume(){
        super.onResume();
        my_gl_view.onResume();
    }
    protected void onPause(){
        super.onPause();
        my_gl_view.onPause();
    }
}
