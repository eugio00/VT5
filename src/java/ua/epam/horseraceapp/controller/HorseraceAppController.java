/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.epam.horseraceapp.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.controller.command.AbstractCommand;
import ua.epam.horseraceapp.controller.command.CommandFactory;

/**
 *
 * @author Koroid Daniil
 */
public class HorseraceAppController extends HttpServlet {

    /**
     * Encoding.
     */
    private final static String ENCODING = "UTF-8";
    
    /**
     * Command parameter
     */
    private static final String COMMAND_PARAMETER = "command";

    /**
     * Log4j init file parameter.
     */
    private final String LOG4J_INIT_FILE_PARAMETER = "log4j-init-file";
    
    /**
     * Command factory.
     */
    private final CommandFactory factory;
    
    /**
     * Creates horserace application controller.
     * <p>
     * Also creates instance of command factory.
     * </p>
     */
    public HorseraceAppController() {
        factory = CommandFactory.getInstance();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding(ENCODING);
        response.setContentType("text/html;charset=UTF-8");
        String commandString = request.getParameter(COMMAND_PARAMETER);
        
        AbstractCommand command = factory.getCommand(commandString);
        String forward = command.execute(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
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

    @Override
    public void init() throws ServletException {
        String realPath = getServletContext().getRealPath(getInitParameter(LOG4J_INIT_FILE_PARAMETER));
        File log4jInitFile = new File(realPath);
        if(log4jInitFile.exists()) {
            org.apache.log4j.PropertyConfigurator.configure(realPath);
        } else {
            org.apache.log4j.BasicConfigurator.configure();
        }
    }

    
}
