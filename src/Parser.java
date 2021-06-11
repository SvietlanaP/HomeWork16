import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Parser implements IParser{

    private Map<String, Document> resultMap = new HashMap<>();

    @Override
    public void parse(String pathToFolder, int countToParse) {

        File folder = new File(pathToFolder);

        if(folder.isDirectory()){
            // получаем только ТХТ файл согласно условию
            File[] files = folder.listFiles((dir, name) -> name.endsWith("txt"));

            // проверка на то, что после фильтрации есть файлы, подходящие под наше условие
            if(files.length == 0){
                System.out.println("Нет подходящих файлов");
                return;
            }

            if (countToParse <= 0){
                System.out.println("Количество файлов для обработки должно быть целым положительным числом");
                return;
            }

            //определяем количество файлов для чтения
            int countToRead;
            if (files.length < countToParse){
                countToRead = files.length;
            } else{
                countToRead = countToParse;
            }

            //считываем информацию
            for (int i = 0; i < countToRead; i++){
                    readFile(files[i]);
                    //System.out.println("------");
            }

            //HashMap write
            for (Map.Entry item : resultMap.entrySet()){
                System.out.println(item);

            }

            System.out.println("Обработано документов: " + resultMap.size());

        } else {
            System.out.println("Невалидный путь");
        }
    }

    private void readFile(File file){
        Document document = new Document();

        Pattern docPattern = Pattern.compile("\\d{4}[-][a-zа-я]{3}[-]\\d{4}[-][a-zа-я]{3}[-]\\d[a-zа-я]\\d[a-zа-я]", Pattern.CASE_INSENSITIVE);
        Pattern phonePattern = Pattern.compile("(\\+*)[(]\\d{2}[)]\\d{7}([\\W\\n\\t]|$)");
        Pattern emailPattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String docOneLine;

            // читаем документ посстрочно и анализируем
            while((docOneLine = reader.readLine()) != null){
                Matcher docMatcher = docPattern.matcher(docOneLine);
                Matcher phoneMatcher = phonePattern.matcher(docOneLine);
                Matcher emailMatcher = emailPattern.matcher(docOneLine);

                if(docMatcher.find()){
                    document.docNum.add(docOneLine.substring(docMatcher.start(),docMatcher.end()));
                    //System.out.println(docOneLine.substring(docMatcher.start(),docMatcher.end()));
                }

                if(phoneMatcher.find()){
                    document.phoneNum = docOneLine.substring(phoneMatcher.start(),phoneMatcher.end());
                    //System.out.println(docOneLine.substring(phoneMatcher.start(),phoneMatcher.end()));
                }

                if(emailMatcher.find()){
                    document.emailNum = docOneLine.substring(emailMatcher.start(),emailMatcher.end());
                    //System.out.println(docOneLine.substring(emailMatcher.start(),emailMatcher.end()));
                }

            }
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        String nameOfFile = file.getName().substring(0, file.getName().lastIndexOf("."));
        resultMap.put(nameOfFile, document);

    }

}
