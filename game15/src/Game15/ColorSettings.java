package Game15;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// Klass för att låta användaren ändra färg på brickor och bakgrund
public class ColorSettings extends JPanel {
    private final JComboBox<String> tileColorBox;
    private final JComboBox<String> backgroundColorBox;
    private final Map<String, Color> colorMap;

    // Ett enkelt gränssnitt för att skicka tillbaka valda färger
    public interface ColorChangeListener {
        void onTileColorChanged(Color color);
        void onBackgroundColorChanged(Color color);
    }

    public ColorSettings(ColorChangeListener listener) {
        setLayout(new GridLayout(2, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Färginställningar"));

        // Enkla grundfärger
        colorMap = new HashMap<>();
        colorMap.put("Standard", null);
        colorMap.put("Röd", Color.RED);
        colorMap.put("Blå", Color.BLUE);
        colorMap.put("Grön", Color.GREEN);
        colorMap.put("Gul", Color.YELLOW);
        colorMap.put("Orange", Color.ORANGE);
        colorMap.put("Ljusgrå", Color.LIGHT_GRAY);
        colorMap.put("Vit", Color.WHITE);

        // Dropdown för brickfärg
        add(new JLabel("Brickfärg:"));
        tileColorBox = new JComboBox<>(colorMap.keySet().toArray(new String[0]));
        tileColorBox.addActionListener(e -> {
            String selected = (String) tileColorBox.getSelectedItem();
            listener.onTileColorChanged(colorMap.get(selected));
        });
        add(tileColorBox);

        // Dropdown för bakgrundsfärg
        add(new JLabel("Bakgrundsfärg:"));
        backgroundColorBox = new JComboBox<>(colorMap.keySet().toArray(new String[0]));
        backgroundColorBox.addActionListener(e -> {
            String selected = (String) backgroundColorBox.getSelectedItem();
            listener.onBackgroundColorChanged(colorMap.get(selected));
        });
        add(backgroundColorBox);
    }
}
