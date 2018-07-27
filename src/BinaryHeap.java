import java.util.Arrays;
import java.util.NoSuchElementException;

public class BinaryHeap<E extends Comparable<? super E>> {
    private int currentSize;
    private E[] array;

    @SuppressWarnings("unchecked")
    public BinaryHeap(){
        currentSize = 0;
        array = (E[]) new Object[15];
    }

    public BinaryHeap(E[] items){
        currentSize = items.length;
        array = Arrays.copyOfRange(items, 0, items.length);
        buildHeap();
    }

    private void buildHeap(){
        for(int i = currentSize / 2; i > 0; i--){
            percolateDown(i);
        }
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public E findMin(){
        if(isEmpty()) throw new NoSuchElementException();
        return array[1];
    }

    public void insert(E x){
        if(currentSize == array.length - 1){
            enlargeArray(array.length * 2 + 1);
        }

        currentSize++;
        int hole = currentSize;
        for(;x.compareTo(array[hole / 2]) < 0; hole /= 2){
            array[hole] = array[hole / 2];
        }
        array[hole] = x;
    }

    public E deletMin(){
        if(isEmpty()) throw new NoSuchElementException();
        E minItem = findMin();
        array[1] = array[currentSize];
        currentSize--;
        percolateDown(1);
        return minItem;
    }

    private void percolateDown(int hole){
        E x = array[hole];
        int child = hole * 2;
        while(child <= currentSize){
            if(child != currentSize && array[child + 1].compareTo(array[child]) < 0){
                child++;
            }
            if(array[child].compareTo(x) < 0){
                array[hole] = array[child];
            }
            else break;
            hole = child;
            child *= 2;
        }
        array[hole] = x;
    }

    private void enlargeArray(int size){
        E[] newArray = (E []) new Object[size];
        newArray = Arrays.copyOfRange(array, 0, array.length);
        array = newArray;
    }
}
