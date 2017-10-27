package com.workday;

public class RangeContainerFactoryArrayImpl implements RangeContainerFactory {
    @Override
    public RangeContainer createContainer(long[] data) {
        return new RangeContainerArrayImpl(data);
    }
}
