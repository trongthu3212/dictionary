import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DictionaryManagement {
    //Đường dẫn đến file .db
    static final String path = "jdbc:sqlite:dict_hh.db";

    //Chèn một từ
    public static void insertWord(Word word) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(path);
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO av (id,word,html,description,pronounce) VALUES (?,?,?,?,?)");

            ps.setString(1, word.getId());
            ps.setString(2, word.getWord());
            ps.setString(3, word.getHtml());
            ps.setString(4, word.getDescription());
            ps.setString(5, word.getPronounce());

            int status = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //Lấy ra các từ bắt đầu bởi String
    public static List<Word> dictionarySearcher(String word) {
        List<Word> wordList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(path);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM av WHERE word LIKE ?");
            ps.setString(1, word + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                wordList.add(new Word(rs.getString(1), rs.getString(2), rs.getString(3)
                        , rs.getString(4), rs.getString(5)));
            }
            return wordList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    //Xóa một từ theo tên từ đó
    public static void deleteWord(String word) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(path);
            PreparedStatement ps = connection.prepareStatement("DELETE FROM av WHERE word=?");

            ps.setString(1, word);

            int status = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //Update một từ theo tên từ đó
    public static void editWord(String wordWhere, String word, String html, String description, String pronounce) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(path);
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE av SET word=?,html=?,description=?,pronounce=? WHERE word=?");

            ps.setString(1, word);
            ps.setString(2, html);
            ps.setString(3, description);
            ps.setString(4, pronounce);
            ps.setString(5, wordWhere);

            int status = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    //Phát âm
    public static void mySpeak(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");
        voice.allocate();
        voice.speak(text);
    }
}
