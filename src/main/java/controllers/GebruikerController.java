package controllers;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.HibernateUtil;


@WebServlet(name = "GebruikerController", urlPatterns = {"/readall", "/gebruikers"})
public class GebruikerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();

        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        String dispatchUrl = "index.jsp";

        if (action.equals("readall")) {

            List<User> gebruikersLijst = new LinkedList();

            // Zet de session in een variabele
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);

            gebruikersLijst = criteria.list();

            // Zet de lijst met gebruikers en het totaal aantal gebruikers op het request
            request.setAttribute("gebruikersUitSessie", gebruikersLijst);
            request.setAttribute("aantalGebruikers", gebruikersLijst.size());

            dispatchUrl = "gebruikers.jsp";

        }

        if (dispatchUrl != null) {
            RequestDispatcher rd =
                    request.getRequestDispatcher(dispatchUrl);
            rd.forward(request, response);
        }
    }

    /* HTTP GET request */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
