package mypkg;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cirq on 2017-04-27.
 */
public class BioModelPainter implements Runnable{
    private String mid;
    private ArrayList<Reaction> rl;
    private ArrayList<Species> sl;
    HashMap<String, Point> vlist = new HashMap<>();
    ArrayList<String[]> elist = new ArrayList<>();
    private String path = "";

    public BioModelPainter(String mid, ArrayList<Reaction> rl, ArrayList<Species> sl, String path){
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mid = mid; this.rl = rl; this.sl = sl;
        this.path = path;
    }

    private class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private void DrawVertex(Graphics g, String v){
        FontMetrics f = g.getFontMetrics();
        int nodeheight = Math.max(30, f.getHeight());
        int nodewidth = Math.max(30, f.stringWidth(v)+15);
        Point p = vlist.get(v);
        g.setColor(Color.white);
        g.fillOval(p.x-nodewidth/2, p.y-nodeheight/2, nodewidth, nodeheight);
        g.setColor(Color.black);
        g.drawOval(p.x-nodewidth/2, p.y-nodeheight/2, nodewidth, nodeheight);
        v = v.substring(1, v.length());
        g.drawString(v, p.x-f.stringWidth(v)/2, p.y+f.getHeight()/2);
    }

    private void DrawEdge(Graphics g, String vr, String vp){
        Point pvr = vlist.get(">" + vr);
        Point pvp = vlist.get("<" + vp);
        if(pvr==null || pvp==null)
            return;
        g.drawLine(pvr.x, pvr.y, pvp.x, pvp.y);
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        for(String[] vs: elist)
            DrawEdge(g, vs[0], vs[1]);
        for(String v: vlist.keySet())
            DrawVertex(g, v);
    }

    @Override
    public void run(){
        for(int i = 0; i < sl.size(); i++){
            // > for reactants
            vlist.put(">"+sl.get(i).getSid(), new Point(80, (i+1)*50));
            // < for products
            vlist.put("<"+sl.get(i).getSid(), new Point(420, (i+1)*50));
        }
        for(Reaction r: rl){
            String[] reactants = r.getReactants().split(" ");
            String[] products = r.getProducts().split(" ");
            for(String re: reactants)
                for(String pr: products)
                    elist.add(new String[]{re, pr});
        }

        BufferedImage bi = new BufferedImage(500, 55+sl.size()*50, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        this.paint(g2d);
        try {
            ImageIO.write(bi, "PNG", new File(path+"/"+mid+".png"));
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
