package com.imaginarycompany.service;

import com.imaginarycompany.model.Account;
import com.imaginarycompany.model.dto.AccountDto;
import com.imaginarycompany.util.JsonUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class AccountService {

    public static final String ACCOUNT_NO = "accountNo";

    private Dao<Account, Long> accountDao = DatabaseService.getInstance().createAccountDao();
    private JsonUtil<Account> jsonUtil = new JsonUtil<>();

    public AccountService() throws SQLException {
    }

    public String createAccount() throws SQLException {
        Account accountCreated = accountDao.createIfNotExists(new Account());
        return jsonUtil.toJson(accountCreated);
    }

    public String getAccount(String accountNo) throws SQLException {
        Account accountCreated = findByAccountNo(accountNo);
        return jsonUtil.toJson(accountCreated);
    }

    public String depositMoney(AccountDto accountDto) throws SQLException {
        Account account = findByAccountNo(accountDto.getAccountNo());
        account.setBalance(account.getBalance().add(accountDto.getBalance()));
        updateAccount(account);
        return jsonUtil.toJson(account);
    }

    public String withdrawalMoney(AccountDto accountDto) throws SQLException {
        Account account = findByAccountNo(accountDto.getAccountNo());
        account.setBalance(account.getBalance().subtract(accountDto.getBalance()));
        updateAccount(account);
        return jsonUtil.toJson(account);
    }

    private Account findByAccountNo(String accountNo) throws SQLException {
        return accountDao.queryBuilder()
                .where()
                .eq(ACCOUNT_NO, accountNo)
                .queryForFirst();
    }

    private void updateAccount(Account account) throws SQLException {
        accountDao.update(account);
    }
}
