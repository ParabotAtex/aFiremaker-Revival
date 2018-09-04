package org.parabot.scriptwriter.revival.afiremaker.UI;

import org.parabot.scriptwriter.revival.afiremaker.data.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JPanel contentPane;
    private JTextField txtLogId;
    private JButton startButton;
    private Settings s;

    public GUI() {
        setTitle("Configure script");
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(startButton);
        pack();
        setVisible(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(txtLogId.getText());
                    s = new Settings(id);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "That is not a valid id");
                }
            }
        });
    }

    public Settings getSettings() {
        return s;
    }
}
