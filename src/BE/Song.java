package BE;

public class Song {
    private String title;
    private int year;

    public Song(String title, int year){
        this.title=title;
        this.year=year;
    }

    public int getYear() {
        return year;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setYear(int year) {
        this.year = year;
    }
}