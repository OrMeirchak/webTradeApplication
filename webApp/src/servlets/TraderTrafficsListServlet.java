package servlets;

import com.google.gson.Gson;
import constants.Constants;
import exception.NonTraderUserException;
import exception.UserDidntExistException;
import user.SingleTrafficEntry;
import user.SingleUserEntry;
import utils.ListAndVersion;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "TraderTrafficsListServlet", urlPatterns = {"/TraderTrafficsList"})
public class TraderTrafficsListServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());
     String usernameFromSession = SessionUtils.getUsername(request);


        int TraderTrafficsListVersion = ServletUtils.getIntParameter(request, Constants.TRADER_TRAFFICS_LIST_VERSION_PARAMETER);
        if (TraderTrafficsListVersion == Constants.INT_PARAMETER_ERROR) {
            return;
        }

        /*
        Synchronizing as minimum as I can to fetch only the relevant information from the chat manager and then only processing and sending this information onward
        Note that the synchronization here is on the ServletContext, and the one that also synchronized on it is the chat servlet when adding new chat lines.
         */
        int TraderTrafficsListEngineVersion = 0;
        List<SingleTrafficEntry> TraderTrafficsEntries=null;

        synchronized (getServletContext()) {
            try {
                TraderTrafficsListEngineVersion = engine.getTrafficsListVersion(usernameFromSession);
                TraderTrafficsEntries = engine.getTraderTrafficsEntries(TraderTrafficsListVersion, usernameFromSession);
            } catch (UserDidntExistException | NonTraderUserException e) {
                e.printStackTrace();
            }
        }

        // log and create the response json string
        ListAndVersion<SingleTrafficEntry> ulav = new ListAndVersion<SingleTrafficEntry>(TraderTrafficsEntries, TraderTrafficsListEngineVersion);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(ulav);
        logServerMessage("Server trader traffics list version: " + TraderTrafficsListEngineVersion + "' Trdaer traffics list version: " + TraderTrafficsListVersion);
        logServerMessage(jsonResponse);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    private void logServerMessage(String message){
        System.out.println(message);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
