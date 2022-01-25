import javafx.scene.control.*;

public class SudokuButton {
    
    private SudokuBoard owner;
    private int val;

    SudokuButton(SudokuBoard _owner, int _val) {
        super();
        this.val = _val;
        this.owner = _owner;

    }

    public int getVal() {
        return val;
    }

}
