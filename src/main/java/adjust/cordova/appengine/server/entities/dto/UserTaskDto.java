package adjust.cordova.appengine.server.entities.dto;

import adjust.cordova.appengine.server.entities.UserTask;

public class UserTaskDto {
	private String text;
	
	public UserTaskDto() {}
	
	public UserTaskDto(UserTask userTask) {
		this.text = userTask.getText();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	
}
