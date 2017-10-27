package com.workday;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ReducerHeapImpl implements Reducer {
    @Override
    public void Reduce(ArrayList<Ids> idList) {
        System.out.println("Reducing using Heap...");

        PriorityQueue<HeapNode> heap = new PriorityQueue<HeapNode>(idList.size(), new Comparator<HeapNode>(){

            @Override
            public int compare(HeapNode o1, HeapNode o2) {
                if(o1.value < o2.value) return -1;
                else if (o1.value == o2.value) return 0;
                else return 1;
            }
        });
        for (int d = 0; d < idList.size(); d++){
            short empId = idList.get(d).nextId();
            if (empId != Ids.END_OF_IDS)
            {
                HeapNode heapNode = new HeapNode();
                heapNode.value = empId;
                heapNode.id = idList.get(d);
                heap.add(heapNode);
            }
        }
        ArrayList<Short> finalResult = new ArrayList();

        short lastValInserted = -1;
        while(!heap.isEmpty()){
            HeapNode rootNode = heap.poll();
            Ids id = rootNode.id;
            short nextVal = id.nextId();
            if(nextVal != Ids.END_OF_IDS)
            {
                HeapNode nextNode = new HeapNode();
                nextNode.value = nextVal;
                nextNode.id = id;
                heap.add(nextNode);
            }
            if( rootNode.value != lastValInserted) {
                finalResult.add(rootNode.value);
                lastValInserted = rootNode.value;
            }
        }
        Short [] output = new Short [finalResult.size()];
        finalResult.toArray(output);
        System.out.println("\tReduced item count :" + output.length);
    }
}
