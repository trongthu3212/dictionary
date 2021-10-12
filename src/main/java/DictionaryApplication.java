import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class DictionaryApplication {
    private JTextField wordSearchField;
    private JList wordList;
    private JPanel wordSearchBarPanel;
    private JScrollPane wordListScrollPane;
    private JPanel dictionaryMainPanel;
    private JPanel wordListPanel;
    private JPanel wordDetailPanel;
    private JEditorPane wordDetailEditorPane;
    private JScrollPane wordDetailScrollPane;
    private ListSelectionListener wordListListener;

    private void showWordDescription() {
        Word word = (Word) wordList.getSelectedValue();
        if (word == null) {
            wordDetailScrollPane.setVisible(false);
        } else {
            wordDetailEditorPane.setContentType("text/html");
            wordDetailEditorPane.setText(word.getHtml());

            wordDetailEditorPane.setCaretPosition(0);
            wordDetailScrollPane.setVisible(true);

            return;
        }
    }

    private void runWordSearch(String searchHint) {
        SwingUtilities.invokeLater(() -> {
            wordList.removeListSelectionListener(wordListListener);

            // Create new model, using the old model makes add very slow for some reason
            java.util.List<Word> words = DictionaryManagement.dictionarySearcher(searchHint);
            DefaultListModel<Word> models = new DefaultListModel<Word>();

            models.removeAllElements();

            for (int i = 0; i < words.size(); i++) {
                models.add(i, words.get(i));
            }

            // Initialize list selection
            wordList.setModel(models);
            wordList.addListSelectionListener(wordListListener);
            wordList.setSelectedIndex(0);
        });
    }

    private void initialize() {
        // Initialize word list listener
        wordListListener = event -> {
            if (!event.getValueIsAdjusting()) {
                showWordDescription();
            }
        };

        // Initialize word list content
        DefaultListModel<Word> models = new DefaultListModel<Word>();
        models.add(0, new Word("0", "Please wait for words to be loaded", "", "",
                ""));

        wordList.setModel(models);

        runWordSearch("");

        // Initialize text field callback
        wordSearchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            public void removeUpdate(DocumentEvent e) {
                update();
            }
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                runWordSearch(wordSearchField.getText());
            }
        });
    }

    public static void runApplication() {
        DictionaryApplication app = new DictionaryApplication();
        app.initialize();

        JFrame frame = new JFrame("Dictionary");
        frame.setContentPane(app.dictionaryMainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                runApplication();
            }
        });
    }
}
