package servlets;


import exception.NonTraderUserException;
import exception.UserDidntExistException;
import utils.ServletUtils;
import utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "GetUserAlert", urlPatterns = {"/getUserAlert"})
public class GetUserAlertServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");

        String usernameFromSession = SessionUtils.getUsername(request);
        String usertypeFromSession = SessionUtils.getUsertype(request);
        Engine.Engine engine= ServletUtils.getEngine(getServletContext());

        if ((usernameFromSession != null)&&(usertypeFromSession.equals("trader"))){

            try {
                String massage=engine.getTraderInformation(usernameFromSession);
                if(massage==null){
                    response.setStatus(409);
                }
                else{
                    response.getOutputStream().println(massage);
                }
            } catch (Exception e) {
                response.setStatus(409);
            }
        }
        else{
            response.setStatus(409);
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
