package ru.zvo.controller;

import ru.zvo.model.*;
import ru.zvo.view.ConsoleView;
import ru.zvo.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {

    private static final String CLIENT_NAME_PATTERN = "Клиент #%d";
    private static final String PROCESSOR_NAME_PATTERN = "Обработчик заявок #%d";
    private static final String BALANCE_GENERATOR_NAME_PATTERN = "Система выдачи денег банку #%d";
    private static final int BALANCE_GENERATORS_AMOUNT = 3;
    private static final int MONEY_RATIO = 100000;
    private static final int LOWER_TIME_BOUND = 5000;
    private static final int UPPER_TIME_BOUND = 10000;
    private static final int LOWER_BALANCE_BOUND = 20000;
    private static final int UPPER_BALANCE_BOUND = 40000;
    private static final int DEFAULT_BANK_BALANCE = 100000;
    private final int clientsAmount;
    private final int processorsAmount;
    private final View view;
    private BackSystem backSystem;
    private FrontSystem frontSystem;
    private List<Client> clients;
    private List<RequestProcessor> processors;

    public Controller(int clientsAmount, int processorsAmount) {
        this.clientsAmount = clientsAmount;
        this.processorsAmount = processorsAmount;
        view = new ConsoleView();
    }

    public void initModels() {
        frontSystem = new FrontSystem();
        int initialBankBalance;
        try {
            initialBankBalance = getInitialBalance();
        } catch (ExecutionException | InterruptedException e) {
            view.informAboutError(e);
            initialBankBalance = DEFAULT_BANK_BALANCE;
        }
        backSystem = new BackSystem(initialBankBalance, clientsAmount, view);
        clients = createClients(clientsAmount);
        processors = createProcessors(processorsAmount);
    }

    public void startBankWorking() {
        ExecutorService clientExecutor = Executors.newFixedThreadPool(clientsAmount);
        ExecutorService processorExecutor = Executors.newFixedThreadPool(processorsAmount);

        clients.forEach(clientExecutor::submit);
        processors.forEach(processorExecutor::submit);

        while (true) {
            if (backSystem.getRequestsLeft().get() == 0) {
                break;
            }
        }
        clientExecutor.shutdown();
        processorExecutor.shutdownNow();
    }

    private Integer getInitialBalance() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(BALANCE_GENERATORS_AMOUNT);
        List<BalanceSystem> balanceSystems = new ArrayList<>();
        for (int i = 0; i < BALANCE_GENERATORS_AMOUNT; i++) {
            balanceSystems.add(new BalanceSystem(LOWER_TIME_BOUND, UPPER_TIME_BOUND, LOWER_BALANCE_BOUND, UPPER_BALANCE_BOUND, String.format(BALANCE_GENERATOR_NAME_PATTERN, (i + 1)), view));
        }
        List<Future<Integer>> balances = service.invokeAll(balanceSystems);
        int finalBalance = 0;
        for (Future<Integer> balance : balances) {
            finalBalance += balance.get();
        }
        service.shutdown();
        return finalBalance;
    }

    private List<Client> createClients(int clientsAmount) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < clientsAmount; i++) {
            clients.add(new Client(String.format(CLIENT_NAME_PATTERN, (i + 1)), getRandomMoney(), defineOperationType(i), frontSystem, view));
        }
        return clients;
    }

    private List<RequestProcessor> createProcessors(int processorsAmount) {
        List<RequestProcessor> processors = new ArrayList<>();
        for (int i = 0; i < processorsAmount; i++) {
            processors.add(new RequestProcessor(String.format(PROCESSOR_NAME_PATTERN, (i + 1)), frontSystem, backSystem, view));
        }
        return processors;
    }

    private BankOperationType defineOperationType(int i) {
        if (i % 2 == 0) {
            return BankOperationType.CREDIT;
        } else {
            return BankOperationType.REPAYMENT;
        }
    }

    private int getRandomMoney() {
        return (int) (MONEY_RATIO * Math.random());
    }
}
