package mypkg;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by cirq on 2017-04-27.
 */
public class ViewBioModel extends HttpServlet{
    DataSource pool;

    @Override
    public void init() throws ServletException{
        try {
            InitialContext ctx = new InitialContext();
            pool = (DataSource)ctx.lookup("java:comp/env/jdbc/BioDB");
            if (pool == null)
                throw new ServletException("Unknown DataSource 'jdbc/BioDB'");
        } catch (NamingException ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String[] item = request.getRequestURI().split("/");
        String uri = item[item.length-1];
        request.setAttribute("model", uri);
        this.getServletContext().getRequestDispatcher("/modelview.jsp").forward(request, response);
    }
}
