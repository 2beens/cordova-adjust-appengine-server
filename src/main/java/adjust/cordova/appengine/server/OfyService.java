package adjust.cordova.appengine.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import adjust.cordova.appengine.server.entities.User;
import adjust.cordova.appengine.server.entities.UserTask;

public class OfyService {
    private static final Logger logger = Logger.getLogger(OfyService.class.getName());

    static {
        try {
            ObjectifyService.register(User.class);
            ObjectifyService.register(UserTask.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}

