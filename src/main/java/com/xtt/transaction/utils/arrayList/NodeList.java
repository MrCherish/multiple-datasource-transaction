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
     * 头插法 列表反转
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
