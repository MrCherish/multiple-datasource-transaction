package com.xtt.transaction.utils.arrayList;

/**
 * ArrayListUtils
 *
 * @author dexu.tian
 * @date 2020/11/16
 */
public class ArrayListUtils {


    public static void main(String[] args) {

        NodeList list = new NodeList();
        System.out.println("构造长度为10的双向链表");
        for (int i = 0; i < 10; i++) {
            int r = (int)(Math.random() * 100);
            Node node = new Node(r);
            if (i == 0) {
                list.head = node;// 头节点
            } else {
                list.head.append(node);
            }
            list.tail = node;// 尾节点
        }

        // 遍历
        System.out.println(list.toString());

        // 倒置反转
        System.out.println(list.reverseByHead().toString());


    }
}
