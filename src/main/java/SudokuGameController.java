import java.net.URL;
import java.util.Deque;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;


public class SudokuGameController implements Initializable {

    //
    // Sizing Attributes:

    private final int GAME_BUTTON_SIZE = 65;

    //
    // Styling Classes:
    private final String NOT_EDITABLE_BOX_SELECTED = "selectedButtonRed";
    private final String NOT_EDITABLE_BOX = "notClickableButton";
    private final String EDITABLE_BOX = "button";
    private final String EDITABLE_BOX__HOVER = "button:hover";
    private final String EDITABLE_BOX_PRESSED_HOVER = "button:pressed:hover";
    private final String EDITABLE_BOX_SELECTED = "selectedButton";
    private final String EDITABLE_BOX_CORESPONDING = "buttonCorresponding";
    private final String NOT_EDITABLE_BOX_CORESPONDING = "notClickableButtonCorresponding";

    //
    // FXML Variables:

    @FXML
    GridPane GameGrid;




    //
    // Non-FXML Variables:

    SudokuBoard Board;
    Button GameBoxes[][];
    GridPane SubGameGrids[][];
    boolean isClickable[][];
    boolean selectedButtonIsEditable;

    Button selectedButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Board = new SudokuBoard("game1.txt");
        GameBoxes = new Button[9][9];
        SubGameGrids = new GridPane[3][3];
        isClickable = new boolean[9][9];
        initializeBoardGUI();
        selectedButton = null;
    }

    private void initializeBoardGUI() {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                GameGrid.add(buildSubGrid(x * 3, y * 3), y, x);
    }

    private GridPane buildSubGrid(int _x, int _y) {
        int arr[][] = Board.getButtonsArray();
        GridPane gp = new GridPane();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                String s = (arr[x+_x][y+_y] == 0) ? "" : Integer.toString(arr[x+_x][y+_y]);
                Button b = new Button(s);
                isClickable[x+_x][y+_y] = (s == "");
                if (s != "") {
                    b.getStyleClass().clear();
                    b.getStyleClass().add("notClickableButton");
                }
                setButtonAttributes(b);
                GameBoxes[x+_x][y+_y] = b;
                b.setOnAction(e -> sudokuButtonAction(b));
                gp.add(b, y, x);
            }
        }
        return gp;
    }

    private void setButtonAttributes(Button b) {
        b.setMinHeight(GAME_BUTTON_SIZE);
        b.setMaxHeight(GAME_BUTTON_SIZE);
        b.setMinWidth(GAME_BUTTON_SIZE);
        b.setMaxWidth(GAME_BUTTON_SIZE);
    }

    private void disableCorrespondingBoxes(Button b) {
        int x = 0, y = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (GameBoxes[i][j] == b) {
                    x = i;
                    y = j;
                }
            }
        }
        for (int col = 0; col < 9; col++) {
            if (col != y) {
                GameBoxes[x][col].getStyleClass().remove(
                    (isClickable[x][col]) ? EDITABLE_BOX_CORESPONDING : NOT_EDITABLE_BOX_CORESPONDING);
            }
        }
        for (int row = 0; row < 9; row++) {
            if (row != x)
            GameBoxes[row][y].getStyleClass().remove(
                (isClickable[row][y]) ? EDITABLE_BOX_CORESPONDING : NOT_EDITABLE_BOX_CORESPONDING);
        }
        int a = x - (x % 3);
        int c = y - (y % 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!((x == a+i) && (y == c+j)))
                GameBoxes[a+i][c+j].getStyleClass().remove(
                    (isClickable[a+i][c+j]) ? EDITABLE_BOX_CORESPONDING : NOT_EDITABLE_BOX_CORESPONDING);
            }
        }
    }

    private void enableCorrespondingBoxes(Button b) {
        int x = 0, y = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (GameBoxes[i][j] == b) {
                    x = i;
                    y = j;
                }
            }
        }
        for (int col = 0; col < 9; col++) {
            if (col != y) {
                GameBoxes[x][col].getStyleClass().add(
                    (isClickable[x][col]) ? EDITABLE_BOX_CORESPONDING : NOT_EDITABLE_BOX_CORESPONDING);
            }
        }
        for (int row = 0; row < 9; row++) {
            if (row != x)
            GameBoxes[row][y].getStyleClass().add(
                (isClickable[row][y]) ? EDITABLE_BOX_CORESPONDING : NOT_EDITABLE_BOX_CORESPONDING);
        }
        int a = x - (x % 3);
        int c = y - (y % 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!((x == a+i) && (y == c+j)))
                GameBoxes[a+i][c+j].getStyleClass().add(
                    (isClickable[a+i][c+j]) ? EDITABLE_BOX_CORESPONDING : NOT_EDITABLE_BOX_CORESPONDING);
            }
        }
    }

    // private void updateCorresponding

    private void sudokuButtonAction(Button b) {
        if (selectedButton != null) {
            disableCorrespondingBoxes(selectedButton);
            if (buttonIsClickable(selectedButton)) {
                selectedButton.getStyleClass().remove(EDITABLE_BOX_SELECTED);
                selectedButton.getStyleClass().add(EDITABLE_BOX);
            } else {
                selectedButton.getStyleClass().remove(NOT_EDITABLE_BOX_SELECTED);
                selectedButton.getStyleClass().add(NOT_EDITABLE_BOX);
            }
        }
        if (buttonIsClickable(b)) {
            selectedButtonIsEditable = true;
            b.getStyleClass().remove(EDITABLE_BOX);
            b.getStyleClass().add(EDITABLE_BOX_SELECTED);
        } else {
            selectedButtonIsEditable = false;
            b.getStyleClass().remove(NOT_EDITABLE_BOX);
            b.getStyleClass().add(NOT_EDITABLE_BOX_SELECTED);
        }
        selectedButton = b;
        enableCorrespondingBoxes(b);
    }

    private boolean buttonIsClickable(Button b) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (GameBoxes[x][y] == b) {
                    return (isClickable[x][y]);
                }
            }
        }
        return false;
    }


    private void setButtonDigit(String s) {
        if (selectedButton != null && selectedButtonIsEditable)
            selectedButton.setText((Integer.parseInt(s) > 0) ? s : "");
    }

    private void changeSelectedButton(int x, int y) {
        int cur_x = 0, cur_y = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (selectedButton == GameBoxes[i][j]) {
                    cur_x = i;
                    cur_y = j;
                    break;
                }
            }
        }
        if (!isInBounds(x+cur_x, y+cur_y)) return;
        sudokuButtonAction(GameBoxes[cur_x+x][cur_y+y]);
    }

    private boolean isInBounds(int x, int y) {
        return !(x > 8 || x < 0 || y > 8 || y < 0);
    }

    private void cleanButtons() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (isClickable[i][j]) GameBoxes[i][j].setText("");
    }

    private void animateAiSolution(Deque<SudokuAiAction> AiSolutionMoves) {
        PauseTransition pause = new PauseTransition(Duration.millis(50));
        pause.setOnFinished(e -> {
            SudokuAiAction action = AiSolutionMoves.poll();
            if (action != null) {
                sudokuButtonAction(GameBoxes[action.x][action.y]);
                GameBoxes[action.x][action.y].setText((action.val == 0) ? "" : Integer.toString(action.val));
                animateAiSolution(AiSolutionMoves);
            }
        });
        pause.play();
    }

    //
    // Handle KeyEvents
    //
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode().isDigitKey()) setButtonDigit(event.getCode().getChar());
        else {
            if (selectedButton == null) return;
            switch (event.getCode()) {
                case W: changeSelectedButton(-1, 0); break;
                case S: changeSelectedButton(1, 0); break;
                case A: changeSelectedButton(0, -1); break;
                case D: changeSelectedButton(0, 1); break;
                case G: {
                    cleanButtons();
                    SudokuAiSolver s = new SudokuAiSolver(Board, GameBoxes, SubGameGrids, isClickable);
                    s.solve();
                    animateAiSolution(s.getAiSolutionMoves());
                    break;
                }
            }
        }
    }

}
