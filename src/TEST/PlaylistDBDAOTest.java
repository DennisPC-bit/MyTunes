package TEST;

import DAL.DAO.DB.PlaylistDBDAO;

public class PlaylistDBDAOTest {

    public static void main(String[] args) {
        var playlistDb = new PlaylistDBDAO();
        if (playlistDb.createPlaylist("Test"))
            System.out.println("Added playlist!");
        else System.out.println("Failed to add playlist!");
    }
}
