package GUI.CONTROLLER;

import BE.Song;
import javafx.event.ActionEvent;
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

    public void setMainController(MainViewController mainMainViewController) {
        this.mainMainViewController = mainMainViewController;
    }

    public void browse(ActionEvent event) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                titleTextField.setText(selectedFile.getName());
                filePathTextField.setText(selectedFile.getAbsolutePath());
                mainMainViewController.getSongManager().createSong(new Song(titleTextField.getText(), filePathTextField.getText()));
                mainMainViewController.reloadSongTable();
                closeWindow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        closeWindow();
    }

    protected void closeWindow() {
        mainMainViewController.getWindowStage().close();
    }
}
