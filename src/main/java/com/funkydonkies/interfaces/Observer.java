package com.funkydonkies.interfaces;

import com.funkydonkies.interfaces.Observable;

/**
 * Observer interface for the observer pattern.
 *
 */
public interface Observer {
	
	void update(Observable o, Object arg);
}
