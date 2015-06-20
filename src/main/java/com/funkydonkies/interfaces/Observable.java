package com.funkydonkies.interfaces;

/**
 * Observable interface for the Observer pattern.
 */
public interface Observable {
	/**
	 * Notifies all the observers in the observer array by calling update.
	 * 
	 * @param arg
	 *            argument
	 */
	void notifyObservers(final Object arg);

	/**
	 * Set the changed bit.
	 */
	void setChanged();

	/**
	 * Adds an observer to the observer list to be notified on changes.
	 * 
	 * @param o
	 *            Observer to add.
	 */
	void addObserver(Observer o);
}
