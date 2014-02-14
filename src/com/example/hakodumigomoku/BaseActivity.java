package com.example.hakodumigomoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Android標準のタイトルバーを無効にする
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
