package com.funkydonkies.interfaces;

/**
 * Observer interface for the observer pattern.
 */
public interface Observer {

	/**
	 * Updates this observer of new changes in the observable.
	 * 
	 * @param o
	 *            Observable calling the update.
	 * @param arg
	 *            argument
	 */
	void update(Observable o, Object arg);
}
