package ru.zvo.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FrontSystem {

    private static final int MAX_REQUESTS_AMOUNT = 2;

    /**
     *
     * ��� ����, ����� ���������� ������ ������ ������ ����������,
     * ���������� ������������ ����������� ������� ������������ �������,
     * ����� ��������� ������ ������� ������������ ����� � ������� ��� ����������
     * ������, ���� ��� �����, � ������� ��������� �������� ��� ������� ���������
     * ������, ���� ������� �����.
     * ����� �������, ��� ������ - BlockingQueue.
     * � ���� ����, ��� ������� ��������, � ���������� � �������� ��������� ����������
     * � ������ �������, ��� ���������� �������� ������������ ���������� ������ �� ������ �������,
     * � �� ���������� ������.
     *
     * */
    private final BlockingQueue<Request> requests = new ArrayBlockingQueue<>(MAX_REQUESTS_AMOUNT, true);

    public void addRequest(Request request) {
        try {
            requests.put(request);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Request getRequest() {
        Request request = null;
        try {
            request = requests.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return request;
    }

}
