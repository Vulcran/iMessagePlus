public class Contacts {
    String name;
    String number;

    public Contacts(){
        name = "";
        number = "";
    }
    public Contacts(String namePar, String numberPar){
        name = namePar;
        number = numberPar;
    }
    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
}
