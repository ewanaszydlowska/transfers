package com.imaginarycompany.service;

import com.imaginarycompany.model.Account;
import com.imaginarycompany.model.Transfer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseService {

    private static DatabaseService databaseService;
    private final static String DATABASE_URL = "jdbc:h2:mem:imaginarycompany";
    final ConnectionSource connectionSource;

    private DatabaseService() throws SQLException {
        this.connectionSource = new JdbcPooledConnectionSource(DATABASE_URL);
    }

    public synchronized static DatabaseService getInstance() throws SQLException {
        if (databaseService == null) {
            databaseService = new DatabaseService();
        }
        return databaseService;
    }

    public void createDatabase() throws SQLException {
        TableUtils.createTable(connectionSource, Account.class);
        TableUtils.createTable(connectionSource, Transfer.class);
    }

    public void closeConnection() throws IOException {
        connectionSource.close();
    }

    Dao<Account, Long> createAccountDao() throws SQLException {
        return DaoManager.createDao(connectionSource, Account.class);
    }

    Dao<Transfer, Long> createTransferDao() throws SQLException {
        return DaoManager.createDao(connectionSource, Transfer.class);
    }

}
