package com.workday;

public class RangeContainerFactoryTreeMapImpl implements RangeContainerFactory {
    @Override
    public RangeContainer createContainer(long[] data) {
        return new RangeContainerTreeMapImpl(data);
    }
}
