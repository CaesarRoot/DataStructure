import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class test {
    int outer = 1;
    public static void main(String[] args){
        ArrayList<Integer> list=new ArrayList<>();
        CopyOnWriteArrayList<Integer> list1=new CopyOnWriteArrayList<>();
        ArrayList<Integer> list_copy=new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.clear();
        linkedList.isEmpty();


        for(int i=1;i<=5;i++) {
            list.add(i);
            list1.add(i);
            list_copy.add(i);
        }
//        for(int i:list) {
//            list.remove(i);
//            System.out.println(list);
//        }
        Iterator<Integer> iterator = list1.iterator();
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            list1.remove(integer);
            System.out.println(list1);
        }
    }

    public void go(){
        inner i = new inner();
        outer--;
        i.print();
        System.out.println(outer);
    }

    private class inner{
        int inner = outer;
        public void print(){
            System.out.println(inner);
        }
    }
}
