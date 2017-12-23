package adjust.cordova.appengine.server.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import adjust.cordova.appengine.server.OfyService;
import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.UserTask;

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
		if(userName == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if(!UsersServlet.userExists(userName)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String text = request.getParameter("text");
		if(text == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		UserTask newUserTask = new UserTask(userName, text);
		
		OfyService.ofy().save().entity(newUserTask).now();
		
		User user = OfyService.ofy().load().type(User.class).id(userName).now();
		user.addTask(newUserTask);
		
		response.getWriter().print("New task for user [" + userName + "] saved.");
	}

}
