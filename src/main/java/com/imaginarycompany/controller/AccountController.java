package com.imaginarycompany.controller;

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

    public static Handler countAccounts = ctx -> ctx.result(getCountAccountFuture());
    public static Handler createAccount = ctx -> ctx.result(getCreateAccountFuture());

    private static CompletableFuture<String> getCountAccountFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(accountService.countAccounts()), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

    private static CompletableFuture<String> getCreateAccountFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(accountService.createAccount()), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

}
