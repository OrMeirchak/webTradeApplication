package utils;

import constants.Constants;
import user.UsersList;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    private static final String ENGINE_ATTRIBUTE_NAME = "engine";
    private static final String STOCK_SYMBOL_FOR_TRADE_ATTRIBUTE_NAME = "stocksymbolfortrade";

    public static Engine.Engine getEngine(ServletContext servletContext) {
        servletContext.setAttribute(ENGINE_ATTRIBUTE_NAME, Engine.Engine.getEngine());

        return (Engine.Engine)servletContext.getAttribute(ENGINE_ATTRIBUTE_NAME);
    }

    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return Constants.INT_PARAMETER_ERROR;
    }
}
