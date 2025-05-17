import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class parser {
    public static JTextField field = new JTextField();
    public static JLabel resultLabel = new JLabel("Result: ");
    public static JTextField resultField = new JTextField();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Parser");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.setSize(700, 600);
        panel.setLayout(null);

        JLabel label = new JLabel("Enter Expression: ");
        label.setBounds(300, 300, 150, 20);
        panel.add(label);

        field.setBounds(420, 300, 100, 20);
        panel.add(field);

        JButton button = new JButton("Enter");
        button.setBounds(530, 300, 100, 20);
        panel.add(button);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                resultField.setText("");
            }
        });

        resultField = new JTextField();
        resultField.setBounds(420, 350, 100, 20);
        resultField.setEditable(false);
        panel.add(resultField);

        resultLabel.setBounds(365, 350, 100, 20);
        panel.add(resultLabel);

        //

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String expression = field.getText();
                int calculated = calculate(expression);
                field.setText("");
                resultField.setText(String.valueOf(calculated));
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static int calculate(String expression) {
        int result = 0;
        StringBuilder[] operands = new StringBuilder[countOperands(expression)];
        char[] operators = new char[countOperators(expression)];

        initOperands(operands);
        putOperandsIntoArray(operands, expression);
        putOperatorsIntoArray(operators, expression);

        char currentOperator;
        for (int i = 0; i < operators.length; i++) {
            currentOperator = operators[i];
            int first = Integer.parseInt(operands[i].toString()), second = Integer.parseInt(operands[i + 1].toString());
            switch (currentOperator) {
                case '+':
                    result = first + second;

                    break;
                case '-':
                    result = first - second;
                    break;
                case '*':
                    result = first * second;
                    break;
                case '/':
                    result = (int) first / second;
                    break;
                default:
                    throw new AssertionError();
            }

            StringBuilder temp = new StringBuilder(String.valueOf(result));
            operands[i + 1] = temp;
        }

        return result;
    }

    private static void initOperands(StringBuilder[] operands) {
        for (int i = 0; i < operands.length; i++) {
            operands[i] = new StringBuilder();
        }
    }

    private static void putOperatorsIntoArray(char[] operators, String expression) {
        int opIdx = 0;
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if ("+-/*".indexOf(ch) != -1) {
                operators[opIdx++] = ch;
            }
        }
    }

    private static int countOperands(String expression) {
        int operands = 0;
        char[] expressionA = expression.toCharArray();
        for (int i = 0; i < expressionA.length; i++) {
            if (i < expressionA.length && Character.isDigit(expressionA[i])
                    && Character.isSpaceChar(expressionA[i + 1]))
                operands++;

        }
        return operands;
    }

    private static int countOperators(String expression) {
        int noOperators = 0;
        for (int i = 0; i < expression.length(); i++) {
            if ("+-/*".indexOf(expression.toCharArray()[i]) != -1) {
                noOperators++;
            }
        }
        return noOperators;
    }

    private static void putOperandsIntoArray(StringBuilder[] operands, String expression) {
        int index = 0;
        for (int i = 0; i < expression.length(); i++) {
            if ("+-/*".indexOf(expression.toCharArray()[i]) != -1) {
                index++;
            }

            else if (Character.isDigit(expression.toCharArray()[i])) {
                operands[index].append(expression.toCharArray()[i]);
            }
        }

    }

}