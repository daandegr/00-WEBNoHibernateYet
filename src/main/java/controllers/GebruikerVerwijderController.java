package controllers;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import models.User;

public class GebruikerVerwijderController extends HttpServlet {
    /* HTTP GET request */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Als er een id wordt meegegeven, dan verwijderen we deze gebruiker
        // uit de lijst met gebruikers
        if (request.getParameter("id") != null) {
            // Haal het 'id' parameter op uit het request
            long id = Long.parseLong(request.getParameter("id"));
            // Haal een sessie object op uit het request
            HttpSession sessie = request.getSession();
            
            // Haal de lijst met gebruikers op uit de sessie
            List<User> gebruikers = (List) sessie.getAttribute("gebruikers");
            
            // Controleer of de lijst met gebruikers niet null is, zo ja, 
            // creër een lege lijst en zet deze op de sessie
            if (gebruikers == null) {
                gebruikers = new LinkedList<User>();
                sessie.setAttribute("gebruikers", gebruikers);
            }
            
            // Ga na welke gebruiker dezelfde userId heeft als
            // de 'id' parameter
            for (int i = 0; i < gebruikers.size(); i++) {
                User tempGebruiker = (User) gebruikers.get(i);
                
                // Indien het UserId gelijk is aan de 'id' parameter,
                // verwijder deze gebruiker dan van de lijst
                if (tempGebruiker.getUserId() == id) {
                    gebruikers.remove(i);
                }
            }
            
            // Stuur een redirect terug naar de client. De client zal dan
            // meteen een nieuwe GET request doen naar ../gebruikers
            response.sendRedirect("../gebruikers");
        }
    }
}
