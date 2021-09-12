package servlets;

import com.google.gson.Gson;
import exception.*;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetDetailsForTradeServlet", urlPatterns = {"/GetDetailsForTrade"})
public class GetDetailsForTradeServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());

     String usernameFromSession = SessionUtils.getUsername(request);
     String userTypeFromSession = SessionUtils.getUsertype(request);
        String stockSymbolFromServletContext = SessionUtils.getStcokForTrade(request);
        int theAmountTheTraderHoldInStock=0;
        int traderBalance=0;
        int salesRate=0;
        int purchaseRate=0;

     try {

        theAmountTheTraderHoldInStock = engine.getHoldingsAmount(usernameFromSession, stockSymbolFromServletContext);
         traderBalance = engine.getTraderBalance(usernameFromSession);
         salesRate = engine.getSalesRate(stockSymbolFromServletContext);
         purchaseRate = engine.getBuyRate(stockSymbolFromServletContext);
     }
          catch (Exception e) {
              e.printStackTrace();
              response.setStatus(409);
          }
     
        // log and create the response json string
        TradeDetails tradeDetails = new TradeDetails(usernameFromSession, userTypeFromSession,stockSymbolFromServletContext, theAmountTheTraderHoldInStock, traderBalance, salesRate, purchaseRate);
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(tradeDetails);

        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse);
            out.flush();
        }
    }

    private class TradeDetails{

        private final String userName;
        private final String userType;
        private final String stockSymbol;
        private final int theAmountTheTraderHoldInStock;
        private final int traderBalance;
        private final int stockSaleGate;
        private final int stockBuyGate;

        private TradeDetails(String userName, String userType, String stockSymbol, int theAmountTheTraderHoldInStock, int traderBalance, int stockSaleGate, int stockBuyGate) {
            this.userName = userName;
            this.userType = userType;
            this.stockSymbol = stockSymbol;
            this.theAmountTheTraderHoldInStock = theAmountTheTraderHoldInStock;
            this.traderBalance = traderBalance;
            this.stockSaleGate = stockSaleGate;
            this.stockBuyGate = stockBuyGate;
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
