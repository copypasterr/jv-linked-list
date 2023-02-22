package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> prev;
    private Node<T> next;
    private int size;

    public MyLinkedList() {
        prev = null;
        next = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (prev == null) {
            prev = next = new Node<>(null, value, null);
        } else if (index == 0) {
            prev.prev = new Node<>(null, value, prev);
            prev = prev.prev;
        } else if (index == size) {
            next.next = new Node<>(next, value, null);
            next = next.next;
        } else {
            Node<T> current = prev;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.prev.next = new Node<>(current.prev, value, current);
            current.prev = current.prev.next;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isIndexOutOfBoundsException(index);
        Node<T> current = prev;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        isIndexOutOfBoundsException(index);
        Node<T> current = prev;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexOutOfBoundsException(index);
        if (prev == null) {
            throw new RuntimeException("List is empty");
        }
        Node<T> current;
        if (index == 0) {
            current = prev;
        } else if (index == size - 1) {
            current = next;
        } else {
            current = prev;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T node) {
        Node<T> current = prev;
        while (current != null) {
            if (areEqual(node, current.item)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean areEqual(T a, T b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }

    private void isIndexOutOfBoundsException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    private void unlink(Node<T> node) {
        if (node.prev == null) {
            prev = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            next = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}