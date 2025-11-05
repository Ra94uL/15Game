package Game15;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;




public class GameView extends JFrame {

    private final GameModel model;
    private final JButton[] buttons = new JButton[16];
    private final JLabel movesLabel = new JLabel("Drag: 0");




    public GameView(GameModel model){
        this.model = model; // kopplar logiken till vyn

        // Grundinställningarna
        setTitle("15-spelet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel buttonPanel = new JPanel();

        JButton newGameBtn = new JButton("Nytt spel");
        newGameBtn.addActionListener(e -> {
            model.shuffle();
            refresh();
        });


        JPanel boardPanel = new JPanel(new GridLayout(4,4,4,4));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(newGameBtn);
        buttonPanel.add(movesLabel);




        // 16 knappar i boardPanel
        for (int i = 0; i < 16; i++) {
            JButton btn = new JButton();
            buttons [i] = btn;  // spara knappen i arrayen

            if (i < 15){
                btn.setText(String.valueOf(i + 1));
            }else {
                //sista rutan
                btn.setText("");
                btn.setEnabled(false);  // ej klickbar
                btn.setBackground(Color.LIGHT_GRAY);
            }
            btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,28));
            btn.addActionListener(this::handleTileClick);
            boardPanel.add(btn);

        }

        panel.add(buttonPanel,BorderLayout.NORTH);
        panel.add(boardPanel,BorderLayout.CENTER);
        add(panel);
        setVisible(true);

    }

    private void handleTileClick(ActionEvent e){
        JButton clickedButton = (JButton) e.getSource();

        // hitta vilken index knapparna har i arrayen
        int index = -1;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == clickedButton){
                index = i;
                break;
            }
        }

        boolean moved = model.tryMove(index);  // försök flytta brickan i modellen
        if (moved){
            refresh();
            if (model.isSolved()){
                JOptionPane.showMessageDialog(this,
                        "Grattis, du vann!","Vinnare",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        refresh();  // rätt start läge direkt


    }

    // uppdatera knapparna
    private void refresh(){
        for (int i = 0; i < model.getSize(); i++) {
            int value = model.getValueAt(i);
            JButton btn = buttons[i];

            if (value == 0){
                btn.setText("");
                btn.setEnabled(false);
                btn.setBackground(Color.LIGHT_GRAY);
            }else{
                btn.setText(String.valueOf(value));
                btn.setEnabled(true);
                btn.setBackground(null);
            }

        }

        movesLabel.setText("Drag: " + model.getMoves());
    }







}

