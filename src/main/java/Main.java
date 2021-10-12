import java.util.List;

public class Main {
    /**
     * Trước khi run:
     * 1. Điều chỉnh version JDK và maven trong file pom.xml hợp lí
     * 2. Điều chỉnh đường dẫn đến file .db (class DictionaryManagement)
     **/
    public static void main(String[] args) {
        List<Word> wordList = DictionaryManagement.dictionarySearcher("");
        for (Word w : wordList) {
            System.out.println(w.getWord() + " " + w.getDescription());
        }
    }
}
