import java.util.BitSet;

public class Board implements Cloneable {
    final int BOARD_WIDTH = 7;
    final int BOARD_HEIGHT = 6;

    int[] dx = {1, 1, 1, 0};
    int[] dy = {1, -1, 0, 1};

    long player;
    long mask;
    int turn;
    int[] height;

    public Board() {
        player = 0;
        mask = 0;
        turn = 1;
        height = new int[BOARD_WIDTH];
    }

    public Board(long player, long mask, int turn, int[] height) {
        this.player = player;
        this.mask = mask;
        this.turn = turn;
        this.height = height;
    }

    public int getTurn() {
        return turn;
    }

//    public int get(int col, int row) {
//        return board[col][row];
//    }

    public boolean addTile(int column) {
        if (height[column] == BOARD_HEIGHT) {
            return false;
        }

        long l = (1L << (7 * column + height[column]));
        mask = mask | l;
        if (turn == 1) {
            player = player | l;
        }
        height[column]++;
        turn = -turn;
        return true;
    }

    public int checkWin() {
        if (checkTurnWin(1)) {
            return 1;
        }
        if (checkTurnWin(-1)) {
            return -1;
        }
        return 0;
    }

    private boolean checkTurnWin(int turn) {
        long check;
        long l;
        if (turn == 1) {
            check = player;
        } else {
            check = player ^ mask;
        }

        l = check & (check >> 7);
        if ((l & (l >> 14)) != 0) {
            return true;
        }

        l = check & (check >> 1);
        if ((l & (l >> 2)) != 0) {
            return true;
        }

        l = check & (check >> 6);
        if ((l & (l >> 12)) != 0) {
            return true;
        }

        l = check & (check >> 8);
        if ((l & (l >> 16)) != 0) {
            return true;
        }

        return false;
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        String sp = String.format("%" + BOARD_WIDTH * (BOARD_HEIGHT + 1) + "s", Long.toBinaryString(player)).replaceAll(" ", "0");
        String sm = String.format("%" + BOARD_WIDTH * (BOARD_HEIGHT + 1) + "s", Long.toBinaryString(mask)).replaceAll(" ", "0");
        int[][] board = new int[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = sp.length() - 1; i >= 0; i--) {
            int j = (sp.length() - 1 - i);
            int x = j / 7;
            int y = j % 7;
            if (y >= BOARD_HEIGHT) {
                continue;
            }

            if (sm.charAt(i) == '0') {
                board[x][y] = 0;
            } else if (sp.charAt(i) == '1') {
                board[x][y] = 1;
            } else {
                board[x][y] = -1;
            }
        }

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
        return new Board(player, mask, turn, height.clone());
    }
}
