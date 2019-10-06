package com.imaginarycompany;

import com.imaginarycompany.service.DatabaseService;
import io.javalin.Javalin;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.sql.SQLException;

import static com.imaginarycompany.controller.AccountController.*;

@AllArgsConstructor
public class TransfersApp {

    public static void main(String[] args) throws SQLException {

        Javalin app = Javalin
                .create(config -> config.server(() -> new Server(new QueuedThreadPool(10, 2, 60_000))))
                .start();

        DatabaseService.getInstance().createDatabase();

        startAccountRoutes(app);

    }

    private static void startAccountRoutes(Javalin app) {
        app.get("/accounts", countAccounts);
        app.get("/account", createAccount);

    }







}



