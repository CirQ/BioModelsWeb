package mypkg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cirq on 2017-04-27.
 */
public class BioModelPainter implements Runnable{
    private String mid;
    private ArrayList<Reaction> rl;
    private ArrayList<Species> sl;

    public BioModelPainter(String mid, ArrayList<Reaction> rl, ArrayList<Species> sl){
        this.mid = mid; this.rl = rl; this.sl = sl;
    }

    @Override
    public void run(){
        System.out.println(mid);
        System.out.println(rl.size());
        System.out.println(sl.size());
    }


    public static void main(String[] asa){
        testcase("BIOMD0000000011.xml");
    }

    private static void testcase(String filename) {
        ArrayList<Reaction> rlist = new ArrayList<>();
        ArrayList<Species> slist = new ArrayList<>();
        try {
            File input = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            doc.getDocumentElement().normalize();

            XPath xpath = XPathFactory.newInstance().newXPath();
            NodeList nlist = (NodeList) xpath.evaluate("//listOfReactions/reaction", doc, XPathConstants.NODESET);
            for (int i = 0; i < nlist.getLength(); i++) {
                Node node = nlist.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String rid = element.getAttribute("id");
                    String name = element.getAttribute("name");

                    NodeList lor = element.getElementsByTagName("listOfReactants");
                    if (lor.getLength() > 0) lor = lor.item(0).getChildNodes();
                    NodeList lop = element.getElementsByTagName("listOfProducts");
                    if (lop.getLength() > 0) lop = lop.item(0).getChildNodes();
                    String reactants = "@";
                    String products = "@";
                    for (int k = 0; k < lor.getLength(); k++) {
                        Node n = lor.item(k);
                        if (n.getNodeType() == Node.ELEMENT_NODE)
                            reactants += (((Element) n).getAttribute("species") + "@");
                    }
                    for (int k = 0; k < lop.getLength(); k++) {
                        Node n = lop.item(k);
                        if (n.getNodeType() == Node.ELEMENT_NODE)
                            products += (((Element) n).getAttribute("species") + "@");
                    }

                    rlist.add(new Reaction(rid, name, reactants, products));
                }
            }

            xpath = XPathFactory.newInstance().newXPath();
            nlist = (NodeList) xpath.evaluate("//listOfSpecies/species", doc, XPathConstants.NODESET);
            for (int i = 0; i < nlist.getLength(); i++) {
                Node node = nlist.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String sid = element.getAttribute("id");
                    String initial = element.getAttribute("initialAmount");
                    if (initial.equals(""))
                        initial = element.getAttribute("initialConcentration");
                    double initial_amount = 0f;
                    if (!initial.equals(""))
                        initial_amount = Double.parseDouble(initial);
                    String name = element.getAttribute("name");
                    String compartment = element.getAttribute("compartment");
                    slist.add(new Species(sid, initial_amount, name, compartment));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            BioModelPainter bmp = new BioModelPainter("wang", rlist, slist);
            new Thread(bmp).start();
        }
    }
}
