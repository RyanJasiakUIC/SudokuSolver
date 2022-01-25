import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SudokuAiSolver {
    SudokuBoard Board;
    Button GameBoxes[][];
    GridPane SubGameGrids[][];
    boolean isClickable[][];

    Deque<SudokuAiAction> AiSolutionMoves;

    public SudokuAiSolver(SudokuBoard sb, Button[][] gb, GridPane[][] sgg, boolean[][] ic) {
        Board = sb;
        GameBoxes = gb;
        SubGameGrids = sgg;
        isClickable = ic;
        AiSolutionMoves = new LinkedList<>();
    }

    public Deque<SudokuAiAction> getAiSolutionMoves() {
        return AiSolutionMoves;
    }

    private void updateGameBoxes() {
        for (int i = 0; i < 9; i++) {
            for (int j =0; j < 9; j++) {
                GameBoxes[i][j].setText(Integer.toString(Board.getButtonsArray()[i][j]));
            }
        }
    }

    public void printArray() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Board.getButtonsArray()[i][j] + " ");
            }
            d("");
        }
    }

    public void solve() {
        solveEachBoxRecursive(0, 0);
        // getListOfValidNumbers(0, 1);
        // printArray();
        // if (solveEachBoxRecursive(0, 0))
        //     updateGameBoxes();
        // System.out.println("HERE");
    }

    private void d(int x, int y) {
        System.out.println("x: " + x + ", y: " + y);
    }

    private void d(String s) {
        System.out.println(s);
    }

    public boolean solveEachBoxRecursive(int x, int y) {
        // d(x,y);
        //
        // If we're at the end, STOP
        if (x == 8 && y == 9) return true;
        else if (y == 9) {
            x++;
            y = 0;
        }
        //
        // Or, if the box already has a
        // number, just move on:
        if (!isClickable[x][y]) {
            // System.out.println("SKIP");
            return solveEachBoxRecursive(x, y+1);
        }
        //
        // Otherwise, just starting trying 
        // numbers willy nilly!
        for (int i = 1; i < 10; i++) {
            // System.out.println("Trying: " + i + ", " + x + "," + y);
            if (isValidNumberToPlace(x, y, i)) {
                Board.getButtonsArray()[x][y] = i;
                AiSolutionMoves.add(new SudokuAiAction(x, y, i));
                if (solveEachBoxRecursive(x, y+1)) {
                    return true;
                }
            }
            // else System.out.println("INVALID NUM");
            Board.getButtonsArray()[x][y] = 0;
            SudokuAiAction s = AiSolutionMoves.getLast();
            if (!(s.val == 0 && s.x == x && s.y == y))
                AiSolutionMoves.add(new SudokuAiAction(x, y, 0));
        }
        // System.out.println("FALSE, " + x + "," + y);
        return false;
    }

    public boolean isValidNumberToPlace(int x, int y, int n) {
        if (numberExistsInSquare(x, y, n)) return false;
        if (numberExistsInRow(x, n)) return false;
        if (numberExistsInColumn(y, n)) return false;
        return true;
    }

    
    private boolean numberExistsInColumn(int col, int n) {
        for (int x = 0; x < 9; x++)
            if (Board.getButtonsArray()[x][col] == n) {
                // d("col exists, "+ x+","+col);
                return true;
            }
        return false;
    }

   private boolean numberExistsInRow(int row, int n) {
       for (int y = 0; y < 9; y++)
           if (Board.getButtonsArray()[row][y] == n) {
                // d("row exists, "+ row+","+y);
                return true;
           }
       return false;
   }

    private boolean numberExistsInSquare(int x, int y, int n) {
        x-=x%3;
        y-=y%3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (Board.getButtonsArray()[i+x][j+y] == n)  {
                    // d("square exists, "+ (i+x)+","+(j+y));
                    return true;
                }
        return false;
    }
}
