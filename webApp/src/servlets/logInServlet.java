package servlets;


import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "login", urlPatterns = {"/logInServlet"})
public class logInServlet extends HttpServlet {

    private final String MAIN_URL = "../main/main.html";
    private final String SIGN_UP_URL = "../signUp/signUp.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        if (usernameFromSession == null) {
            // returns answer to the browser to go back to the sign up URL page
            response.getOutputStream().println(SIGN_UP_URL);
            } else {
            // returns answer to the browser to go back to the sign up URL page
            response.getOutputStream().println(MAIN_URL);
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
