package DAL.DAO.FILE;

import BE.Song;
import BLL.SongManager;
import DAL.DAO.SongDAOInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class SongLocalDAO implements SongDAOInterface {
    private SongManager songManager;
    private static final String LOCAL_SONG_PATH = "Data/localSongs.data";
    private static final int SONG_NAME_SIZE=100;
    private static final int SONG_PATH_SIZE=100;
    private static final String emptyNameValue = String.format("%-" + SONG_NAME_SIZE + "s",-1);
    private static final String emptyPathValue = String.format("%-" + SONG_PATH_SIZE + "s",-1);
    private static final String LOCAL_PLAYLIST_SONG = "Data/localPlaylist_song.data";

    /**
     *
     * @param songManager
     */
    @Override
    public void setSongManager(SongManager songManager) {
        this.songManager=songManager;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public List<Song> loadSongs() throws IOException {
        File file = new File(LOCAL_SONG_PATH);
        List<Song> tmp = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(file,"r")){
            while(raf.getFilePointer()<raf.length())
            {
                int song_id = raf.readInt();
                String songName ="";
                String path="";
                for(int i=0;i<SONG_NAME_SIZE;i++)
                    songName+=raf.readChar();
                for(int i=0;i<SONG_PATH_SIZE;i++)
                    path+=raf.readChar();
                if(!songName.equals(emptyNameValue) && !path.equals(emptyPathValue))
                tmp.add(new Song(song_id,songName.trim(),path.trim()));
            }
            return tmp;
        }catch (FileNotFoundException e){
            file.createNewFile();
            return tmp;
        }
    }

    /**
     *
     * @param name
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public boolean createSong(String name, String path) throws IOException {
        String formattedName = String.format("%-" + SONG_NAME_SIZE + "s",name).substring(0,SONG_NAME_SIZE);
        String formattedPath = String.format("%-" + SONG_PATH_SIZE + "s",path).substring(0,SONG_PATH_SIZE);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"rw")){
            if(raf.length()==0) {
                raf.writeInt(1);
                raf.writeChars(emptyNameValue);
                raf.writeChars(emptyPathValue);
                raf.seek(0);
            }
            while(raf.getFilePointer()<raf.length()){
                String songName = "";
                raf.skipBytes(4);
                for(int i=0;i<SONG_NAME_SIZE;i++)
                    songName+=raf.readChar();
                if(songName.equals(emptyNameValue)){
                    raf.seek(raf.getFilePointer()-SONG_NAME_SIZE*2);
                        raf.writeChars(formattedName);
                        raf.writeChars(formattedPath);
                        return true;
                    }
                else raf.skipBytes(SONG_PATH_SIZE*2);

                }
            raf.seek(raf.length()-(SONG_NAME_SIZE*2)-(SONG_PATH_SIZE*2)-4);
            int index = raf.readInt()+1;
            raf.seek(raf.length());
            raf.writeInt(index);
            raf.writeChars(formattedName);
            raf.writeChars(formattedPath);
            return true;
            }
        }

    /**
     *
     * @param name
     * @param path
     * @param categoryId
     * @return
     */
    @Override
    public boolean createSong(String name, String path, int categoryId) {
    return false;
    }

    /**
     *
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public Song getSong(String name) throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"r")){
            while(raf.getFilePointer()<raf.length())
            {
                int song_id = raf.readInt();
                String songName ="";
                String songPath="";
                for(int i=0;i<SONG_NAME_SIZE;i++)
                    songName+=raf.readChar();
                for(int i=0;i<SONG_PATH_SIZE;i++)
                    songPath+=raf.readChar();
                if(songName.trim().equals(name))
                    return new Song(song_id,songName.trim(),songPath);
            }
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    public Song getSong(int id) throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"r")){
            while(raf.getFilePointer()<raf.length())
            {
                int song_id = raf.readInt();
                String songName ="";
                String songPath="";
                for(int i=0;i<SONG_NAME_SIZE;i++)
                    songName+=raf.readChar();
                for(int i=0;i<SONG_PATH_SIZE;i++)
                    songPath+=raf.readChar();
                if(song_id==id)
                    return new Song(song_id,songName.trim(),songPath.trim());
            }
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public boolean deleteSong(int id) throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==id){
                    raf.writeChars(emptyNameValue);
                    raf.writeChars(emptyPathValue);
                }
                else raf.skipBytes(SONG_NAME_SIZE*2+SONG_PATH_SIZE*2);
                }
        }

        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_PLAYLIST_SONG),"rw")){
            while (raf.getFilePointer()<raf.length()){
                raf.skipBytes(4);
                if(raf.readInt()==id){
                    raf.seek(raf.getFilePointer()-8);
                    raf.writeInt(-1);
                    raf.writeInt(-1);
                }
            }
        }
        return true;
    }

    /**
     *
     * @param id
     * @param modified
     * @return
     * @throws IOException
     */
    @Override
    public boolean updateSong(int id, Song modified) throws IOException {
        String formattedName = String.format("%-" + SONG_NAME_SIZE + "s",modified.getTitle()).substring(0,SONG_NAME_SIZE);
        String formattedPath = String.format("%-" + SONG_PATH_SIZE + "s",modified.getFilePath()).substring(0,SONG_PATH_SIZE);
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==id){
                    raf.writeChars(formattedName);
                    raf.writeChars(formattedPath);
                    return true;
                }
                else raf.skipBytes(SONG_NAME_SIZE*2+SONG_PATH_SIZE*2);
            }
            return false;
        }
    }

    @Override
    public List<Song> searchSong(String search) throws IOException{
        if(search.isEmpty())
            return loadSongs();
        List<Song> tmp = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
            int songID = raf.readInt();
            String songName = "";
            String songPath = "";
            for(int i = 0; i<SONG_NAME_SIZE;i++)
                songName += raf.readChar();
            for(int i = 0; i<SONG_PATH_SIZE;i++)
                songPath += raf.readChar();
            if(songName.trim().toLowerCase().contains(search.trim().toLowerCase()) || songPath.trim().toLowerCase().contains(search.trim().toLowerCase()))
                tmp.add(new Song(songID,songName.trim(),songPath.trim()));
            }
            return tmp;
        }
    }
}