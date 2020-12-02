package BE;

import javafx.beans.property.*;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;


import java.io.File;

public class Song {
    private SimpleIntegerProperty id;
    private SimpleBooleanProperty visible;

    private StringProperty title;
    protected StringProperty artist;
    protected SimpleDoubleProperty duration;
    protected StringProperty filePath;
    protected SimpleIntegerProperty categoryId;
    protected Media media;

    public Song() {

    }

    public Song(int id, String title, String filePath) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty();
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        duration = new SimpleDoubleProperty();
        getMeta();
    }

    public Song(String title, String filePath) {
        this.id = new SimpleIntegerProperty(-1);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty();
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        duration = new SimpleDoubleProperty();
        getMeta();
    }

    public Song(int id, String title, String filePath, int categoryId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty();
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        duration = new SimpleDoubleProperty();
        getMeta();
    }

    public Song(int id, String title, String artist, String filePath, int categoryId) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        duration = new SimpleDoubleProperty();
        getMeta();
    }

    public void getMeta() {

        var file = new File(getFilePath());
        if (file.exists()) {
            media = new Media(file.toURI().toString());

            media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> c) -> {
                if (c.wasAdded()) {
                    if ("artist".equals(c.getKey())) {
                        setArtist(c.getValueAdded().toString());
                    } else if ("title".equals(c.getKey())) {
                        setTitle(c.getValueAdded().toString());
                    } else if ("album".equals(c.getKey())) {
                        //album = c.getValueAdded().toString();
                    }
                }
            });
            setDuration(media.getDuration().toMinutes());
        }
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

    public double getDuration() {
        return duration.get();
    }

    public SimpleDoubleProperty durationProperty() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration.set(duration);
    }

    public String getArtist() {
        return artist.get();
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
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

    public StringProperty toStringProperty() {
        return new SimpleStringProperty(this.title.getValue());
    }
}
