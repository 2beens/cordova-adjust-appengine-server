package adjust.cordova.appengine.server.entities.dto;

public class ResponseMessage {
	private int success;
	private String message;
	
	public ResponseMessage() {}
	
	public ResponseMessage(int success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
