package powered_tasks;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class MouseHandler extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	@Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
        //int x = e.getX();
        //int y = e.getY();
        //System.out.println("Mouse Entered frame at X: " + x + " - Y: " + y);
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
        //int x = e.getX();
        //int y = e.getY();
        //System.out.println("Mouse Exited frame at X: " + x + " - Y: " + y);
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        //int x = e.getX();
        //int y = e.getY();
        //System.out.println("Mouse Pressed at X: " + x + " - Y: " + y);
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
        //int x = e.getX();
        //int y = e.getY();
        //System.out.println("Mouse Released at X: " + x + " - Y: " + y);
    }

}