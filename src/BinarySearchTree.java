import java.beans.BeanInfo;
import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<? super E>> {
    private BinaryNode<E> root;

    public BinarySearchTree(){
        root = null;
    }

    public void clear(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public boolean contains(E item){
        return contains(item, root);
    }

    private boolean contains(E item, BinaryNode<E> node){
        if(node == null) return false;
        if(node.item.compareTo(item) == 0) return true;
        if(node.item.compareTo(item) < 0) return contains(item, node.right);
        if(node.item.compareTo(item) > 0) return contains(item, node.left);
        return false;
    }

    public E findMin(){
        return findMin(root).item;
    }

    private BinaryNode<E> findMin(BinaryNode<E> node){
        if(node == null) return null;
        return node.left == null? node: findMin(node.left);
    }

    public E findMax(){
        return findMax(root).item;
    }

    public void remove(E item){
        if(!contains(item)) throw new NoSuchElementException();
        root = remove(item, root);
    }

    private BinaryNode<E> remove(E item, BinaryNode<E> node){
        if(node == null) return null;
        if(node.item.compareTo(item) < 0) node.right = remove(item, node.right);
        else if(node.item.compareTo(item) > 0) node.left = remove(item, node.left);
        else if(node.left != null && node.right != null){
            node.item = findMin(node.right).item;
            node.right = remove(node.item, node.right);
        }
        else return node.left == null? node.right: node.left;
        return node;
    }

    private BinaryNode<E> findMax(BinaryNode<E> node){
        if(node == null) return null;
        BinaryNode<E> maxNode = node;
        while(maxNode.right != null){
            maxNode = maxNode.right;
        }
        return maxNode;
    }

    public void insert(E item){
        root = insert(item, root);
    }

    private BinaryNode<E> insert(E item, BinaryNode<E> node){
        if(node == null) return new BinaryNode<>(null, item, null);
        else if(node.item.compareTo(item) < 0) node.right = insert(item, node.right);
        else if(node.item.compareTo(item) > 0) node.left = insert(item, node.left);
        return node;
    }

    private class BinaryNode<E>{
        BinaryNode<E> left;
        BinaryNode<E> right;
        E item;
        BinaryNode(E item){
            this.item = item;
        }
        BinaryNode(BinaryNode<E> left, E item, BinaryNode<E> right){
            this.left = left;
            this.item = item;
            this.right = right;
        }
    }
}

/**
 *        5
 *     3     7
 *   2   4 6    9
 *                10
 */
class testForTree{
    public static void main(String[] args){
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(5);
        tree.insert(7);
        tree.insert(3);
        tree.insert(9);
        tree.insert(2);
        tree.insert(10);
        tree.insert(6);
        tree.insert(4);
        System.out.println(tree.findMax());
        tree.remove(10);
        System.out.println(tree.findMax());
        tree.remove(2);
        System.out.println(tree.findMin());
    }
}
