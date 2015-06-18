package com.funkydonkies.interfaces;

public interface Observable {
	void notifyObservers(final Object arg);
	
	void setChanged();
	
	void addObserver(Observer o);
}
