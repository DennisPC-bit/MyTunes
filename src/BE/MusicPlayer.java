package BE;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {
    protected MediaPlayer mediaPalyer;
    protected Media media;
    protected Song song;

    public Media getMedia() {
        return media;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        if (song != null) {
            this.song = song;
            if (!song.getFilePath().isBlank()) {
                media = new Media(new File(song.getFilePath()).toURI().toString());
                mediaPalyer = new MediaPlayer(media);
                setVolume(100);
            }
        }
    }

    public void mute() {
        setVolume(0);
    }

    public void play() {
        if (mediaPalyer != null)
            mediaPalyer.play();
    }

    public void pause() {
        if (mediaPalyer != null)
            mediaPalyer.pause();
    }

    public void stop() {
        if (mediaPalyer != null)
            mediaPalyer.stop();
    }

    public double getVolume() {
        if (mediaPalyer != null)
            return mediaPalyer.getVolume();
        return 0;
    }

    public void setVolume(double volume) {
        if (mediaPalyer != null)
            mediaPalyer.setVolume(volume);
    }
}
