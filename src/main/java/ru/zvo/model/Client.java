package ru.zvo.model;

import ru.zvo.view.View;

public class Client implements Runnable {

    private final View view;
    private final FrontSystem frontSystem;
    private final String name;
    private final int moneyAmount;
    private final BankOperationType operationType;
    private final Request request;

    public Client(String name, int moneyAmount, BankOperationType operationType, FrontSystem frontSystem, View view) {
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.operationType = operationType;
        this.request = new Request(name, moneyAmount, operationType);
        this.frontSystem = frontSystem;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        frontSystem.addRequest(request);
        view.informAboutClient(this, request);
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
