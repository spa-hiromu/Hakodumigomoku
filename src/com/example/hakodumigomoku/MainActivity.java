package com.example.hakodumigomoku;

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

    Button battle_button;//対人対戦モードを呼び出すボタン
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        battle_button=(Button)findViewById(R.id.buttle_button);

        battle_button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent battleintent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(battleintent);
            

    }

}
