package com.guseyn;

public interface IntContainer {
    void add(int number);
    default void addAll(IntContainer other) {
        throw new UnsupportedOperationException();
    }
    long countUnique();
}
