public class Board implements Cloneable {
    final int BOARD_WIDTH = 7;
    final int BOARD_HEIGHT = 6;

    int[] dx = {1, 1, 1, 0};
    int[] dy = {1, -1, 0, 1};

    int[][] board;
    int player;

    public Board() {
        board = new int[BOARD_WIDTH][BOARD_HEIGHT];
        player = 1;
    }

    public Board(int[][] board, int player) {
        this.board = board;
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public int get(int col, int row) {
        return board[col][row];
    }

    public boolean addTile(int column) {
        if (board[column][BOARD_HEIGHT - 1] != 0) {
            return false;
        }

        int h = 0;
        while (board[column][h] != 0) {
            h++;
        }

        board[column][h] = player;
        player = -player;
        return true;
    }

    public int checkWin(int latestColumn) {
        if (latestColumn == -1) {
            return 0;
        }
        int h = 0;
        while (h < BOARD_HEIGHT && board[latestColumn][h] != 0) {
            h++;
        }
        h--;
        int checkPlayer = board[latestColumn][h];
        boolean won = false;
        for (int k = 0; k < 4; k++) {
            for (int o = -3; o <= 0; o++) {
                boolean valid = true;
                int x = latestColumn + o * dx[k];
                int y = h + o * dy[k];
                for (int d = 0; d < 4; d++) {
                    int nx = x + d * dx[k];
                    int ny = y + d * dy[k];
                    if (isOOB(nx, ny) || board[nx][ny] != checkPlayer) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    won = true;
                }
            }
        }

        if (won) {
            return checkPlayer;
        } else {
            return 0;
        }
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int h = BOARD_HEIGHT - 1; h >= 0; h--) {
            for (int column = 0; column < BOARD_WIDTH; column++) {
                if (board[column][h] == 0) {
                    System.out.print(".");
                } else if (board[column][h] == 1) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean isOOB(int x, int y) {
        return x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT;
    }

    public Object clone() {
        return new Board(cloneBoard(), player);
    }

    private int[][] cloneBoard() {
        int[][] ret = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                ret[x][y] = board[x][y];
            }
        }
        return ret;
    }
}
