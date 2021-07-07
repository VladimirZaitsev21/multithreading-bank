package ru.zvo.application;

import ru.zvo.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static final int MONEY_RATIO = 10000;

    public static void main(String[] args) {

        //Creating clients
        List<Client> clients = createClients(5);

        //Creating request processors
        List<RequestProcessor> processors = createProcessors(2);

        //Creating clients threads
        List<Thread> clientThreads = new ArrayList<>();
        clientThreads.addAll(clients.stream().map(client -> new Thread(client, client.getName())).collect(Collectors.toList()));
        clientThreads.forEach(Thread::start);

        //Creating processors threads
        List<Thread> processorThreads = new ArrayList<>();
        processorThreads.addAll(processors.stream().map(processor -> new Thread(processor, processor.getName())).collect(Collectors.toList()));
        processorThreads.forEach(Thread::start);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted");
            Thread.currentThread().interrupt();
        }

        processorThreads.forEach(Thread::interrupt);
        clientThreads.forEach(Thread::interrupt);

    }

    private static List<Client> createClients(int clientsAmount) {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            clients.add(new Client("Client #" + (i + 1), getRandomMoney(), defineOperationType(i)));
        }
        return clients;
    }

    private static List<RequestProcessor> createProcessors(int processorsAmount) {
        List<RequestProcessor> processors = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            processors.add(new RequestProcessor("Request processor #" + (i + 1)));
        }
        return processors;
    }

    private static BankOperationType defineOperationType(int i) {
        if (i % 2 == 0) {
            return BankOperationType.CREDIT;
        } else {
            return BankOperationType.REPAYMENT;
        }
    }

    private static int getRandomMoney() {
        return (int) (MONEY_RATIO * Math.random());
    }
}