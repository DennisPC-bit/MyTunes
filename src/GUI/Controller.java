package GUI;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import BLL.SongManager;
import GUI.Dialogs.dialogController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import GUI.MODELS.SongModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Stage windowStage = new Stage();
    private Song selectedSong;
    private Song selectedSongOnPlayList;
    private Playlist selectedPlaylist;
    private double volumePercentage;
    private ObservableList<Song> songs;
    private ObservableList<Song> playlistSongs;
    private ObservableList<Playlist> playlists;

    private final PlaylistManager playlistManager = new PlaylistManager();
    private final SongManager songManager = new SongManager();
    private SongModel songModel;

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
     * should load the lists from the memory, is temporarily almost empty lists
     */
    public void load() {
        this.songs = FXCollections.observableArrayList(new ArrayList<>());
        this.playlists = FXCollections.observableArrayList(new ArrayList<>());
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

        songModel = new SongModel();

        this.songsTable.setItems(songModel.getSongs());
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
                    try {
                        if (newValue.contains(","))
                            newValue = newValue.replaceAll(",", ".");
                        volumeSlider.setValue(Integer.parseInt(newValue));
                    } catch (IllegalArgumentException e) {
                    }
                }
        );

        // Makes the volume field change when the volume slider is changed.
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    volumePercentage = newValue.doubleValue();
                    volumeSliderField.setText(String.format("%.0f", volumePercentage));
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

    public void addPlaylist(Playlist playlist){
        this.playlists.add(playlist);
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
        dialog("playlist name:","Add playlist","");
    }

    private void dialog(String labelFieldText,String dialogTitleText,String titleFieldText) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/dialog.fxml"));
        AnchorPane dialog = loader.load();
        dialogController controller = loader.getController();
        controller.setMainController(this);
        controller.setLabelField(labelFieldText);
        controller.setTitleField(titleFieldText);
        controller.setDialogTitle(dialogTitleText);
        windowStage = new Stage();
        windowStage.setScene(new Scene(dialog));
        windowStage.initModality(Modality.APPLICATION_MODAL);
        windowStage.alwaysOnTopProperty();
        windowStage.show();
    }

    /**
     * Edits the selected playlist.
     */
    public void editPlaylistButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Dialogs/dialog.fxml"));
        AnchorPane dialog = loader.load();
        dialogController controller = loader.getController();
        controller.setMainController(this);
        controller.setLabelField("Playlist name:");
        controller.setDialogTitle("Add Playlist");
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