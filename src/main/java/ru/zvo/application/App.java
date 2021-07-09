package ru.zvo.application;

import ru.zvo.controller.Controller;

public class App {
    private static final int CLIENTS_AMOUNT = 5;
    private static final int PROCESSORS_AMOUNT = 2;

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    private void start() {
        Controller controller = new Controller(CLIENTS_AMOUNT, PROCESSORS_AMOUNT);
        controller.initModels();
        controller.startBankWorking();
    }
}