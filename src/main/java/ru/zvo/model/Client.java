package ru.zvo.model;

public class Client implements Runnable {

    private static final String CLIENT_MESSAGE_PATTERN = "%s: %s отправлена в банк\n";
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
        System.out.printf(CLIENT_MESSAGE_PATTERN, name, request);
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
