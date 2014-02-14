package hakodumigomoku.plane;

public class GameSolver {
    private static final int WIN_NUM = 5;

    /**
     * ５つ並んでいるかどうかを判定する
     * 
     * @param game_board
     *            現在の盤面
     * @param touched_position
     *            今置いた場所
     * @return true 勝利,false 継続;
     */
    protected static boolean Solver(int[] game_board, int touched_position) {
        if (rowCheck(game_board, touched_position)) {//横の並びをチェック
            return true;
        } else if (columnCheck(game_board, touched_position)) {//縦の並びをチェック
            return true;
        } else if (crossCheck(game_board, touched_position)) {//斜めの並びをチェック
            return true;
        } else {//どの並びも存在しなかったらfalseを返してゲーム継続
            return false;
        }
    }
    /**
     * 斜めの並びをチェックするメソッド
     * @param game_board
     * @param touched_position
     * @return
     */
    private static boolean crossCheck(int[] game_board, int touched_position) {
        int check_flag = game_board[touched_position];
        int win_count = 0;
        // 斜めをチェック
        win_count = 1;//現在の点が含まれる
        /*左上→右下をチェック*/
       //現在点から左上を見る
        for(int i=touched_position-13;;i-=13){
            if(game_board[i]!=check_flag){
                break;
            }else{
                win_count++;
                if(win_count==WIN_NUM){
                    return true;
                }
            }   
        }
        //現在点から右下を見る
        for(int i=touched_position+13;;i+=13){
            if(game_board[i]!=check_flag){
                break;
            }else{
                win_count++;
                if(win_count==WIN_NUM){
                    return true;
                }
            }
        }
        /*右上→左下をチェック*/
        win_count=1;//カウントを初期化
        //現在点から右上を見る
        for(int i=touched_position-11;;i-=11){
            if(game_board[i]!=check_flag){
                break;
            }else{
                win_count++;
                if(win_count==WIN_NUM){
                    return true;
                }
            }
        }
        //現在点から左下を見る
        for(int i=touched_position+11;;i+=11){
            if(game_board[i]!=check_flag){
                break;
            }else{
                win_count++;
                if(win_count==WIN_NUM){
                    return true;
                }
            }
        }
        return false;//斜めの並びが存在しなかったらfalseを返してゲーム継続
    }

    /**
     * 横に並んでいるかチェックするメソッド
     * 
     * @param game_board
     * @param touched_position
     * @return
     */
    private static boolean rowCheck(int[] game_board, int touched_position) {
        // 横をチェック
        int check_flag = game_board[touched_position];
        int win_count = 0;// カウントを初期化
        int check_row = (touched_position / 12) * 12 + 1;// チェックする行を計算する
        for (int i = check_row;; i++) {// 右側をチェック
            if (game_board[i] == check_flag) {
                win_count++;
                if (win_count == WIN_NUM) {// ５つ並んでいたらtrueを返す
                    return true;
                }
            } else {
                win_count = 0;// 違う値で遮られたらカウントを初期化
            }
            if (game_board[i] == -1) {// 右端に来たらループを抜ける
                break;
            }
        }
        return false;
    }

    /**
     * 縦に並んでいるかチェックするメソッド
     * 
     * @param game_board
     * @param touched_position
     * @return
     */
    private static boolean columnCheck(int[] game_board, int touched_position) {
        int check_flag = game_board[touched_position];
        int win_count = 0;
        // 縦をチェック
        for (int i = touched_position;; i += 12) {
            if (game_board[i] == check_flag) {
                win_count++;
                if (win_count == WIN_NUM) {// 五つ並んでいたらtrueを返す
                    return true;
                }
                // 並びがあったらループ継続
            } else {// 違う値で遮られたら終了
                break;
            }
        }
        return false;
    }
}
