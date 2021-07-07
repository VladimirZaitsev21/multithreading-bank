package ru.zvo.model;

public class Request {
    private final String clientName;
    private final int moneyAmount;
    private final BankOperationType operationType;

    public Request(String clientName, int moneyAmount, BankOperationType operationType) {
        this.clientName = clientName;
        this.moneyAmount = moneyAmount;
        this.operationType = operationType;
    }

    public String getClientName() {
        return clientName;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public BankOperationType getOperationType() {
        return operationType;
    }

    @Override
    public String toString() {
        return "Заявка{" +
                "clientName='" + clientName + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", operationType=" + operationType +
                '}';
    }
}
