package com.frost2.javabasic.dataStructure;

/**
 * 链表操作
 *
 * @author frost2
 * @date 2022-11-22 11:19
 */
public class SinglyLinkedList {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        ListNode listNode1 = new ListNode(2);

        ListNode listNode2 = addHead(listNode, listNode1);
        traversal(listNode2);

        ListNode listNode3 = insertFromTail(listNode, listNode1);
        traversal(listNode3);
    }

    //遍历
    public static void traversal(ListNode head) {
        while (null != head) {
            System.out.println(head.value);
            head = head.next;
        }
    }

    //头插:将head插入node
    @SuppressWarnings("UnnecessaryLocalVariable")
    public static ListNode addHead(ListNode node, ListNode head) {
        ListNode next = node.next;
        head.next = next;
        node.next = head;
        return node;
    }
    //尾插:将head插入node
//    public static ListNode addTail(ListNode node, ListNode newNode)

    public static ListNode insertFromTail(ListNode head1, ListNode newNode){
        if(head1 == null){ //如果是个空链表，直接把新节点赋值给head，然后结束，要先判断null的情况    其实这是一段错误代码，大家可以查看我另外一篇文章，Java参数引用传递之例外：null
            head1 =newNode;
            return head1;
        }
        ListNode temp = head1; //用temp代替head去遍历找到最后一个节点，一定不要用head自己去遍历，不然就找不到链表头了
        while (temp.next!=null){
            temp=temp.next;
        }
        temp.next=newNode;
        return head1;
    }

    static class ListNode {
        int value;
        ListNode next;

        public ListNode(int value) {
            this.value = value;
        }
    }
}
