
package hakodumigomoku.plane;

import com.example.hakodumigomoku.R;
import com.example.hakodumigomoku.R.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class Game2DView extends View {
    // 画像を用意
    private Resources res = this.getContext().getResources();
    private final Bitmap IMG_BOARD = BitmapFactory.decodeResource(res,
            R.drawable.board);
    private final Bitmap IMG_CIRCLE = BitmapFactory.decodeResource(res,
            R.drawable.circle);
    private final Bitmap IMG_CROSS = BitmapFactory.decodeResource(res,
            R.drawable.cross);
    private final Bitmap IMG_SELECTED_TILE = BitmapFactory.decodeResource(res,
            R.drawable.select_tile);
    // 画面サイズ
    private int Width;
    private int Height;
    // 盤面表現用の変数
    private Paint paint = new Paint();
    private static final int ROW = 10;
    private static final int CELL_LINE = 12;
    private static final int CELLS = (CELL_LINE) * (CELL_LINE);// 升目の数
    private int CELL_SIZE;// １マスのサイズ
    private int BOARD_EDGE;// 盤面の一辺の長さ
    private int[] game_board = new int[CELLS];// 余白含め12*12のボードとして扱う(1マス40px*40px)
    private int turn_flag;
    // マスの管理
    private static final int UP_AND_UNDER = 12;
    // 分岐用の変数
    private static final int NOT_CELL = -1;
    private static final int VOID_CELL = 0;
    private static final int FIRST_PLAYER = 1;
    private static final int SECOND_PLAYER = 2;// 二人対専用
    private static final int COM_PLAYER = 3;// COM対戦用
    private static final int SELECTED_CELL = 4;
    private int finish_flag;
    // Activityからcontextを受け取る
    private Context context;

    public Game2DView(Context context) {
        super(context);
        this.context = context;
        getDisplaySize();
        gameBoardInit();// 盤面を初期化
        turn_flag = FIRST_PLAYER;
    }

    /**
     * 画面サイズを取得するメソッド
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void getDisplaySize() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            disp.getSize(size);
            Width = size.x;
            Height = size.y;
        } else {

            Width = disp.getWidth();
            Height = disp.getHeight();
        }
    }

    /**
     * ゲームを管理する配列を初期化する
     */
    private void gameBoardInit() {
        BOARD_EDGE = Width;
        CELL_SIZE = Width / 12;

        for (int i = 0; i < CELLS; i++) {
            if (i < 12 || i > 132 || i % 12 == 0 || i % 12 == 11) {// 画像の余白 上 ||
                                                                   // 下　|| 左 ||
                                                                   // 右　を指定不可に
                game_board[i] = NOT_CELL;
            } else {
                game_board[i] = VOID_CELL;
            }
        }
    }

    /**
     * 画面の描画
     */
    public void onDraw(Canvas canvas) {
        // ボードを描画
        canvas.drawBitmap(IMG_BOARD, new Rect(0, 0, IMG_BOARD.getWidth(), IMG_BOARD.getHeight()),
                new Rect(0, 0, Width, Width), paint);
        for (int i = 14; i <= 131; i++) {
            if (game_board[i] == FIRST_PLAYER)
                canvas.drawBitmap(IMG_CIRCLE,
                        new Rect(0, 0, IMG_CIRCLE.getWidth(), IMG_CIRCLE.getHeight()), new Rect(
                                CELL_SIZE * (i % CELL_LINE), CELL_SIZE * (i / CELL_LINE), CELL_SIZE
                                        * (i % CELL_LINE) + CELL_SIZE, CELL_SIZE * (i / CELL_LINE)
                                        + CELL_SIZE), paint);
            if (game_board[i] == SECOND_PLAYER)
                canvas.drawBitmap(IMG_CROSS,
                        new Rect(0, 0, IMG_CROSS.getWidth(), IMG_CROSS.getHeight()), new Rect(
                                CELL_SIZE * (i % CELL_LINE), CELL_SIZE * (i / CELL_LINE), CELL_SIZE
                                        * (i % CELL_LINE) + CELL_SIZE, CELL_SIZE * (i / CELL_LINE)
                                        + CELL_SIZE), paint);
            if (game_board[i] == SELECTED_CELL)
                canvas.drawBitmap(
                        IMG_SELECTED_TILE,
                        new Rect(0, 0, IMG_SELECTED_TILE.getWidth(), IMG_SELECTED_TILE.getHeight()),
                        new Rect(
                                CELL_SIZE * (i % CELL_LINE), CELL_SIZE * (i / CELL_LINE), CELL_SIZE
                                        * (i % CELL_LINE) + CELL_SIZE, CELL_SIZE * (i / CELL_LINE)
                                        + CELL_SIZE), paint);
        }
        invalidate();
    }

    /**
     * タッチ動作の処理
     */
    public boolean onTouchEvent(MotionEvent event) {
        /* タッチ位置取得 */
        int touch_x = (int) (event.getX() / CELL_SIZE);// タッチされたパネルが何列目かを調べる
        Log.d("pointX", String.valueOf(event.getX()));
        Log.d("pointY", String.valueOf(event.getY()));
        int touch_y = (int) (event.getY() / CELL_SIZE);// タッチされたパネルが何行目かを調べる
        int touched_position = touch_x + (touch_y * (ROW + 2));// タッチされた座標を升目に変換

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /* 終了判定 */
            if (finish_flag == 1) {
                ((Game2DActivity) context).finish();
            }
            /* 位置判定 */
            if (touched_position > CELLS) {// 盤面外
                Toast.makeText(context, "そこには置けません", Toast.LENGTH_SHORT).show();
            } else if (game_board[touched_position] == VOID_CELL) {// マス目を選択
                checkCells();// 他に選択されているマスがないかチェック
                if (setCellCheck(touched_position)) {// 本当に置けるセルかチェックする
                    game_board[touched_position] = SELECTED_CELL;
                }
            } else if (game_board[touched_position] == SELECTED_CELL) {// 選択されているセルをタッチすると○、×を置く
                if (turn_flag == FIRST_PLAYER) {// 先攻プレイヤ
                    game_board[touched_position] = FIRST_PLAYER;
                    if (GameSolver.Solver(game_board, touched_position)) {
                        Toast.makeText(context, "PLAYER1の勝ちです。",
                                Toast.LENGTH_SHORT).show();
                        finish_flag = 1;

                    }
                    turn_flag = SECOND_PLAYER;
                } else if (turn_flag == SECOND_PLAYER) {// 後攻プレイヤ
                    game_board[touched_position] = SECOND_PLAYER;
                    if (GameSolver.Solver(game_board, touched_position)) {
                        Toast.makeText(context, "PLAYER2の勝ちです。",
                                Toast.LENGTH_SHORT).show();
                        finish_flag = 1;

                    }
                    turn_flag = FIRST_PLAYER;
                }
            } else {// 盤面余白
                Toast.makeText(context, "そこには置けません", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    /**
     * すでに選択されているマスがないかチェックし、存在すれば選択を解除する
     */
    private void checkCells() {
        for (int i = 0; i < CELLS; i++) {
            if (game_board[i] == SELECTED_CELL) {
                game_board[i] = VOID_CELL;
            }
        }
    }

    /**
     * 選択されたマスは置けるのかチェックする。
     * 
     * @param touched_position タッチされたパネルのインデックス
     * @return true 置ける, false 置けないs
     */
    private boolean setCellCheck(int touched_position) {
        if (game_board[touched_position + UP_AND_UNDER] == VOID_CELL) {// 一つ下のマスが空だと置けない
            Toast.makeText(context, "そこには置けません", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
