import java.sql.Timestamp;

public class PriorityQ<T> {

     private java.util.PriorityQueue<IntPriorityComparableWrapper<T>> queue;
    

    public int getSize(){
    	return queue.size();
    }
    
    public PriorityQ() {
            queue = new java.util.PriorityQueue<IntPriorityComparableWrapper<T>>();
    }

    public void add( Timestamp priority, T object ) {
            queue.add( new IntPriorityComparableWrapper<T>(object, priority) );
    }

    public T get() {
            return (null != queue.peek())? queue.poll().getObject() : null;
    }

    /**
     * A "wrapper" to impose comparable properties on any object placed in the
     * queue.
     */
    private static class IntPriorityComparableWrapper<T>
    implements Comparable<IntPriorityComparableWrapper<T>> {

            private T object;
            private Timestamp priority;

            public IntPriorityComparableWrapper( T object, Timestamp priority ) {
//            	System.out.println("%%#$%#$#$#$#$ PQ created///////");
                    this.object = object;
                    this.priority = priority;
            }

            public int compareTo(IntPriorityComparableWrapper<T> anotherObject ) {
//                    return this.priority - anotherObject.priority;
            	return this.priority.compareTo(anotherObject.priority);
            }

            public Timestamp getPriority() {
                    return priority;
            }

            public T getObject() {
                    return object;
            }
    }
}