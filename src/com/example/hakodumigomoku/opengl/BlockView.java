
package com.example.hakodumigomoku.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.example.hakodumigomoku.R;

public class BlockView {
    private final FloatBuffer mVertexBuffer;
    private float[] vertices = {
            // 前面の点四点の座標(x, y, z)
            -0.1f, -0.1f, 0.1f,// 左下前1
            0.1f, -0.1f, 0.1f,// 右下前2
            -0.1f, 0.1f, 0.1f,// 左上前3
            0.1f, 0.1f, 0.1f,// 右上前4

            // 背面の点四点の座標(x, y, z)
            -0.1f, -0.1f, -0.1f,// 左下奥5
            0.1f, -0.1f, -0.1f,// 右下奥6
            -0.1f, 0.1f, -0.1f,// 左上奥7
            0.1f, 0.1f, -0.1f,// 右上奥8

            // 左面の点四点の座標(x, y, z)
            -0.1f, -0.1f, 0.1f,// 左下前9
            -0.1f, -0.1f, -0.1f,// 左下奥10
            -0.1f, 0.1f, 0.1f,// 左上前11
            -0.1f, 0.1f, -0.1f,// 左上奥12

            // 右面の点四点の座標(x, y, z)
            0.1f, -0.1f, 0.1f,// 右下前13
            0.1f, -0.1f, -0.1f,// 右下奥14
            0.1f, 0.1f, 0.1f,// 右上前15
            0.1f, 0.1f, -0.1f,// 右上下16

            // 上面の点四点の座標(x, y, z)
            -0.1f, 0.1f, 0.1f,// 左上前17
            0.1f, 0.1f, 0.1f,// 右上前18
            -0.1f, 0.1f, -0.1f,// 左上奥19
            0.1f, 0.1f, -0.1f,// 右上奥20

            // 底面の点四点の座標(x, y, z)
            -0.1f, -0.1f, 0.1f,// 左下前21
            0.1f, -0.1f, 0.1f,// 右下前22
            -0.1f, -0.1f, -0.1f,// 左下奥23
            0.1f, -0.1f, -0.1f
            // 右下奥24
    };
    // 立方体を表現する配列
    private static final int CUBE_POINTS = 72; // 立方体を表現する点の数
    protected static final float CUBE_EDGE = 0.2f;// 立方体の一辺の長さ
    protected float border_Xpoint;// 基準となるX座標
    protected float border_Ypoint;// 基準となるY座標
    private final float border_Zpoint = 0f;// 基準となるZ座標
    private float height_rate;
    private int pTexture;
    private int aTexture;
    private int[] buffers;
    private Context mContext;

    public BlockView(Context context) {
        mContext = context;
        border_Xpoint = 0.0f;
        border_Ypoint = 1.0f;
        height_rate = 0.1f;
        // pTexture = loadTexture(R.drawable.circle);

        mVertexBuffer = makeFloatBuffer(vertices);
    }

    /**
     * 受け取った配列をバッファに書き直す
     * 
     * @param array
     * @return
     */
    private FloatBuffer makeFloatBuffer(float[] array) {
        FloatBuffer fb = ByteBuffer.allocateDirect(array.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fb.put(array).position(0);
        return fb;

    }

    public void draw(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        // 頂点配列の指定
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glTranslatef(0, height_rate, -3f);// 平行移動
        // 色の指定
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.0f);
        // テクスチャの指定
        buffers = new int[1];
        gl.glGenTextures(1, buffers, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, buffers[0]);

        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.circle);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
        // Front
        gl.glNormal3f(0, 0, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        // Back
        gl.glNormal3f(0, 0, -0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
        // Left
        gl.glNormal3f(-0.5f, 0, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        // Right
        gl.glNormal3f(0.5f, 0, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
        // Top
        gl.glNormal3f(0, 0.5f, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        // Bottom
        gl.glNormal3f(0, -0.5f, 0);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);

        if (border_Ypoint + height_rate >= -1.0f) {
            height_rate -= 0.01f;
        }
    }
}
