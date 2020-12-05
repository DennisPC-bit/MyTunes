package GUI.CONTROLLER;

import BE.Song;
import javafx.event.ActionEvent;
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

    private MainViewController mainMainViewController;

    public void setMainController(MainViewController mainMainViewController) {
        this.mainMainViewController = mainMainViewController;
    }

    public void setSelectedSong(Song song) {
        if (song != null) {
            selectedSong = song;
            titleTextField.setText(selectedSong.getTitle());
            filePathTextField.setText(selectedSong.getFilePath());
            System.out.println(selectedSong.getId());
        }
    }

    public void browse(ActionEvent event) {
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

    public void save(ActionEvent event) throws Exception {
        if (selectedSong != null) {
            selectedSong.setTitle(titleTextField.getText());
            selectedSong.setFilePath(filePathTextField.getText());
            mainMainViewController.getSongManager().updateSong(selectedSong.getId(), selectedSong);
            closeWindow();
        }
    }

    public void close(ActionEvent event) {
        closeWindow();
    }

    protected void closeWindow() {
        mainMainViewController.getWindowStage().close();
    }
}
