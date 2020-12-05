package DAL.DAO.FILE;

import BE.Song;
import BLL.SongManager;
import DAL.DAO.SongDAOInterface;
import java.io.File;
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

    @Override
    public void setSongManager(SongManager songManager) {
        this.songManager=songManager;
    }

    @Override
    public List<Song> loadSongs() throws Exception{
        List<Song> tmp = new ArrayList<>();
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"r")){
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
        }
    }

    @Override
    public boolean createSong(String name, String path) throws Exception {
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


    @Override
    public boolean createSong(String name, String path, int categoryId) {
    return false;
    }

    @Override
    public Song getSong(String name) throws Exception {
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

    public Song getSong(int id) throws Exception {
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

    @Override
    public boolean deleteSong(int id) throws Exception {
        try(RandomAccessFile raf = new RandomAccessFile(new File(LOCAL_SONG_PATH),"rw")){
            while(raf.getFilePointer()<raf.length()){
                if(raf.readInt()==id){
                    raf.writeChars(emptyNameValue);
                    raf.writeChars(emptyPathValue);
                    return true;
                }
                else raf.skipBytes(SONG_NAME_SIZE*2+SONG_PATH_SIZE*2);
                }
            return false;
        }
    }

    @Override
    public boolean updateSong(int id, Song modified) throws Exception {
        String formattedName = String.format("%-" + SONG_NAME_SIZE + "s",modified.getTitle().substring(0,SONG_NAME_SIZE));
        String formattedPath = String.format("%-" + SONG_PATH_SIZE + "s",modified.getFilePath().substring(0,SONG_PATH_SIZE));
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
}
