import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.border.EmptyBorder;

public class Calculadora extends JFrame {

    private JTextField campoTexto;
    private double numero1, numero2, resultado;
    private char operador;

    public Calculadora() {
        configureJFrame();
        createUI();
        addKeyListeners();
    }

    private void configureJFrame() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        campoTexto = new JTextField();
        configureTextField();

        mainPanel.add(campoTexto, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton clearButton = new JButton("C");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextField();
            }
        });
        clearButton.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton backspaceButton = new JButton("\u2190");
        backspaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = campoTexto.getText();
                if (!text.isEmpty()) {
                    campoTexto.setText(text.substring(0, text.length() - 1));
                }
            }
        });
        backspaceButton.setFont(new Font("Arial", Font.PLAIN, 18));

        JPanel clearBackspacePanel = new JPanel(new GridLayout(1, 2, 5, 5));
        clearBackspacePanel.add(clearButton);
        clearBackspacePanel.add(backspaceButton);

        bottomPanel.add(clearBackspacePanel, BorderLayout.WEST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(mainPanel);
    }

    private void configureTextField() {
        campoTexto.setEditable(false);
        campoTexto.setHorizontalAlignment(JTextField.RIGHT);
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] labels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : labels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onButtonClicked(e.getActionCommand());
                }
            });
            button.setFont(new Font("Arial", Font.BOLD, 18));
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private void onButtonClicked(String command) {
        if (command.matches("[0-9.]")) {
            campoTexto.setText(campoTexto.getText() + command);
        } else if (command.matches("[-+*/]")) {
            numero1 = Double.parseDouble(campoTexto.getText());
            operador = command.charAt(0);
            campoTexto.setText("");
        } else if (command.equals("=")) {
            calcular();
        }
    }

    private void calcular() {
        numero2 = Double.parseDouble(campoTexto.getText());

        switch (operador) {
            case '+':
                resultado = numero1 + numero2;
                break;
            case '-':
                resultado = numero1 - numero2;
                break;
            case '*':
                resultado = numero1 * numero2;
                break;
            case '/':
                if (numero2 != 0) {
                    resultado = numero1 / numero2;
                } else {
                    campoTexto.setText("Não tem como dividir por 0.");
                    return;
                }
                break;
        }

        campoTexto.setText(String.valueOf(resultado));
    }

    private void clearTextField() {
        campoTexto.setText("");
        numero1 = 0;
        numero2 = 0;
        resultado = 0;
    }

    private void addKeyListeners() {
        campoTexto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'c' || e.getKeyChar() == 'C') {
                    clearTextField();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculadora calculadora = new Calculadora();
            calculadora.setVisible(true);
        });
    }
}
