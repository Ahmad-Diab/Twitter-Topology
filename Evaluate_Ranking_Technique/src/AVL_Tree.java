import java.util.*;
import java.io.*;

class Node <T>
{
    T key ;
    int height ;
    Node<T> left , right ;
    Node(T a)
    {
        key = a ;
        height = 1 ;
    }
}

public class AVL_Tree <T extends Comparable<T>> implements Iterable<T>
{
    private Node<T> root ;
    private final Comparator<? super T> comparator ;

    public AVL_Tree(){comparator = Comparator.naturalOrder();}
    public AVL_Tree(Comparator<? super T> comparator) { this.comparator = comparator; }

    public Node<T> add(T key) { return root = insert(root , key) ; }
    public Node<T> remove(T key) { return root = deleteNode(root , key) ; }
    public boolean contains(T key) { return contains(root , key) ; }

    private int height(Node n)
    {
        if(n == null)return 0 ;

        return n.height;
    }
    private Node rightRotate(Node y)
    {
        Node x = y.left ;
        Node T2 = x.right ;

        x.right = y ;
        y.left = T2;

        y.height = Math.max(height(y.left) , height(y.right)) + 1;
        x.height = Math.max(height(x.left) , height(x.right)) + 1;

        return x ;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private int getBalance(Node n)
    {
        if(n == null)
            return 0 ;
        return height(n.left) - height(n.right) ;
    }

    private boolean contains(Node<T> node , T key)
    {
        if(node == null)return false ;
        if(comparator.compare(key , node.key) == 0) return true ;
        boolean left = comparator.compare(key , node.key) < 0 ? contains(node.left , key) : false;
        boolean right = comparator.compare(key , node.key) > 0 ? contains(node.right , key) : false;
        return left | right ;
    }

    Node<T> insert(Node<T> node , T key){
        if(node == null) return new Node<T>(key) ;
        if(comparator.compare(key , node.key) < 0)
            node.left = insert(node.left , key) ;
        else if(comparator.compare(key , node.key) > 0)
            node.right = insert(node.right , key) ;
        else
            return node ;

        int balance = getBalance(node) ;
        if(balance > 1 && comparator.compare(key , node.left.key) < 0)
            return rightRotate(node) ;

        if(balance > 1 && comparator.compare(key , node.left.key) > 0)
        {
            node.left = leftRotate(node.left) ;
            return rightRotate(node) ;
        }

        if(balance < -1 && comparator.compare(key , node.right.key) > 0)
            return leftRotate(node) ;

        if(balance < -1 && comparator.compare(key , node.right.key) < 0)
        {
            node.right = rightRotate(node.right) ;
            return leftRotate(node) ;
        }
        return node ;
    }
    Node<T> minValueNode(Node<T> node)
    {
        if(node.left == null)
            return node ;
        return minValueNode(node.left) ;
    }
    private Node<T> deleteNode(Node<T>root , T key)
    {
        if(root == null)return root ;

        if(comparator.compare(key , root.key) < 0)
            root.left = deleteNode(root.left, key);
        else if(comparator.compare(key , root.key) > 0)
            root.right = deleteNode(root.right, key);
        else
        {
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null ;
                if(temp == root.left)
                    temp = root.right ;
                else
                    temp = root.left ;

                if(temp == null)
                    root  = null ;
                else
                    root = temp ;
            }
            else
            {
                Node<T> temp = minValueNode(root.right) ;
                root.key = temp.key ;
                root.right = deleteNode(root.right , temp.key) ;
            }

        }
        if (root == null) return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

       if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

       if (balance < -1 && getBalance(root.right) > 0)
       {
            root.right = rightRotate(root.right);
            return leftRotate(root);
       }
        return root;
    }
    public List<T> getList()
    {
        List<T> list = new ArrayList<>() ;
        dfs(root , list);
        return list ;
    }
    private void dfs(Node<T> root , List<T> list)
    {
        if(root == null) return;
        dfs(root.left , list);
        list.add(root.key);
        dfs(root.right , list);

    }
    @Override
    public Iterator<T> iterator() { return getList().iterator(); }

    public static void main (String [] args) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        AVL_Tree<String> strings = new AVL_Tree<>((s1 , s2) -> s1.length() - s2.length()) ;
        out.flush();
        out.close();
    }
}
