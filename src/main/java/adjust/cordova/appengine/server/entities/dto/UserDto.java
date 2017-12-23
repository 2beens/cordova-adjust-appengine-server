package adjust.cordova.appengine.server.entities.dto;

import java.util.ArrayList;
import java.util.List;

import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.UserTask;

public class UserDto {
	private String userName;
	private List<UserTaskDto> tasks = new ArrayList<>();
	
	public UserDto() {}
	
	public UserDto(User user) {
		this.userName = user.getUserName();
		this.tasks = new ArrayList<>(user.getTasks().size());
		
		List<UserTask> userTasks = user.getTasks();
		for(UserTask task : userTasks) {
			this.tasks.add(new UserTaskDto(task));
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UserTaskDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<UserTaskDto> tasks) {
		this.tasks = tasks;
	}
}
