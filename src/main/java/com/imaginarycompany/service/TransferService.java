package com.imaginarycompany.service;

import com.google.gson.Gson;
import com.imaginarycompany.model.Account;
import com.imaginarycompany.model.Transfer;
import com.imaginarycompany.model.dto.AccountDto;
import com.imaginarycompany.model.dto.TransferDto;
import com.imaginarycompany.util.JsonUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TransferService {

    private Dao<Transfer, Long> transferDao = DatabaseService.getInstance().createTransferDao();
    private JsonUtil<Transfer> jsonMapper = new JsonUtil<>();
    private Gson gson = new Gson();
    private AccountService accountService = new AccountService();

    public TransferService() throws SQLException {
    }

    public String transferMoney(TransferDto body) throws SQLException {

        Transfer transfer = TransactionManager.callInTransaction(DatabaseService.getInstance().connectionSource,
                () -> {
                    BigDecimal amount = body.getAmount();

                    String accountFromString = accountService.withdrawalMoney(new AccountDto(body.getFromAccountNo(), amount));
                    String accountToString = accountService.depositMoney(new AccountDto(body.getToAccountNo(), amount));

                    return transferDao.createIfNotExists(
                            new Transfer(
                                    gson.fromJson(accountFromString, Account.class),
                                    gson.fromJson(accountToString, Account.class),
                                    amount));
                });

        return jsonMapper.toJson(transfer);
    }

}
