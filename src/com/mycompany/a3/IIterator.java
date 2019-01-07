package com.mycompany.a3;

public interface IIterator {
    boolean hasNext();
    Object getNext();
    int getSize();
    void removeObject(Object object);
}
