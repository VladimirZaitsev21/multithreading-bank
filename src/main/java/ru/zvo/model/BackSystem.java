package ru.zvo.model;

import ru.zvo.view.View;

import java.util.concurrent.atomic.AtomicInteger;

public class BackSystem {

    private final AtomicInteger moneyBalance;
    private final AtomicInteger requestsLeft;
    private final View view;

    public AtomicInteger getRequestsLeft() {
        return requestsLeft;
    }

    public BackSystem(int moneyBalance, int requestAmount, View view) {
        this.moneyBalance = new AtomicInteger(moneyBalance);
        this.requestsLeft = new AtomicInteger(requestAmount);
        this.view = view;
    }

    public void doCredit(Request request, String processorName) {
        int credit = request.getMoneyAmount();
        if (moneyBalance.get() < credit) {
            view.informAboutBackSystem(request, processorName, moneyBalance.get(), false);
        } else {
            view.informAboutBackSystem(request, processorName, moneyBalance.addAndGet(-request.getMoneyAmount()), true);
        }
        requestsLeft.decrementAndGet();

    }

    public void doRepayment(Request request, String processorName) {
        view.informAboutBackSystem(request, processorName, moneyBalance.addAndGet(request.getMoneyAmount()), true);
        requestsLeft.decrementAndGet();
    }

}
