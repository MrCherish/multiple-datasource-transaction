package com.xtt.transaction.utils.arrayList;

/**
 * NodeList
 *
 * @author dexu.tian
 * @date 2020/11/16
 */
public class NodeList {

    /**
     * 头指针
     */
    public Node head;

    /**
     * 尾指针
     */
    public Node tail;

    /**
     * 头插法 双链表列表反转
     *
     * @return
     */
    public NodeList reverseByHead() {

        // 当前节点
        Node cur = head;

        // 反转之后的头指针
        Node newHead = null;

        // 反转之后的尾指针
        Node newTail = null;

        while (cur != null ) {

            // 当前节点的前一个节点
            Node pre = cur.getPre();

            // 当前节点的后一个节点
            Node next = cur.getNext();

            // 如果当前节点的前一个节点是null,则说明该节点为头节点，
            if (null == pre) {
                 // 此时 反转之后的尾指针 = 当前的节点的头指针.
                newTail = cur;
            }

            // 如果当前节点的后一个节点是null,则说明该节点为尾节点，
            if (null == next) {
                // 此时 反转之后的头指针 = 当前的节点的尾指针.
                newHead = cur;
            }

            cur.setNext(pre);
            cur.setPre(next);
            cur = cur.getPre();
        }

        this.head = newHead;
        this.tail = newTail;

        return this;
    }

    /**
     * 头插法 单链表列表反转
     *
     */
    public NodeList reverseSingleByHead() {

        // 当前切下来的节点
        Node now = null;

        // 新的头指针
        Node newHead = null;

        // 8->10->12
        while (head != null) {

            // 当前切下来的节点
            now = head;

            // 头指针后移
            head = head.getNext();

            // 将当前头指针 的下一个节点
            now.setNext(newHead);

            // 新的头指针指向当前切下来的节点
            newHead = now;

        }

        this.head = newHead;
        return this;
    }

    /**
     * 尾插法 单链表列表反转
     *
     * @return
     */
    public NodeList reverseSingleByTail() {

        // 反转之后的节点
        Node p0 = null;

        // 当前指针指向的节点
        Node p1 = head;

        // 当前指针指向的下一个节点
        Node p2 = head.getNext();

        while (p1 != null ) {

            // 将当前节点的下一个节点指向 反转之后的节点
            p1.setNext(p0);

            // 将反转之后的节点 指向 当前节点
            p0 = p1;

            // 当前指针后移
            p1 = p2;

            // 下一个节点也跟着后移
            if (p2 != null) {
                p2 = p2.getNext();
            }
        }

        this.head = p0;
        return this;
    }



    /**
     * 遍历
     */
    @Override
    public String toString() {
        Node curr = head;
        StringBuilder sBuilder = new StringBuilder();
        while (curr != null) {
            sBuilder.append(curr.getData()).append(curr.hasNext() ? "->" : "");
            curr = curr.getNext();
        }
        return sBuilder.toString();
    }

    /**
     * 追加
     * @param node
     */
    public void append(Node node) {
        if (head.getNext() == null) {
            head.setNext(node);
            node.setPre(head);
        } else {
            head.getNext().append(node);
        }
    }

}
