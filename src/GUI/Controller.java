package GUI;

import BE.InputAlert;
import BE.MusicPlayer;
import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import BLL.SongManager;
import GUI.Dialogs.dialogController;
import GUI.MODELS.SongModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private Song songPlaying;
    @FXML
    private TextField volumeSliderField;
    private Song selectedSong;
    private Song selectedSongOnPlayList;
    private Playlist selectedPlaylist;
    private double volumePercentage;
    private boolean playing=false;
    private ObservableList<Song> songs;
    private ObservableList<Song> playlistSongs;
    private ObservableList<Playlist> playlists;

    private PlaylistManager playlistManager = new PlaylistManager();
    private SongManager songManager = new SongManager();
    private InputAlert inputAlert = new InputAlert();
    private MusicPlayer musicPlayer = new MusicPlayer();
    private Stage windowStage = new Stage();
    private SongModel songModel;

    public Controller(){
        playlistManager.setMainController(this);
        songManager.setMainController(this);

    }

    /**
     * listens to whatever happens in the window and acts accordingly.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
        initTables();
        volumeFieldControl();
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
            if(selectedSongOnPlayList !=null){
                currentSong.setText(selectedSongOnPlayList.getTitle());
                songPlaying=selectedSongOnPlayList;
            }
        }));
    }

    /**
     * Changes selected song to the song clicked in the songsTable
     */
    private void selectedSong() {
        this.songsTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSong =(Song)newValue;
            if(selectedSong !=null){
                currentSong.setText(selectedSong.getTitle());
                songPlaying=selectedSong;
            }
        }));
    }

    /**
     * should load the lists from the db
     */
    public void load() {
        try {
        this.playlists = FXCollections.observableArrayList(PlaylistManager.loadPlaylists());
        this.playlistTable.setItems(playlists);
        this.songs = FXCollections.observableArrayList(SongManager.loadSongs());
        this.songsTable.setItems(songs);
        }
        catch (Exception e){
            playlists=FXCollections.observableArrayList(new ArrayList<>());
            this.playlistTable.setItems(playlists);

            songs=FXCollections.observableArrayList(new ArrayList<>());
            this.songsTable.setItems(songs);
            inputAlert.showAlert("You are not connected to the Database. Nothing will be saved !");
        }
    }

    /**
     * Puts values into the tables
     */
    private void initTables() {
        songModel = new SongModel();
        songTableTitleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());
        songTableArtistColumn.setCellValueFactory(cellData-> new SimpleStringProperty("123"));
        songTableCategoryColumn.setCellValueFactory(cellData-> new SimpleStringProperty("456"));
        songTableTimeColumn.setCellValueFactory(cellData-> new SimpleStringProperty("789"));

        playlistSongsColumn.setCellValueFactory(cellData->cellData.getValue().toStringProperty());

        playlistNameColumn.setCellValueFactory(cellData->cellData.getValue().getPlayListNameProperty());
        playlistAmountOfSongsColumn.setCellValueFactory(cellData-> new SimpleStringProperty("123"));
        playlistTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty("123"));
    }

    /**
     * Makes the volume slider change when the volume field is changed to a valid value.
     */
    private void volumeFieldControl() {
        volumeSliderField.textProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    try {
                        if (newValue.contains(","))
                            newValue = newValue.replaceAll(",", ".");
                        volumeSlider.setValue(Integer.parseInt(newValue));
                        musicPlayer.setVolume(volumeSlider.getValue());
                        musicPlayer.setVolume(volumePercentage/100);
                    } catch (IllegalArgumentException e) {
                    }
                }
        );

        // Makes the volume field change when the volume slider is changed.
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    volumePercentage = newValue.doubleValue();
                    volumeSliderField.setText(String.format("%.0f", volumePercentage));
                    musicPlayer.setVolume(volumePercentage/100);
                }
        );
    }

    /**
     * gets the value of the volume slider
     *
     * @return the volume
     */
    public double getVolumePercentage() {
        return volumeSlider.getValue();
    }

    public Stage getWindowStage() {
        return windowStage;
    }

    /**
     * should change songsTable, whenever the searchField changes.
     */
    public void search() {
        this.songsTable.setItems(FXCollections.observableList(songModel.searchSong(searchField.getText())));
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
    public void addPlayListButton() throws IOException {
        dialog("playlist name:","Add playlist","",1);
    }

    public void addPlaylist(Playlist playlist){
        try {
            playlistManager.createPlaylist(playlist.getPlayListName());
            load();
        } catch (Exception e) {
            playlists.add(new Playlist(playlist.getPlayListName()));
        }
    }

    /**
     * Edits the selected playlist.
     */
    public void editPlaylistButton() throws IOException {
        if(selectedPlaylist!=null){
        dialog("playlist name:", "Edit playlist",selectedPlaylist.getPlayListName(),2);
        }
    }

    public void editPlaylist(String newTitle){
        try {
            playlistManager.deletePlaylist(selectedPlaylist.getPlayListName());
            playlistManager.createPlaylist(newTitle);
            load();
        } catch (Exception e) {
            playlists.add(new Playlist(newTitle,selectedPlaylist.getSongList()));
            playlists.remove(selectedPlaylist);
        }
    }

    /**
     * Opens a dialog window
     * @param labelFieldText
     * @param dialogTitleText
     * @param titleFieldText
     * @param mode
     * @throws IOException
     */
    private void dialog(String labelFieldText,String dialogTitleText,String titleFieldText, int mode) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/dialog.fxml"));
        AnchorPane dialog = loader.load();
        dialogController controller = loader.getController();
        controller.setMainController(this);
        controller.setLabelField(labelFieldText);
        controller.setTitleField(titleFieldText);
        controller.setDialogTitle(dialogTitleText);
        controller.setMode(mode);
        windowStage = new Stage();
        windowStage.setScene(new Scene(dialog));
        windowStage.initModality(Modality.APPLICATION_MODAL);
        windowStage.alwaysOnTopProperty();
        windowStage.show();
    }

    /**
     * Deletes the selected playlist.
     */
    public void deletePlaylistButton() {
        try {
            playlistManager.deletePlaylist(selectedPlaylist.getPlayListName());
            load();
        } catch (Exception e) {
            playlists.remove(selectedPlaylist);
        }
    }

    /**
     * Removes the selected song from the current playlist.
     */
    public void removeFromPlaylistButton() {
        selectedPlaylist.removeSong(selectedSongOnPlayList);
    }

    /**
     * Adds Song to the current playlist.
     */
    public void addToPlaylistButton() {
        selectedPlaylist.addSong(selectedSong);
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
        FileChooser fileChooser = new FileChooser();
        windowStage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3-Files","*.mp3"));
        File selectedFile=fileChooser.showOpenDialog(windowStage);
        if(selectedFile!=null){
        songManager.createSong(selectedFile.getName().substring(0,selectedFile.getName().indexOf('.')),selectedFile.getPath());
        load();
        }
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
        songManager.deleteSong(selectedSong.getTitle());
        load();
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
        if(selectedSong!=null&&!playing){
            musicPlayer.setSong(selectedSong);
            musicPlayer.play();
            playing=!playing;
        }
        else{
            musicPlayer.pause();
            playing=!playing;
        }
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