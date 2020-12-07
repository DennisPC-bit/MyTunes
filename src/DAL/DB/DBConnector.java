package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector
    {
        private SQLServerDataSource dataSource;

        /**
         *
         * @throws IOException
         */
        public DBConnector() throws IOException
        {
            Properties props = new Properties();
            props.load(new FileReader("DBSettings.txt"));
            dataSource = new SQLServerDataSource();
            dataSource.setDatabaseName(props.getProperty("database"));
            dataSource.setUser(props.getProperty("user"));
            dataSource.setPassword(props.getProperty("password"));
            dataSource.setServerName(props.getProperty("server"));
        }

        public Connection getConnection() throws SQLServerException
        {
            return dataSource.getConnection();
        }
    }

