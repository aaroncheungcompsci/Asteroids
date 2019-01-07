package com.mycompany.a3;

import java.util.ArrayList;

public class GameCollection implements ICollection {

    private ArrayList collection;

    public GameCollection() {
        collection = new ArrayList();
    }

    public void add(GameObject object) {
        collection.add(object);
    }

    public void remove(GameObject object) {
        collection.remove(object);
    }

    public void removeAtIndex(int i) {
        collection.remove(i);
    }

    public IIterator getIterator() {
        return new SpaceIterator();
    }

    public int getSize() {
        return collection.size();
    }

    public Object getElement(int i) {
        return collection.get(i);
    }

    private class SpaceIterator implements IIterator {
        private int currIndex;
        public SpaceIterator() {
            currIndex = -1;
        }

        public boolean hasNext() {
            if (collection.size() <= 0) {
                return false;
            } else if (currIndex == collection.size() - 1) {
                return false;
            } else {
                return true;
            }
        }

        public Object getNext() {
            currIndex++;
            return collection.get(currIndex);
        }

        public int getSize() {
            return collection.size();
        }

        public void removeObject (Object o) {
            collection.remove(o);
            currIndex--;
        }
    }

}



