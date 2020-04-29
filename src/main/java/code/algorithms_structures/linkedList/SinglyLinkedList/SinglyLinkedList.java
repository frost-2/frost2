package code.algorithms_structures.linkedList.SinglyLinkedList;

import java.util.ArrayList;

/**
 * 单向链表
 * @author 陈伟平
 * @date 2020-4-20 11:36:09
 */
public class SinglyLinkedList<E> {

    //头节点
    Node<E> head = null;

    /**
     * 尾插法
     * @param data 插入链表的数据
     */
    public void addLast(E data){

        //初始化一个新的节点
        Node<E> newNode = new Node<>(data);

        if(null == head){
            head = newNode;
        }else {

            Node last = head;
            while (null != last.next){   //遍历链表取尾节点
                last = last.next;
            }
            last.next = newNode;
        }
    }

    /**
     * 头插法
     * @param data 插入链表的数据
     */
    public void addHead(E data){

        //初始化一个新的节点
        Node<E> newNode = new Node<>(data);

        if(null == head){
            head = newNode;
        }else {
            newNode.next = head;
            head = newNode;
        }

    }

    /**
     * 打印链表
     */
    public void printList() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }


    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        list.add("second");

        SinglyLinkedList<Object> singlyLinkedList = new SinglyLinkedList<>();
        singlyLinkedList.addHead(1);
        singlyLinkedList.addHead(list);
        singlyLinkedList.addHead(3);
        singlyLinkedList.addHead("forth");

        singlyLinkedList.addLast(5);

        singlyLinkedList.printList();

    }

}