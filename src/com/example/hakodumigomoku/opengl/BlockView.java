
package com.example.hakodumigomoku.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class BlockView {
    private FloatBuffer mVertexBuffer;
    private final float[] vertices = {
            1.0f, 1.0f, 1.0f,//0
            1.0f, 1.0f,-1.0f,//1
           -1.0f, 1.0f, 1.0f,//2
           -1.0f, 1.0f,-1.0f,//3
            1.0f,-1.0f, 1.0f,//4
            1.0f,-1.0f,-1.0f,//5
           -1.0f,-1.0f, 1.0f,//6
           -1.0f,-1.0f,-1.0f //7
    };
    private ByteBuffer mIndexBuffer;
    private final byte[] indexies = {
            0,1,2,3,6,7,4,5,0,1,//0
            1,5,3,7,            //1
            0,2,4,6             //2
    };
    // 立方体を表現する配列

    protected static final float CUBE_EDGE = 0.2f;// 立方体の一辺の長さ

    private int[] buffers;
    private Context mContext;

    public BlockView(Context context) {
        mContext = context;
        mVertexBuffer = makeFloatBuffer(vertices);
        mIndexBuffer = makeByteBuffer(indexies);
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
    private ByteBuffer makeByteBuffer(byte[] array){
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        bb.put(array).position(0);
        return bb;
    }

    public void draw(GL10 gl) {
        /** 頂点バッファの指定 */
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);

        /** 面0の描画 */
        gl.glColor4f(0, 1, 0, 1);
        mIndexBuffer.position(0);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 10, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        /** 面1の描画 */
        gl.glColor4f(0, 0, 1, 1);
        mIndexBuffer.position(10);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        /** 面2の描画 */
        gl.glColor4f(1, 0, 0, 1);
        mIndexBuffer.position(14);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
    }
}
