import java.awt.print.Book;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Iterable<E>{
    private int size;
    private Node<E> first;
    private Node<E> last;
    private int modCount = 0;

    /**
     * 初始化 有一个虚拟的头和尾
     */
    public MyLinkedList(){
        doClear();
    }

    public void clear(){
        doClear();
    }

    private void doClear(){
        first = new Node<>(null, null, null);
        last = new Node<>(first, null, null);
        first.setSucc(last);
        size = 0;
        modCount = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(E item){
        add(size, item);
    }

    public void add(int index, E item){
        Node<E> pre = getNode(index);
        addAfter(pre, item);
    }

    private void addAfter(Node<E> pre, E item){
        Node<E> succ = pre.getSucc();
        Node<E> mid = new Node<>(pre, item, succ);
        pre.setSucc(mid);
        succ.setPrev(mid);
        size++;
        modCount = 0;
    }

    public E get(int index){
        Node<E> mid = getNode(index);
        return mid.getData();
    }

    public E set(int index, E item){
        E old = getNode(index).getData();
        getNode(index).setData(item);
        return old;
    }

    public E remove(int index){
        E old = get(index);
        remove(getNode(index));
        return old;
    }

    private void connectNodes(Node<E> pre, Node<E> succ){
        pre.setSucc(succ);
        succ.setPrev(pre);
    }

    private Node<E> getNode(int index){
        if(index < size/2){
            Node<E> node = first;
            for(int i = 0; i <= index; i++){
                node = node.getSucc();
            }
            return node;
        }
        else {
            index = size - index;
            Node<E> node = last;
            for(int i = 0; i <= index; i++){
                node = node.getPrev();
            }
            return node;
        }
    }

    private void remove(Node<E> node){
        connectNodes(node.getPrev(), node.getSucc());
        size--;
        modCount--;
    }

    public void print(){
        Node<E> node = first.getSucc();
        while(node.getData() != null){
            System.out.println(node.getData());
            node = node.getSucc();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E>{
        private Node<E> current = first.getSucc();
        private boolean okToRemove = false;
        private int expectedModCount = modCount;

        @Override
        public void remove() {
            if(expectedModCount != modCount){
                throw new ConcurrentModificationException();
            }
            if(!okToRemove){
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(current);
            okToRemove = false;
            expectedModCount++;
        }

        @Override
        public boolean hasNext() {
            return current != last;
        }

        @Override
        public E next() {
            if(expectedModCount != modCount){
                throw new ConcurrentModificationException();
            }
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            current = current.getSucc();
            okToRemove = true;
            return current.getPrev().getData();
        }
    }
    private class Node<E> {
        private E item;  //节点存储的值
        private Node<E> succ;  //节点的后继
        private Node<E> prev;  //节点的前驱

        Node(Node<E> prev, E element, Node<E> next){
            this.item = element;
            this.prev = prev;
            this.succ = next;
        }

        public E getData(){
            return item;
        }

        public void setData(E item){
            this.item = item;
        }

        public Node<E> getPrev(){
            return this.prev;
        }

        public void setPrev(Node<E> prev){
            this.prev = prev;
        }

        public Node<E> getSucc(){
            return this.succ;
        }

        public void setSucc(Node<E> succ){
            this.succ = succ;
        }
    }
}

class testForLinkedList{
    public static void main(String[] args){
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("one");
        myLinkedList.add("two");
        myLinkedList.add(1, "oneTotwo");
        myLinkedList.set(3, "three");
        Iterator<String> iterator = myLinkedList.iterator();
        myLinkedList.remove(2);
        iterator.next();
        iterator.remove();
//        myLinkedList.clear();
        myLinkedList.print();
    }
}