package GUI.Dialogs;

import BE.Playlist;
import GUI.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class dialogController {
    @FXML
    private Label labelField;
    @FXML
    private TextField titleField;
    @FXML
    private Label dialogTitle;
    private Controller mainController;

    public void setMainController(Controller mainController){
        this.mainController=mainController;
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
            mainController.addPlaylist(new Playlist(titleField.getText()));
            mainController.getWindowStage().close();
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        mainController.getWindowStage().close();
    }
}
