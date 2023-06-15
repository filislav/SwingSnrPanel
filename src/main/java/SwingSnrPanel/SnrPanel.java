/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwingSnrPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author vaceslavfilippov
 */
public class SnrPanel extends JPanel{
    private ArrayList<Double> snrList;
    public final static int BLOCK_WIDTH = 20;
    private Point2D.Double xStartPoint;
    private Point2D.Double yStartPoint;
    
    public SnrPanel(int width,int height,List<Double> snrList){
        this.setSize(width, height);
        this.snrList = (ArrayList)snrList;       
    }
    
    public void paintAxis(Graphics2D g2){
        yStartPoint = new Point2D.Double(0.0,0.0);
        xStartPoint = new Point2D.Double(0,this.getHeight()/2);
        
        Point2D.Double yEndPoint = new Point2D.Double(0.0,this.getHeight());
        Point2D.Double xEndPoint = new Point2D.Double(this.getWidth(),this.getHeight()/2);
        
        g2.drawLine((int)xStartPoint.getX(),(int)xStartPoint.getY(),
                (int)xEndPoint.getX(),(int)xEndPoint.getY());
        g2.drawLine((int)yStartPoint.getX(),(int)yStartPoint.getY(),
                (int)yEndPoint.getX(),(int)yEndPoint.getY());
    }
    
    private int dbToPixels(double snr){
            double max = Arrays.stream(snrList.stream().toArray(Double[]::new))
                    .mapToDouble(e->(Math.abs(e)))
                    .summaryStatistics()
                    .getMax(); 
            
            double pixMax = xStartPoint.getY();
            return (int)(Math.abs(snr)*pixMax/max);
    }
    
    private void drawSnrBlock(int pos,double snrVal,Graphics2D g2){
        if(snrVal>=0){
            g2.fillRect(pos,(int)(xStartPoint.getY() - dbToPixels(snrVal)),BLOCK_WIDTH,dbToPixels(snrVal));
        }else{
            g2.fillRect(pos,(int)(xStartPoint.getY()),BLOCK_WIDTH,dbToPixels(snrVal));
        }
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.green);
        this.setBackground(Color.white);
        paintAxis(g2);
        int pos =0;
        for(int i=0;i<snrList.size();i++){
            drawSnrBlock(pos,snrList.get(i),g2);
            pos+=BLOCK_WIDTH;
        }        
    }
}
