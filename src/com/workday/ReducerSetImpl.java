package com.workday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReducerSetImpl implements Reducer {
    @Override
    public void Reduce(ArrayList<Ids> idList) {
        System.out.println("Reducing using Set...");

        Set finalResult = new HashSet<Short>();
        for (int d = 0; d < idList.size(); d++){
            Ids ids = idList.get(d);
            for (Short s: ((IdsConcreteImpl)ids).EmployeeIds
                    ) {
                finalResult.add(s);
            }
        }
        Short [] output = new Short [finalResult.size()];
        finalResult.toArray(output);
        Arrays.sort(output);
        System.out.println("\tReduced item count :" + output.length);
    }
}
