package code.algorithms_structures.linkedList.DoubleLinkedList;

/**
 * 双向链表--Node节点
 * @author 陈伟平
 * @date 2020-4-16 10:41:37
 */
public class Node<E> {

    public E data;      //数据域
    public Node next;   //后继指针
    public Node prev;   //前驱指针

    public Node (E data){
        this.data = data;
    }

//    @Override
//    public String toString() {
//        return "Node{" +
//                "data=" + data +
//                ", next=" + next.data +
//                ", prev=" + prev.data +
//                '}';
//    }
}
