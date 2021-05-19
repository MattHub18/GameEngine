package com.company.network;

public class ReadWriteThreadSafe {
    private Object myObject = null;
    private Object otherObject = null;

    private boolean readOther = false;
    private boolean readMy = false;

    public synchronized void setMyObject(Object object) throws InterruptedException {
        while (readMy) {
            wait();
        }
        readMy = true;
        notifyAll();
        myObject = object;

    }

    public synchronized Object getMyObject() throws InterruptedException {
        while (myObject == null) {
            wait();
        }
        readMy = false;
        notifyAll();
        return myObject;
    }

    public synchronized void setOtherObject(Object object) throws InterruptedException {
        while (readOther) {
            wait();
        }
        readOther = true;
        otherObject = object;
        notifyAll();

    }

    public synchronized Object getOtherObject() throws InterruptedException {
        while (otherObject == null) {
            wait();
        }
        readOther = false;
        notifyAll();
        return otherObject;
    }
}
