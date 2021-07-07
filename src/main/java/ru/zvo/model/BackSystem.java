package ru.zvo.model;

import ru.zvo.view.ConsoleView;

public class BackSystem {

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

    public void doCredit(Request request, String processorName) {
        synchronized (this) {
            int credit = request.getMoneyAmount();
            if (moneyBalance > credit) {
                moneyBalance -= credit;
                ConsoleView.getInstance().informAboutBackSystem(request, processorName, moneyBalance, true);
            } else {
                ConsoleView.getInstance().informAboutBackSystem(request, processorName, moneyBalance, false);
            }
            this.notifyAll();
        }
    }

    public void doRepayment(Request request, String processorName) {
        synchronized (this) {
            moneyBalance += request.getMoneyAmount();
            ConsoleView.getInstance().informAboutBackSystem(request, processorName, moneyBalance, true);
            this.notifyAll();
        }
    }

}
