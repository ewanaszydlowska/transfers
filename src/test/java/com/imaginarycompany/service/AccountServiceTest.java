package com.imaginarycompany.service;

import com.google.gson.Gson;
import com.imaginarycompany.model.Account;
import com.imaginarycompany.model.dto.AccountDto;
import com.j256.ormlite.dao.Dao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Dao<Account, Long> accountDao;

    @InjectMocks
    private static AccountService accountService;

    static {
        try {
            accountService = new AccountService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Gson gson = new Gson();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAccountTest() throws SQLException {
        Account account = new Account();
        account.setId(1L);
        when(accountDao.createIfNotExists(any())).thenReturn(account);

        String accountJson = accountService.createAccount();
        Account actual = gson.fromJson(accountJson, Account.class);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(actual.getBalance(), BigDecimal.ZERO);
    }

    @Test(expected = SQLException.class)
    public void createAccountExceptionTest() throws SQLException {
        when(accountDao.createIfNotExists(any())).thenThrow(SQLException.class);

        accountService.createAccount();
    }

    @Test
    public void updateAccountTest() throws SQLException {
        Account account = new Account();
        AccountDto accountDto = new AccountDto(account.getAccountNo(), new BigDecimal(10));

        mockFindAccountByNo(account);
        mockUpdateAccount();

        String accountString = accountService.depositMoney(accountDto);
        Account actual = gson.fromJson(accountString, Account.class);

        assertEquals(actual.getBalance(), new BigDecimal(10));
    }

    @Test
    public void withdrawalAccountTest() throws SQLException {
        Account account = new Account();
        account.setBalance(new BigDecimal(100));
        AccountDto accountDto = new AccountDto(account.getAccountNo(), new BigDecimal(10));

        mockFindAccountByNo(account);
        mockUpdateAccount();

        String accountString = accountService.withdrawalMoney(accountDto);
        Account actual = gson.fromJson(accountString, Account.class);

        assertEquals(actual.getBalance(), new BigDecimal(90));
    }

    private void mockFindAccountByNo(Account account) throws SQLException {
        when(accountDao.queryBuilder()
                .where()
                .eq(anyString(), anyObject())
                .queryForFirst())
                .thenReturn(account);
    }

    private void mockUpdateAccount() throws SQLException {
        when(accountDao.update(any(Account.class))).thenReturn(1);
    }

}
