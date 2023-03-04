import java.io.*;
import java.nio.file.FileSystems;

public class TerminalInterface extends Thread{
    private static File bash;
    //variables specific to the message and recipiant
    private static int sendPace;
    private static String text;
    private static String number;
    private static boolean mode;
    private static int pace;
    private static Thread currThread;
    private static Message currentMessage;

//    public static void messageHelper(String textBox, String numberBox, boolean mode){
//        sendPace = 1;
//        try {
//            bash = absLocGenerator();
//            if(mode){
//                messegeEdit(textBox,numberBox);
//            }else{
//                messageParser(textBox,numberBox);
//            }
//
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//    }
    public static void dataInput(Message message){
        text = message.getContent();
        number = message.getRecipiant();
        mode = message.isMode();
        pace = message.getPace();
        currThread = message.getCurThread();
        currentMessage = message;
    }

    public void run(){
        try {
            bash = absLocGenerator();
            if(mode){
                bashEdit(text,number);
            }else{
                messageParser(text,number);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    private static void messageSend() throws IOException {
        Runtime.getRuntime().exec(bash.getPath());
    }

    private static boolean messageParser(String textBox, String numberBox) throws InterruptedException{
        String words[] = textBox.split("[.]");
        //System.out.println(words.length);
        for(int i = 0; i < words.length; i++){
            //catches errors in the message edit stage and retuns a false boolean
            if(!bashEdit(words[i],numberBox)){
                return false;
            }
        }
        return true;
    }
    protected static boolean bashEdit(String textSting, String numberBox) throws InterruptedException{
        FileWriter bashEditer = null;
        try{
            bashEditer = new FileWriter(bash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        try{
            BufferedWriter out = new BufferedWriter(bashEditer);
            out.write("#!/bin/bash \n\n");
            String combinedLine = "osascript -e 'tell application \"Messages\" to send \"" + textSting + "\" to buddy \"" + numberBox + "\"'";
            out.write(combinedLine);
            out.close();
            bashEditer.close();
            messageSend();
            currThread.sleep(pace);

        }catch (IOException e){
            System.out.println("something happened");
            return false;
        }
        
        return true;
    }
    private static File absLocGenerator() {
        try {
            String absLoc = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();
            absLoc = absLoc.substring(0, absLoc.indexOf("iMessagePlus"));
            return new File(absLoc + "iMessagePlus/HelperFiles/Bash_File");
        } catch (IndexOutOfBoundsException r) {

        }
        return new File((String) null);
    }
    private void markDone(){
        for(int i = 0; i < Builder.messageHistory.size(); i++){
            if (Builder.messageHistory.get(i).equals(currentMessage)){
                Builder.messageHistory.get(i).finished();
            }
        }

    }
}
