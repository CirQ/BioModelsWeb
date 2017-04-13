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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by cirq on 2017-04-12.
 */
public class BookQuery extends HttpServlet{
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection con = null;
        Statement stat = null;
        try{
            out.print("<!DOCTYPE html>" +
                      "<html>" +
                      "<head><title>Qurey Servlet</title></head>" +
                      "<body>");

            con = pool.getConnection();

            stat = con.createStatement();
            ResultSet rset = stat.executeQuery("SELECT title, author FROM books;");
            int count = 0;
            while(rset.next()){
                out.print(String.format("<p>%s, %s</p>",
                        rset.getString("title"), rset.getString("author")));
                count += 1;
            }
            out.print("<p>===== " + count + " rows found =====</p></body></html>");
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            out.close();
            try{
                if (stat != null) stat.close();
                if (con != null) con.close();
            } catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
