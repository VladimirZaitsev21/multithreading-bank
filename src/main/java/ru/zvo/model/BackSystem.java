package ru.zvo.model;

public class BackSystem {

    private int requestsRemaining = 5;
    private static final String NAME = "��� �������";
    private static final String SUCCESS_MESSAGE_PATTERN = "��� �������: %s ������� ���������. �������� �� %s. ������ �����: %d\n";
    private static final String FAILURE_MESSAGE_PATTERN = "��� �������: %s �� ���������. �������� �� %s. ������ �����: %d\n";
    private int moneyBalance;
    private static volatile BackSystem instance;
    public static int instanceCount = 0;

    private BackSystem() {
        instanceCount++;
    }

    public static BackSystem getInstance() {
        if (instance == null) {
            synchronized (FrontSystem.class) {
                if (instance == null) {
                    instance = new BackSystem();
                }
            }
        }
        return instance;
    }

    public synchronized int getRequestsRemaining() {
        return requestsRemaining;
    }

    public void doCredit(Request request, String processorName) {
        synchronized (this) {
            int credit = request.getMoneyAmount();
//            while (moneyBalance < credit) {
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
            if (moneyBalance > credit) {
                moneyBalance -= credit;
                requestsRemaining--;
                System.out.printf(SUCCESS_MESSAGE_PATTERN, request, processorName, moneyBalance);
            } else {
                System.out.printf(FAILURE_MESSAGE_PATTERN, request, processorName, moneyBalance);
            }
//            System.out.println(NAME + ": " + request + "������� ���������. �������� �� " + processorName + ". ������ �����: " + moneyBalance);
            this.notifyAll();
        }
    }

    public void doRepayment(Request request, String processorName) {
        synchronized (this) {
            moneyBalance += request.getMoneyAmount();
            requestsRemaining--;
            System.out.printf(SUCCESS_MESSAGE_PATTERN, request, processorName, moneyBalance);
//            System.out.println(NAME + ": " + request + "������� ���������. �������� �� " + processorName + ". ������ �����: " + moneyBalance);
            this.notifyAll();
        }
    }

}
