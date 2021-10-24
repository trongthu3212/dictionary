import javax.swing.*;
import java.awt.*;

public class DictionaryWordEditor {
    private JPanel wordEditorMainPanel;
    private JTextField wordEditorWordField;
    private JLabel P;
    private JTextField wordEditorPronunciationField;
    private JButton wordEditorSubmitButton;
    private JButton wordEditorCancelButton;
    private JEditorPane wordEditorMeaningPane;
    private Word wordTarget;
    private JDialog editorDialog;
    private DictionaryWordEditedListener wordEditedListener;

    public interface DictionaryWordEditedListener {
        void onWordEdited(Word newWordInstance);
    }

    public DictionaryWordEditor(JFrame parentFrame, Word original, DictionaryWordEditedListener observer) {
        if (original != null) {
            wordTarget = new Word(original.getId(), original.getWord(), original.getHtml(), original.getDescription(),
                    original.getPronounce());

            wordEditorWordField.setText(original.getWord());
            wordEditorMeaningPane.setText(original.getDescription());
            wordEditorPronunciationField.setText(original.getPronounce());
        } else {
            wordTarget = new Word();
        }

        wordEditedListener = observer;

        wordEditorSubmitButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(editorDialog, "Are you sure you want to submit the edited result?",
                    "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                wordTarget.setWord(wordEditorWordField.getText());
                wordTarget.setDescription(wordEditorMeaningPane.getText());
                wordTarget.setPronounce(wordEditorPronunciationField.getText());

                if (wordEditedListener != null) {
                    wordEditedListener.onWordEdited(wordTarget);
                    editorDialog.dispose();
                }
            }
        });

        wordEditorCancelButton.addActionListener(e -> {
            editorDialog.dispose();
        });

        editorDialog = new JDialog(parentFrame, "Word editor", true);
        editorDialog.setContentPane(wordEditorMainPanel);
        editorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editorDialog.setMinimumSize(new Dimension(480, 140));
        editorDialog.setLocationRelativeTo(null);      // Center the dialog
        editorDialog.pack();
        editorDialog.setVisible(true);
    }

    static void showWordEditor(JFrame parentFrame, Word original, DictionaryWordEditedListener listener) {
        new DictionaryWordEditor(parentFrame, original, listener);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
