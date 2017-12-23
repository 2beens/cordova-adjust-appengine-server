package adjust.cordova.appengine.server.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class UserTask {
	@Id
	private Long id;
	@Index
	private String userName;
	private String text;
	
	public UserTask() {}
	
	public UserTask(String userName, String text) {
		this.setUserName(userName);
		this.setText(text);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
