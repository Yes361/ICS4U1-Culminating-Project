import javax.swing.*;

public class SettingScreen extends JPanel {
    public SettingScreen() {
        JComboBox<String> languageComboBox = new JComboBox<>();
        JSlider slider = new JSlider();

        add(new JLabel("Language Setting"));
        add(languageComboBox);

        add(slider);
    }
}
