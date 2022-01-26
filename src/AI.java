import java.util.HashMap;
import java.util.Map;

public class AI {
    int player;
    Map<Long, Integer> lookup;

    public AI(int player) {
        this.player = player;
        this.lookup = new HashMap<>();
    }

    public int getMove(Board board) {
        return minimax(board, -1000000, 1000000, 16, 0)[0];
    }

    private int[] minimax(Board board, int alpha, int beta, int depth, int turns) {
        if (lookup.containsKey(board.getHash())) {
            int res = lookup.get(board.getHash());
            return decode(res);
        }
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
        int retScore;
        if (depth % 2 == 0) {
            retScore = -1000;
            for (int i = 0; i < board.BOARD_WIDTH; i++) {
                int move = calcMove(i, board);
                Board newBoard = (Board)board.clone();
                if (newBoard.addTile(move)) {
                    int[] res = minimax(newBoard, alpha, beta, depth - 1, turns + 1);
                    if (res[1] > 0) {
                        lookup.put(board.getHash(), encode(ret, retScore));
                        return new int[] {ret, retScore};
                    }
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
        } else {
            retScore = 1000;
            for (int i = 0 ; i < board.BOARD_WIDTH; i++) {
                int move = calcMove(i, board);
                Board newBoard = (Board)board.clone();
                if (newBoard.addTile(move)) {
                    int[] res = minimax(newBoard, alpha, beta, depth - 1, turns);
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
        }
        lookup.put(board.getHash(), encode(ret, retScore));
        return new int[] {ret, retScore};
    }

    private int encode(int ret, int retScore) {
        if (retScore < 0) {
            return -(1000 * ret + -1 * retScore);
        } else {
            return 1000 * ret + retScore;
        }
    }

    private int[] decode(int hash) {
        if (hash < 0) {
            return new int[] {-hash / 1000, -(-hash % 1000)};
        } else {
            return new int[] {hash / 1000, hash % 1000};
        }
    }

    private int calcMove(int i, Board board) {
        if (i % 2 == 0) {
            return board.BOARD_WIDTH / 2 + i / 2;
        } else {
            return board.BOARD_WIDTH / 2 - (i + 1) / 2;
        }
    }
}
