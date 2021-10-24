import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DictionaryApplication {
    private JTextField wordSearchField;
    private JList wordList;
    private JPanel wordSearchBarPanel;
    private JScrollPane wordListScrollPane;
    private JPanel dictionaryMainPanel;
    private JPanel wordListPanel;
    private JPanel wordDetailPanel;
    private JTextPane wordMeaningPane;
    private JScrollPane wordDetailScrollPane;
    private JPanel wordManipulationPanel;
    private JButton wordAddNewButton;
    private JPanel wordBriefPane;
    private JLabel wordNameLabel;
    private JButton wordSpeakButton;
    private JLabel wordPronounciationLabel;
    private ListSelectionListener wordListListener;
    private JFrame wordAppFrame;

    public DictionaryApplication() {
        wordAppFrame = new JFrame("Dictionary");
        wordAppFrame.setContentPane(dictionaryMainPanel);
        wordAppFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wordAppFrame.pack();
        wordAppFrame.setExtendedState(wordAppFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        wordAppFrame.setVisible(true);
    }

    private void showWordDescription() {
        Word word = (Word) wordList.getSelectedValue();
        if (word == null) {
            wordDetailScrollPane.setVisible(false);
        } else {
            wordMeaningPane.setText(word.getDescription());
            wordMeaningPane.setCaretPosition(0);

            wordNameLabel.setText(word.getWord());
            wordPronounciationLabel.setText(word.getPronounce());

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

        // Initialize pop up menu
        wordList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem removeItem = new JMenuItem("Remove");
                    removeItem.addActionListener(e1 -> {
                        Word activeWord = (Word) wordList.getSelectedValue();
                        String wordToDelete = activeWord.getWord();
                        if (JOptionPane.showConfirmDialog(wordList, "Are you sure you want to " +
                                "delete \"" + wordToDelete + "\"?",  "Confirmation",
                                JOptionPane.YES_NO_OPTION) == 0) {
                            DictionaryManagement.deleteWord(wordToDelete);

                            // Only using the model to delete can the UI also be updated
                            // https://groups.google.com/g/comp.lang.java.gui/c/-_H8Iy5QsGg?pli=1
                            DefaultListModel<Word> defaultModel = (DefaultListModel<Word>) wordList.getModel();
                            defaultModel.remove(wordList.getSelectedIndex());
                        }
                    });
                    JMenuItem editItem = new JMenuItem("Edit");
                    editItem.addActionListener(e1 -> {
                        DictionaryWordEditor.showWordEditor(wordAppFrame, (Word) wordList.getSelectedValue(), w -> {
                            DictionaryManagement.editWord(w.getWord(), w.getWord(), w.getHtml(),
                                    w.getDescription(), w.getPronounce());
                            DefaultListModel<Word> defaultModel = (DefaultListModel<Word>) wordList.getModel();
                            defaultModel.setElementAt(w, wordList.getSelectedIndex());

                            showWordDescription();
                        });
                    });
                    menu.add(removeItem);
                    menu.add(editItem);
                    menu.show(wordList, e.getX(), e.getY());
                }
            }
        });

        // Handle the Add button
        wordAddNewButton.addActionListener(e -> {
            DictionaryWordEditor.showWordEditor(wordAppFrame, null, w -> {
                DictionaryManagement.insertWord(w);

                // Rerun the word search to match the words
                // And update the IDs from database
                runWordSearch(wordSearchField.getText());
            });
        });

        // Set up brief style
        wordNameLabel.setFont(new Font("Serif", Font.BOLD, 23));
        wordPronounciationLabel.setFont(new Font("Serif", Font.ITALIC, 19));
        wordMeaningPane.setFont(new Font("Serif", Font.PLAIN, 16));
        wordSpeakButton.setBorder(BorderFactory.createEmptyBorder());
        wordSpeakButton.setContentAreaFilled(false);

        // Let it speak
        wordSpeakButton.addActionListener(e-> {
            DictionaryManagement.mySpeak(wordNameLabel.getText());
        });
    }

    public static void runApplication() {
        DictionaryApplication app = new DictionaryApplication();
        app.initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                runApplication();
            }
        });
    }
}
