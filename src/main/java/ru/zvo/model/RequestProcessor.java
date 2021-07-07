package ru.zvo.model;

import ru.zvo.view.ConsoleView;

public class RequestProcessor implements Runnable {

    private final String name;

    public RequestProcessor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void run() {
        FrontSystem frontSystem = FrontSystem.getInstance();
        BackSystem backSystem = BackSystem.getInstance();
        while (!Thread.currentThread().isInterrupted()) {
            Request request = frontSystem.getRequest();
            if (request == null) {
                continue;
            }
            ConsoleView.getInstance().informAboutProcessor(this, request);
            if (request.getOperationType() == BankOperationType.CREDIT) {
                backSystem.doCredit(request, name);
            } else {
                backSystem.doRepayment(request, name);
            }
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public String toString() {
        return "RequestProcessor{" +
                "name='" + name + '\'' +
                "thread= " + Thread.currentThread().getName() +
                '}';
    }
}
