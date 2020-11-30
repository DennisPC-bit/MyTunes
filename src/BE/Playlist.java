package BE;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public String playListName;
    private int playlistId;
    private final List<Song> songList;


    public Playlist() {
        this.songList = new ArrayList();
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        playlistId = playlistId;
    }

    public List<Song> getSongList() {
        return songList;
    }
}
