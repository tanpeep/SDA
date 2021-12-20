/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

package sda.lab5;

import java.util.ListIterator;

public class LinkedListTest {
    public static void main(String[] args) {
        MyList list1 = new MyList();
        list1.add(1); list1.add(2); list1.add(3); list1.add(4); list1.add(5);
        System.out.println(list1);

        MyList list2 = new MyList();
        list2.add(3); list2.add(5); list2.add(6); list2.add(7);
        System.out.println(list2);

        MyList diff = myFunc(list1, list2);
        System.out.println(diff);
    }

    /**
     * mirip dengan algo merge pada merge sort, program akan mengecek index sampai salah satu
     * diantara kedua linked list sudah melewati index terakhir, jika list1 masih ada , maka akan ditambahkan
     * kompleksitas = length(list1) + length(lis2), bisa dianggap O(n)
     * @param list1
     * @param list2
     * @return
     */
    static MyList myFunc(MyList list1, MyList list2) {
        // TODO: implement your code here ...

        MyList ret = new MyList();

        Node tempA = list1.head,tempB = list2.head;

        while(tempA!=null && tempB!=null){
            if(tempA.value<tempB.value){
                ret.add(tempA.value);
                tempA = tempA.next;
            }
            else if(tempA.value> tempB.value){
                tempB = tempB.next;
            }
            else {
                tempA = tempA.next;
                tempB = tempB.next;
            }
        }

        while(tempA!=null){
            ret.add(tempA.value);
            tempA = tempA.next;
        }

        return ret;
    }
}

class MyList {
    Node head;

    MyList() {
        head = null;
    }

    void add(int val) {
        if (head == null)
            head = new Node(val, null);
        else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new Node(val, null);
        }
    }

    public String toString() {
        String s = "";
        Node temp = head;
        while (temp != null) {
            s += temp.value;
            temp = temp.next;
            if (temp != null)
                s += (" ");
        }
        return s;
    }
}

class Node {
    int value;
    Node next;

    Node(int v, Node n) {
        value = v;
        next = n;
    }
}

