package controllers;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.HibernateUtil;

public class GebruikerWijzigController extends HttpServlet {

    private static String titelNieuw = "Nieuwe gebruiker"; //Titel voor de Nieuwe gebruiker pagina
    private static String titelWijzig = "Wijzigen gebruiker"; //Titel voor de Wijzig gebruiker pagina

    /* HTTP GET request */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (request.getParameter("id") != null) {
            //Als er een id is meegegeven, worden de gegevens van de gebruiker opgehaald.
            long id = Long.parseLong(request.getParameter("id"));
            request.setAttribute("id", id); // TODO: why?

            // Haal een sessie object op uit het request
            HttpSession sessie = request.getSession();
            LinkedList gebruikers = (LinkedList) sessie.getAttribute("gebruikers"); //Haalt de lijst met gebruikers op en slaat deze op in een LinkedList

            for (int i = 0; i < gebruikers.size(); i++) {
                User tempGebruiker = (User) gebruikers.get(i);

                //Als de gebruiker overeenkomt met het gegeven id, worden de gegevens ingevuld in het formulier.
                if (tempGebruiker.getUserId() == id) {
                    request.setAttribute("firstName", tempGebruiker.getFirstName());
                    request.setAttribute("lastName", tempGebruiker.getLastName());
                    request.setAttribute("email", tempGebruiker.getEmail());
                }
            }
            doorsturen(request, response, titelWijzig); //Stuurt door naar de Wijzig gebruiker pagina.
        } else {
            doorsturen(request, response, titelNieuw); //Stuurt door naar de Nieuwe gebruiker pagina.
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Haal een sessie object op uit het request
        HttpSession sessie = request.getSession();

        // Als the parameter 'id' niet null is, dan hebben we te maken met
        // een user die wordt geupdate
        boolean isUserUpdate = request.getParameter("id") != null;

        // Haal de lijst met gebruikers op uit de sessie
        List<User> gebruikers = (List) sessie.getAttribute("gebruikers");

        // Controleer of de lijst met gebruikers niet null is, zo ja, 
        // creër een lege lijst en zet deze op de sessie
        if (gebruikers == null) {
            gebruikers = new LinkedList<User>();
        }

        // Zet de form parameters om in een User object
        User formUser = getUserFromRequest(request);

        // Indien we hier met een gebruikers update te maken hebben, dan halen
        // we deze gebruiker op uit de lijst met gebruikers en wijzigen we zijn
        // gegevens
        if (isUserUpdate) {
//            for (int i = 0; i < gebruikers.size(); i++) {
//                User tempGebruiker = (User) gebruikers.get(i);
//
//                // Als het UserId van de tempGebruiker overeenkomt met 
//                // het CutomerId van de formUser, dan wordt de gebruiker geupdate.
//                if (tempGebruiker.getUserId() == formUser.getUserId()) {
//                    tempGebruiker.setFirstName(formUser.getFirstName());
//                    tempGebruiker.setLastName(formUser.getLastName());
//                    tempGebruiker.setEmail(formUser.getEmail());
//                }
//            }
            
            long userId = Long.parseLong(request.getParameter("id"));
           
           Session session = HibernateUtil.getSessionFactory().getCurrentSession();
           Transaction tx = session.beginTransaction();
           User managedUser = (User)session.load(User.class, userId);
                     
           managedUser.setFirstName(request.getParameter("firstName"));
           managedUser.setLastName(request.getParameter("lastName"));
           managedUser.setEmail(request.getParameter("email"));
           
           session.update(managedUser);
           
           tx.commit();

        } else {
//            // Anders zetten we een uniek id op het User object en voegen we 
//            // deze als nieuwe gebruiker toe aan de lijst met gebruikers
//            long uniekId = System.nanoTime();
//            formUser.setUserId(uniekId);
//            gebruikers.add(formUser);


            User user = new User();

            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setEmail(request.getParameter("email"));


            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            Transaction tx = session.beginTransaction();

            session.save(user);

            tx.commit();

        }

        sessie.setAttribute("gebruikers", gebruikers);

        // Stuur een redirect terug naar de client. De client zal dan
        // meteen een nieuwe GET request doen naar ../gebruikers
        //response.sendRedirect("../gebruikers.jsp");
        goToGebruikers(request, response);
    }

    private void doorsturen(HttpServletRequest request, HttpServletResponse response, String titel)
            throws ServletException, IOException {
        // Set de pagina titel op het request
        request.setAttribute("paginaTitel", titel);

        // Stuur het resultaat van gebruiker_wijzigen.jsp terug naar de client
        String address = "/gebruiker_wijzigen.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void goToGebruikers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> gebruikersLijst;

        // Zet de session in een variabele
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(User.class);

        gebruikersLijst = criteria.list();

        // Zet de lijst met gebruikers en het totaal aantal gebruikers op het request
        request.setAttribute("gebruikersLijst", gebruikersLijst);
        request.setAttribute("aantalGebruikers", gebruikersLijst.size());

        RequestDispatcher rd = request.getRequestDispatcher("../gebruikers.jsp");
        rd.forward(request, response);
    }

    /**
     * Maakt een User object aan de hand van de parameters uit het http request.
     */
    private User getUserFromRequest(HttpServletRequest request) {
        User user = new User();

        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            user.setUserId(Long.parseLong(request.getParameter("id")));
        }
        if (request.getParameter("firstName") != null) {
            user.setFirstName(request.getParameter("firstName"));
        }
        if (request.getParameter("lastName") != null) {
            user.setLastName(request.getParameter("lastName"));
        }
        if (request.getParameter("email") != null) {
            user.setEmail(request.getParameter("email"));
        }

        return user;
    }
}
