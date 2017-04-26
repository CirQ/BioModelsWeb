package mypkg;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cirq on 2017-04-12.
 */
public class UploadSpeciesXML extends HttpServlet {
    private DataSource pool = null;

    @Override
    public void init() throws ServletException{
        try{
            InitialContext ctx = new InitialContext();
            this.pool = (DataSource)ctx.lookup("java:comp/env/jdbc/BioDB");
            if(this.pool == null)
                throw new ServletException("Unknow DataSource jdbc/BioDB");
        } catch(NamingException ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        if(!ServletFileUpload.isMultipartContent(request))
            return;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024*1024*1);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024*1024*10);
        upload.setSizeMax(1024*1024*12);
        upload.setHeaderEncoding("UTF-8");

        String filepath = getServletContext().getRealPath("./") + File.separator + "tmpupload";
        File uploadpath = new File(filepath);
        if(!uploadpath.exists())
            uploadpath.mkdir();

        try{
            @SuppressWarnings("unchecked")
            List<FileItem> formitems = upload.parseRequest(request);
            if(formitems != null && formitems.size() > 0){
                for(FileItem item: formitems) {
                    if (!item.isFormField()){
                        String filename = new File(item.getName()).getName();
                        filepath = filepath + File.separator + filename;
                        File storeFile = new File(filepath);
                        item.write(storeFile);
                        this.insertToDB(filepath);
                    }
                }
                request.setAttribute("message", "Upload Successfully");
            }
        } catch(Exception ex){
            request.setAttribute("message", "Upload Error: " + ex);
        } finally {
            this.getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
            new File(filepath).delete();
        }
    }


    private ArrayList<Species> getList(Document doc) throws XPathExpressionException{
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            String path = "//listOfSpecies/species";
            NodeList nlist = (NodeList)xpath.compile(path).evaluate(doc, XPathConstants.NODESET);
            ArrayList<Species> list = new ArrayList<>();
            for(int i = 0; i < nlist.getLength(); i++){
                Node node = nlist.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    String sid = element.getAttribute("id");
                    double initial_amount = Double.parseDouble(element.getAttribute("initialAmount"));
                    String name = element.getAttribute("name");
                    String metaid = element.getAttribute("metaid");
                    String compartment = element.getAttribute("compartment");
                    list.add(new Species(sid, initial_amount, name, metaid, compartment));
                }
            }
            return list;
        } catch(XPathExpressionException xpex){
            throw new XPathExpressionException("error file format as biomodel xml");
        }
    }

    private void InsertSpeciesToDB(ArrayList<Species> list, String mid){
        Connection con = null;
        PreparedStatement stat = null;
        try {
            con = this.pool.getConnection();
            stat = con.prepareStatement("INSERT INTO species VALUES(?, ?, ?, ?, ?, ?)");
            for (Species species : list) {
                stat.setString(1, species.getSid());
                stat.setDouble(2, species.getInitial_amount());
                stat.setString(3, species.getName());
                stat.setString(4, species.getMetaid());
                stat.setString(5, species.getCompartment());
                stat.setString(6, mid);
                stat.executeUpdate();
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally {
            try { if (stat != null) stat.close(); if (con != null) con.close(); } catch (SQLException e){}
        }
    }
    private String InsertFileToDB(String abspath){
        Connection con = null;
        PreparedStatement stat = null;
        String mid = "";
        try {
            File f = new File(abspath);
            mid = f.getName().split("\\.")[0];
            FileInputStream fis = new FileInputStream(f);
            con = this.pool.getConnection();
            stat = con.prepareStatement("INSERT INTO models VALUES(?, ?);");
            stat.setString(1, mid);
            stat.setBinaryStream(2, fis, (int)f.length());
            stat.executeUpdate();
        } catch(FileNotFoundException | SQLException ex){
            ex.printStackTrace();
        } finally {
            try{
                if(stat != null) stat.close();
                if(con != null) con.close();
            } catch(SQLException e){
            } finally {
                return mid;
            }
        }
    }

    public void insertToDB(String filename) throws SAXException, XPathExpressionException{
        try{
            File input = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            doc.getDocumentElement().normalize();

            ArrayList<Species> list = this.getList(doc);
            String mid = this.InsertFileToDB(filename);
            this.InsertSpeciesToDB(list, mid);
        } catch(ParserConfigurationException | IOException piex){
            piex.printStackTrace();
        }
    }
}
