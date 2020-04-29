package code.algorithms_structures.linkedList.DoubleLinkedList;

/**
 * 双向链表
 * @author 陈伟平
 * @date 2020-4-16 10:43:37
 */
public class DoubleLinkedList<E> {

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
        }else{

            Node last = head;
            while (null != last.next){   //遍历链表取尾节点
                last = last.next;
            }
            last.next = newNode;
            newNode.prev = last;

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
            head.prev = newNode;
            head = newNode;
        }


    }


    /**
     * 正向遍历链表
     */
    public void printList() {

        if(head == null) {
            System.out.println("空表");
        }

        Node curNode = head;
        while (curNode != null) {
            System.out.println("data:"+curNode.data);
            if(null != curNode.next){
                System.out.println("next:"+curNode.next.data);
            }else{
                System.out.println("next:"+"尾节点");
            }
            if(null != curNode.prev){
                System.out.println("prev:"+curNode.prev.data);
            }else{
                System.out.println("prev:"+"头节点");
            }
            System.out.println();
            curNode = curNode.next;
        }

    }

    public static void main(String[] args) {

        DoubleLinkedList<Object> doubleLinkedList = new DoubleLinkedList<>();
        doubleLinkedList.addHead(1);
        doubleLinkedList.addHead(2);
        doubleLinkedList.addHead(3);
        doubleLinkedList.addHead(4);

        doubleLinkedList.addLast(0);

        doubleLinkedList.printList();

    }

}
