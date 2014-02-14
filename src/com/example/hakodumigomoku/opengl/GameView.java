package com.example.hakodumigomoku.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.view.MotionEvent;

public class GameView implements Renderer {

    private int[] block_position;
    private static final int ALL_TURN = 100;
    BlockView[] cube;
    private Context mContext;

    public GameView(Context context) {
        mContext = context;
        cube = new BlockView[ALL_TURN];
        for (int i = 0; i < cube.length; i++) {
            cube[i] = new BlockView(context);
        }
        initStage();
    }

    private void initStage() {
        block_position = new int[110];// 配列を初期化
        /* 画面を折り返す(10*10)ために番兵(-1)を用意 */
        block_position[10] = -1;
        block_position[21] = -1;
        block_position[32] = -1;
        block_position[43] = -1;
        block_position[54] = -1;
        block_position[65] = -1;
        block_position[76] = -1;
        block_position[87] = -1;
        block_position[98] = -1;
        block_position[109] = -1;

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // cube[now_turn].border_Xpoint = (float) now_turn +
        // BlockView.CUBE_EDGE;
        // オブジェクトの傾きを指定する
        gl.glRotatef(10f, 0, 1, 0);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45f, (float) width / height, 1f, 50f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);

        // シェーディング
        gl.glShadeModel(GL10.GL_SMOOTH);
        // ライティング
        // gl.glEnable(GL10.GL_LIGHTING);
        // gl.glEnable(GL10.GL_LIGHT0);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            new BlockView(mContext).draw(gl);
        }
        return true;
    }

}
