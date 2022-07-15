import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuResolver {
    private static int solutions = 0;
    private static int[][] board = {
            {4, 1, 5, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 7, 0, 4},
            {0, 6, 0, 1, 0, 4, 0, 5, 0},
            {0, 7, 0, 0, 0, 6, 0, 0, 1},
            {0, 0, 3, 0, 1, 0, 6, 0, 0},
            {1, 0, 0, 7, 0, 0, 0, 9, 0},
            {0, 3, 0, 2, 0, 8, 0, 4, 0},
            {8, 0, 1, 5, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 4, 0, 9, 7, 8},
    };

    public static void main(String[] args) {
        solve(board);
        System.out.println("Total solutions: "+ solutions);
    }

    private static boolean solve(int[][] board) {
        if (isSolved(board)) {
            solutions++;
            displayBoard(board);
            return true;
        } else {
            int rootX = 0;
            int rootY = 0;
            int min = 10;
            for (int i = 0 ; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[j][i] == 0) {
                        int temp = getPossibleMoves(board, i, j).size();
                        if (temp < min) {
                            min = temp;
                            rootX = i;
                            rootY = j;
                        }
                    }
                }
            }
            Set<Integer> possibleMoves = getPossibleMoves(board, rootX, rootY);
            for (int move : possibleMoves) {
                board[rootY][rootX] = move;
                if (solve(board)) {
                    // break if any found
                    // return true;
                }
                board[rootY][rootX] = 0;
            }
            return false;
        }
    }

    private static void displayBoard(int[][] board) {
        System.out.println("Solution No." + solutions);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((j % 3) == 2) {
                    System.out.print(board[i][j] + "|");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            if ((i % 3) == 2) {
                System.out.println("\n------------------");
            } else {
                System.out.println();
            }
        }
    }
    
    private static Set<Integer> getPossibleMoves(int[][] onGoingBoard, int x, int y) {
        Set<Integer> possibleMoves = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toSet());

        // check rows
        for (int i = 0; i < 9; i++) {
            if (onGoingBoard[y][i] != 0) {
                possibleMoves.remove(onGoingBoard[y][i]);
            }
        }

        // check columns
        for (int i = 0; i < 9; i++) {
            if (onGoingBoard[i][x] != 0) {
                possibleMoves.remove(onGoingBoard[i][x]);

            }
        }
        // check mini square 3x3
        int groupX = x / 3;
        int groupY = y / 3;
        for (int i = groupX * 3; i < (groupX + 1) * 3; i++) {
            for (int j = groupY * 3; j < (groupY + 1) * 3; j++) {
                if (onGoingBoard[j][i] != 0) {
                    possibleMoves.remove(onGoingBoard[j][i]);

                }
            }
        }

        return possibleMoves;
    }

    private static boolean isSolved(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j]  == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}