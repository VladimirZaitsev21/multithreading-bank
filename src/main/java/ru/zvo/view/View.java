package ru.zvo.view;

import ru.zvo.model.Client;
import ru.zvo.model.Request;
import ru.zvo.model.RequestProcessor;

public interface View {

    void informAboutClient(Client client, Request request);

    void informAboutProcessor(RequestProcessor processor, Request request);

    void informAboutBackSystem(Request request, String processorName, int moneyBalance, boolean isSucceed);

    void informAboutBalanceSystem(String name, Integer balance);

    void informAboutError(Exception e);
}
