package BE;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {
    private SimpleIntegerProperty id;
    private SimpleBooleanProperty visible;
    private StringProperty title;
    protected StringProperty filePath;
    protected SimpleIntegerProperty categoryId;

    public Song() {

    }

    public Song(int id, String title, String filePath) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
    }

    public Song(String title, String filePath) {
        this.id = new SimpleIntegerProperty(-1);
        this.title = new SimpleStringProperty(title);
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
    }

    public Song(int id, String title, String filePath, int categoryId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        this.categoryId = new SimpleIntegerProperty(categoryId);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public boolean isVisible() {
        return visible.get();
    }

    public SimpleBooleanProperty visibleProperty() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getFilePath() {
        return filePath.get();
    }

    public void setFilePath(String filePath) {
        this.filePath.setValue(filePath);
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public void setCategoryId(int id) {
        categoryId.set(id);
    }
}
