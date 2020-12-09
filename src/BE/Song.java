package BE;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Song {
    private SimpleIntegerProperty id;
    private SimpleBooleanProperty visible;
    private StringProperty title;
    protected StringProperty artist;
    protected SimpleStringProperty duration;
    protected StringProperty filePath;
    protected SimpleIntegerProperty categoryId;
    protected SimpleStringProperty categoryName;
    protected Media media;

    /**
     * Initialize a new Song instance.
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
        duration = new SimpleStringProperty("");
        getMeta();
    }

    /**
     * Initialize a new Song instance.
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
        duration = new SimpleStringProperty("");
        getMeta();
    }

    /**
     * Initialize a new Song instance.
     *
     * @param id           song id
     * @param title        song title
     * @param filePath     song filepath
     * @param categoryName song category
     */
    public Song(int id, String title, String filePath, String categoryName) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty();
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        this.categoryName = new SimpleStringProperty(categoryName);
        duration = new SimpleStringProperty("");
        getMeta();
    }


    /**
     * Initialize a new Song instance.
     *
     * @param id           song id
     * @param title        song title
     * @param artist       song artist
     * @param filePath     song filepath
     * @param categoryName song categoryName
     */
    public Song(int id, String title, String artist, String filePath, String categoryName) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        this.categoryName = new SimpleStringProperty(categoryName);
        duration = new SimpleStringProperty("");
        getMeta();
    }

    /**
     * Initialize a new Song instance.
     *
     * @param id           song id
     * @param title        song title
     * @param artist       song artist
     * @param filePath     song filepath
     * @param categoryName song categoryName
     */
    public Song(int id, String title, String artist, String filePath, int categoryId, String categoryName) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.visible = new SimpleBooleanProperty(true);
        this.filePath = new SimpleStringProperty(filePath);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        this.categoryName = new SimpleStringProperty(categoryName);
        duration = new SimpleStringProperty("");
        getMeta();
    }


    /**
     * Get the value of meta.
     */
    public void getMeta() {
        if (this.getFilePath() != null) {
            var file = new File(getFilePath());
            if (file.exists()) {
                media = new Media(file.toURI().toString());
                if (this.getDuration().equals("")) {
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.setOnReady(new Runnable() {
                        @Override
                        public void run() {
                            setDuration(String.format("%02d", (int) media.getDuration().toMinutes()) + ":" + String.format("%02d", ((int) media.getDuration().toSeconds() % 60)));
                        }
                    });
                }
            }
            media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> c) -> {
                if (c.wasAdded()) {
                    if ("artist".equals(c.getKey()) && this.getArtist() == null) {
                        setArtist(c.getValueAdded().toString());
                    }
                    if ("title".equals(c.getKey()) && this.getTitle() == null) {
                        setTitle(c.getValueAdded().toString());
                    }
                    if ("album".equals(c.getKey())) {
                        //album = c.getValueAdded().toString();
                    }
                }

            });
        }
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id.get();
    }

    /**
     * @return
     */
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id.set(id);
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public SimpleIntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public void setCategoryId(int id) {
        categoryId.set(id);
    }

    /**
     * Set the value of isVisible.
     *
     * @return the value of isVisible
     */
    public boolean isVisible() {
        return visible.get();
    }

    /**
     * @return
     */
    public SimpleBooleanProperty visibleProperty() {
        return visible;
    }

    /**
     * Set the value of Visible
     *
     * @param visible new value of Visible
     */
    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    /**
     * Get the value of Duration
     *
     * @return the value of Duration.
     */
    public String getDuration() {
        return duration.get();
    }

    /**
     * @return duration
     */
    public SimpleStringProperty durationProperty() {
        return duration;
    }

    /**
     * Set the value of duration
     *
     * @param duration new value of duration
     */
    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    /**
     * Get the value of Artist
     *
     * @return the value of Artist
     */
    public String getArtist() {
        return artist.get();
    }

    /**
     * @return artist
     */
    public StringProperty artistProperty() {
        return artist;
    }

    /**
     * Set the value of Artist
     *
     * @param artist new value of Artist
     */
    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    /**
     * Get the value of Title
     *
     * @return the value of Title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * @return Title
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Get the value of filePath
     *
     * @return the value of filepath
     */
    public String getFilePath() {
        return filePath.get();
    }

    /**
     * Set the value of filepath
     *
     * @param filePath new value of filepath
     */
    public void setFilePath(String filePath) {
        this.filePath.setValue(filePath);
    }

    /**
     * Get the value of CategoryId
     *
     * @return the value of CategoryId
     */
    public String getCategoryName() {
        return categoryName.get();
    }

    public SimpleStringProperty categoryNameProperty() {
        return categoryName;
    }


    /**
     * Set the value of CategoryId
     *
     * @param newCategoryName new value of CategoryName
     */
    public void setCategoryName(String newCategoryName) {
        categoryName.set(newCategoryName);
    }

    /**
     * @return value of title
     */
    public StringProperty toStringProperty() {
        return new SimpleStringProperty(this.title.getValue());
    }
}
