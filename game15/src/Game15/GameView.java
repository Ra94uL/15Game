package Game15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays; // för enklare indexsökning

public class GameView extends JFrame {

    private final GameModel model;
    private final JButton[] buttons = new JButton[16];
    private final JLabel movesLabel = new JLabel("Drag: 0");

    // Färginställningar (standard)
    private Color tileColor = UIManager.getColor("Button.background");
    private Color backgroundColor = Color.WHITE;

    public GameView(GameModel model){
        this.model = model; // kopplar logiken till vyn

        // Grundinställningar
        setTitle("15-spelet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600,600);
        setLocationRelativeTo(null);

        // Huvudlayout
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

        // Spelbräde
        JPanel boardPanel = new JPanel(new GridLayout(4,4,4,4));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        boardPanel.setBackground(backgroundColor);

        // spel-knapp
        JButton newGameBtn = new JButton("Nytt spel");
        newGameBtn.addActionListener(e -> { model.shuffle(); refresh(); });

        // Dropdowns för färginställningar
        ColorSettings colorSettings = new ColorSettings(new ColorSettings.ColorChangeListener() {
            @Override
            public void onTileColorChanged(Color color) {
                tileColor = (color != null) ? color : UIManager.getColor("Button.background");
                refresh();
            }
            @Override
            public void onBackgroundColorChanged(Color color) {
                backgroundColor = (color != null) ? color : Color.WHITE;
                boardPanel.setBackground(backgroundColor);
                refresh();
            }
        });

        // TopPanel med knappar och dropdowns
        topPanel.add(newGameBtn);
        topPanel.add(movesLabel);
        topPanel.add(colorSettings);

        // 16 knappar i boardPanel
        for (int i = 0; i < 16; i++) {
            JButton btn = new JButton();
            buttons[i] = btn;

            if (i < 15) btn.setText(String.valueOf(i + 1));
            else {
                btn.setText("");
                btn.setEnabled(false);
                btn.setBackground(Color.LIGHT_GRAY);
            }
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            btn.addActionListener(this::handleTileClick);
            boardPanel.add(btn);
        }

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        add(mainPanel);

        refresh();
        setVisible(true);
    }

    // Hanterar klick på brickor
    private void handleTileClick(ActionEvent e){
        JButton clickedButton = (JButton) e.getSource();
        int index = Arrays.asList(buttons).indexOf(clickedButton); // enklare än loop
        boolean moved = model.tryMove(index);
        if (moved){
            refresh();
            if (model.isSolved()){
                JOptionPane.showMessageDialog(this, "Grattis, du vann!",
                        "Vinnare", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Uppdaterar brickor och drag-räknare
    private void refresh(){
        for (int i = 0; i < model.getSize(); i++) {
            int value = model.getValueAt(i);
            JButton btn = buttons[i];
            if (value == 0){
                btn.setText("");
                btn.setEnabled(false);
                btn.setBackground(Color.LIGHT_GRAY);
            } else {
                btn.setText(String.valueOf(value));
                btn.setEnabled(true);
                btn.setBackground(tileColor);
            }
        }
        movesLabel.setText("Drag: " + model.getMoves());
    }
}
