package ru.zvo.model;

import ru.zvo.view.View;

import java.util.Random;
import java.util.concurrent.Callable;

public class BalanceSystem implements Callable<Integer> {
    private final Random random = new Random();
    private final int lowerTimeBound;
    private final int upperTimeBound;
    private final int lowerBalanceBound;
    private final int upperBalanceBound;
    private final String name;
    private final View view;

    public BalanceSystem(int lowerTimeBound, int upperTimeBound, int lowerBalanceBound, int upperBalanceBound, String name, View view) {
        this.lowerTimeBound = lowerTimeBound;
        this.upperTimeBound = upperTimeBound;
        this.lowerBalanceBound = lowerBalanceBound;
        this.upperBalanceBound = upperBalanceBound;
        this.name = name;
        this.view = view;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(random.nextInt(upperTimeBound - lowerTimeBound) + lowerTimeBound);
        Integer balance = (random.nextInt(upperBalanceBound - lowerBalanceBound) + lowerBalanceBound);
        view.informAboutBalanceSystem(name, balance);
        return balance;
    }
}
