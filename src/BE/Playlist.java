package BE;

import BLL.PlaylistManager;
import javafx.beans.property.*;

public class Playlist {

    private String playListName;
    private StringProperty playListNameProperty;
    protected SimpleDoubleProperty playlistDurationProperty;
    protected SimpleStringProperty playlistDurationStringProperty;
    private ObjectProperty<Integer> playlistSize = new SimpleObjectProperty<>();
    private int playlistId;

    /**
     * Constructor with playlistName
     *
     * @param playListName
     */
    public Playlist(String playListName) {
        initialize();
        setPlayListName(playListName);
    }

    /**
     * Constructor with id and playlistName
     *
     * @param id
     * @param playListName
     */
    public Playlist(int id, String playListName) {
        initialize();
        setPlaylistId(id);
        setPlayListName(playListName);
    }

    public Playlist(int id, String playListName, double totalDuration) {
        initialize();
        setPlaylistId(id);
        setPlayListName(playListName);
        setPlaylistDurationProperty(totalDuration);
        setPlaylistDurationStringProperty(totalDuration);
    }

    /**
     * initializes the variables
     */
    private void initialize() {
        playListNameProperty = new SimpleStringProperty();
        playlistDurationProperty = new SimpleDoubleProperty();
        playlistDurationStringProperty = new SimpleStringProperty();
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getPlayListName() {
        return playListName;
    }

    /**
     * Get the value of name property
     *
     * @return the value of name property
     */
    public StringProperty getPlayListNameProperty() {
        return playListNameProperty;
    }

    /**
     * Set the value of name.
     *
     * @param playListName new value of name
     */
    public void setPlayListName(String playListName) {
        this.playListName = playListName;
        this.playListNameProperty.setValue(playListName);
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     * Set the value of id
     *
     * @param playlistId new value of id
     */
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public SimpleStringProperty playlistDurationPropertyString() {
        return playlistDurationStringProperty;
    }

    public void setPlaylistDurationStringProperty(double duration) {
        var min = (int) duration;
        var sec = (int) duration / 60;
        String str = String.format("%d:%02d", min, sec);
        playlistDurationStringProperty.set(str);
    }

    public SimpleDoubleProperty getPlaylistDurationProperty() {
        return playlistDurationProperty;
    }

    public void setPlaylistDurationProperty(double duration) {
        playlistDurationProperty.set(duration);
    }

    public double getTotalDuration() {
        return playlistDurationProperty.get();
    }

    public String getTotalDurationString() {
        return playlistDurationStringProperty.get();
    }

    public ObjectProperty<Integer> getPlaylistSize() {
        PlaylistManager playlistManager = new PlaylistManager();
        try {
            playlistSize.setValue(playlistManager.loadSongsOnPlaylist(this.getPlaylistId()).size());
            return playlistSize;
        } catch (Exception e) {
            e.printStackTrace();
            return new SimpleObjectProperty<>(0);
        }
    }
}
