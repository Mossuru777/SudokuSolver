public class Main {
    public static void main(String[] args) {
        int[][] ex = {
                {3, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 7, 0, 0, 0},
                {7, 0, 6, 0, 3, 0, 5, 0, 0},
                {0, 7, 0, 0, 0, 9, 0, 8, 0},
                {9, 0, 0, 0, 2, 0, 0, 0, 4},
                {0, 1, 0, 8, 0, 0, 0, 5, 0},
                {0, 0, 9, 0, 4, 0, 3, 0, 1},
                {0, 0, 0, 7, 0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0, 8, 0, 0, 6},
        };

        Sudoku sudoku = new Sudoku(ex);

        if (sudoku.solve()) {
            System.out.println("*** 解けた ***");
        } else {
            System.out.println("*** 解けなかった ***");
        }

        System.out.println(sudoku);
    }
}