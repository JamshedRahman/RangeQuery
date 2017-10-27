package com.workday;
import java.util.*;

public class RangeContainerTreeMapImpl implements RangeContainer {
    public TreeMap<Long, Short> DataMap = new TreeMap<Long, Short>();

    public RangeContainerTreeMapImpl(long [] data) {
        for (short i = 0; i < data.length; i++) {
            DataMap.put(data[i], i);
        }
    }

    @Override
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        IdsConcreteImpl ids = new IdsConcreteImpl();

        ids.EmployeeIds = new ArrayList<Short>(DataMap.subMap(fromValue, fromInclusive, toValue, toInclusive).values());

        Collections.sort(ids.EmployeeIds);

        return ids;
    }
}
