package ru.zvo.model;

import ru.zvo.view.View;

public class RequestProcessor implements Runnable {

    private final String name;
    private final View view;
    private final FrontSystem frontSystem;
    private final BackSystem backSystem;

    public RequestProcessor(String name, FrontSystem frontSystem, BackSystem backSystem, View view) {
        this.name = name;
        this.frontSystem = frontSystem;
        this.backSystem = backSystem;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Request request = frontSystem.getRequest();
            if (request == null) {
                break;
            }
            view.informAboutProcessor(this, request);
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
