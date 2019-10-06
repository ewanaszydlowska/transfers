package com.imaginarycompany;

import com.imaginarycompany.service.DatabaseService;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.io.IOException;
import java.sql.SQLException;

import static com.imaginarycompany.controller.AccountController.*;
import static com.imaginarycompany.controller.TransferController.*;

@AllArgsConstructor
public class TransfersApp {

    public static void main(String[] args) throws SQLException {

        Javalin app = Javalin
                .create(config -> config.server(() -> new Server(new QueuedThreadPool(10, 2, 60_000))))
                .start();

        DatabaseService.getInstance().createDatabase();

        startAccountRoutes(app);
        startTransferRoutes(app);
    }

    private static void startAccountRoutes(Javalin app) {
        app.get("/account", createAccount);
        app.get("/account/:accountNo", getAccount);
        app.post("/account/deposit", depositMoney);
        app.post("/account/withdraw", withdrawMoney);

    }

    private static void startTransferRoutes(Javalin app) {
        app.post("/transfer", transfer);
    }







}



