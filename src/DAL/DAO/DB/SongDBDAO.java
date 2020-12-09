package DAL.DAO.DB;

import BE.Song;
import BLL.SongManager;
import DAL.DAO.SongDAOInterface;
import DAL.DB.DbConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongDBDAO implements SongDAOInterface {
    protected DbConnectionHandler database;
    protected SongManager songManager;

    /**
     * Sets the manager.
     *
     * @param songManager
     */
    @Override
    public void setSongManager(SongManager songManager) {
        this.songManager = songManager;
    }

    /**
     * Tries to connect to the database.
     *
     * @throws SQLException if it cant get connection to the database or something went wrong.
     */
    public SongDBDAO() {
        database = DbConnectionHandler.getInstance();
        try {
            if (database.getConnection().isClosed())
                songManager.goLocal();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Tries to load all songs.
     *
     * @return all songs on the database or if there are no songs on the database an empty list.
     * @throws SQLException if it cant get connection to the database or something went wrong.
     */
    @Override
    public List<Song> loadSongs() throws SQLException {
        List<Song> temp = new ArrayList<>();
        try (var con = database.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT song.*, category.category_name FROM song INNER JOIN category ON song.category_id = category.category_id;");
            while (rs.next()) {
                int song_id = rs.getInt("song_id");
                String song_title = rs.getString("song_title");
                String song_artist = rs.getString("song_title");
                String song_filepath = rs.getString("song_filepath");
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                temp.add(new Song(song_id, song_title, song_artist, song_filepath, category_id, category_name));
            }
            return temp;
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
            return temp;
        }
    }


    /**
     * tries to create a song.
     *
     * @param song the song.
     * @throws SQLException
     */
    @Override
    public void createSong(Song song) throws SQLException {
        var sql = "INSERT INTO song (song_title, song_artist, song_filepath, category_id) VALUES(?,?,?,?);";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, song.getTitle());
            st.setString(2, song.getArtist());
            st.setString(3, song.getFilePath());
            st.setInt(4, song.getCategoryId());
            st.executeUpdate();
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
        }
    }

    /**
     * Tries to find a song with the given name.
     *
     * @param name the name of the song.
     * @return A song with the name.
     * @throws SQLException if it cant get connection to the database or something went wrong.
     */
    @Override
    public Song getSong(String name) throws SQLException {
        var sql = "SELECT FROM song WHERE song_name = ?;";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            var resultSet = st.getResultSet();
            var id = resultSet.getInt("song_id");
            var name1 = resultSet.getString("song_name");
            var path = resultSet.getString("song_filepath");
            String artist = resultSet.getString("song_artist");
            var song = new Song(id, name1, path, artist);
            return song;
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
            return null;
        }
    }

    /**
     * Tries to delete a song with the given id.
     *
     * @param id the id of the song.
     * @throws SQLException if it cant get connection to the database or something went wrong.
     */
    @Override
    public void deleteSong(int id) throws SQLException {
        var sql = "DELETE FROM song WHERE song_id = ?;";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
        }
    }

    /**
     * Tries to update a Song.
     *
     * @param id       id of the song
     * @param modified the modified version of the song.
     * @throws SQLException if it cant get connection to the database or something went wrong.
     */
    @Override
    public void updateSong(int id, Song modified) throws SQLException {
        var sql = "UPDATE song SET song_title = ?, song_filepath = ?, song_artist=?, category_id=? WHERE song_id = ?;";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, modified.getTitle());
            st.setString(2, modified.getFilePath());
            st.setString(3, modified.getArtist());
            st.setInt(4, modified.getCategoryId());
            st.setInt(5, modified.getId());
            st.executeUpdate();
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
        }
    }

    /**
     * Tries to find a list of songs that contain search
     *
     * @param search the search string
     * @return A list containing songs that match, or an empty list if no song matches.
     */
    @Override
    public List<Song> searchSong(String search) {
        List<Song> resultSongs = new ArrayList<>();
        try (var connection = database.getConnection()) {
            String sql = "SELECT * FROM song WHERE LOWER(song_title) LIKE LOWER(?) OR song_id LIKE LOWER(?) OR LOWER(song_filepath) LIKE LOWER(?) OR LOWER(song_artist) LIKE LOWER(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            preparedStatement.setString(4, "%" + search + "%");
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    var id = resultSet.getInt("song_id");
                    var name = resultSet.getString("song_title");
                    var artist = resultSet.getString("song_artist");
                    var path = resultSet.getString("song_filepath");
                    var song = new Song(id, name, artist, path, "not done yet");
                    resultSongs.add(song);
                }
                return resultSongs;
            } else {
                System.out.println(String.format("Couldn't find the song: %s", search));
            }
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSongs;
    }

    @Override
    public Map<Integer, String> getGenres() throws Exception {
        HashMap<Integer, String> temp = new HashMap<>();
        try (var con = database.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM category;");
            while (rs.next()) {
                int category_id = rs.getInt("category_id");
                String category_name = rs.getString("category_name");
                if (!temp.containsKey(category_id))
                    temp.put(category_id, category_name);
            }
            return temp;
        } catch (SQLNonTransientConnectionException e) {
            songManager.goLocal();
            return temp;
        }
    }
}
