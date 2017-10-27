package com.workday;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class RangeQueryBasicTest {
    private  RangeContainer rc;
    private int numPayRollResults = 32000, numMachines = 100, payRollResultLimit = 10000;

    /*
    Helper methods to allow dynamic RangeContainerFactory creation
     */
    private String RangeContainerFactoryType;
    public void setRangeContainerFactoryType(String rangeContainerFactoryType) {
        RangeContainerFactoryType = rangeContainerFactoryType;
    }

    public RangeContainerFactory CreateRangeContainerFactory(){
        switch (RangeContainerFactoryType)
        {
            case "Array":
                return new RangeContainerFactoryArrayImpl();

            case "TreeMap":
                return new RangeContainerFactoryTreeMapImpl();

            default:
                return new RangeContainerFactoryArrayImpl();
        }
    }

    @Before
    public void setUp(){
        RangeContainerFactory rf = CreateRangeContainerFactory();
        System.out.println("Using " + rf.getClass().getSimpleName());
        rc = rf.createContainer(new long[]{10,12,17,21,2,15,16});
    }

    @Test
    public void runARangeQuery(){
        Ids ids = rc.findIdsInRange(14, 17, true, true);
        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
        assertEquals(3, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }

    public void runManyRangeQueries(){
        System.out.println("\nSimulating Distributed Tests for profiling...");
        System.out.println("# Machines = " + numMachines);
        System.out.println("# Payroll Results / Machine = " + numPayRollResults);
        System.out.println();

        // Creating Test Data
        ArrayList<long[]> dataList = new ArrayList();

        Random random = new Random(System.nanoTime());
        for(int m = 0; m < numMachines; m++){
            long [] data = new long[numPayRollResults];

            for(int p = 0; p < numPayRollResults; p++){
                data[p] = random.nextInt(payRollResultLimit);
            }
            dataList.add(data);
        }
        // Running Tests on different RangeContainerFactories
        ArrayList<Ids> idList;

        RangeContainerFactoryType = "Array";
        idList = MapOut(dataList, CreateRangeContainerFactory());

        RangeContainerFactoryType = "TreeMap";
        idList = MapOut(dataList, CreateRangeContainerFactory());

        // Reducing results to a single unique list
        Reducer reducer;

        // Using an unique Set to reduce
        reducer = new ReducerSetImpl();
        Profiler.Start();
        reducer.Reduce(idList);
        Profiler.End();

        // Using a min heap to reduce
        reducer = new ReducerHeapImpl();
        Profiler.Start();
        reducer.Reduce(idList);
        Profiler.End();
    }

    private ArrayList<Ids> MapOut(ArrayList<long[]> dataList, RangeContainerFactory rf) {
        ArrayList<Ids> idList = new ArrayList();

        System.out.println("Mapping using " + rf.getClass().getSimpleName());

        Profiler.Start();

        for (int d = 0; d < dataList.size(); d++){
            Ids ids = rf.createContainer(dataList.get(d)).findIdsInRange(14, 88, true, true);
//            System.out.println( ((IdsConcreteImpl)ids).EmployeeIds.size() + " employees in range");
            idList.add(ids);
        }

        Profiler.End();

        return idList;
    }

}
class HeapNode {
    public Short value;
    public Ids id;
}

