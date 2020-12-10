/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.epam.horseraceapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ua.epam.horseraceapp.util.dao.entity.User;
import ua.epam.horseraceapp.util.dao.entity.UserType;

/**
 *
 * @author Koroid Daniil
 */
@WebFilter(filterName = "AdminLoginFilter", urlPatterns = {"/app/admin/*"})
public class AdminLoginFilter extends AbstractLoginFilter implements Filter {

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(USER);
            if (user != null) {
                if (user.getType() == UserType.ADMIN) {
                    chain.doFilter(request, response);
                } else {
                    httpResp.sendRedirect(httpReq.getContextPath() + USER_DEFAULT_PAGE);
                }
            } else {
                httpResp.sendRedirect(httpReq.getContextPath() + DEFAULT_PAGE);
            }
        } else {
            httpResp.sendRedirect(httpReq.getContextPath() + DEFAULT_PAGE);
        }
    }

}
