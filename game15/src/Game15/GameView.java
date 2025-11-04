package Game15;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Hanterar GUI och kopplar modell och användarinteraktion.
// Strukturerad med buttonPanel, boardPanel och bigPanel.

public class GameView extends JFrame {
    private final GameModel model;
    private final TileButton[] buttons;
    private final JLabel movesLabel;

    public GameView(GameModel model) {
        this.model = model;
        int size = model.getSize();
        buttons = new TileButton[size];

        setTitle("15-spelet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //Skapa Panel

        JPanel bigPanel = new JPanel(new BorderLayout(10, 10)); // Huvudpanelet
        JPanel buttonPanel = new JPanel(); // För knappar & text
        JPanel boardPanel = new JPanel(new GridLayout(model.getRows(), model.getCols(), 4, 4)); // Själva spelet

        //Bygg buttonpanel

        JButton newGameBtn = new JButton("Nytt spel");
        newGameBtn.addActionListener(e -> {
            model.shuffle();
            refresh();
        });

        movesLabel = new JLabel("Drag: 0");
        buttonPanel.add(newGameBtn);
        buttonPanel.add(movesLabel);

        //Bygg boardpanel som är brädet

        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < size; i++) {
            TileButton btn = new TileButton(i);
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            btn.addActionListener(this::handleTileClick);
            buttons[i] = btn;
            boardPanel.add(btn);
        }

        //Lägg ihop allt i bigpanel
        bigPanel.add(buttonPanel, BorderLayout.NORTH);
        bigPanel.add(boardPanel, BorderLayout.CENTER);

        //Lägg bigpanel i fönstret
        add(bigPanel);

        // Visa
        refresh();
        //pack(); om det behövs för auto storlek (backup)
        setSize(600, 600);
        setLocationRelativeTo(null); // Centrerar fönstret på skärmen
        setVisible(true);
    }

    // Hanterar klick på brickor.
    private void handleTileClick(ActionEvent e) {
        TileButton btn = (TileButton) e.getSource();
        int index = btn.getIndex();

        boolean moved = model.tryMove(index);
        if (moved) {
            refresh();
            if (model.isSolved()) {
                JOptionPane.showMessageDialog(this, "Grattis, du vann!", "Vinnare",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //Uppdaterar alla knappars utseende baserat på modellen.
    private void refresh() {
        for (int i = 0; i < buttons.length; i++) {
            int v = model.getValueAt(i);
            TileButton b = buttons[i];
            if (v == 0) {
                b.setText("");
                b.setEnabled(false);
                b.setBackground(Color.LIGHT_GRAY);
            } else {
                b.setText(String.valueOf(v));
                b.setEnabled(true);
                b.setBackground(null);
            }
        }
        movesLabel.setText("Drag: " + model.getMoves());
    }
}

