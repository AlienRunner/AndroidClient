package com.spoton.alienrunner;

import java.util.ArrayList;

public interface DatabaseHandlerInterface {

	//Should return a list of User close to myUser
	public ArrayList<User> setAndFetch(User myUser);
	
}
