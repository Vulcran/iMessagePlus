import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.FileSystems;
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
    private JTextField dateField;
    private JTextField timeField;
    private JRadioButton PMRadioButton;
    private JRadioButton AMRadioButton;
    private JLabel dateWarning;
    private JSlider slider1;
    private JLabel bunny;
    private JLabel turtle;
    private JLabel messageTracker;
    private JLabel termOut;
    private boolean messageMode;

    public GraphicInterface(){
        modeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(messageMode){
                    modeButton.setText(" 😈  Spam Mode  😈 ");
                    messageMode = false;
                    dateField.setText("");
                    timeField.setText("");
                    dateField.setBackground(new Color(24,24,24, 142));
                    timeField.setBackground(new Color(24,24,24, 142));
                    dateField.setEditable(false);
                    timeField.setEditable(false);
                    AMRadioButton.setSelected(false);
                    AMRadioButton.setEnabled(false);
                    PMRadioButton.setSelected(false);
                    PMRadioButton.setEnabled(false);
                    dateWarning.setVisible(false);
                    slider1.setEnabled(true);
                }else {
                    modeButton.setText("🕚 Delayed Mode 🕚");
                    messageMode = true;
                    dateField.setBackground(new Color(255,255,255));
                    timeField.setBackground(new Color(255,255,255));
                    dateField.setEditable(true);
                    timeField.setEditable(true);
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


        dateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                super.keyTyped(e);
                if(!dataValidation(dateField,e,'/')) {
                    dateWarning.setVisible(true);
                }else {
                    dateWarning.setVisible(false);
                }
            }
        });

        timeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                try{
                    String absLoc = FileSystems.getDefault()
                            .getPath("")
                            .toAbsolutePath()
                            .toString();
//                    absLoc = absLoc.substring(0,absLoc.indexOf("iMessagePlus"));
                    termOut.setText(absLoc);
                    termOut.setVisible(true);
                }catch (Exception p){
                    termOut.setText(p.toString());
                    termOut.setVisible(true);
                }
                if(!dataValidation(timeField,e,':')) {
                    dateWarning.setVisible(true);
                }else {
                    dateWarning.setVisible(false);

                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread messageThread = new Thread(new TerminalInterface());

                int pace = 1;
                //non blocking thread
                //asycronous threads

                if(saveToContactsRadioButton.isSelected()){
                    ContactHandler test = new ContactHandler();
                    test.dataWritter();
                }
                if(!messageMode){
                    pace = (slider1.getValue()+1)*40;

                }
                if(comboBox1.getSelectedItem().equals("------------")){
                    Message currMessage = new Message(editorPane1.getText(),textField1.getText(), messageMode, pace, new Thread(new TerminalInterface()));
                    TerminalInterface.dataInput(currMessage);
                    messageThread.start();
                }else{
                    int numLength = comboBox1.getSelectedItem().toString().length();
                    int beginNumIndex = comboBox1.getSelectedItem().toString().indexOf('-');
                    String parsNum = comboBox1.getSelectedItem().toString().substring(beginNumIndex+1,numLength);

                    Message currMessage = new Message(editorPane1.getText(), parsNum, messageMode,pace, new Thread(new TerminalInterface()));
                    TerminalInterface.dataInput(currMessage);
                    messageThread.start();
                    Builder.messageHistory.add(currMessage);
                }
//                if(!sendSucess){
//                    sendButton.setBackground(Color.red);
//                }
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
//            System.out.println("key overwrite");
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
}