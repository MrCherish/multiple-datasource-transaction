package com.xtt.transaction.utils.arrayList;

/**
 * Node
 *
 * @author dexu.tian
 * @date 2020/11/16
 */
public class Node {

    /**
     * 上一个节点
     */
    private Node pre;

    /**
     * 下一个节点
     */
    private Node next;

    /**
     * 数据
     */
    private int data;

    public boolean hasPre() {
        return this.pre != null;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public void append(Node node) {
        if (this.next == null) {
            this.next = node;
            node.pre = this;
        } else {
            this.next.append(node);
        }
    }

    public Node() {
    }

    public Node(int data) {
        this.data = data;
    }

    public Node(Node pre, Node next, int data) {
        this.pre = pre;
        this.next = next;
        this.data = data;
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
