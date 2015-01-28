/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

/**
 *
 * @author yousef
 */
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import javax.swing.border.Border;


public class HanoiPuzzle {

    public static void main(String[] args)
    {
        HanoiPuzzleFrame frame = new HanoiPuzzleFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}


class HanoiPuzzleFrame extends JFrame
{
    JComboBox from ;
    JComboBox destination;
    JButton move;
    HanoiPuzzlePanel panel ;
    Stick[] list ;
    int MoveCount=0;
    JLabel label ;

   public HanoiPuzzleFrame()
    {

        setSize(600,600);
        setTitle("TowerOfHanoi by \"MohamedDiaa\" ");
        list=new Stick[3];

         panel =new HanoiPuzzlePanel();
        add(panel,BorderLayout.CENTER);

        JPanel movePanel =new JPanel();

        from =new JComboBox();
        from.addItem(1);
        from.addItem(2);
        from.addItem(3);
        movePanel.add(new JLabel("form"));
        movePanel.add(from);
        
        destination=new JComboBox();
        destination.addItem(1);
        destination.addItem(2);
        destination.addItem(3);
        movePanel.add(new JLabel("to"));
        movePanel.add(destination);
      
        move=new JButton("move");

        movePanel.add(move);

        add(movePanel,BorderLayout.NORTH);

        move.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) {

                MoveCount++;
                int chosen =(Integer)from.getSelectedItem()-1;

                 int disk = list[chosen].takeTopDisk();

                     if(disk==0)
                     {
                        Toolkit.getDefaultToolkit().beep();
                        return;
                     }

                 int to =(Integer)destination.getSelectedItem()-1;

                 boolean done =list[to].addDisk(disk);

                         if(done==false)
                         {
                             Toolkit.getDefaultToolkit().beep();
                             list[chosen].addDisk(disk);
                         }
                 
                 label.setText("MoveCount =   "+MoveCount);
                 panel.repaint();

                 if(list[2].isFilled())JOptionPane.showConfirmDialog(panel, "you are won with Move Count = "+MoveCount);
            }


        });

         label=new JLabel("MoveCount =   "+MoveCount);
        add(label,BorderLayout.SOUTH);

    }






class HanoiPuzzlePanel extends JPanel
{


    public HanoiPuzzlePanel()
    {
        setLayout(new GridLayout(1,3,5,5));

      list[0] = new Stick("Stick_One");
        list[0].addDisk(6);
        list[0].addDisk(5);
        list[0].addDisk(4);
        list[0].addDisk(3);
        list[0].addDisk(2);
        list[0].addDisk(1);
        add(list[0]);

     list[1] = new Stick("Stick_two");
        add(list[1]);

      list[2] = new Stick("Stick_three");
        add(list[2]);

    }

}

}






class Stick extends JPanel
{
  LinkedList<Integer> Disks;

  public static int DISK_Height = 10 ;
  public static int DISK_WIDTH= 5 ;
  String Title;
 //Disks are ordered in the linked list from bottom to top so they are painted in this order

  public Stick(String name)
  {
    super();
    Disks=new LinkedList<Integer>();
    Title=name;
    setBackground(Color.WHITE);
    Border border=BorderFactory.createEtchedBorder();
  
    setBorder(border);
    
  }
  public boolean addDisk(int disk)
 {
    if(peekTopDisk()<disk )return false;

    Disks.addFirst(disk);
    return true;
  }

  public Integer takeTopDisk()
  {
      if(Disks.size()==0)return 0;
    return Disks.removeFirst();

  }

public Integer peekTopDisk()
{

    if(Disks.size()==0)return 100;
    return Disks.getFirst();
}

public boolean isFilled()
{
    if(Disks.size()==6)return true;
    else return false;
}

@Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    Graphics2D g2=(Graphics2D)g;
    g2.drawString(Title,getWidth()/3 ,getHeight()/5);
    g2.draw(new Line2D.Float(getWidth()/2,0 ,getWidth()/2 ,getHeight()));

    if(Disks.size()==0)return;
        int topY=this.getHeight()-DISK_WIDTH-10;
        int topX=this.getWidth()/2;

        for(int i=Disks.size()-1 ;i>=0 ;i--)
         {
            int w=Disks.get(i);
            g2.fill(new Rectangle2D.Float(topX,topY,w*DISK_Height,DISK_WIDTH));
            g2.fill(new Rectangle2D.Float(topX-w*DISK_Height,topY,w*DISK_Height,DISK_WIDTH));
            topY=topY-DISK_WIDTH-10;
            }

       

  }
}