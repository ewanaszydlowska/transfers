package com.imaginarycompany.controller;

import com.imaginarycompany.model.dto.TransferDto;
import com.imaginarycompany.service.TransferService;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TransferController {

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private static TransferService transferService;

    static {
        try {
            transferService = new TransferService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TransferController() {
    }

    public static Handler transfer = ctx -> ctx.result(transferMoney(ctx.bodyAsClass(TransferDto.class)));

    private static CompletableFuture<String> transferMoney(TransferDto body) {
        CompletableFuture<String> future = new CompletableFuture<>();
        executorService.schedule(() -> future.complete(transferService.transferMoney(body)), 100L, TimeUnit.MILLISECONDS);
        return future;
    }

}
