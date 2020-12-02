package GUI.CONTROLLER;

import BE.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddPlaylistController {
    @FXML
    private Label labelField;
    @FXML
    private TextField titleField;
    @FXML
    private Label dialogTitle;
    private MainViewController mainMainViewController;
    private int mode;

    public void setMainController(MainViewController mainMainViewController){
        this.mainMainViewController = mainMainViewController;
    }
    public void setMode(int i){
        this.mode=i;
    }

    public void setLabelField(String labelText) {
        this.labelField.setText(labelText);
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle.setText(dialogTitle);
    }

    public void setTitleField(String titleFieldText) {
        this.titleField.setText(titleFieldText);
    }

    public void confirmButton(ActionEvent actionEvent) {
        if(titleField!=null&&!titleField.getText().isEmpty()){
            switch (mode){
                case (1) -> {
                    mainMainViewController.addPlaylist(new Playlist(titleField.getText()));
                }
                case(2)->{
                    mainMainViewController.editPlaylist(titleField.getText());
                }
            }
            mainMainViewController.getWindowStage().close();
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        mainMainViewController.getWindowStage().close();
    }
}
