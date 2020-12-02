package GUI;

import BE.Playlist;
import BE.Song;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Slider volumeSlider;
    @FXML
    private TextField searchField;
    @FXML
    private TableView playlistTable;
    @FXML
    private TableColumn<Playlist, String> playlistNameColumn;
    @FXML
    private TableColumn<Playlist, String> playlistAmountOfSongsColumn;
    @FXML
    private TableView songsOnPlaylistTable;
    @FXML
    private TableColumn<Song, String> playlistSongsColumn;
    @FXML
    private TableColumn<Playlist, String> playlistTimeColumn;
    @FXML
    private TableView songsTable;
    @FXML
    private TableColumn<Song,String> songTableTitleColumn;
    @FXML
    private TableColumn<Song,String> songTableArtistColumn;
    @FXML
    private TableColumn<Song,String> songTableCategoryColumn;
    @FXML
    private TableColumn<Song,String> songTableTimeColumn;
    @FXML
    private Label currentSong;
    @FXML
    private TextField volumeSliderField;
    private Song selectedSong;
    private Song selectedSongOnPlayList;
    private Playlist selectedPlaylist;
    private double volumePercentage;
    private ObservableList<Song> songs;
    private ObservableList<Song> playlistSongs;
    private ObservableList<Playlist> playlists;

    /**
     * listens to whatever happens in the window and acts accordingly.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
        initTables();
        volumeFieldControl();
        volumeSliderControl();
        selectedSong();
        selectedSongOnPlayList();
        selectedPlaylist();
    }

    /**
     * Changes selected playlist to the playlist clicked in the playlistTable
     */
    private void selectedPlaylist() {
        this.playlistTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedPlaylist =(Playlist) newValue;
            if(selectedPlaylist!=null){
                this.playlistSongs = FXCollections.observableArrayList(selectedPlaylist.getSongList());
                this.songsOnPlaylistTable.setItems(playlistSongs);
                playlistSongsColumn.setCellValueFactory(cellData->cellData.getValue().toStringProperty());
            }
        }));
    }

    /**
     * Changes selected song  on playlist to the song clicked in the songsOnPlaylistTable
     */
    private void selectedSongOnPlayList() {
        this.songsOnPlaylistTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSongOnPlayList =(Song)newValue;
            if(selectedSongOnPlayList !=null)
                System.out.println(selectedSongOnPlayList.getTitle());
        }));
    }

    /**
     * Changes selected song to the song clicked in the songsTable
     */
    private void selectedSong() {
        this.songsTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSong =(Song)newValue;
            if(selectedSong !=null)
                System.out.println(selectedSong.getTitle());
        }));
    }

    /**
     * should load the lists from the memory, is temporarily almost empty lists
     */
    public void load() {
        this.songs = FXCollections.observableArrayList(new ArrayList<Song>());
        this.playlists = FXCollections.observableArrayList(new ArrayList<Playlist>());
        Playlist playlist1 = new Playlist("woah that's a nice playlist");
        Playlist playlist2 = new Playlist("woah that's a nice playlist2");
        songs.add(new Song(69,"lol","xd",123));
        songs.add(new Song(69,"lol2","xd",123));
        playlists.add(playlist1);
        playlists.add(playlist2);

        playlist1.addSong(new Song(69,"lol3","xd",123));
        playlist1.addSong(new Song(70,"lol4","xd",456));
        playlist2.addSong(new Song(71,"lol5","xd",123));
        playlist2.addSong(new Song(72,"lol6","xd",456));
    }

    /**
     * Puts values into the tables
     */
    private void initTables() {
        this.songsTable.setItems(songs);
        songTableTitleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());
        songTableArtistColumn.setCellValueFactory(cellData-> new SimpleStringProperty("123"));
        songTableCategoryColumn.setCellValueFactory(cellData-> new SimpleStringProperty("456"));
        songTableTimeColumn.setCellValueFactory(cellData-> new SimpleStringProperty("789"));

        this.songsOnPlaylistTable.setItems(playlistSongs);
        playlistSongsColumn.setCellValueFactory(cellData->cellData.getValue().toStringProperty());

        this.playlistTable.setItems(playlists);
        playlistNameColumn.setCellValueFactory(cellData->cellData.getValue().toStringProperty());
        playlistAmountOfSongsColumn.setCellValueFactory(cellData-> new SimpleStringProperty("123"));
        playlistTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty("123"));
    }

    /**
     *  Makes the volume field change when the volume slider is changed.
     */
    private void volumeSliderControl() {
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    volumePercentage = newValue.doubleValue();
                    volumeSliderField.setText(String.format("%.0f",volumePercentage));
                }
        );
    }

    /**
     * Makes the volume slider change when the volume field is changed to a valid value.
     */
    private void volumeFieldControl() {
        volumeSliderField.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try{
                        if(newValue.contains(","))
                            newValue=newValue.replaceAll(",",".");
                        volumeSlider.setValue(Integer.parseInt(newValue));
                    }
                    catch (IllegalArgumentException e){
                        //doesnt do anything if the input is invalid
                    }
                }
        );
    }

    /**
     * gets the value of the volume slider
     * @return the volume
     */
    public double getVolumePercentage() {
        return volumeSlider.getValue();
    }

    /**
     * should change songsTable, whenever the searchField changes.
     */
    public void search() {
        //TO DO implement this
        System.out.println(searchField.getText());
    }

    /**
     * Clears the searchField.
     */
    public void clearSearchButton() {
        searchField.setText("");
    }

    /**
     * Adds a new playlist.
     */
    public void addPlayListButton() {
        //TO DO implement this
    }

    /**
     * Edits the selected playlist.
     */
    public void editPlaylistButton() {
        //TO DO implement this
    }

    /**
     * Deletes the selected playlist.
     */
    public void deletePlaylistButton() {
        //TO DO implement this
    }

    /**
     * Removes the selected song from the current playlist.
     */
    public void removeFromPlaylistButton() {
        //TO DO implement this
    }

    /**
     * Adds Song to the current playlist.
     */
    public void addToPlaylistButton() {
        //TO DO implement this
    }

    /**
     * Moves a song up on the current playlist.
     */
    public void moveSongUpOnPlaylistButton() {
        //TO DO implement this
    }

    /**
     * Moves a song down on the current playlist
     */
    public void moveSongDownOnPlaylistButton() {
        //TO DO implement this
    }

    /**
     * Adds a new song
     */
    public void newSongButton() {
        //TO DO implement this
    }

    /**
     * Edits the selected song
     */
    public void editSongButton() {
        //TO DO implement this
    }

    /**
     * Deletes the selected song
     */
    public void deleteSongButton() {
        //TO DO implement this
    }

    /**
     * closes something i dont actually know what this was for.
     */
    public void closeButton() {
        //TO DO implement this
    }

    /**
     * Plays from the playlist
     */
    public void playButton() {
        //TO DO implement this
    }

    /**
     * Goes to the next song on the playlist
     */
    public void nextButton() {
        //TO DO implement this
    }

    /**
     * Goes to the last song on the playlist
     */
    public void previousButton() {
        //TO DO implement this
    }
}