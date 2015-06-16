package com.funkydonkies.interfaces;

import com.funkydonkies.interfaces.Observable;

public interface Observer {
	
	void update(Observable o, Object arg);
}
