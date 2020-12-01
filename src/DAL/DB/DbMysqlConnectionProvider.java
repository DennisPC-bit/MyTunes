package DAL.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Carlo De Leon
 * Version: 1.0
 */
public class DbMysqlConnectionProvider implements DAL.DB.IINTERFACE.IDbConnectionProvider {

    protected String host;
    protected String user;
    protected String password;
    protected String database;
    protected int port = 3306;

    protected Connection connection;

    public DbMysqlConnectionProvider() {
    }

    @Override
    public void connect() {
        try {
            // Connect to the database.
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", getHost(), getPort(), getDatabase()), getUser(), getPassword());
        } catch (SQLException e) {
            System.out.println(String.format("MySQL connect exception: %s", e.getMessage()));
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public String getDatabase() {
        return database;
    }


    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setDatabase(String database) {
        if (database.isEmpty()) return;
        this.database = database;
    }

    @Override
    public void setHost(String host) {
        if (host.isEmpty()) return;
        this.host = host;
    }

    @Override
    public void setUser(String user) {
        if (user.isEmpty()) return;
        this.user = user;
    }

    @Override
    public void setPassword(String password) {
        if (password.isEmpty()) return;
        this.password = password;
    }

    @Override
    public void setPort(int port) {
        if (port <= 0) return;
        this.port = port;
    }
}
