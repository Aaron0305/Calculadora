import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class Calculadora {
    
    private JFrame frame;
    private JTextField display;
    private JPanel panel;

    private StringBuilder currentInput = new StringBuilder();
    private double num1 = 0.0;
    private double num2 = 0.0;
    private double result = 0.0;
    private char operator = '\0';

    private Color[] numberColors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW};
    private int colorIndex = 0;

    private void createAndShowGUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 4));

        buttonsPanel.add(createButton("7", Color.BLACK));
        buttonsPanel.add(createButton("8", Color.BLUE));
        buttonsPanel.add(createButton("9", Color.GREEN));
        buttonsPanel.add(createOperatorButton("/", Color.RED));
        buttonsPanel.add(createButton("4", Color.ORANGE));
        buttonsPanel.add(createButton("5", Color.MAGENTA));
        buttonsPanel.add(createButton("6", Color.CYAN));
        buttonsPanel.add(createOperatorButton("*", Color.YELLOW));
        buttonsPanel.add(createButton("1", Color.PINK));
        buttonsPanel.add(createButton("2", Color.YELLOW));
        buttonsPanel.add(createButton("3", Color.RED));
        buttonsPanel.add(createOperatorButton("-", Color.PINK));
        buttonsPanel.add(createButton("0", Color.BLUE));
        buttonsPanel.add(createButton(".", Color.GREEN)); // No necesita cambiar de color
        buttonsPanel.add(createButton("=", Color.ORANGE)); // No necesita cambiar de color
        buttonsPanel.add(createOperatorButton("+", Color.BLUE));
        buttonsPanel.add(createButton("C", Color.RED)); // Botón para limpiar

        panel.add(buttonsPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (Character.isDigit(command.charAt(0)) || command.equals(".")) {
                    currentInput.append(command);
                    display.setText(currentInput.toString());
                } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                    if (currentInput.length() > 0) {
                        num1 = Double.parseDouble(currentInput.toString());
                        operator = command.charAt(0);
                        currentInput.setLength(0);
                        changeButtonColors(buttonsPanel);
                    }
                } else if (command.equals("=")) {
                    if (currentInput.length() > 0) {
                        num2 = Double.parseDouble(currentInput.toString());
                        switch (operator) {
                            case '+':
                                result = num1 + num2;
                                break;
                            case '-':
                                result = num1 - num2;
                                break;
                            case '*':
                                result = num1 * num2;
                                break;
                            case '/':
                                if (num2 == 0) {
                                    JOptionPane.showMessageDialog(frame, "Cannot divide by zero");
                                    return;
                                }
                                result = num1 / num2;
                                break;
                        }
                        display.setText(String.valueOf(result));
                        currentInput.setLength(0);
                        changeButtonColors(buttonsPanel);
                    }
                } else if (command.equals("C")) { // Manejo del botón de limpiar
                    currentInput.setLength(0);
                    num1 = 0.0;
                    num2 = 0.0;
                    result = 0.0;
                    operator = '\0';
                    display.setText("");
                    changeButtonColors(buttonsPanel);
                }
            }
        };

        for (Component component : buttonsPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(actionListener);
            }
        }
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(color);
        return button;
    }

    private JButton createOperatorButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        return button;
    }

    private void changeButtonColors(JPanel buttonsPanel) {
        Random random = new Random();
        for (Component component : buttonsPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (!button.getActionCommand().equals("=") && !button.getActionCommand().equals(".") && !button.getActionCommand().equals("C")) {
                    button.setForeground(numberColors[random.nextInt(numberColors.length)]);
                    button.setBackground(numberColors[random.nextInt(numberColors.length)]);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculadora().createAndShowGUI();
            }
        });
    }
}
