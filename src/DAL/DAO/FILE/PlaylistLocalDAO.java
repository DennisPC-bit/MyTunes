package DAL.DAO.FILE;

import BE.Playlist;
import BE.Song;
import BLL.PlaylistManager;
import DAL.DAO.PlaylistDAOInterface;

import java.io.*;
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

    /**
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void createPlaylist(String name) throws IOException {
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

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public List<Playlist> loadPlaylist() throws IOException {
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

    /**
     *
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public Playlist getPlaylist(String name) throws IOException {
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

    /**
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void deletePlaylist(String name) throws IOException {
        String formattedName = String.format("%-" + PLAYLISTNAMESIZE + "s",name);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                raf.skipBytes(4);
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
        }catch (EOFException e){
            System.out.println("Could not delete playlist " + name);
        }
    }

    /**
     *
     * @param playlist
     * @throws IOException
     */
    @Override
    public void updatePlaylist(Playlist playlist) throws IOException {
        String formattedName = String.format("%-" + PLAYLISTNAMESIZE + "s",playlist.getPlayListName()).substring(0,PLAYLISTNAMESIZE);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==playlist.getPlaylistId()){
                    raf.writeChars(formattedName);
                    break;
                }
            }
        }
    }

    /**
     *
     * @param playlist_id
     * @return
     * @throws IOException
     */
    @Override
    public List<Song> loadSongsFromPlaylist(int playlist_id) throws IOException {
        File file = new File(LOCAL_PLAYLIST_SONG);
        SongLocalDAO songLocalDAO = new SongLocalDAO();
        List<Song> tmp = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(file,"r")){
            while (raf.getFilePointer()<raf.length()) {
                int playlistId=raf.readInt();
                if(playlistId==playlist_id)
                    tmp.add(songLocalDAO.getSong(raf.readInt()));
                else
                    raf.skipBytes(4);
            }
            return tmp;
        }
        catch (FileNotFoundException e){
            file.createNewFile();
            return tmp;
        }
    }

    /**
     *
     * @param playlist_id
     * @param song_id
     * @throws IOException
     */
    @Override
    public void AddSongToPlaylist(int playlist_id, int song_id) throws IOException {
        File file = new File(LOCAL_PLAYLIST_SONG);
        try(RandomAccessFile raf = new RandomAccessFile(file,"rw")){
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

    /**
     *
     * @param playlist_id
     * @param song_id
     * @throws IOException
     */
    @Override
    public void deleteFromPlaylist(int playlist_id, int song_id) throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_SONG),"rw")){
            while (raf.getFilePointer()<raf.length()){
                int playlistId = raf.readInt();
                int songId=raf.readInt();
                if(playlistId==playlist_id && songId==song_id){
                    raf.seek(raf.getFilePointer()-8);
                    raf.writeInt(-1);
                    raf.writeInt(-1);
                    break;
                }
        }
    }catch (EOFException e){
        System.out.println("Could not delete playlist " + playlist_id);
        }
    }

    /**
     *
     * @param playlist_id
     * @throws IOException
     */
    private void deleteAllFromPlaylist(int playlist_id) throws IOException {
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