import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DictionaryManagement {
    public static void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of word: ");

        int wordCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < wordCount; i++) {
            System.out.printf("Enter the spelling of word %d: ", i + 1);
            String target = scanner.nextLine().toLowerCase();

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

    public static void dictionaryExportToFile() throws IOException {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream("dictionaries.txt"));
            for (Word i : Dictionary.wordList) {
                String line = i.getTarget() + "\t" + i.getExplain() + "\n";
                out.write(line.getBytes(StandardCharsets.UTF_8));
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void insertWord() throws IOException {
        insertFromFile();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your new word: ");
        String newWordTarget = scan.nextLine().toLowerCase();
        System.out.println("Enter your meaning of word: ");
        String newWordExplain = scan.nextLine();

        Word word = new Word(newWordTarget, newWordExplain);
        Dictionary.addWord(word);

        dictionaryExportToFile();
    }

    public static void deleteWord() throws IOException {
        insertFromFile();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your remove word: ");
        String deleteWord = scan.nextLine().toLowerCase();
        boolean isWordExist = false;

        for (int i = 0; i < Dictionary.wordList.size(); i++) {
            if (Dictionary.wordList.get(i).getTarget().equals(deleteWord)) {
                Dictionary.wordList.remove(i);
                isWordExist = true;
                break;
            }
        }
        if (!isWordExist) System.out.println("Sorry!! Your word doesn't exist in our dictionary");
        else System.out.println("Remove successfully !!");

        dictionaryExportToFile();
    }

    public static void editWord() throws IOException {
        insertFromFile();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your edit word: ");
        String deleteWord = scan.nextLine().toLowerCase();
        boolean isWordExist = false;

        for (int i = 0; i < Dictionary.wordList.size(); i++) {
            if (Dictionary.wordList.get(i).getTarget().equals(deleteWord)) {
                System.out.println("Enter your new target: ");
                String newWordTarget = scan.nextLine().toLowerCase();
                System.out.println("Enter your new explain: ");
                String newWordExplain = scan.nextLine();

                Dictionary.wordList.get(i).setTarget(newWordTarget);
                Dictionary.wordList.get(i).setExplain(newWordExplain);

                isWordExist = true;
                break;
            }
        }
        if (!isWordExist) System.out.println("Sorry!! Your word doesn't exist in our dictionary");
        else System.out.println("Edit successfully !!");

        dictionaryExportToFile();
    }
}
