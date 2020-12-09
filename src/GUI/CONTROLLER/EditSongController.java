package GUI.CONTROLLER;

import BE.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EditSongController extends Component implements Initializable {
    @FXML
    TextField filePathTextField;
    @FXML
    TextField titleTextField;
    @FXML
    TextField artistTextField;
    @FXML
    ComboBox genreComboBox;
    @FXML
    Button saveButton;

    private MainViewController mainViewController;
    private Song selectedSong;
    private Map<Integer, String> genres;
    private String selectedCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCategory();
    }

    /**
     * Initialize the combo box to listen to when a new item is selected.
     */
    private void selectedCategory() {
        genreComboBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            selectedCategory = (String) newValue;
            System.out.println("Selected category: " + selectedCategory);
        }));
    }

    /**
     * Assign the genre combo box to have the specfied hash map genres.
     *
     * @param genres The genres to add.
     */
    public void setGenreComboBox(Map<Integer, String> genres) {
        this.genres = new HashMap<>(genres);
        genreComboBox.getItems().clear();
        genreComboBox.getItems().addAll(genres.values());
    }

    /**
     * Sets the maing view controller
     *
     * @param mainViewController
     */
    public void setMainController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * Find the specfiied category name and return its id.
     *
     * @param categoryName The category name to find.
     * @return
     */
    private int getCategoryIdFromName(String categoryName) {
        for (var category : genres.entrySet()) {
            if (category.getValue() == categoryName) {
                return category.getKey();
            }
        }
        return -1;
    }

    /**
     * Sets the selected song
     *
     * @param song the selected song
     */
    public void setSelectedSong(Song song) {
        if (song != null) {
            selectedSong = song;
            titleTextField.setText(selectedSong.getTitle());
            filePathTextField.setText(selectedSong.getFilePath());
        }
    }

    /**
     * Opens a window in which you can select files
     */
    public void browse() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                titleTextField.setText(selectedFile.getName());
                filePathTextField.setText(selectedFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(ActionEvent event) {
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the changes made to the selected song.
     *
     * @throws Exception
     */
    public void save() throws Exception {
        if (selectedSong != null) {
            selectedSong.setTitle(titleTextField.getText());
            selectedSong.setFilePath(filePathTextField.getText());
            selectedSong.setArtist(artistTextField.getText());
            selectedSong.setCategoryId(getCategoryIdFromName(selectedCategory));
            mainViewController.getSongManager().updateSong(selectedSong.getId(), selectedSong);
            mainViewController.reloadSongTable();
            close();
        }
    }

    /**
     * Close the window.
     */
    public void close() {
        mainViewController.getWindowStage().close();
        ;
    }
}
