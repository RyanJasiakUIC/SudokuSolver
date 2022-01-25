import java.io.File;
import java.util.Scanner;


public class SudokuBoard {

    int buttons[][];
    
    public SudokuBoard(String filename) {
        buttons = new int[9][9];
        initializeBoard(filename);
    }

    public int[][] getButtonsArray() {
        return buttons;
    }

    private void initializeBoard(String filename) {
        try {
            File f = new File ("src/main/resources/SudokuGames/" + filename);
            Scanner s = new Scanner(f);
            int x = 0, y = 0;
            while (s.hasNextInt()) {
                int n = s.nextInt();
                buttons[x][y++] = n;
                if (y == 9) {
                    y = 0;
                    x++;
                }
            }
            s.close();
        } catch (Exception e) {
            System.out.println("\nUh... didn't work ???");
            System.out.println(e.getMessage() + "\n");
        }
    }

}
