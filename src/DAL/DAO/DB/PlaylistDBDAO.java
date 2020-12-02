package DAL.DAO.DB;

import BE.Playlist;
import BLL.PlaylistManager;
import DAL.DB.DbConnectionHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDBDAO {
    protected List<Playlist> playlists;
    protected DbConnectionHandler database;
    protected PlaylistManager playlistManager;

    public void setPlaylistManager(PlaylistManager playlistManager){
        this.playlistManager=playlistManager;
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
}
