public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public static void main(String[] args) {
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public MyLinkedList(Node node) {
        head = node;
        tail = node;
        size = 1;
    }

    public Node insertFront(int length, int red, int green, int blue) {
        Node add = new Node(length, red, green, blue);
        if (size == 0) {
            head = add;
            tail = add;
        }
        else {
            head.prev = add;
            add.next = head;
            head = add;
        }
        size++;
        return add;
    }

    public Node insertBack(int length, int red, int green, int blue) {
        Node add = new Node(length, red, green, blue);
        if (size == 0) {
            head = add;
            tail = add;
        }
        else {
            tail.next = add;
            add.prev = tail;
            tail = add;
        }
        size ++;
        return add;
    }

    public Node insertAfter(Node node, int length, int red, int green, int blue) {
        Node add = new Node(length, red, green ,blue);
        add.next = node.next;
        add.prev = node;
        node.next = add;
        if (add.next != null) {
            add.next.prev = add;
        }
        else tail = add;
        size++;
        return add;
    }

    public Node insertBefore(Node node, int length, int red, int green, int blue) {
        Node add = new Node(length, red, green, blue);
        add.next = node;
        add.prev = node.prev;
        node.prev = add;
        if (add.prev != null) {
            add.prev.next = add;
        }
        else head = add;
        size++;
        return add;
    }

    public Node remove(Node node) {
        if (node == tail) {
            tail = node.prev;
            node.prev.next = null;
            return tail;
        }
        else {
            Node curr = head;
            while (curr != null && curr != node) {
                curr = curr.getNext();
            }
            if (curr == node) {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                size--;
            }
            return curr;
        }
    }

    public Node getFront() {
        return head;
    }

    public Node getBack() {
        return tail;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    public String toString() {
        Node curr = head;
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        while (curr != null) {
            sb.append(curr);
            sb.append(" ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    class Node {

        private Node next;
        private Node prev;
        private int length;
        private int red;
        private int green;
        private int blue;

        public Node(int length, int red, int green, int blue) {
            setLength(length);
            setRGB(red, green, blue);
            next = null;
            prev = null;
        }

        public void setRGB(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public int getLength() {
            return length;
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }

        public int[] getData() {
            int[] result = new int[] {
                length, red, green, blue
            };
            return result;
        }

        public boolean equals(int length, int red, int green, int blue) {
            return this.length == length && this.red == red && this.green == green && this.blue == blue;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(length);
            sb.append(": ");
            sb.append(red);
            sb.append(", ");
            sb.append(green);
            sb.append(", ");
            sb.append(blue);
            sb.append(")");
            return sb.toString();

        }


    }

}
