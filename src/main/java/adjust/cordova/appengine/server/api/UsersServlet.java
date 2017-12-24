package adjust.cordova.appengine.server.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.Key;

import adjust.cordova.appengine.server.OfyService;
import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.dto.UserDto;
import adjust.cordova.appengine.server.services.UserService;

/**
 * Servlet implementation class UsersServlet
 */
@WebServlet("/user")
public class UsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ObjectMapper objectMapper = new ObjectMapper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action != null && action.toLowerCase().equals("all")) {
			String allUsers = "";
			List<Key<User>> userKeys = OfyService.ofy().load().type(User.class).keys().list();
			for(Key<User> userKey : userKeys) {
				allUsers += userKey.getName() + ", ";
			}
			
			if(userKeys.size() > 0)
				allUsers = allUsers.substring(0, allUsers.length() - 2);
			
			response.getWriter().print(allUsers);
			return;
		}
		
		String userName = request.getParameter("userName");
		if(userName == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if(!UserService.userExistsInDb(userName))  {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		User user = OfyService.ofy().load().type(User.class).id(userName).now();
		UserDto userDto = new UserDto(user);
		response.getWriter().print(this.objectMapper.writeValueAsString(userDto));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		if(UserService.exists(userName)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		User newUser = new User(userName);
		
		OfyService.ofy().save().entity(newUser).now();
		
		response.getWriter().print("New User [" + userName + "] saved.");
	}
}
