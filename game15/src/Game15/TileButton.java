package Game15;
import javax.swing.JButton;

//En knapp som representerar en spelbricka.
public class TileButton extends JButton {
    private int index;

    public TileButton(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}