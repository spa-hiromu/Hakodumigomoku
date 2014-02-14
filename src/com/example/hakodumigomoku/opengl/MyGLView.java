package com.example.hakodumigomoku.opengl;


import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLView extends GLSurfaceView{

    GameView my_renderer;    
    
    public MyGLView(Context context) {
        super(context);
        my_renderer = new GameView(context);
        setRenderer(my_renderer);
        this.setOnTouchListener(my_renderer);
//        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

}
