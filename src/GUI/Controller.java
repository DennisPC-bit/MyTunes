package GUI;

import BE.Playlist;
import BE.Song;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Slider volumeSlider;
    @FXML
    private TextField searchField;
    @FXML
    private TableView playlistTable;
    @FXML
    private TableColumn playlistNameColumn;
    @FXML
    private TableColumn playlistAmountOfSongsColumn;
    @FXML
    private TableView songsOnPlaylistTable;
    @FXML
    private TableColumn playlistSongsColumn;
    @FXML
    private TableView songsTable;
    @FXML
    private TableColumn songTableTitleColumn;
    @FXML
    private TableColumn songTableArtistColumn;
    @FXML
    private TableColumn songTableCategoryColumn;
    @FXML
    private TableColumn songTableTimeColumn;
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
        this.songsTable.setItems(songs);
        this.songsOnPlaylistTable.setItems(playlistSongs);
        this.playlistTable.setItems(playlists);

        // Makes the volume slider change when the volume field is changed.
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

        // Makes the volume field change when the volume slider is changed.
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    volumePercentage = newValue.doubleValue();
                    volumeSliderField.setText(String.format("%.0f",volumePercentage));
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
