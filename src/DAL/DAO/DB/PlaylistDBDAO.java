package DAL.DAO.DB;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import DAL.DAO.PlaylistDAOInterface;
import DAL.DB.DbConnectionHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDBDAO implements PlaylistDAOInterface{
    protected DbConnectionHandler database;
    protected PlaylistManager playlistManager;

    /**
     *
     * @param playlistManager
     */
    @Override
    public void setPlaylistManager(PlaylistManager playlistManager) {
        this.playlistManager = playlistManager;
    }

    /**
     *
     * @throws SQLException
     */
    public PlaylistDBDAO() throws SQLException {
        database = DbConnectionHandler.getInstance();
        if(database.getConnection()==null){
            throw new SQLException("could not connect to database");
        }
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Playlist> loadPlaylist() throws SQLException {
        var temp = new ArrayList<Playlist>();
        try (var con = database.getConnection();
             Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist;");
            while (rs.next()) {
                int id = rs.getInt("playlist_id");
                String name = rs.getString("playlist_name");
                temp.add(new Playlist(id, name));
            }
            return temp;
        }
    }

    /**
     *
     * @param name
     * @throws SQLException
     */
    @Override
    public void createPlaylist(String name) throws SQLException {
        var sql = "INSERT INTO playlist (playlist_name) VALUES(?);";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
        }
    }

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    @Override
    public Playlist getPlaylist(String name) throws SQLException {
        var sql = "SELECT FROM playlist WHERE playlist_name = ?;";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            var resultSet = st.getResultSet();
            var id = resultSet.getInt("playlist_id");
            var name1 = resultSet.getString("playlist_name");
            var playlist = new Playlist(id, name1);
            return playlist;

        }
    }

    /**
     *
     * @param name
     * @throws SQLException
     */
    @Override
    public void deletePlaylist(String name) throws SQLException {

        var sql = "DELETE FROM playlist WHERE playlist_name = ?;";
        try (var con = database.getConnection(); PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, name);
            st.executeUpdate();
            return;
        }
    }

    /**
     *
     * @param playlist_id
     * @return
     * @throws SQLException
     */
    @Override
    public List<Song> loadSongsFromPlaylist(int playlist_id) throws SQLException {
        var temp = new ArrayList<Song>();
        var sql = "SELECT song.song_id,song.song_title,song.song_filepath FROM playlist_song,song WHERE playlist_song.playlist_id=? AND playlist_song.song_id=song.song_id;";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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


    /**
     *
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    @Override
    public void AddSongToPlaylist(int playlist_id,int song_id) throws SQLException {
        var sql = "INSERT INTO playlist_song (playlist_id,song_id) VALUES (?,?);";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1,playlist_id);
            st.setInt(2,song_id);
            st.executeUpdate();
        }
    }

    /**
     *
     * @param playlist_id
     * @param song_id
     * @throws SQLException
     */
    @Override
    public void deleteFromPlaylist(int playlist_id,int song_id) throws SQLException {
        var sql = "DELETE FROM playlist_song WHERE playlist_id=? AND song_id=?;";
        try (var con = database.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, playlist_id);
            st.setInt(2,song_id);
            st.executeUpdate();
        }
    }


    /**
     *
     * @param playlist
     * @throws SQLException
     */
    @Override
    public void updatePlaylist(Playlist playlist) throws SQLException {
        String sql = "UPDATE playlist SET playlist_name=?  WHERE playlist_id=?;";
        try (var con = database.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, playlist.getPlayListName());
            preparedStatement.setInt(2, playlist.getPlaylistId());
            if (preparedStatement.executeUpdate() != 1) {
                System.out.println("Could not update Movie: " + playlist.toString());
            }
        }
    }
}