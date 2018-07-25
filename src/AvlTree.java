import java.util.NoSuchElementException;

public class AvlTree<E extends Comparable<? super E>> {
    private static final int ALLOWED_IMBALANCE = 1;
    private AvlNode<E> root;

    private int height(AvlNode<E> node){
        return node == null? -1: node.height;
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

    private boolean contains(E item, AvlNode<E> node){
        if(node == null) return false;
        if(node.item.compareTo(item) == 0) return true;
        if(node.item.compareTo(item) < 0) return contains(item, node.right);
        if(node.item.compareTo(item) > 0) return contains(item, node.left);
        return false;
    }

    public E findMin(){
        return findMin(root).item;
    }

    private AvlNode<E> findMin(AvlNode<E> node){
        if(node == null) return null;
        return node.left == null? node: findMin(node.left);
    }

    public E findMax(){
        return findMax(root).item;
    }
    private AvlNode<E> findMax(AvlNode<E> node){
        if(node == null) return null;
        AvlNode<E> maxNode = node;
        while(maxNode.right != null){
            maxNode = maxNode.right;
        }
        return maxNode;
    }

    public void insert(E item){
        root = insert(item, root);
    }

    private AvlNode<E> insert(E item, AvlNode<E> node){
        if(node == null) return new AvlNode<E>(null, item, null);
        if(node.item.compareTo(item) < 0) node.right = insert(item, node.right);
        else if(node.item.compareTo(item) > 0) node.left = insert(item, node.left);
        return balance(node);
    }

    private AvlNode<E> balance(AvlNode<E> node){
        if(node == null) return node;
        if(height(node.left) - height(node.right) > ALLOWED_IMBALANCE)
            if(height(node.left.left) >= height(node.left.right))
                node = rotateWithLeftChild(node);
            else
                node = doubleWithLeftChild(node);
        else if(height(node.right) - height(node.left) > ALLOWED_IMBALANCE)
            if(height(node.right.right) >= height(node.right.left))
                node = rotateWithRightChild(node);
            else
                node = doubleWithRightChild(node);
        //更新每棵树的高度
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    public void remove(E item){
        if(!contains(item)) throw new NoSuchElementException();
        root = remove(item, root);
    }

    private AvlNode<E> remove(E item, AvlNode<E> node){
        if(node == null) return null;
        if(node.item.compareTo(item) < 0) node.right = remove(item, node.right);
        else if(node.item.compareTo(item) > 0) node.left = remove(item, node.left);
        else if(node.left != null && node.right != null){
            node.item = findMin(node.right).item;
            node.right = remove(node.item, node.right);
        }
        else return node.left == null? node.right: node.left;
        return balance(node);
    }
    private AvlNode<E> rotateWithLeftChild(AvlNode<E> k2){
        AvlNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        //更新树的高度
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        return k1;
    }

    private AvlNode<E> doubleWithLeftChild(AvlNode<E> k3){
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<E> rotateWithRightChild(AvlNode<E> k1){
        AvlNode<E> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        //更新树的高度
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        return k2;
    }

    private AvlNode<E> doubleWithRightChild(AvlNode<E> k1){
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
    private class AvlNode<E>{
        AvlNode<E> left;
        E item;
        AvlNode<E> right;
        int height = 0;

        AvlNode(E item){this.item = item;}

        AvlNode(AvlNode<E> left, E item, AvlNode<E> right){
            this.left = left;
            this.item = item;
            this.right = right;
        }
    }
}

class testForAvlTree{
    public static void main(String[] args){
        AvlTree<Integer> tree = new AvlTree<>();
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
