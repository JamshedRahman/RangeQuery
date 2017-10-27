package com.workday;
import java.util.*;

public class RangeContainerArrayImpl implements RangeContainer {
    long [] dataArray;
    public RangeContainerArrayImpl(long [] data) {
        dataArray = data;
        }

    @Override
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        IdsConcreteImpl ids = new IdsConcreteImpl();
        if (!fromInclusive) fromValue++;
        if (!toInclusive) toValue--;

        ids.EmployeeIds = new ArrayList<Short>();

        // Scan for ids that fall between range and collect them
        for(short i = 0; i < dataArray.length; i++)
        {
            if(fromValue <= dataArray[i] && dataArray[i] <= toValue)
                ids.EmployeeIds.add(i);
        }

        return ids;
    }
}
