import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

class MainFrame {

    public void addComponentsToPane(Container pane) {
        pane.setBackground(new Color(250,225,180));
        JButton button = new JButton("Click me");
        button.setBounds(721, 410, 100, 50);
        pane.add(button);
        JLabel label = new JLabel("Welcome to the Power-Ahn Project, a productivity tool to make you power on!");
        label.setBounds(20, -20, 1000, 100);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        pane.add(label);

    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        addComponentsToPane(frame.getContentPane());
        frame.setVisible(true);
        Component mouseClick = new MouseHandler();
        frame.addMouseListener((MouseListener) mouseClick);

    }

}