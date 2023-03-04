import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ContactHandler {
    public boolean dataWritter(){
        FileWriter contactEditor = null;
        File contact = null;
        try{
            contact = new File("contacts.csv");
            contact.createNewFile();
            contactEditor = new FileWriter(contact);
            contactEditor.write("Test");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
