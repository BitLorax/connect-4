public class AI {

    int[] dx = {1, 1, 1, 0, 0, -1, -1 ,-1};
    int[] dy = {1, 0, -1, 1, -1, 1, 0, -1};
    int[] checkOrder = {3, 4, 2, 5, 1, 6, 0};

    int player;

    public AI(int player) {
        this.player = player;
    }

    public int getMove(Board board) {
        return minimax(board, -1000000, 1000000, true, 12, 0)[0];
    }

    private int[] minimax(Board board, int alpha, int beta, boolean maximizing, int depth, int turns) {
        int won = board.checkWin();
        if (won != 0) {
            if (won == player) {
                return new int[] {-1, (22 - turns)};
            } else {
                return new int[] {-1, -(22 - turns)};
            }
        }
        if (depth == 0) {
            return new int[] {-1, 0};
        }
        int ret = 0;
        if (maximizing) {
            int retScore = -1000;
            for (int i = 0 ; i < board.BOARD_WIDTH; i++) {
                int move = checkOrder[i];
                Board newBoard = (Board)board.clone();
                if (newBoard.addTile(move)) {
                    int[] res = minimax(newBoard, alpha, beta, !maximizing, depth - 1, turns + 1);
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
                    int[] res = minimax(newBoard, alpha, beta, !maximizing, depth - 1, turns);
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
}
