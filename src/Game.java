import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Player vs Computer");
        System.out.println("2. Player vs Player");
        System.out.print("Select mode: ");
        String mode = sc.nextLine();
        while (!mode.equals("1") && !mode.equals("2")) {
            System.out.println("Invalid mode.");
        }
        System.out.println();

        if (mode.equals("1")) {
            pvc(sc);
        } else {
            pvp(sc);
        }
    }

    private static void pvp(Scanner sc) {
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

    private static void pvc(Scanner sc) {
        Board board = new Board();
        System.out.print("Player moves first? (y/N) ");
        String first = sc.nextLine();
        AI ai;
        if (first.equals("y")) {
            ai = new AI(-1);
        } else {
            ai = new AI(1);
        }
        while (true) {
            if (first.equals("y")) {
                if (playerMove(board, sc, "Player (X)")) {
                    break;
                }
                if (aiMove(board, ai)) {
                    break;
                }
            } else {
                if (aiMove(board, ai)) {
                    break;
                }
                if (playerMove(board, sc, "Player (O)")) {
                    break;
                }
            }
        }
    }

    private static boolean playerMove(Board board, Scanner sc, String playerName) {
        System.out.println();
        board.printBoard();
        System.out.println(playerName + "'s move. ");

        int column = getPlayerMove(sc);
        while (column < 0 || column >= Board.BOARD_WIDTH || !board.addTile(column)) {
            System.out.println("Invalid move.");
            column = getPlayerMove(sc);
        }
        if (board.checkWin() != 0) {
            board.printBoard();
            System.out.println(playerName + " wins.");
            return true;
        }
        return false;
    }

    private static int getPlayerMove(Scanner sc) {
        String inp = sc.nextLine();
        int column;
        while (true) {
            try {
                column = Integer.parseInt(inp);
                return column;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                inp = sc.nextLine();
            }
        }

    }

    private static boolean aiMove(Board board, AI ai) {
        System.out.println();
        board.printBoard();
        System.out.println("Computer's move...");

        int aiMove = ai.getMove(board);
        board.addTile(aiMove);
        if (board.checkWin() != 0) {
            board.printBoard();
            System.out.println("Computer wins.");
            return true;
        }
        return false;
    }
}
