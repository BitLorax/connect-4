import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        pVc(sc);
    }

    private static void pVp(Scanner sc) {
        Board board = new Board();
        while (true) {
            if (playerMove(board, sc, "Player 1 (X)")) {
                break;
            }
            if (playerMove(board, sc, "Player 2 (O)")) {
                break;
            }
        }
    }

    private static void pVc(Scanner sc) {
        Board board = new Board();
        System.out.println("Player first? (y/N)");
        String first = sc.nextLine();
        AI ai = new AI(-1);
        while (true) {
            if (first.equals("y")) {
                if (playerMove(board, sc, "Player")) {
                    break;
                }
                if (aiMove(board, ai)) {
                    break;
                }
            } else {
                if (aiMove(board, ai)) {
                    break;
                }
                if (playerMove(board, sc, "Player")) {
                    break;
                }
            }
        }
    }

    private static boolean playerMove(Board board, Scanner sc, String playerName) {
        System.out.println();
        board.printBoard();
        System.out.println(playerName + "'s move. ");
        int column = Integer.parseInt(sc.nextLine());
        while (!board.addTile(column)) {
            System.out.println("Invalid move.");
            column = Integer.parseInt(sc.nextLine());
        }
        if (board.checkWin(column) != 0) {
            board.printBoard();
            System.out.println(playerName + " wins.");
            return true;
        }
        return false;
    }

    private static boolean aiMove(Board board, AI ai) {
        int aiMove = ai.getMove(board);
        board.addTile(aiMove);
        if (board.checkWin(aiMove) != 0) {
            board.printBoard();
            System.out.println("Computer wins.");
            return true;
        }
        return false;
    }
}
