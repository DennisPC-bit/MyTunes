package GUI.CONTROLLER;

import BE.InputAlert;
import BE.MusicPlayer;
import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import BLL.SongManager;
import GUI.MODEL.SongModel;
import GUI.Main;
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
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
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
    private TableColumn<Song, String> songTableTitleColumn;
    @FXML
    private TableColumn<Song, String> songTableArtistColumn;
    @FXML
    private TableColumn<Song, String> songTableCategoryColumn;
    @FXML
    private TableColumn<Song, Number> songTableTimeColumn;
    @FXML
    private Label currentSong;
    private Song songPlaying;
    @FXML
    private TextField volumeSliderField;
    private Song selectedSong;
    private Song selectedSongOnPlayList;
    private Playlist selectedPlaylist;
    private double volumePercentage;
    private boolean playing = false;
    private ObservableList<Song> songs;
    private ObservableList<Song> playlistSongs;
    private ObservableList<Playlist> playlists;
    private static final PlaylistManager playlistManager = new PlaylistManager();
    private static final SongManager songManager = new SongManager();
    private InputAlert inputAlert = new InputAlert();
    private MusicPlayer musicPlayer = new MusicPlayer();
    private Stage windowStage = new Stage();
    private SongModel songModel;

    public MainViewController() {
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
            this.selectedPlaylist = (Playlist) newValue;
            if (selectedPlaylist != null) {
                try{
                    this.playlistSongs=FXCollections.observableArrayList(playlistManager.loadSongsOnPlaylist(selectedPlaylist.getPlaylistId()));
                    songsOnPlaylistTable.setItems(playlistSongs);
                }
                catch(Exception e){
                    e.printStackTrace();}
            }
        }));
    }


    /**
     * Changes selected song  on playlist to the song clicked in the songsOnPlaylistTable
     */
    private void selectedSongOnPlayList() {
        this.songsOnPlaylistTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSongOnPlayList = (Song) newValue;
            if (selectedSongOnPlayList != null) {
                currentSong.setText(selectedSongOnPlayList.getTitle());
                songPlaying = selectedSongOnPlayList;
                this.songsTable.getSelectionModel().clearSelection();
            }
        }));
    }

    /**
     * Changes selected song to the song clicked in the songsTable
     */
    private void selectedSong() {
        this.songsTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSong = (Song) newValue;
            if (selectedSong != null) {
                currentSong.setText(selectedSong.getTitle());
                songPlaying = selectedSong;
                this.songsOnPlaylistTable.getSelectionModel().clearSelection();
            }
        }));
    }

    /**
     * should load the lists from the db, if it cannot load from db, it will load from local storage.
     */
    public void load() {
        try {
            this.playlists = FXCollections.observableArrayList(PlaylistManager.loadPlaylists());
            reloadPlaylistTable();
            this.songs = FXCollections.observableArrayList(SongManager.loadSongs());
            reloadSongTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Puts values into the tables
     */
    private void initTables() {
        songModel = new SongModel();
        songTableTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        songTableArtistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        songTableCategoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty("456"));
        songTableTimeColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());

        playlistSongsColumn.setCellValueFactory(cellData -> cellData.getValue()==null?new SimpleStringProperty(""):cellData.getValue().toStringProperty());

        playlistNameColumn.setCellValueFactory(cellData -> cellData.getValue().getPlayListNameProperty());
        playlistAmountOfSongsColumn.setCellValueFactory(cellData -> new SimpleStringProperty("123"));
        playlistTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty("123"));
    }

    public void reloadSongTable() {
        try {
            this.songsTable.setItems(FXCollections.observableList(songManager.loadSongs()));
        }catch (Exception exception) {
            System.out.println("could not load songs locally");
        }
    }

    private void reloadPlaylistTable() {
        try {
            this.playlistTable.setItems(FXCollections.observableList(playlistManager.loadPlaylists()));
        } catch (Exception exception) {
            System.out.println("could not load playlistTable locally");
        }
    }

    private void reloadSongsOnPlaylist() {
        try {
            this.songsOnPlaylistTable.setItems(FXCollections.observableList(playlistManager.loadSongsOnPlaylist(selectedPlaylist.getPlaylistId())));
        } catch (Exception exception) {
            System.out.println("could not load playlistTable locally");
        }
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
                        musicPlayer.setVolume(volumePercentage / 100);
                    } catch (IllegalArgumentException e) {
                    }
                }
        );

        // Makes the volume field change when the volume slider is changed.
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    volumePercentage = newValue.doubleValue();
                    volumeSliderField.setText(String.format("%.0f", volumePercentage));
                    musicPlayer.setVolume(volumePercentage / 100);
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
        dialog("playlist name:", "Add playlist", "", 1);
    }

    public void addPlaylist(Playlist playlist) {
        try {
            playlistManager.createPlaylist(playlist.getPlayListName());
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits the selected playlist.
     */
    public void editPlaylistButton() throws IOException {
        if (selectedPlaylist != null) {
            dialog("playlist name:", "Edit playlist", selectedPlaylist.getPlayListName(), 2);
        }
    }

    public void editPlaylist(String newTitle) {
        try {
            selectedPlaylist.setPlayListName(newTitle);
            playlistManager.updatePlaylist(selectedPlaylist);
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog window
     *
     * @param labelFieldText
     * @param dialogTitleText
     * @param titleFieldText
     * @param mode
     * @throws IOException
     */
    private void dialog(String labelFieldText, String dialogTitleText, String titleFieldText, int mode) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("DIALOGUE/AddPlaylist.fxml"));
        AnchorPane dialog = loader.load();
        AddPlaylistController controller = loader.getController();
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
        var result = InputAlert.showMessageBox("Are you sure?", String.format("Deleting %s", selectedPlaylist.getPlayListName()),
                "You cannot undo this action once it's done!", Alert.AlertType.CONFIRMATION);
        if (result.get() == ButtonType.OK) {
        try {
            playlistManager.deletePlaylist(selectedPlaylist.getPlayListName());
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    /**
     * Removes the selected song from the current playlist.
     */
    public void removeFromPlaylistButton() {
        if(selectedPlaylist!=null&&selectedSongOnPlayList!=null){
        try{
        playlistManager.deleteSongFromPlaylist(selectedPlaylist.getPlaylistId(),selectedSongOnPlayList.getId());
        reloadSongsOnPlaylist();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }
    }

    /**
     * Adds Song to the current playlist.
     */
    public void addToPlaylistButton() {
        if(selectedPlaylist!=null){
            try {
                for(Song song : playlistManager.loadSongsOnPlaylist(selectedPlaylist.getPlaylistId())){
                    if(song.getId()==selectedSong.getId())
                        return;
                }
                playlistManager.addSongsToPlaylist(selectedPlaylist.getPlaylistId(),selectedSong.getId());
                reloadSongsOnPlaylist();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("DIALOGUE/AddSong.fxml"));
        AnchorPane dialog = null;
        try {
            dialog = loader.load();
            AddSongController controller = loader.getController();
            controller.setMainController(this);
            windowStage = new Stage();
            windowStage.setScene(new Scene(dialog));
            windowStage.initModality(Modality.APPLICATION_MODAL);
            windowStage.alwaysOnTopProperty();
            windowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits the selected song
     */
    public void editSongButton() {
        if (selectedSong != null) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("DIALOGUE/EditSong.fxml"));
            AnchorPane dialog = null;
            try {
                dialog = loader.load();
                EditSongController controller = loader.getController();
                controller.setMainController(this);
                controller.setSelectedSong(selectedSong);
                windowStage = new Stage();
                windowStage.setScene(new Scene(dialog));
                windowStage.initModality(Modality.APPLICATION_MODAL);
                windowStage.alwaysOnTopProperty();
                windowStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            InputAlert.showMessageBox("No song selected", "Cannot modify something that doesn't exist!", "Please select a song.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Deletes the selected song
     */
    public void deleteSongButton() {
        var result = InputAlert.showMessageBox("Are you sure?", String.format("Deleting %s", selectedSong.getTitle()),
                "You cannot undo this action once it's done!", Alert.AlertType.CONFIRMATION);
        if (result.get() == ButtonType.OK) {
            try {
                songManager.deleteSong(selectedSong.getId());
                reloadSongsOnPlaylist();
            } catch (Exception e) {
                e.printStackTrace();
            }
            load();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Adds multiple songs at once.
     */
    public void bulkAddButton() {
        FileChooser fileChooser = new FileChooser();
        windowStage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3-Files", "*.mp3"));
        List<File> selectedFiles = new ArrayList<>();
        try{
        selectedFiles.addAll(fileChooser.showOpenMultipleDialog(windowStage));
        try {
                if(!selectedFiles.isEmpty()){
                    for (File selectedFile : selectedFiles)
                        songManager.createSong(selectedFile.getName().substring(0, selectedFile.getName().indexOf('.')), selectedFile.getPath());
                    load();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch(Exception e){
            System.out.println( "selected files is empty: " + selectedFiles.isEmpty());
        }

    }

    /**
     * Plays from the playlist
     */
    public void playButton() {
        if(selectedSongOnPlayList!= null && !playing)
        {
            musicPlayer.setSong(selectedSongOnPlayList);
            musicPlayer.setVolume(volumePercentage);
            musicPlayer.play();
        }
        else if (selectedSong != null && !playing) {
            musicPlayer.setSong(selectedSong);
            musicPlayer.setVolume(volumePercentage);
            musicPlayer.play();
        } else {
            musicPlayer.pause();
        }
        playing = !playing;
    }

    /**
     * Goes to the next song on the playlist
     */
    public void nextButton() {
        if(selectedSongOnPlayList!=null){
            this.songsOnPlaylistTable.getSelectionModel().selectBelowCell();
            setSong(selectedSongOnPlayList);
        }
        if(selectedSong!=null){
            this.songsTable.getSelectionModel().selectBelowCell();
            setSong(selectedSong);
        }
    }

    private void setSong(Song selectedSong) {
        musicPlayer.pause();
        musicPlayer.setSong(selectedSong);
        musicPlayer.play();
    }

    /**
     * Goes to the last song on the playlist
     */
    public void previousButton() {
        if(selectedSongOnPlayList!=null){
            this.songsOnPlaylistTable.getSelectionModel().selectAboveCell();
            setSong(selectedSongOnPlayList);
        }
        if(selectedSong!=null){
            this.songsTable.getSelectionModel().selectAboveCell();
            setSong(selectedSong);
        }
    }

    public SongManager getSongManager() {
        return songManager;
    }
}