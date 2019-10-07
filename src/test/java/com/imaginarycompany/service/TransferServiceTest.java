package com.imaginarycompany.service;

import com.google.gson.Gson;
import com.imaginarycompany.model.Account;
import com.imaginarycompany.model.Transfer;
import com.imaginarycompany.model.dto.TransferDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TransferServiceTest {

    private static AccountService accountService;
    private static TransferService transferService;

    static {
        try {
            transferService = new TransferService();
            accountService = new AccountService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Gson gson = new Gson();

    @Before
    public void init() throws SQLException {
        DatabaseService.getInstance().createDatabase();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void transferMoneyTest() throws SQLException {
        String accountFromString = accountService.createAccount();
        String accountToString = accountService.createAccount();
        Account accountFrom = gson.fromJson(accountFromString, Account.class);
        Account accountTo = gson.fromJson(accountToString, Account.class);

        BigDecimal amount = new BigDecimal(100);

        TransferDto transferDto = new TransferDto(
                accountFrom.getAccountNo(),
                accountTo.getAccountNo(),
                amount);

        String actualString = transferService.transferMoney(transferDto);
        Transfer actual = gson.fromJson(actualString, Transfer.class);

        assertEquals(actual.getAmount(), amount);
        assertEquals(actual.getFrom().getBalance(), accountFrom.getBalance().subtract(amount));
        assertEquals(actual.getTo().getBalance(), accountTo.getBalance().add(amount));
        assertNotNull(actual.getSent());

    }

}

