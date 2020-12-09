package GUI.CONTROLLER;

import BE.Song;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddSongController extends Component {
    @FXML
    TextField filePathTextField;
    @FXML
    TextField titleTextField;

    private MainViewController mainMainViewController;

    /**
     * Sets the main view controller
     * @param mainViewController
     */
    public void setMainController(MainViewController mainViewController) {
        this.mainMainViewController = mainViewController;
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
                mainMainViewController.getSongManager().createSong(new Song(titleTextField.getText(), filePathTextField.getText()));
                mainMainViewController.reloadSongTable();
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the window
     */
    public void close() {
        mainMainViewController.getWindowStage().close();
    }
}
