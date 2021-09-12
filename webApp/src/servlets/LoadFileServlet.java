package servlets;

//taken from: http://www.servletworld.com/servlet-tutorials/servlet3/multipartconfig-file-upload-example.html
// and http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html

import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collection;
import java.util.Scanner;

@WebServlet("/pages/main/loadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("fileupload/form.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        Engine.Engine engine = ServletUtils.getEngine(getServletContext());
        Collection<Part> parts = request.getParts();

        StringBuilder fileContent = new StringBuilder();
        Gson gson = new Gson();

        for (Part part : parts) {
            try {
                engine.loadFile(part.getInputStream(), usernameFromSession);
                String jsonResponse = gson.toJson(new massage("File uploaded successfully"));
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonResponse);
                    out.flush();
                }
            } catch (Exception e) {

                String jsonResponse = gson.toJson(new massage(e.getMessage()));
                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonResponse);
                    out.flush();
                }
            }
        }
    }

    class massage{
        private final String massage;

        massage(String massage) {
            this.massage = massage;
        }
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}