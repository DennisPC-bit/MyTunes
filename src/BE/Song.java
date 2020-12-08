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

    /**
     *
     * @param id
     * @param title
     * @param filePath
     */
    public Song(int id, String title, String filePath) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty();
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        duration = new SimpleDoubleProperty();
        getMeta();
    }

    /**
     *
     * @param title
     * @param filePath
     */
    public Song(String title, String filePath) {
        this.id = new SimpleIntegerProperty(-1);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty();
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        duration = new SimpleDoubleProperty();
        getMeta();
    }

    /**
     *
     * @param id
     * @param title
     * @param filePath
     * @param categoryId
     */
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


    /**
     *
     * @param id
     * @param title
     * @param artist
     * @param filePath
     * @param categoryId
     */
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


    /**
     * Get the value of meta.
     */
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

    /**
     * Get the value of id
     * @return the value of id
     */
    public int getId() {
        return id.get();
    }

    /**
     *
     * @return
     */
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Set the value of isVisible.
     * @return the value of isVisible
     */
    public boolean isVisible() {
        return visible.get();
    }

    /**
     *
     * @return
     */
    public SimpleBooleanProperty visibleProperty() {
        return visible;
    }

    /**
     * Set the value of Visible
     * @param visible new value of Visible
     */
    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    /**
     * Get the value of Duration
     * @return the value of Duration.
     */
    public double getDuration() {
        return duration.get();
    }

    /**
     *
     * @return duration
     */
    public SimpleDoubleProperty durationProperty() {
        return duration;
    }

    /**
     * Set the value of duration
     * @param duration new value of duration
     */
    public void setDuration(double duration) {
        this.duration.set(duration);
    }

    /**
     * Get the value of Artist
     * @return the value of Artist
     */
    public String getArtist() {
        return artist.get();
    }

    /**
     *
     * @return artist
     */
    public StringProperty artistProperty() {
        return artist;
    }

    /**
     * Set the value of Artist
     * @param artist new value of Artist
     */
    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    /**
     * Get the value of Title
     * @return the value of Title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     *
     * @return Title
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Set the value of title
     * @param title new value of title.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Get the value of filePath
     * @return the value of filepath
     */
    public String getFilePath() {
        return filePath.get();
    }

    /**
     * Set the value of filepath
     * @param filePath new value of filepath
     */
    public void setFilePath(String filePath) {
        this.filePath.setValue(filePath);
    }

    /**
     * Get the value of CategoryId
     * @return the value of CategoryId
     */
    public int getCategoryId() {
        return categoryId.get();
    }

    /**
     * Set the value of CategoryId
     * @param id new value of CategoryId
     */
    public void setCategoryId(int id) {
        categoryId.set(id);
    }

    /**
     *
     * @return value of title
     */
    public StringProperty toStringProperty() {
        return new SimpleStringProperty(this.title.getValue());
    }
}
