import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {
    public static void showWords(List<Word> wordList) {
        if (wordList.isEmpty()) {
            System.out.println("Khong tim thay tu nao!");
            return;
        }

        int maxLengthWord = DictionaryManagement.getLongestVariableSize("word");
        int maxLengthPronounce = DictionaryManagement.getLongestVariableSize("pronounce");
        
        int padding1 = Math.max(maxLengthWord, 10);
        int padding2 = Math.max(maxLengthPronounce, 10);

        System.out.printf("No   | %-" + padding1 + "s  | %-" + padding2 + "s | Vietnamese\n", "English", "Pronounce");
        String formatEachLine = "%-5d| %-" + padding1 + "s  | %-" + padding2 + "s | %s\n";

        for (int i = 0; i < wordList.size(); i++) {
            Word targetWord = wordList.get(i);
            System.out.printf(formatEachLine, i, targetWord.getWord(), targetWord.getPronounce(), targetWord.getDescription());
        }
    }

    public static void showAllWords() {
        showWords(DictionaryManagement.dictionarySearcher(""));
    }

    public static void dictionarySearcher() {
        System.out.print("Hay nhap tu ban can tra: ");
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();
        System.out.println("Ban cho ket qua 1 ti nhe!");
        List<Word> wordList = DictionaryManagement.dictionarySearcher(str);

        System.out.println("Ket qua da tim duoc:");

        showWords(wordList);
    }

    public static void dictionaryBasic() {
        //DictionaryManagement.insertFromCommandline();
        //DictionaryCommandline.showAllWords();
    }

    public static void printHelp() {
        System.out.println("Cac lenh: ");
        System.out.println("\t-search: Tra cuu cac tu");
        System.out.println("\t-all: In het tat ca cac tu");
        System.out.println("\t-help: In lai cac dong huong dan nay");
        System.out.println("\t-exit: In lai cac dong huong dan nay");
    }

    public static void dictionaryAdvanced() {
        System.out.println("Chao mung den voi chuong trinh tu dien!");
        printHelp();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Nhap lenh cua ban: ");

            String str = scanner.next();
            if (str.equals("exit")) {
                return;
            } else if (str.equals("all")) {
                showAllWords();
            } else if (str.equals("help")) {
                printHelp();
            } else if (str.equals("search")) {
                dictionarySearcher();
            } else {
                System.out.println("Lenh cua ban khong duoc ho tro!");
            }
        }
    }

    public static void main(String[] args) {
        dictionaryAdvanced();
    }
}
