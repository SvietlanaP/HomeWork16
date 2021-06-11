import java.util.ArrayList;

public class Document {

    public ArrayList<String> docNum = new ArrayList<>();
    public String phoneNum;
    public String emailNum;

    @Override
    public String toString() {
        return "Document{" +
                "docNum=" + docNum +
                ", phoneNum='" + phoneNum + '\'' +
                ", emailNum='" + emailNum + '\'' +
                '}';
    }
}
