import java.lang.ArrayIndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Dictionary {
    public static ArrayList<Word> wordList = new ArrayList<Word>();

    public static boolean addWord(Word word) {
        Optional<Word> findResult = wordList.stream().filter(targetWord -> {
            return word.getTarget().equalsIgnoreCase(targetWord.getTarget());
        }).findFirst();

        if (findResult.isEmpty()) {
            wordList.add(word);
            return true;
        }

        return false;
    }

    public static Word getLongest(boolean longestTarget) {
        if (wordList.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("The word array is empty!");
        }

        Word longestWord = new Word();

        for (Word targetWord: wordList) {
            if ((longestTarget && (targetWord.getTarget().length() > longestWord.getTarget().length()))
            || (!longestTarget && (targetWord.getExplain().length() > longestWord.getExplain().length()))) {
                longestWord = targetWord;
            }
        };

        return longestWord;
    }
}
