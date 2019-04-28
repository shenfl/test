package com.test.java;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * 有序list实现：https://stackoverflow.com/questions/416266/sorted-collection-in-java
 */
public class SortedList implements List<Long> {
    private List<Long> data;

    public SortedList() {
        data = new LinkedList<>();
    }

    public static void main(String[] args) {
        SortedList list = new SortedList();
        list.addOrdered(4L);
        list.addOrdered(47L);
        list.addOrdered(12L);
        list.addOrdered(78L);
        System.out.println(list);
        System.out.println(list.get(1));
    }

    public synchronized void addOrdered(long data) {
        int index = Collections.binarySearch(this.data, data);
        if(index < 0) {
            index = -(index + 1);
        }
        this.data.add(index, data);
    }

    @Override
    public Long get(int index) {
        return data.get(index);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Long> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super Long> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Long> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super Long> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<Long> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super Long> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long set(int index, Long element) {
        return null;
    }

    @Override
    public void add(int index, Long element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Long> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<Long> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Long> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<Long> spliterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Long> stream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Long> parallelStream() {
        throw new UnsupportedOperationException();
    }
}
