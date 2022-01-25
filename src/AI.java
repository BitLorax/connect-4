public class AI {

    int[] dx = {1, 1, 1, 0, 0, -1, -1 ,-1};
    int[] dy = {1, 0, -1, 1, -1, 1, 0, -1};
    int[] checkOrder = {3, 4, 2, 5, 1, 6, 0};

    int player;

    public AI(int player) {
        this.player = player;
    }

    public int getMove(Board board) {
        return minimax(board, -1000000, 1000000, -1, true, 10)[0];
    }

    private int[] minimax(Board board, int alpha, int beta, int lastMove, boolean maximizing, int depth) {
        int won = board.checkWin(lastMove);
        if (won != 0) {
            if (won == 1) {
                return new int[] {-1, -100000000};
            } else {
                return new int[] {-1, 100000000};
            }
        }
        if (depth == 0) {
            return new int[] {-1, getScore(board)};
        }
        int ret = 0;
        if (maximizing) {
            int retScore = -1000;
            for (int i = 0 ; i < board.BOARD_WIDTH; i++) {
                int move = checkOrder[i];
                Board newBoard = (Board)board.clone();
                if (newBoard.addTile(move)) {
                    int[] res = minimax(newBoard, alpha, beta, move, !maximizing, depth - 1);
                    if (res[1] > retScore) {
                        ret = move;
                        retScore = res[1];
                    }
                    alpha = Math.max(alpha, res[1]);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            return new int[] {ret, retScore};
        } else {
            int retScore = 1000;
            for (int i = 0 ; i < board.BOARD_WIDTH; i++) {
                int move = checkOrder[i];
                Board newBoard = (Board)board.clone();
                if (newBoard.addTile(move)) {
                    int[] res = minimax(newBoard, alpha, beta, move, !maximizing, depth - 1);
                    if (res[1] < retScore) {
                        ret = move;
                        retScore = res[1];
                    }
                    beta = Math.min(beta, res[1]);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            return new int[] {ret, retScore};
        }
    }

    private int getScore(Board board) {
        int ret = 0;
        for (int col = 0; col < board.BOARD_WIDTH; col++) {
            for (int row = 0; row < board.BOARD_HEIGHT; row++) {
                if (board.get(col, row) == 0) {
                    continue;
                }
                for (int k = 0; k < 8; k++) {
                    int len = 0;
                    boolean free = true;
                    for (int d = 0; d < 4; d++) {
                        int nx = col + d * dx[k];
                        int ny = row + d * dy[k];
                        if (board.isOOB(nx, ny)) {
                            break;
                        }
                        if (board.get(nx, ny) == board.get(col, row)) {
                            len += 1;
                        } else if (board.get(nx, ny) == -board.get(col, row)) {
                            free = false;
                            break;
                        }
                    }
                    if (free) {
                        if (board.get(col, row) == player) {
                            if (len == 2) {
                                ret += 2;
                            } else if (len == 3) {
                                ret += 5;
                            } else if (len == 4) {
                                ret += 1000;
                            }
                        } else {
                            if (len == 3) {
                                ret -= 4;
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }
}
