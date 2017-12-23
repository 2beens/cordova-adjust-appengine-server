package adjust.cordova.appengine.server.entities;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import adjust.cordova.appengine.server.OfyService;

@Entity
public class User {
	@Id
	private String userName;
	private List<Ref<UserTask>> tasks = new ArrayList<Ref<UserTask>>();
	
	public User() {}
	
	public User(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void addTask(UserTask task) {
		this.tasks.add(Ref.create(task));
		OfyService.ofy().save().entity(this).now();
	}
	
	public List<UserTask> getTasks() {
		ArrayList<UserTask> tasks = new ArrayList<>();
		for(Ref<UserTask> userTaskRef : this.tasks) {
			tasks.add(userTaskRef.get());
		}
		return tasks;
	}
	
	@Override
	public String toString() {
		return "User [" + this.userName + "] [tasksNo: " + tasks.size() + "]";
	}
}
