package it.unibo.geometrybash.view.menu;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class MainMenuViewImpl  implements MainMenuView {

    private MainMenuViewObserver observer;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel promptLabel;
    private JTextField inputField;
    private JTextArea outputArea;


    @Override
    public void show() {
        this.frame.setVisible(true);
    }

    @Override
    public void close() {
        this.frame.dispose();
    }

    @Override
    public void setObserver(final MainMenuViewObserver observer) {
        this.observer = observer;

    }

    @Override
    public void showMessage(final String message) {
        this.outputArea.append(message + "\n");
    }

    @Override
    public void clear() {
        this.outputArea.setText("");
    }
    public MainMenuViewImpl() {
        this.frame = new JFrame("GeometryBash");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setSize(screenSize);
        this.frame.setUndecorated(false);

        // Main Panel
        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setBackground(Color.BLACK);

        this.frame.setContentPane(this.mainPanel);

        // Top Panel
        this.topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.topPanel.setBackground(Color.BLACK);
        this.topPanel.setPreferredSize(new Dimension(0, 40));


        this.mainPanel.add(this.topPanel, BorderLayout.NORTH);
        
        // Output Area
        this.outputArea = new JTextArea();
        this.outputArea.setEditable(false);
        this.outputArea.setBackground(Color.BLACK);
        this.outputArea.setForeground(Color.GREEN);
        this.outputArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        this.outputArea.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(this.outputArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.BLACK);

        this.mainPanel.add(scrollPane, BorderLayout.CENTER);


        // Bottom Panel with Prompt Label and Input Field
        this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.bottomPanel.setBackground(Color.BLACK);
        this.bottomPanel.setPreferredSize(new Dimension(0, 40));

        this.promptLabel = new JLabel("geometrybash@oop24:~# ");
        this.promptLabel.setForeground(Color.GREEN);
        this.promptLabel.setFont(new Font("Consolas", Font.PLAIN, 16));

        this.inputField = new JTextField(40);
        this.inputField.setForeground(Color.GREEN);
        this.inputField.setBackground(Color.BLACK);
        this.inputField.setCaretColor(Color.GREEN);
        this.inputField.setBorder(null);
        this.inputField.setFont(new Font("Consolas", Font.PLAIN, 16));

        this.bottomPanel.add(this.promptLabel);
        this.bottomPanel.add(this.inputField);

        this.mainPanel.add(this.bottomPanel, BorderLayout.SOUTH);

        this.inputField.addActionListener(e -> {
            final String command = this.inputField.getText();
            this.inputField.setText("");
            if (this.observer != null) {
                this.observer.onCommand(command);
            }
        }); 


    }  
}
