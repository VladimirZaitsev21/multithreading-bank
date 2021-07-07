package ru.zvo.view;

import ru.zvo.model.Client;
import ru.zvo.model.FrontSystem;
import ru.zvo.model.Request;
import ru.zvo.model.RequestProcessor;

public class ConsoleView {

    private static final String CLIENT_MESSAGE_PATTERN = "%s: %s отправлена в банк\n";
    private static final String PROCESSOR_MESSAGE_PATTERN = "%s: Получена заявка на обработку по клиенту - %s\n";
    private static final String SUCCESS_MESSAGE_PATTERN = "Бэк система: %s УСПЕШНО ВЫПОЛНЕНА. Получена от %s. Баланс банка: %d\n";
    private static final String FAILURE_MESSAGE_PATTERN = "Бэк система: %s НЕ ВЫПОЛНЕНА. Получена от %s. Баланс банка: %d\n";

    private static volatile ConsoleView instance;

    public static ConsoleView getInstance() {
        if (instance == null) {
            synchronized (ConsoleView.class) {
                if (instance == null) {
                    instance = new ConsoleView();
                }
            }
        }
        return instance;
    }

    public void informAboutClient(Client client, Request request) {
        System.out.printf(CLIENT_MESSAGE_PATTERN, client.getName(), request);
    }

    public void informAboutProcessor(RequestProcessor processor, Request request) {
        System.out.printf(PROCESSOR_MESSAGE_PATTERN, processor.getName(), request.getClientName());
    }

    public void informAboutBackSystem(Request request, String processorName, int moneyBalance, boolean isSucceed) {
        if (isSucceed) {
            System.out.printf(SUCCESS_MESSAGE_PATTERN, request, processorName, moneyBalance);
        } else {
            System.out.printf(FAILURE_MESSAGE_PATTERN, request, processorName, moneyBalance);
        }
    }

}
