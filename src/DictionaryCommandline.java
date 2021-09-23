import java.io.FileNotFoundException;

public class DictionaryCommandline {
    public static void showAllWords() {
        Word longestTarget = Dictionary.getLongest(true);
        int padding = Math.max(longestTarget.getTarget().length(), 7);

        System.out.printf("No   | %-" + padding + "s  | Vietnamese\n", "English");
        String formatEachLine = "%-5d| %-" + padding + "s  | %s\n";

        for (int i = 0; i < Dictionary.wordList.size(); i++) {
            Word targetWord = Dictionary.wordList.get(i);

            System.out.printf(formatEachLine, i, targetWord.getTarget(), targetWord.getExplain());
        }
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandline();
        DictionaryCommandline.showAllWords();
    }

    public static void dictionaryAdvanced() throws FileNotFoundException {
        DictionaryManagement.insertFromFile();
        DictionaryCommandline.showAllWords();
        DictionaryManagement.dictionaryLookup();
        //DictionaryManagement.dictionarySearcher();
    }

    public static void main(String[] args) throws FileNotFoundException {
//        dictionaryBasic();
        dictionaryAdvanced();
    }
}
