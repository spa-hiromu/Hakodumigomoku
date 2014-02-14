package hakodumigomoku.plane;

import com.example.hakodumigomoku.BaseActivity;

import android.os.Bundle;

public class Game2DActivity extends BaseActivity{
    Game2DView game_view ;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        game_view = new Game2DView(this);
        setContentView(game_view);
    }
}
