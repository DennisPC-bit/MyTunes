package BE;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public String playListName;
    private int playlistId;
    private final List<Song> songList;

    public void addSong(Song song){
        songList.add(song);
    }

    public void removeSong(Song song){
        songList.remove(song);
    }

    public void editSong(Song newSong,Song oldSong){
        removeSong(oldSong);
        addSong(newSong);
    }


    public Playlist(String playListName) {
        this.songList = new ArrayList();
        this.playListName = playListName;
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
