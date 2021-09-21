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
}
