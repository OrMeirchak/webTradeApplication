package servlets;


import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "GetUserDetailsServlet", urlPatterns = {"/getUserDetails"})
public class GetUserDetailsServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        String usertypeFromSession = SessionUtils.getUsertype(request);
        Engine.Engine engine= ServletUtils.getEngine(getServletContext());
        int userbalanceFromEngine=0;

        if (usernameFromSession == null) {
            // returns answer to the browser to go back to the sign up URL page
            response.setStatus(409);
        } else {

            if(usertypeFromSession.equals("trader")) {

                try {
                     userbalanceFromEngine = engine.getTraderBalance(usernameFromSession);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(new UserDetails(usernameFromSession,usertypeFromSession,userbalanceFromEngine));

            try (PrintWriter out = response.getWriter()) {
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

    class UserDetails{
        private final String username;
        private final String usertype;
        private final int userbalance;

        UserDetails(String username, String usertype, int userbalance) {
            this.username = username;
            this.usertype = usertype;
            this.userbalance = userbalance;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
