import com.sun.tools.javac.Main;

import java.io.*;

public class TerminalInterface extends Builder {
    protected String text;
    protected String number;
    protected String contactName;
    private static File bash;
    private static int sendPace;

    public static void messageHelper(String textBox, String numberBox, boolean mode){
        sendPace = 1;
        try {
            bash = new File("/Users/will/Desktop/Bash_file");
            if(mode){
                messegeEdit(textBox,numberBox);
            }else{
                messageParser(textBox,numberBox);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public static boolean messageHelper(String textBox, String numberBox, boolean mode, int pace){
        sendPace = pace;
        try {
            bash = new File("HelperFiles/Bash_File");
            if(mode){
                messegeEdit(textBox,numberBox);
            }else{
                messageParser(textBox,numberBox);
            }
            return true;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    private static void messageSend() throws IOException {
        Runtime.getRuntime().exec(bash.getPath());
    }
    private static boolean messageParser(String textBox, String numberBox) throws InterruptedException{
        String words[] = textBox.split("[.]");
        System.out.println(words.length);
        for(int i = 0; i < words.length; i++){
            //catches errors in the message edit stage and retuns a false boolean
            if(!messegeEdit(words[i],numberBox)){
                return false;
            }
        }
        return true;
    }
    protected static boolean messegeEdit(String textSting, String numberBox) throws InterruptedException{
        try{
            FileWriter bashEditer = new FileWriter(bash);
            BufferedWriter out = new BufferedWriter(bashEditer);
            out.write("#!/bin/bash \n\n");
            String combinedLine = "osascript -e 'tell application \"Messages\" to send \"" + textSting + "\" to buddy \"" + numberBox + "\"'";
            out.write(combinedLine);
            out.close();
            bashEditer.close();
            messageSend();
            Thread.sleep(sendPace);
        }catch (IOException e){
            System.out.println("something happened");
            return false;
        }
        return true;
    }
}