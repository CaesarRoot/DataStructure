import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements Iterable<E> {
    private Object[] elements;
    private int size;  //非空元素的数目(可用于trimToSize()方法)
    private static final int DEFAULT_SIZE = 10;

    public MyArrayList(){
        doClear();
    }

    /**
     * 清理整个list返回初始化状态
     */
    public void clear(){
        doClear();
    }

    /**
     * 把size清零并且设置为默认大小
     */
    private void doClear(){
        size = 0;
        ensureCapacity(DEFAULT_SIZE);
    }
    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void trimToSize(){
        ensureCapacity(size);
    }

    public void ensureCapacity(int newCapacity){
        if(newCapacity > size){
            //直接赋值保存一份原来的副本
            Object[] old = elements;
            //创建一个新的数组
            elements = new Object[newCapacity];
            //逐一赋值 注意这里是用size当作边界 即只要实际的大小复制过去（这样clear的时候只要size清零即可）
            for(int i = 0; i < size; i++){
                elements[i] = old[i];
            }
        }
    }

    public void add(E item){
        add(size, item);
    }

    /**
     * 抽象出一个ensureCapacity用于扩容
     * @param index 插入元素序号
     * @param item 要插入的元素
     */
    public void add(int index, E item){
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
        //先扩容
        if(elements.length == size)
            ensureCapacity(size  + 1);
        //在index之后的元素后移
        for(int i = size; i > index; i--){
            elements[i] = elements[i - 1];
        }
        //插入
        elements[index] = item;
        //更新大小
        size++;
    }

    /**
     * 注意边界是按size来计算
     * @param index 要得到元素的index
     * @return 指定位置的元素
     */
    @SuppressWarnings("unchecked")
    public E get(int index){
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
        return (E) elements[index];
    }

    /**
     * 设置指定位置的元素并且返回原来的元素
     * @param index 指定位置
     * @param item 要设定的新值
     * @return 原来的值
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E item){
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
        Object old = elements[index];
        elements[index] = item;
        return (E) old;
    }

    /**
     * 移除指定位置元素并且返回这个值
     * @param index 指定位置
     * @return 被移除的值
     */
    @SuppressWarnings("unchecked")
    public E remove(int index){
        Object old = get(index);
        for(int i = index; i < size - 1; i++){
            elements[i] = elements[i + 1];
        }
        size--;
        return (E) old;
    }
    @Override
    public Iterator<E> iterator(){
        return null;
    }

    private class ArrayListIterator implements Iterator<E>{
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return get(current++);
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(--current);
        }
    }
}
