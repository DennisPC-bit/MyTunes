package BE;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {
    protected int id;
    protected boolean visible;
    protected String title;
    protected String filePath;

    public Song() {

    }

    public Song(int id, String title, String filePath) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.visible = true;
    }

    public StringProperty getTitleAsProperty(){
        return new SimpleStringProperty(title);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
