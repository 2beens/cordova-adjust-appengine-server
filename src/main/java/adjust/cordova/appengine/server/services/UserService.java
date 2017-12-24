package adjust.cordova.appengine.server.services;

import com.googlecode.objectify.Key;

import adjust.cordova.appengine.server.OfyService;
import adjust.cordova.appengine.server.entities.User;

public class UserService {
	
	public static User get(String userName) {
		if(!userExistsInDb(userName)) {
			return null;
		}
		
		return OfyService.ofy().load().type(User.class)
				.id(userName).now();
	}

	public static boolean exists(String userName) {
		if(userName == null || userName.length() == 0) {
			return false;
		}
		
		return userExistsInDb(userName);
	}
	
	public static boolean userExistsInDb(String userName) {
	    Key<User> key = Key.create(User.class, userName);
	    User user = OfyService.ofy().load().key(key).now();
	    if (user != null)
	        return true;

	    return false;
	}
}
