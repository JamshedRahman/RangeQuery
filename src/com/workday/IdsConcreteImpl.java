package com.workday;

import java.util.ArrayList;

public class IdsConcreteImpl implements Ids {
    public ArrayList<Short> EmployeeIds;
    private int index = 0;

    @Override
    public short nextId() {
        if(index >= EmployeeIds.size()) return END_OF_IDS;

        return EmployeeIds.get(index++);
    }
}
