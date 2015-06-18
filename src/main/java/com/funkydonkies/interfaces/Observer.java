package com.funkydonkies.interfaces;

/**
 * Observer interface for the observer pattern.
 *
 */
public interface Observer {
	
	void update(Observable o, Object arg);
}
