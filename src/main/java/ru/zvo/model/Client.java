package ru.zvo.model;

import ru.zvo.view.ConsoleView;

public class Client implements Runnable {

    private String name;
    private int moneyAmount;
    private BankOperationType operationType;
    private Request request;

    public Client(String name, int moneyAmount, BankOperationType operationType) {
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.operationType = operationType;
        this.request = new Request(name, moneyAmount, operationType);
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        FrontSystem.getInstance().addRequest(request);
        ConsoleView.getInstance().informAboutClient(this, request);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", operationType=" + operationType +
                ", request=" + request +
                ", thread=" + Thread.currentThread().getName() +
                '}';
    }
}
