package ru.zvo.model;

public class RequestProcessor implements Runnable {

    private static final String MESSAGE_PATTERN = "%s: ѕолучена за€вка на обработку по клиенту - %s\n";
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
            System.out.printf(MESSAGE_PATTERN, name, request.getClientName());
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
