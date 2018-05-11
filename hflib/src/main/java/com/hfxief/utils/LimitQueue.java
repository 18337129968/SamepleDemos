package com.hfxief.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * LongFor
 * com.hfxief.utils
 *
 * @Author: xie
 * @Time: 2017/6/5 20:27
 * @Description:
 */


public class LimitQueue implements Queue<String> {
    private int limit;
    private Queue<String> queue = new LinkedList<>();

    public LimitQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.size() == 0 ? true : false;
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public Iterator iterator() {
        return queue.iterator();
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean add(String o) {
        return queue.add(o);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return queue.addAll(c);
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean retainAll(Collection c) {
        return queue.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return queue.containsAll(c);
    }

    /**
     * 入队
     */
    @Override
    public boolean offer(String o) {
        if(queue.size() >= limit){
            //如果超出长度,入队时,先出队
            queue.poll();
        }
        return queue.offer(o);
    }

    @Override
    public String remove() {
        return queue.remove();
    }

    @Override
    public String poll() {
        return queue.poll();
    }

    @Override
    public String element() {
        return queue.element();
    }

    @Override
    public String peek() {
        return queue.peek();
    }

    public Queue getQueue(){
        return queue;
    }

    public int getLimit(){
        return limit;
    }

}
