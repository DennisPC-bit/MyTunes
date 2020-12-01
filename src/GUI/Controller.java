package GUI;

import BE.Playlist;
import BE.Song;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private double volumePercentage;
    private String search;
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
    }

    /**
     * should load the lists from the memory, is temporarily almost empty lists
     */
    public void load() {
        this.songs= FXCollections.observableArrayList(new ArrayList<Song>());
        this.playlistSongs= FXCollections.observableArrayList(new ArrayList<Song>());
        this.playlists= FXCollections.observableArrayList(new ArrayList<Playlist>());

        songs.add(new Song(69,"lol","xd",123));
        playlistSongs.add(new Song(69,"lol","xd",123));
        playlists.add(new Playlist("woah thats a nice playlist"));
    }

    /**
     * Puts values into the tables
     */
    private void initTables() {
        this.songsTable.setItems(songs);
        this.songsOnPlaylistTable.setItems(playlistSongs);
        this.playlistTable.setItems(playlists);

        songTableTitleColumn.setCellValueFactory(cellData->cellData.getValue().titleProperty());
        songTableArtistColumn.setCellValueFactory(cellData-> new SimpleStringProperty("123"));
        songTableCategoryColumn.setCellValueFactory(cellData-> new SimpleStringProperty("456"));
        songTableTimeColumn.setCellValueFactory(cellData-> new SimpleStringProperty("789"));

        playlistSongsColumn.setCellValueFactory(cellData->cellData.getValue().toStringProperty());

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
     * Makes the volume slider change when the volume field is changed.
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
     * should change songsTable, whenever the searchField changes
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

    public void addPlayListButton(ActionEvent actionEvent) {
    }

    public void editPlaylistButton(ActionEvent actionEvent) {
    }

    public void deletePlaylistButton(ActionEvent actionEvent) {
    }

    public void removeFromPlaylistButton(ActionEvent actionEvent) {
    }

    public void addToPlaylistButton(ActionEvent actionEvent) {
    }

    public void moveSongUpOnPlaylistButton(ActionEvent actionEvent) {
    }

    public void moveSongDownOnPlaylistButton(ActionEvent actionEvent) {
    }

    public void newSongButton(ActionEvent actionEvent) {
    }

    public void editSongButton(ActionEvent actionEvent) {
    }

    public void deleteSongButton(ActionEvent actionEvent) {
    }

    public void closeButton(ActionEvent actionEvent) {
    }

    public void playButton(ActionEvent actionEvent) {
    }

    public void nextButton(ActionEvent actionEvent) {
    }

    public void previousButton(ActionEvent actionEvent) {
    }
}