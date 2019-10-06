package com.imaginarycompany.service;

import com.imaginarycompany.model.Account;
import com.imaginarycompany.util.JsonUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class AccountService {

    private Dao<Account, Long> accountDao = DatabaseService.getInstance().createAccountDao();
    private JsonUtil<Account> jsonUtil = new JsonUtil<>();

    public AccountService() throws SQLException {
    }

    public String countAccounts() throws SQLException {
        return String.valueOf(accountDao.countOf());
    }

    public String createAccount() throws SQLException {
        Account accountCreated = accountDao.createIfNotExists(new Account());
        return jsonUtil.toJson(accountCreated);
    }

}
