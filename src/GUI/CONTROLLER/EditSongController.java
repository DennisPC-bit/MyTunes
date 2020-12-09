package GUI.CONTROLLER;

import BE.Song;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditSongController extends Component {
    @FXML
    TextField filePathTextField;
    @FXML
    TextField titleTextField;

    public Song selectedSong;

    private MainViewController mainViewController;

    /**
     * Sets the maing view controller
     * @param mainViewController
     */
    public void setMainController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * Sets the selected song
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

    /**
     * Saves the changes made
     * @throws Exception
     */
    public void save() throws Exception {
        if (selectedSong != null) {
            selectedSong.setTitle(titleTextField.getText());
            selectedSong.setFilePath(filePathTextField.getText());
            mainViewController.getSongManager().updateSong(selectedSong.getId(), selectedSong);
            close();
        }
    }

    public void close() {
        mainViewController.getWindowStage().close();;
    }
}
