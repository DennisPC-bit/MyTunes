package BE;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {

    protected MediaPlayer mediaPlayer;
    protected Media media;
    protected Song song;

    /**
     *
     * @return
     */
    public Media getMedia() {
        return media;
    }

    /**
     *
     * @return
     */
    public Song getSong() {
        return song;
    }

    /**
     *
     * @param song
     */
    public void setSong(Song song) {
        if (song != null && this.song != song) {
            this.song = song;
            if (!song.getFilePath().isBlank()) {
                media = new Media(new File(song.getFilePath()).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
            }
        }
    }

    /**
     *
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     *
     */
    public void mute() {
        setVolume(0);
    }

    /**
     *
     */
    public void play() {
        if (mediaPlayer != null)
            mediaPlayer.play();
    }

    /**
     *
     */
    public void pause() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    /**
     *
     */
    public void stop() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }

    /**
     *
     * @return
     */
    public double getVolume() {
        if (mediaPlayer != null)
            return mediaPlayer.getVolume();
        return 0;
    }

    /**
     *
     * @param volume
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null)
            mediaPlayer.setVolume(volume);
    }
}
