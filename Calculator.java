import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Calculator
{
    private static JTextField valueX; //Input field for x value
    private static JTextField valueY; //Input field for y value
    private static JLabel result; //Output result

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int y=1;
        JPanel panelFields =new JPanel(); //Panel that will be contain fields for input data, result and names for these fields
        JPanel panelLabels =new JPanel(); //Panel that will be contain names for fields of input data and result
        panelLabels.setLayout(new BoxLayout(panelLabels,BoxLayout.Y_AXIS));
        JPanel panelValues =new JPanel(); //Panel that will be contain fields for input data and result
        panelValues.setLayout(new BoxLayout(panelValues,BoxLayout.Y_AXIS));
        JLabel labelX=new JLabel("x");
        JLabel labelY=new JLabel("y");
        JLabel resultO=new JLabel("Result: ");
        valueX=new JTextField(15);
        //Restrict valueX to 20 symbols
        valueX.setDocument(new PlainDocument() {
        String chars = "0123456789.+-";
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (chars.indexOf(str) != -1) {
                    if (getLength()< 20) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });
        valueX.setText("0");
        valueY=new JTextField(15);
        //Restrict valueY to 20 symbols
        valueY.setDocument(new PlainDocument() {
            String chars = "0123456789.+-";
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (chars.indexOf(str) != -1) {
                    if (getLength()< 20) {
                        super.insertString(offs, str, a);
                    }
                }
            }
        });
//Neogidani cament
        valueY.setText("0");
        result=new JLabel(" ");
        panelLabels.add(labelX);
        panelLabels.add(labelY);
        panelLabels.add(resultO);
        panelValues.add(valueX);
        panelValues.add(valueY);
        panelValues.add(result);
        panelFields.add(panelLabels);
        panelFields.add(panelValues);
        frame.getContentPane().add(BorderLayout.NORTH,panelFields);

        JPanel panelOperations =new JPanel(); //Panel that will be contain all operations
        ArrayList<JButton> buttons=new ArrayList<>();
        panelOperations.setLayout(new GridLayout(3,3));
        buttons.add(new JButton("+"));
        buttons.add(new JButton("-"));
        buttons.add(new JButton("x^2"));
        buttons.add(new JButton("/"));
        buttons.add(new JButton("*"));
        buttons.add(new JButton("x^y"));
        buttons.add(new JButton("sqrt(x)"));
        buttons.add(new JButton("log10(x)"));
        buttons.add(new JButton("C"));
        OperationsListener listener=new OperationsListener();
        Font newFont=new Font("serif", Font.BOLD, 20);
        for (JButton b:buttons)
        {
            b.addActionListener(listener);
            b.setFont(newFont);
            panelOperations.add(b);
        }
        frame.getContentPane().add(BorderLayout.CENTER,panelOperations);

        frame.setSize(350,300);
        frame.setVisible(true);
    }

    private static class OperationsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String operation=e.getActionCommand();
            switch (operation){
                case "+":{
                    if(xyDataCheck()){
                        result.setText((Double.parseDouble(valueX.getText())+Double.parseDouble(valueY.getText()))+"");
                    }
                    break;
                }
                case "-":{
                    if(xyDataCheck()){
                        result.setText((Double.parseDouble(valueX.getText())-Double.parseDouble(valueY.getText()))+"");
                    }
                    break;
                }
                case "*":{
                    if(xyDataCheck()){
                        result.setText((Double.parseDouble(valueX.getText())*Double.parseDouble(valueY.getText()))+"");
                    }
                    break;
                }
                case "/":{
                    if(xyDataCheck()){
                        if(Double.parseDouble(valueY.getText())==0) result.setText("Division by zero!");
                        else result.setText((Double.parseDouble(valueX.getText())/Double.parseDouble(valueY.getText()))+"");
                    }
                    break;
                }
                case "x^y":{
                    if(xyDataCheck()){
                        result.setText((Math.pow(Double.parseDouble(valueX.getText()),Double.parseDouble(valueY.getText())))+"");
                    }
                    break;
                }
                case "x^2":{
                    if(xDataCheck()){
                        result.setText((Math.pow(Double.parseDouble(valueX.getText()),2))+"");
                    }
                    break;
                }
                case "log10(x)":{
                    if(xDataCheck()){
                        if(Double.parseDouble(valueX.getText())<=0) result.setText("Incorrect data for logarithm!");
                        else result.setText((Math.log10(Double.parseDouble(valueX.getText())))+"");
                    }
                    break;
                }
                case "sqrt(x)":{
                    if(xDataCheck()){
                        if(Double.parseDouble(valueX.getText())<0) result.setText("Root of negative number!");
                        else result.setText((Math.sqrt(Double.parseDouble(valueX.getText())))+"");
                    }
                    break;
                }
                case "C":{
                    valueX.setText("0");
                    valueY.setText("0");
                    result.setText(" ");
                    break;
                }
            }
        }

        // Returns true if field x is correct, uses for operations that required only value x
        private static boolean xDataCheck(){
            if(valueX.getText().matches("[+-]{0,1}\\d+(\\.\\d+){0,1}")) return true;
            else {
                result.setText("Input data is incorrect!");
                return false;
            }
        }

        // Returns true if values x and y are correct
        private static boolean xyDataCheck(){
            if(xDataCheck()&&valueY.getText().matches("[+-]{0,1}\\d+(\\.\\d+){0,1}")) return true;
            else {
                result.setText("Input data is incorrect!");
                return false;
            }
        }
    }
}
