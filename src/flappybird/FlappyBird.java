/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 *
 * @author Sławek Olszewski
 */
public class FlappyBird extends JFrame {

    /**
     * @param args the command line arguments
     */
    
    private final Image background;
    private final Image bird;
    private int positionY = 225;
    private final Timer timer;
    private boolean     controls[];
    private int gravity = 1;
    private int velocity = 0;
    private boolean released = true;
    
    class Controls extends TimerTask{
        
        public void run(){
            if(controls[0]){
                positionY -=45;
                velocity = 0;
            }
            
            velocity +=gravity;
            positionY += velocity;
            
            positionY = (positionY < 0)?0:positionY;
            repaint();
        }
    }
    
    public FlappyBird(){
        super("FlappyBird");
        setBounds(50,50,1024,573);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        
        background = new ImageIcon("images/background.png").getImage();
        bird = new ImageIcon("images/bird.png").getImage();
     
        controls = new boolean[2];
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new Controls(),0,50);
        
        this.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e){
                    switch(e.getKeyCode()){
                        case KeyEvent.VK_SPACE: 
                            if(released){
                                controls[0] = true;
                                released = false;
                            }
                            break;
                    }
            }
            public void keyReleased(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_SPACE:
                        controls[0] = false;
                        released = true;
                        break;
                }
            }

            public void keyTyped(KeyEvent e){
                
            }
        }
                            );
    }
        
    public static void main(String[] args) {
        // TODO code application logic here
        FlappyBird game = new FlappyBird();
        game.repaint();
    }
    
    @Override
    public void update(Graphics g){
        paint(g);
    }
    
    public void paint(Graphics g)
    {
        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2d = (Graphics2D)bstrategy.getDrawGraphics();
        
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(bird, 200, positionY, null);
        
        g2d.dispose();
        bstrategy.show();
    }
}
