/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Product;
import models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.HibernateUtil;

/**
 *
 * @author Daan
 */
//@WebServlet(name = "ProductController", urlPatterns = {"/products", "/products/new", "/products/update", "/products/create", "/products/doUpdate", "/products/remove"})
public class ProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        String dispatchUrl = "index.jsp";

        if (action.equals("products")) {
            productList(request);
            dispatchUrl = "products/products.jsp";
        } else if (action.equals("new")) {
            request.setAttribute("paginaTitel", "Create");

            dispatchUrl = "product_update.jsp";
        } else if (action.equals("update")) {

            Product product;

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            product = (Product) HibernateUtil.getSessionFactory().getCurrentSession().get(Product.class, Long.parseLong(request.getParameter("id")));

            request.setAttribute("paginaTitel", "Update");
            request.setAttribute("id", product.getProductId());
            request.setAttribute("name", product.getProductName());
            request.setAttribute("price", product.getPrice());
            request.setAttribute("userId", product.getUserId());

            dispatchUrl = "product_update.jsp";

        } else if (action.equals("doUpdate")) {

            long productId = Long.parseLong(request.getParameter("id"));

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            List<User> userIds = session.createCriteria(User.class).list();

            if (existingId(Long.parseLong(request.getParameter("userId")), userIds)) {
                Product product = (Product) session.load(Product.class, productId);
                User user = (User) session.load(User.class, Long.parseLong(request.getParameter("userId")));

                product.setProductName(request.getParameter("name"));
                product.setPrice(Double.parseDouble(request.getParameter("price")));
                product.setUser(user);

                session.update(product);

                tx.commit();

                productList(request);
                dispatchUrl = "products.jsp";
            } else {

                request.setAttribute("foutMelding", "User id bestaat niet");
                request.setAttribute("paginaTitel", "Update");
                request.setAttribute("id", request.getParameter("id"));
                request.setAttribute("name", request.getParameter("name"));
                request.setAttribute("price", request.getParameter("price"));

                dispatchUrl = "product_update.jsp";
            }


        } else if (action.equals("create")) {

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction tx = session.beginTransaction();

            List<User> userIds = session.createCriteria(User.class).list();

            if (existingId(Long.parseLong(request.getParameter("userId")), userIds)) {

                Product product = new Product();
                User user = (User) session.load(User.class, Long.parseLong(request.getParameter("userId")));

                product.setProductName(request.getParameter("name"));
                product.setPrice(Double.parseDouble(request.getParameter("price")));
                product.setUser(user);

                session.save(product);

                tx.commit();

                productList(request);
                dispatchUrl = "products.jsp";
            } else {

                request.setAttribute("foutMelding", "User id bestaat niet");
                request.setAttribute("paginaTitel", "Create");
                request.setAttribute("name", request.getParameter("name"));
                request.setAttribute("price", request.getParameter("price"));

                dispatchUrl = "product_update.jsp";
            }


        } else if (action.equals("remove")) {
            
            long productId = Long.parseLong(request.getParameter("id"));

            Session session = HibernateUtil.getSessionFactory().getCurrentSession();

            Transaction tx = session.beginTransaction();

            Product product = (Product) session.load(Product.class, productId);

            session.delete(product);

            tx.commit();
            
            
            productList(request);
            dispatchUrl = "products.jsp";
        }


        if (dispatchUrl != null) {
            RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
            rd.forward(request, response);
        }
    }

    public boolean existingId(long id, List<User> userIds) {
        for (User ids : userIds) {
            if (ids.getUserId() == id) {
                return true;
            }
        }
        return false;
    }

    public void productList(HttpServletRequest request) {
        List<Product> productenLijst;

        // Zet de session in een variabele
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        Criteria criteria = session.createCriteria(Product.class);

        productenLijst = criteria.list();

        // Zet de lijst met gebruikers en het totaal aantal gebruikers op het request
        request.setAttribute("productenLijst", productenLijst);
        request.setAttribute("aantalProducten", productenLijst.size());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
