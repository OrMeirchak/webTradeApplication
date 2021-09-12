package servlets;

import com.google.gson.Gson;
import constants.Constants;
import exception.*;
import trade.SingleTradeInformation;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@WebServlet(name = "MakeTradeServlet", urlPatterns = {"/makeTrade"})
public class MakeTradeServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());


        String usernameFromSession = SessionUtils.getUsername(request);
        String stockSymbolFromSession = SessionUtils.getStcokForTrade(request);

//get parmeters from request
        String buyOrSaleOrdinanceFromParameter = request.getParameter(Constants.BUY_OR_SALE_ORDINANCE_FROM_PARAMETER);
        String typeOfOrdinanceFromParameter = request.getParameter(Constants.TYPE_OF_ORDINANCE_FROM_PARAMETER);
        String amountFromParameter = request.getParameter(Constants.STOCKAMOUNT);
        String limitFromParameter = request.getParameter(Constants.LIMIT_FROM_PARAMETER);

        if(typeOfOrdinanceFromParameter.equals("mkt")){limitFromParameter="0";}

        if (buyOrSaleOrdinanceFromParameter == null || buyOrSaleOrdinanceFromParameter.isEmpty()
                || (typeOfOrdinanceFromParameter == null || typeOfOrdinanceFromParameter.isEmpty())
                || (amountFromParameter == null || amountFromParameter.isEmpty())
                || (limitFromParameter == null || limitFromParameter.isEmpty())) {

            response.getOutputStream().println("Please fill in all the fields");
        } else {
            String massage = null;

            try {
                if (buyOrSaleOrdinanceFromParameter.equals(Constants.SALE_ORDINANCE_PARAMETER)) {
                    massage = engine.saleOrdinance(stockSymbolFromSession, Integer.valueOf(amountFromParameter),
                            Integer.valueOf(limitFromParameter), usernameFromSession, typeOfOrdinanceFromParameter.toUpperCase(Locale.ROOT));


                } else if (buyOrSaleOrdinanceFromParameter.equals(Constants.BUY_ORDINANCE_PARAMETER)) {
                    massage = engine.buyOrdinance(stockSymbolFromSession, Integer.valueOf(amountFromParameter),
                            Integer.valueOf(limitFromParameter), usernameFromSession, typeOfOrdinanceFromParameter.toUpperCase(Locale.ROOT));


                }
                response.getOutputStream().println(massage);
            } catch (Exception e) {
                response.getOutputStream().println(e.getMessage());
            }

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
