package com.funkydonkies.interfaces;

public interface Observable {
	public void notifyObservers(final Object arg);
	
	public void setChanged();
	
	public void addObserver(Observer o);
}
