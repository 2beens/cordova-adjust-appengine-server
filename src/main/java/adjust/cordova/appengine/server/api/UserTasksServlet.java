package adjust.cordova.appengine.server.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.UserTask;
import adjust.cordova.appengine.server.services.UserService;
import adjust.cordova.appengine.server.services.UserTaskService;

/**
 * Servlet implementation class UserTasksServlet
 */
@WebServlet("/tasks")
public class UserTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserTasksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		if(!UserService.exists(userName)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String text = request.getParameter("text");
		if(text == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		UserTask newUserTask = new UserTask(userName, text);
		UserTaskService.addNew(newUserTask);
		
		User user = UserService.get(userName);
		user.addTask(newUserTask);
		
		response.getWriter().print("New task for user [" + userName + "] saved.");
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		if(!UserService.exists(userName)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		UserTaskService.removeAllUserTasks(userName);
		
		resp.getWriter().print("About to delete all tasks of [" + userName + "].");
	}

}
