package code.algorithms_structures.LRU;

/**
 * Node节点
 * @author 陈伟平
 * @date 2020-4-16 10:41:37
 */
public class Node {

    public int key,val;
    public Node next,prev;

    public Node (int key,int val){
        this.key = key;
        this.val = val;
    }
}
