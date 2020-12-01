package BE;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty year;
    private SimpleBooleanProperty visible;
    private StringProperty title;
    protected String filePath;

    public Song() {

    }

    public Song(int id, String title, String filePath, int year){
        this.year=new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(year);
        this.visible= new SimpleBooleanProperty(true);
        this.filePath=filePath;
    }

    public Song(String title, String filePath){
        this.id=new SimpleIntegerProperty(-1);
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(-1);
        this.visible= new SimpleBooleanProperty(true);
        this.filePath=filePath;
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

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
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
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
