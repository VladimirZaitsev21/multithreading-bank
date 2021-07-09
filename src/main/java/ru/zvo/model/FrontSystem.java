package ru.zvo.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FrontSystem {

    private static final int MAX_REQUESTS_AMOUNT = 2;

    /**
     *
     * Для того, чтобы обеспечить логику работы нашего приложения,
     * необходимо использовать блокирующую очередь ограниченной емкости,
     * чтобы заставить потоки ожидать освобождения места в очереди при добавлении
     * заявки, если она полна, и ожидать появления элемента при попытке получении
     * заявки, если очередь пуста.
     * Таким образом, тип ссылки - BlockingQueue.
     * В силу того, что очередь невелика, а добавление и удаление элементов происходит
     * с концов очереди, мне показалось логичным использовать реализацию именно на основе массива,
     * а не связанного списка.
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
