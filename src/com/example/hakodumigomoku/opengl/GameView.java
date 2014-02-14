
package com.example.hakodumigomoku.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView implements Renderer, OnTouchListener {
    private GL10 mGl;
    private int[] block_position;
    private static final int ALL_TURN = 100;
    BlockView[] cube;
    private Context mContext;
    private float aspect; // アスペクト比
    private int angle; // 回転角度

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
        /** 画面のクリア */
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        /** 射影変換 */
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, aspect, 0.01f, 100.0f);

        /** ビュー変換 */
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0.5f, 5.0f, 0, 0, 0.0f, 0.0f, 1.0f, 0.0f);

        /** モデル変換 */
        gl.glRotatef(angle, 0, 1, 0);

        /** ボックスの描画 */
        new BlockView(mContext).draw(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // ビューポート変換
        gl.glViewport(0, 0, width, height);
        aspect = (float) width / (float) height;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 頂点配列の有効化
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // デプステストの有効化
        gl.glEnable(GL10.GL_DEPTH_TEST);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("touch", "touch");
            angle++;
            return true;
        } else {
            return false;
        }
    }
}
