package com.imaginarycompany.service;

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
    private AccountService accountService = new AccountService();

    public TransferService() throws SQLException {
    }

    public String transferMoney(TransferDto body) throws SQLException {

        Transfer transfer = TransactionManager.callInTransaction(DatabaseService.getInstance().connectionSource,
                () -> {
                    Account accountFrom = accountService.findByAccountNo(body.getFromAccountNo());
                    Account accountTo = accountService.findByAccountNo(body.getToAccountNo());

                    BigDecimal amount = body.getAmount();

                    accountService.depositMoney(new AccountDto(body.getToAccountNo(), amount));
                    accountService.withdrawMoney(new AccountDto(accountFrom.getAccountNo(), amount));

                    return transferDao.createIfNotExists(new Transfer(accountFrom, accountTo, amount));
                });

        return jsonMapper.toJson(transfer);
    }

}
