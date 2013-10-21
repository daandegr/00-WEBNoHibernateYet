package controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class IndexController extends HttpServlet {

	/* HTTP GET Request */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            // Stuur het resultaat van index.jsp terug naar de client
            String address = "/index.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
    }
}