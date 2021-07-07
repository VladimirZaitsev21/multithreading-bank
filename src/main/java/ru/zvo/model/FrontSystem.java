package ru.zvo.model;

import java.util.LinkedList;
import java.util.Queue;

public class FrontSystem {

    private static final int MAX_REQUESTS_AMOUNT = 2;
    private final Queue<Request> requests = new LinkedList<>();
    private static volatile FrontSystem instance;

    public static FrontSystem getInstance() {
        if (instance == null) {
            synchronized (FrontSystem.class) {
                if (instance == null) {
                    instance = new FrontSystem();
                }
            }
        }
        return instance;
    }

    public void addRequest(Request request) {
        synchronized (this) {
            while (requests.size() == MAX_REQUESTS_AMOUNT) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            requests.add(request);
            this.notifyAll();
        }
    }

    public Request getRequest() {
        synchronized (this) {
            Request request = requests.poll();
            this.notifyAll();
            return request;
        }
    }

}
