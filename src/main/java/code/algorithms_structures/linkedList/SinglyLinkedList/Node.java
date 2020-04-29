package code.algorithms_structures.linkedList.SinglyLinkedList;

/**
 * Node节点
 * @author 陈伟平
 * @date 2020-4-16 10:41:37
 */
public class Node<E> {

    public E data;    //数据域
    public Node next;   //指针域

    public Node(E data){
        this.data = data;
    }
}
