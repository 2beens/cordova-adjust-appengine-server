package adjust.cordova.appengine.server.services;

import com.googlecode.objectify.Key;

import adjust.cordova.appengine.server.OfyService;
import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.UserTask;

public class UserTaskService {

	public static Key<UserTask> addNew(UserTask userTask) {
		if(userTask == null) {
			//throw new Exception("userName cannot be null");
			return null;
		}
		
		return OfyService.ofy().save().entity(userTask).now();
	}
	
	public static void removeAllUserTasks(String userName) {
		if(!UserService.exists(userName))
			return;
		
		User user = UserService.get(userName);
		user.clearAllTasks();
	}
}
