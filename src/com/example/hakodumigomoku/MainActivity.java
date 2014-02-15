package com.example.hakodumigomoku;

import hakodumigomoku.plane.Game2DActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.hakodumigomoku.opengl.GameActivity;

public class MainActivity extends BaseActivity implements OnClickListener {
/**
 * @author HiromuYoshiwara
 */

    Button plane_button;// 2dゲームモードを呼び出すボタン
    Button gl_button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plane_button = (Button) findViewById(R.id.plane_button);
        gl_button = (Button) findViewById(R.id.gl_button);
        

        plane_button.setOnClickListener(this);
        gl_button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == gl_button) {
        Intent battleintent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(battleintent);
        } else if (v == plane_button) {
            Intent mIntent = new Intent(MainActivity.this, Game2DActivity.class);
            startActivity(mIntent);
        }

    }

}
