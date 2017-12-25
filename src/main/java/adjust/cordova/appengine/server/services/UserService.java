package adjust.cordova.appengine.server.services;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;

import adjust.cordova.appengine.server.OfyService;
import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.dto.UserDto;

public class UserService {
	
	public static User get(String userName) {
		if(!userExistsInDb(userName)) {
			return null;
		}
		
		return OfyService.ofy().load().type(User.class)
				.id(userName).now();
	}
	
	public static String getAllAsString() {
		String allUsers = "";
		List<Key<User>> userKeys = OfyService.ofy().load().type(User.class).keys().list();
		for(Key<User> userKey : userKeys) {
			allUsers += userKey.getName() + ", ";
		}
		
		if(userKeys.size() > 0)
			allUsers = allUsers.substring(0, allUsers.length() - 2);
		
		return allUsers;
	}
	
	public static List<User> getAll() {
		List<User> allUsers = OfyService.ofy().load().type(User.class).list();
		return allUsers;
	}
	
	public static List<UserDto> getAllUsersAsDtos() {		
		List<User> allUsers = getAll();
		List<UserDto> allUsersDtos = new ArrayList<>(allUsers.size());
		for(User user : allUsers) {
			allUsersDtos.add(new UserDto(user));
		}		
		return allUsersDtos;
	}
	
	public static boolean add(String userName) {
		User newUser = new User(userName);		
		Key<User> newUserKey = OfyService.ofy().save().entity(newUser).now();
		
		if(newUserKey != null)
			return true;
		
		return false;
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
