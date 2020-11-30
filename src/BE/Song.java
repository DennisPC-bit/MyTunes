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

    public Song(int id, String title, int year){
        this.year=new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(year);
        this.visible= new SimpleBooleanProperty(true);
    }

    public SimpleIntegerProperty getYear() {
        return year;
    }
    public StringProperty getTitle() {
        return title;
    }
    public int getId() {
        return id.get();
    }
    public boolean isVisible() {
        return visible.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }
    public void setYear(int year) {
        this.year = new SimpleIntegerProperty(year);
    }
    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }
}
