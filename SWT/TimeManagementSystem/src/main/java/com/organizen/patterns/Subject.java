package com.organizen.patterns;
//Subject ist das zu beobachhtende Objekt, sobald Zustand ändert werden Observer benachrichtigt

//Implementation Subject Interface
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}
