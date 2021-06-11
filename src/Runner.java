import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Укажите путь к папке с файлами.");
        String fileDir = in.next();

        Scanner inNum = new Scanner(System.in);
        System.out.println("Укажите количество файлов для обработки.");
        int fileCount = inNum.nextInt();

        IParser parser = new Parser();
        parser.parse(fileDir, fileCount);

        in.close();
        inNum.close();
    }


}
