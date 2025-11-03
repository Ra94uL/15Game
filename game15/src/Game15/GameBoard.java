package Game15;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoard extends JFrame {

    private JPanel panel = new JPanel();
    private ArrayList<JButton> knappar = new ArrayList<>();

    public GameBoard() {
        panel.setLayout(new GridLayout(4, 4));

        // skapa knappar 1–15
        for (int i = 1; i <= 15; i++) {
            JButton knapp = new JButton(String.valueOf(i));
            knappar.add(knapp);
            panel.add(knapp);
            knapp.addActionListener(e -> försökFlytta(knapp));
        }

        // tom knapp
        JButton tom = new JButton("");
        knappar.add(tom);
        panel.add(tom);

        add(panel);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean ärGrannar(int index1, int index2) {
        int rad1 = index1 / 4;
        int kolumn1 = index1 % 4;
        int rad2 = index2 / 4;
        int kolumn2 = index2 % 4;

        int radSkillnad = Math.abs(rad1 - rad2);
        int kolumnSkillnad = Math.abs(kolumn1 - kolumn2);

        return (radSkillnad == 0 && kolumnSkillnad == 1) ||
                (kolumnSkillnad == 0 && radSkillnad == 1);
    }

    private void försökFlytta(JButton klickad) {
        JButton tom = null;

        // hitta tom knapp
        for (JButton k : knappar) {
            if (k.getText().isEmpty()) {
                tom = k;
                break;
            }
        }

        int indexKlickad = knappar.indexOf(klickad);
        int indexTom = knappar.indexOf(tom);

        if (ärGrannar(indexKlickad, indexTom)) {
            // byt bara text
            tom.setText(klickad.getText());
            klickad.setText("");
        }
    }

    public static void main(String[] args) {
        new GameBoard();
    }
}
