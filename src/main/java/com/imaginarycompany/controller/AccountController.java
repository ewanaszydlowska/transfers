package com.imaginarycompany.controller;

import com.imaginarycompany.model.dto.AccountDto;
import com.imaginarycompany.service.AccountService;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AccountController {

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static AccountService accountService;

    static {
        try {
            accountService = new AccountService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Handler createAccount = ctx -> ctx.result(getCreateAccountFuture());
    public static Handler getAccount = ctx -> ctx.result(getAccountFuture(ctx.pathParam("accountNo")));
    public static Handler depositMoney = ctx -> ctx.result(getDepositMoneyFuture(ctx.bodyAsClass(AccountDto.class)));
    public static Handler withdrawMoney = ctx -> ctx.result(getWithdrawMoneyFuture(ctx.bodyAsClass(AccountDto.class)));

    private static CompletableFuture<String> getAccountFuture(String accountNo) {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(accountService.getAccount(accountNo)), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

    private static CompletableFuture<String> getCreateAccountFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(accountService.createAccount()), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

    private static CompletableFuture<String> getDepositMoneyFuture(AccountDto body) {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(accountService.depositMoney(body)), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

    private static CompletableFuture<String> getWithdrawMoneyFuture(AccountDto body) {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(accountService.withdrawalMoney(body)), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

}
