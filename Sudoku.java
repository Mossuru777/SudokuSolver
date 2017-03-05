public class Sudoku {
    private int[][] board = new int[9][9];

    // frow, fcol, fboxは
    // index番目のrow,col,boxでの、0〜9の使用可否のflag配列
    // (ただし0は使用せず、dummy)
    private boolean[][] frow = new boolean[9][10];
    private boolean[][] fcol = new boolean[9][10];
    private boolean[][] fbox = new boolean[9][10];

    public Sudoku(int[][] board) {
        if (board.length != 9) {
            throw new IllegalArgumentException("boardは9*9の2次元配列で渡してください");
        }
        for (int i = 0; i < 9; i++) {
            if (board[i].length != 9) {
                throw new IllegalArgumentException("boardは9*9の2次元配列で渡してください");
            }
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    private int getbox(int b, int j) {
        int y = 3 * (b / 3) + j / 3;
        int x = 3 * (b % 3) + j % 3;
        return board[y][x];
    }


    // フラグを正しい状態にセット
    private void makef() {
        // 数字使用可否flag配列をすべてtrueにセット
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j <= 9; j++) {
                frow[i][j] = true;
                fcol[i][j] = true;
                fbox[i][j] = true;
            }
        }

        // boardを見て、すでに埋まっている数字のflagをfalseにセット
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                frow[i][board[i][j]] = false;
                fcol[i][board[j][i]] = false;
                fbox[i][getbox(i, j)] = false;
            }
        }
    }

    // r, c に numを置くことができるかどうかを返す
    private boolean canPut(int r, int c, int num) {
        int b = 3 * (r / 3) + (c / 3);
        return frow[r][num] && fcol[c][num] && fbox[b][num];
    }

    // r, c に 値を入れる
    private int kakutei(int r, int c) {
        int b = 3 * (r / 3) + (c / 3);
        int count = 0;
        int x = 0;
        for (int num = 1; num <= 9; num++) {
            if (canPut(r, c, num)) {
                count++;
                x = num;
            }
        }
        if (count == 1) return x; // 使える数字が１つだけ
        for (int num = 1; num <= 9; num++) {
            if (canPut(r, c, num)) {
                // 行でnumが使えるのはそこだけ？
                count = 0;
                for (int j = 0; j < 9; j++) {
                    if (board[r][j] > 0) continue;
                    if (canPut(r, j, num)) count++;
                }
                if (count == 1) return num;

                // 列でnumが使えるのはそこだけ？
                count = 0;
                for (int j = 0; j < 9; j++) {
                    if (board[j][c] > 0) continue;
                    if (canPut(j, c, num)) count++;
                }
                if (count == 1) return num;

                // 箱でnumが使えるのはそこだけ？
                count = 0;
                for (int j = 0; j < 9; j++) {
                    int by = 3 * (b / 3) + j / 3;
                    int bx = 3 * (b % 3) + j % 3;
                    if (board[by][bx] > 0) continue;
                    if (canPut(by, bx, num)) count++;
                }
                if (count == 1) return num;
            }
        }
        return 0;
    }

    // 埋められるところを一つだけ埋める。成功したらtrueを返す
    private boolean fill() {
        makef();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) continue;
                int x = kakutei(i, j);
                if (x > 0) {
                    board[i][j] = x;
                    return true;
                }
            }
        }
        return false;
    }

    // 仮定を用いずに埋められるだけ埋めて、全部埋まったらtrueを返す
    private boolean fillup() {
        while (fill()) ;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    // r, cに入れられる数字の数を返す
    private int numpos(int r, int c) {
        int count = 0;
        for (int num = 1; num <= 9; num++) {
            if (canPut(r, c, num)) count++;
        }
        return count;
    }

    // 解く 解けたらtrue, 解けなかったらfalse
    public boolean solve() {
        // まずは埋められるだけ埋める
        if (fillup()) return true;
        // だめだったら最も可能性の少ないセルを探す -> r, c
        int r = -1, c = -1;
        int min = 999;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] > 0) continue;
                int x = numpos(i, j);
                if (x < min) {
                    r = i;
                    c = j;
                    min = x;
                }
            }
        }
        for (int num = 1; num <= 9; num++) {
            if (canPut(r, c, num)) {
                Sudoku sd = new Sudoku(board);
                sd.board[r][c] = num;
                if (sd.solve()) {
                    board = sd.board;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(board[i][j]);
            }
            sb.append(String.format("%n"));
        }
        return sb.toString();
    }
}
