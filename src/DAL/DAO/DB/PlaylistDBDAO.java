package DAL.DAO.DB;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import DAL.DB.DBConnector;
import DAL.DB.DbConnectionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDBDAO {
    protected List<Playlist> playlists;
    protected DbConnectionHandler database;
    protected PlaylistManager playlistManager;

    public void setPlaylistManager(PlaylistManager playlistManager) {
        this.playlistManager = playlistManager;
    }

    public PlaylistDBDAO() {
        database = DbConnectionHandler.getInstance();
    }

    public List<Playlist> loadPlaylist() throws SQLException {
        var temp = new ArrayList<Playlist>();
        var con = database.getConnection();
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist;");
            while (rs.next()) {
                int id = rs.getInt("playlist_id");
                String name = rs.getString("playlist_name");
                temp.add(new Playlist(id, name));
            }
            return temp;
        }
    }

    public boolean createPlaylist(String name) throws SQLException {
        var con = database.getConnection();
        var sql = "INSERT INTO playlist (playlist_name) VALUES(?);";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            return true;
        }
    }

    public Playlist getPlaylist(String name) throws SQLException {
        var con = database.getConnection();
        var sql = "SELECT FROM playlist WHERE playlist_name = ?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            var resultSet = st.getResultSet();
            var id = resultSet.getInt("playlist_id");
            var name1 = resultSet.getString("playlist_name");
            var playlist = new Playlist(id, name1);
            return playlist;

        }
    }

    public boolean deletePlaylist(String name) throws SQLException {
        var con = database.getConnection();
        var sql = "DELETE FROM playlist WHERE playlist_name = ?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            return true;
        }
    }

    public List<Song> loadSongsFromPlaylist(int playlist_id) throws SQLException {
        var temp = new ArrayList<Song>();
        var con = database.getConnection();
        var sql = "SELECT song.song_id,song.song_title,song.song_filepath FROM playlist_song,song WHERE playlist_song.playlist_id=? AND playlist_song.song_id=song.song_id;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1,playlist_id);
            st.execute();
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                int song_id = rs.getInt("song_id");
                String song_title = rs.getString("song_title");
                String song_filepath = rs.getString("song_filepath");
                temp.add(new Song(song_id,song_title,song_filepath));
            }
            return temp;
        }
    }

    public boolean AddSongToPlaylist(int playlist_id,int song_id) throws SQLException {
        var con = database.getConnection();
        var sql = "INSERT INTO playlist_song (playlist_id,song_id) VALUES (?,?);";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1,playlist_id);
            st.setInt(2,song_id);
            st.executeUpdate();
            return true;
        }
    }

    public boolean deleteFromPlaylist(int playlist_id,int song_id) throws SQLException {
        var con = database.getConnection();
        var sql = "DELETE FROM playlist_song WHERE playlist_id=? AND song_id=?;";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, playlist_id);
            st.setInt(2,song_id);
            st.executeUpdate();
            return true;
    public void updatePlaylist(Playlist playlist) throws Exception {
        try (Connection con = DBConnector.getConnection()) {
            String sql = "UPDATE playlist SET playlist_name=?  WHERE playlist_id=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlayListName());
            preparedStatement.setInt(2, playlist.getPlaylistId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not update Movie: " + playlist.toString());
            }
        }
    }
}
