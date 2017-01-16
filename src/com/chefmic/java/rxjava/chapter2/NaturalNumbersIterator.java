package com.chefmic.java.rxjava.chapter2;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * Created by cyuan on 1/15/17.
 */
public class NaturalNumbersIterator implements Iterator<BigInteger> {

    private BigInteger current = BigInteger.ZERO;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public BigInteger next() {
        current = current.add(BigInteger.ONE);
        return current;
    }
}
