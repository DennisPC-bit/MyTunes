package DAL.DAO.FILE;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import DAL.DAO.PlaylistDAOInterface;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PlaylistLocalDAO implements PlaylistDAOInterface {
    private PlaylistManager playlistManager;
    private static final int PLAYLISTNAMESIZE=100;
    private static final String emptyValue=String.format("%-" + PLAYLISTNAMESIZE + "s",-1);
    private static final String LOCAL_PLAYLIST_PATH = "Data/localPlaylists.data";
    private static final String LOCAL_PLAYLIST_SONG = "Data/localPlaylist_song.data";

    @Override
    public void setPlaylistManager(PlaylistManager playlistManager) {
        this.playlistManager=playlistManager;
    }

    @Override
    public void createPlaylist(String name) throws Exception {
        String formattedName = String.format("%-" + PLAYLISTNAMESIZE + "s",name).substring(0,PLAYLISTNAMESIZE);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                String playlistName = "";
                raf.skipBytes(4);
                for(int i=0;i<PLAYLISTNAMESIZE;i++){
                playlistName+=raf.readChar();
                if(playlistName.equals(emptyValue)){
                    raf.seek(raf.getFilePointer()-PLAYLISTNAMESIZE*2);
                    raf.writeChars(formattedName);
                    return;
                }
                }
            }
            raf.seek(raf.getFilePointer()-(PLAYLISTNAMESIZE*2)-4);
            int index = raf.readInt()+1;
            raf.seek(raf.length());
            raf.writeInt(index);
            raf.writeChars(formattedName);
        }
    }

    @Override
    public List<Playlist> loadPlaylist() throws Exception {
        List<Playlist> tmp = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"rw")){
            if(raf.length()==0) {
            raf.writeInt(1);
            raf.writeChars(emptyValue);
            raf.seek(0);}
            while(raf.getFilePointer()<raf.length()){
                int playlistId=raf.readInt();
                String playlistName= "";
                for(int i=0;i<PLAYLISTNAMESIZE;i++){
                    playlistName+=raf.readChar();
                }
                if(!playlistName.equals(emptyValue))
                tmp.add(new Playlist(playlistId, playlistName.trim()));
            }
            return tmp;
        }
    }

    @Override
    public Playlist getPlaylist(String name) throws Exception {
        String formattedName = String.format("%-" + PLAYLISTNAMESIZE + "s",name);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"r")){
            while(raf.getFilePointer()<raf.length()){
                int playlistId=raf.readInt();
                String playlistName= "";
                for(int i=0;i<PLAYLISTNAMESIZE;i++){
                    playlistName+=raf.readChar();
                }
                if(playlistName.equals(formattedName))
                    return new Playlist(playlistId, playlistName);
            }
            return null;
        }
    }

    @Override
    public void deletePlaylist(String name) throws Exception {
        String formattedName = String.format("%-" + PLAYLISTNAMESIZE + "s",name);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                String playlistName="";
                for(int i=0;i<PLAYLISTNAMESIZE;i++){
                    playlistName+=raf.readChar();
                }
                if(playlistName.equals(formattedName)){
                    raf.seek(raf.getFilePointer()-PLAYLISTNAMESIZE*2-4);
                    deleteAllFromPlaylist(raf.readInt());
                    raf.writeChars(emptyValue);
                }
            }
        }
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws Exception {
        String formattedName = String.format("%-" + PLAYLISTNAMESIZE + "s",playlist.getPlayListName().substring(0,PLAYLISTNAMESIZE));
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==playlist.getPlaylistId()){
                    raf.writeChars(formattedName);
                    break;
                }
            }
        }
    }

    @Override
    public List<Song> loadSongsFromPlaylist(int playlist_id) throws Exception {
        SongLocalDAO songLocalDAO = new SongLocalDAO();
        List<Song> tmp = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_SONG),"r")){
            if(raf.length()==0)
                return tmp;
            while (raf.getFilePointer()<raf.length()) {
                if(raf.readInt()==playlist_id)
                    tmp.add(songLocalDAO.getSong(raf.readInt()));
                else
                    raf.skipBytes(4);
            }
            return tmp;
        }
    }

    @Override
    public void AddSongToPlaylist(int playlist_id, int song_id) throws Exception {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_SONG),"rw")){
            if(raf.length()==0) {
                raf.writeInt(playlist_id);
                raf.writeInt(song_id);
                return;
            }
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==-1){
                    raf.seek(raf.getFilePointer()-4);
                    raf.writeInt(playlist_id);
                    raf.writeInt(song_id);
                    return;
                }
                else raf.skipBytes(4);
            }
            raf.seek(raf.length());
            raf.writeInt(playlist_id);
            raf.writeInt(song_id);
        }
    }

    @Override
    public void deleteFromPlaylist(int playlist_id, int song_id) throws Exception {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_SONG),"rw")){
            while (raf.getFilePointer()<raf.length()){
                if(raf.readInt()==playlist_id&&raf.readInt()==song_id){
                    raf.seek(raf.getFilePointer()-8);
                    raf.writeInt(-1);
                    raf.writeInt(-1);
                }
        }
    }
        throw new Exception("Could not delete playlist " + playlist_id);
    }

    private void deleteAllFromPlaylist(int playlist_id) throws Exception {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_SONG),"rw")){
            while (raf.getFilePointer()<raf.length()){
                if(raf.readInt()==playlist_id){
                    raf.seek(raf.getFilePointer()-8);
                    raf.writeInt(-1);
                    raf.writeInt(-1);
                }
                else raf.skipBytes(4);
            }
        }
    }
}