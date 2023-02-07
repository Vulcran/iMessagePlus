import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class GraphicInterface extends JFrame{

    private JTextField textField1;
    private JButton sendButton;
    private JComboBox comboBox1;
    private JEditorPane editorPane1;
    private JTextField textField2;
    private JRadioButton saveToContactsRadioButton;
    private JButton modeButton;
    private JList Message_Tracker_Data;
    private JComboBox comboBox2;
    private JTextPane textPane1;
    private JPanel guiPanel;
    private JTextField textField3;
    private JTextField textField4;
    private JRadioButton PMRadioButton;
    private JRadioButton AMRadioButton;
    private JLabel dateWarning;
    private JSlider slider1;
    private JLabel bunny;
    private JLabel turtle;
    private boolean messageMode;

    public GraphicInterface(){
        modeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(messageMode){
                    modeButton.setText(" 😈  Spam Mode  😈 ");
                    messageMode = false;
                    textField3.setText("");
                    textField4.setText("");
                    textField3.setBackground(new Color(24,24,24, 142));
                    textField4.setBackground(new Color(24,24,24, 142));
                    textField3.setEditable(false);
                    textField4.setEditable(false);
                    AMRadioButton.setSelected(false);
                    AMRadioButton.setEnabled(false);
                    PMRadioButton.setSelected(false);
                    PMRadioButton.setEnabled(false);
                    dateWarning.setVisible(false);
                    slider1.setEnabled(true);
                }else {
                    modeButton.setText("🕚 Delayed Mode 🕚");
                    messageMode = true;
                    textField3.setBackground(new Color(255,255,255));
                    textField4.setBackground(new Color(255,255,255));
                    textField3.setEditable(true);
                    textField4.setEditable(true);
                    AMRadioButton.setSelected(false);
                    AMRadioButton.setEnabled(true);
                    PMRadioButton.setSelected(false);
                    PMRadioButton.setEnabled(true);
                    slider1.setEnabled(false);
                    bunny.setOpaque(false);
                }
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(comboBox1.getSelectedItem().equals("------------")){
                    textField1.setBackground(new Color(255,255,255));
                    textField2.setBackground(new Color(255,255,255));
                    textField1.setEditable(true);
                    textField2.setEditable(true);
                    saveToContactsRadioButton.setEnabled(true);
                }else{
                    textField1.setText("");
                    textField2.setText("");
                    textField1.setBackground(new Color(24,24,24, 142));
                    textField2.setBackground(new Color(24,24,24, 142));
                    textField1.setEditable(false);
                    textField2.setEditable(false);
                    saveToContactsRadioButton.setSelected(false);
                    saveToContactsRadioButton.setEnabled(false);
                }
            }
        });
//        textField3.getDocument().addDocumentListener(new SimpleDocumentListener() {
//            @Override
//            public void update(DocumentEvent e) {
//
//            }
//        });

        AMRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(AMRadioButton.isSelected()){
                    PMRadioButton.setSelected(false);
                }
            }
        });
        PMRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(PMRadioButton.isSelected()){
                    AMRadioButton.setSelected(false);
                }
            }
        });


        textField3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                super.keyTyped(e);

                if(!dataValidation(textField3,e,'/')) {
                    dateWarning.setVisible(true);
                }else {
                    dateWarning.setVisible(false);

                }
            }
        });

        textField4.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(!dataValidation(textField4,e,':')) {
                    dateWarning.setVisible(true);
                }else {
                    dateWarning.setVisible(false);

                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pace = 1;
                if(!messageMode){
                    pace = (slider1.getValue()+1)*100;
                }

                if(comboBox1.getSelectedItem().equals("------------")){
                    TerminalInterface.messageHelper(editorPane1.getText(),textField1.getText(), messageMode, pace);
                }else{
                    int numLength = comboBox1.getSelectedItem().toString().length();
                    int beginNumIndex = comboBox1.getSelectedItem().toString().indexOf('-');
                    String parsNum = comboBox1.getSelectedItem().toString().substring(beginNumIndex+1,numLength);
                    TerminalInterface.messageHelper(editorPane1.getText(),parsNum, messageMode,pace);
                }

            }
        });
    }

    public boolean dataValidation(JTextField textbox, KeyEvent key, char specialAuthorized){
        boolean valid = true;
        char[] raw = textbox.getText().toCharArray();
        ArrayList<Character> input = new ArrayList<>();


        // creates Arraylist of Characters
        for(int i = 0; i < textbox.getText().length(); i++){
            input.add((Character) raw[i]);
        }


        //checks all chars excluding most recent keystroke
        for(int i = 0; i < textbox.getText().length(); i++){
            //checks each char in arraylist
            if(!Character.isDigit(input.get(i))){
                valid=false;
                //allows a special character
                if(input.get(i).equals((Character) specialAuthorized)){
                    valid = true;
                }
            }
        }

        // checks the key typed
        if(!Character.isDigit(key.getKeyChar())&&key.getExtendedKeyCode()!=8){
            System.out.println("key overwrite");
            valid=false;
            //allows a special character
            if(specialAuthorized==key.getKeyChar()){
                valid = true;
            }
        }
        return valid;
    }

    public void initiate(){
        setTitle("iMessage+");
        dataPopulation();
        setSize(1075,692);
        setLocation(210,70);
        setDefaultCloseOperation(3);
        setContentPane(guiPanel);
        setVisible(true);

    }
    public void dataPopulation(){
        //sets initial mode to delay
        messageMode = true;

        //Configures the Contacts drop down
        String[] holder = new String[Builder.getPhoneBook().size()+1];
        holder[0] = "------------";
        for(int i = 1; i < Builder.getPhoneBook().size()+1; i++){
            holder[i] = Builder.getPhoneBook().get(i-1).getName() + " - " + Builder.getPhoneBook().get(i-1).getNumber();
        }
        comboBox1.setModel(new DefaultComboBoxModel<String>(holder));
    }



//    public interface SimpleDocumentListener extends DocumentListener {
//        void update(DocumentEvent e);
//
//        @Override
//        default void insertUpdate(DocumentEvent e) {
//            update(e);
//        }
//        @Override
//        default void removeUpdate(DocumentEvent e) {
//            update(e);
//        }
//        @Override
//        default void changedUpdate(DocumentEvent e) {
//            update(e);
//        }
//    }

}