import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement {
    public static void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of word: ");

        int wordCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < wordCount; i++) {
            System.out.printf("Enter the spelling of word %d: ", i + 1);
            String target = scanner.nextLine();

            System.out.printf("Enter the meaning of word %d: ", i + 1);
            String meaning = scanner.nextLine();

            Word word = new Word(target, meaning);
            Dictionary.addWord(word);
        }
    }

    public static void insertFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("dictionaries.txt"));
        /*  dictionaries.txt từ tiếng anh luôn viết thường và ngăn
         cách với từ tiếng viêt bằng dấu tab */
        while (scanner.hasNext()) {
            String stringWord = scanner.nextLine();
            String[] word = stringWord.split("\t");
            Word newWord = new Word(word[0], word[1]);
            Dictionary.addWord(newWord);
        }
    }

    public static void dictionaryLookup() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your lookup word: ");

        String wordLookup = scan.nextLine().toLowerCase();
        boolean isWordLookupExist = false;

        for (Word word : Dictionary.wordList) {
            if (word.getTarget().equals(wordLookup)) {
                System.out.println(wordLookup + ": " + word.getExplain());
                isWordLookupExist = true;
            }
        }

        if (!isWordLookupExist) System.out.println("Sorry!! Your word doesn't exist in our dictionary");
    }
}
