/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 15, 2021
 * Assignment2 : Compressed Literature (MyPriorityQueue class)
 * Professor. Christopher Paul Marriott
 */


import java.util.ArrayList;

public class MyPriorityQueue<Type extends Comparable<Type>> {

    private ArrayList<Type> minHeap;

    public MyPriorityQueue() {
        this.minHeap = new ArrayList<>();
    }

    // Inserts the specified element into this priority queue.
    public void offer(Type item) {
        // add the item into ArrayList
        minHeap.add(item);

        // bubbles up if the value is less than its parent.
        int current = minHeap.size() - 1;
        while (current > 0){
            int parent = (current - 1) / 2;
            if (minHeap.get(current).compareTo(minHeap.get(parent)) < 0){
                Type temp = minHeap.get(current);
                minHeap.set(current, minHeap.get(parent));
                minHeap.set(parent, temp);
                current = parent;
            } else {
                break;
            }
        }
    }

    // Retrieves and removes the head of this queue, or returns null if this queue is empty.
    public Type poll() {
        if (isEmpty()){
            return null;
        } else {
            // swap the minimum element (root) with the last element
            int last = minHeap.size() - 1;
            Type target = minHeap.get(0);
            minHeap.set(0, minHeap.get(last));
            minHeap.set(last, target);
            // remove the minimum value
            minHeap.remove(last);

            // sinks down if the root is not the minimum.
            if (!isEmpty()){
                int i = 0;
                Type element = minHeap.get(i);
                while (2 * i < minHeap.size() - 1){
                    int left = 2*i + 1;
                    int right = 2*i + 2;

                    int min;
                    if (left < minHeap.size() - 1 && minHeap.get(left).compareTo(minHeap.get(right)) > 0) {
                        min = right;
                    } else {
                        min = left;
                    }
                    if (element.compareTo(minHeap.get(min)) > 0) {
                        Type temp = minHeap.get(i);
                        minHeap.set(i, minHeap.get(min));
                        minHeap.set(min, temp);
                        i = min;
                    } else {
                        break;
                    }
                }
            }
            return target;
        }
    }

    // Returns the number of elements in this collection.
    public int size() {
        return minHeap.size();
    }

    // Return true when arraylist is empty
    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    // Print the arraylist
    public String toString() {
        return minHeap.toString();
    }
}
