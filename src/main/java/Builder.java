

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.TimerTask;

public class Builder extends TimerTask {
    public static ArrayList<Contacts> phoneBook = new ArrayList<>();
    private static TerminalInterface iMessageConnection = new TerminalInterface();
    public static ArrayList<Message> messageHistory = new ArrayList<>();

    public static void main(String[] args){
        phoneBook.add(new Contacts("Lillie", "9843779422"));
        GraphicInterface gui = new GraphicInterface();
        gui.initiate();
    }

//    public void termInterface(String number, String message, boolean mode){
//        iMessageConnection.messageHelper(message,number,mode);
//        System.out.println(message);
//    }

    @Override
    public void run() {

    }

    public static ArrayList<Contacts> getPhoneBook(){
        return phoneBook;
    }
}
