/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Carlo De Leon
 * Version: 1.0
 */
public class DbMSSQLConnectionProvider implements DAL.DB.IINTERFACE.IDbConnectionProvider {

    protected String host;
    protected String user;
    protected String password;
    protected String database;
    protected int port = 1433;
    private SQLServerDataSource ds;

    public DbMSSQLConnectionProvider() {
    }

    /**
     *
     */
    public void connect() {
        ds = new SQLServerDataSource();
        ds.setServerName(getHost());
        ds.setDatabaseName(getDatabase());
        ds.setUser(getUser());
        ds.setPassword(getPassword());
        ds.setPortNumber(getPort());
    }

    /**
     *
     * @return
     */
    @Override
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public SQLServerDataSource getDataSource() {
        return ds;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDatabase() {
        return database;
    }

    /**
     *
     * @return
     */
    @Override
    public String getHost() {
        return host;
    }

    /**
     *
     * @return
     */
    @Override
    public String getUser() {
        return user;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     *
     * @param database
     */
    @Override
    public void setDatabase(String database) {
        if (database.isEmpty()) return;
        this.database = database;
    }

    /**
     *
     * @param host
     */
    @Override
    public void setHost(String host) {
        if (host.isEmpty()) return;
        this.host = host;
    }

    /**
     *
     * @param user
     */
    @Override
    public void setUser(String user) {
        if (user.isEmpty()) return;
        this.user = user;
    }

    /**
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {
        if (password.isEmpty()) return;
        this.password = password;
    }

    /**
     *
     * @param port
     */
    @Override
    public void setPort(int port) {
        if (port <= 0) return;
        this.port = port;
    }

}
