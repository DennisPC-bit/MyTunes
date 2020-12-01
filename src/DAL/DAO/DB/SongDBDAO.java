package DAL.DAO.DB;

import BE.Song;
import DAL.DB.DbConnectionHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDBDAO {
    protected List<Song> songs;
    protected DbConnectionHandler database;

    public SongDBDAO() {
        database = DbConnectionHandler.getInstance();
    }

    public List<Song> loadSongs() {
        var temp = new ArrayList<Song>();
        var con = database.getConnection();
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM song;");
            while (rs.next()) {
                int id = rs.getInt("song_id");
                String name = rs.getString("song_name");
                String path = rs.getString("song_filepath");
                int category = rs.getInt("category_id");
                temp.add(new Song(id, name, path, category));
            }

            return temp;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean createSong(String name, String path) {
        var con = database.getConnection();
        var sql = "INSERT INTO song (song_title, song_filepath) VALUES(?,?);";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.setString(2, path);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean createSong(String name, String path, int categoryId) {
        var con = database.getConnection();
        var sql = "INSERT INTO song (song_title, song_filepath, category_id) VALUES(?,?,?);";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.setString(2, path);
            st.setInt(3, categoryId);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Song getSong(String name) {
        var con = database.getConnection();
        var sql = "SELECT FROM song WHERE song_name = ?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            var resultSet = st.getResultSet();
            var id = resultSet.getInt("song_id");
            var name1 = resultSet.getString("song_name");
            var path = resultSet.getString("song_filepath");
            var song = new Song(id, name1, path);
            return song;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean deleteSong(String name) {
        var con = database.getConnection();
        var sql = "DELETE FROM song WHERE song_title = ?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
