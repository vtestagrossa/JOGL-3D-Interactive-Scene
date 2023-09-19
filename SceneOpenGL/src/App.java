import javax.swing.JFrame;

import gui.DisplayPanel;

/*
 * Vincent Testagrossa
 * CMSC 405 - JOGL Project
 * 
 * Calls the init method to create a JFrame() and attach the GLPanel to it. Uses setSize instead of setPreferredSize
 * to ensure the window is 800x600 pixels, otherwise Swing will squash the GLJPanel.
 * 
 */

public class App{
    public static void main(String[] args) throws Exception {
        init(new DisplayPanel());
    }
    private static void init(DisplayPanel glPanel){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.add(glPanel);
        frame.setVisible(true);
    }
}
