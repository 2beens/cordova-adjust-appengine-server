package adjust.cordova.appengine.server.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.UserTask;
import adjust.cordova.appengine.server.entities.dto.ResponseMessage;
import adjust.cordova.appengine.server.services.UserService;
import adjust.cordova.appengine.server.services.UserTaskService;

/**
 * Servlet implementation class UserTasksServlet
 */
@WebServlet("/tasks")
public class UserTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserTasksServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResponseMessage respMsg;
		
		String userName = request.getParameter("userName");
		if (!UserService.exists(userName)) {
			respMsg = new ResponseMessage(0, "UserName cannot be empty/null!");
			response.getWriter().print(this.objectMapper.writeValueAsString(respMsg));
			return;
		}

		String text = request.getParameter("text");
		if (text == null) {
			respMsg = new ResponseMessage(0, "Task text cannot be empty/null!");
			response.getWriter().print(this.objectMapper.writeValueAsString(respMsg));
			return;
		}

		UserTask newUserTask = new UserTask(userName, text);
		UserTaskService.addNew(newUserTask);

		User user = UserService.get(userName);
		user.addTask(newUserTask);

		respMsg = new ResponseMessage(1, "New task for user [" + userName + "] saved.");
		response.getWriter().print(this.objectMapper.writeValueAsString(respMsg));
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		if (!UserService.exists(userName)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		UserTaskService.removeAllUserTasks(userName);

		ResponseMessage respMsg = new ResponseMessage(1, "About to delete all tasks of [" + userName + "].");
		resp.getWriter().print(this.objectMapper.writeValueAsString(respMsg));
	}

}
