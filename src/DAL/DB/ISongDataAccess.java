package DAL.DB;

import BE.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.util.List;

public interface ISongDataAccess {
    public List<Song> getAllSongs() throws Exception, SQLServerException;
}
