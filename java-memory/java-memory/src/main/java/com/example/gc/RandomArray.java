package com.example.gc;

import java.util.Random;

public class RandomArray implements Data{
    private int[] array;
    private Random random;

    public RandomArray(Random random) {
        this.random = random;
    }

    @Override
    public void insert(int elements) {
        array = new int[elements];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(elements - 1) + 1;
        }
    }

    @Override
    public int[] getClone() {
        return array.clone();
    }
}
