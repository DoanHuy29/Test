import java.io.File;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> getResult = def_word_cnt();
        System.out.println(getResult);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("result.jason"), getResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateFiles(1);
    }
    public static LinkedHashMap<String, Integer> def_word_cnt() {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        String str, strWithNoSpace, word;
        char ch;
        int j = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter string: ");
        str = sc.nextLine();
        strWithNoSpace = str.trim();

        for (int i = 0; i < strWithNoSpace.length(); i++) {
            ch = strWithNoSpace.charAt(i);
            if (Character.isSpaceChar(ch)) {
                word = strWithNoSpace.substring(j, i);
                if (result.containsKey(word)) {
                    result.replace(word, result.get(word)+1);
                } else {
                    result.put(word, 1);
                    j = i + 1;
                }
            }
            if (i == strWithNoSpace.length() - 1) {
                word = strWithNoSpace.substring(j);
                if (result.containsKey(word)) {
                    result.replace(word, result.get(word)+1);
                } else {
                    result.put(word, 1);
                }
            }
        }
        return result;
    }
    public static void generateFiles(int n) {
        if (n > 100) {
            return;
        }
        String str = "result_";
        String str1 = ".jason";
        String nameFile;
        try {
            nameFile = str + String.valueOf(n) + str1;
            File file = new File(nameFile);
            if (file.createNewFile()) {
                System.out.println("File was created. File name: " + file.getName());
            } else {
                System.out.println("File has existed");
            }
        } catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
        generateFiles(n + 1);
    }
}