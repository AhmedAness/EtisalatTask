package com.etisalat.sampletask.bases.Utility;

public class CustomPair<T,V> {
    T lift;
    V right;

    public CustomPair(T lift, V right) {
        this.lift = lift;
        this.right = right;
    }

    public T getLift() {
        return lift;
    }

    public void setLift(T lift) {
        this.lift = lift;
    }

    public V getRight() {
        return right;
    }

    public void setRight(V right) {
        this.right = right;
    }
}
