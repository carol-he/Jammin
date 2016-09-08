import java.awt.*;
import java.awt.Image;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.applet.*;
public class jammin extends JApplet implements Runnable, KeyListener {
    Thread t;
    boolean play = false;
    int score;
    int timeStep = 10;
    ArrayList<Arrow> arrows = new ArrayList<Arrow>();
    int v;
    int type = 4;
    Image background;
    Image leftarrow;
    Image uparrow;
    Image downarrow;
    Image rightarrow;
    String phrase = "hello there";
    Graphics bg;
    Image img;
    boolean inPlay;
    String s = "";
    AudioClip clubCantHandleMe;

    public void init() {
        addKeyListener(this);
        setFocusable(true);
        score = 0;
        resize(1000, 500);
        img = createImage(1000, 500);
        bg = img.getGraphics();
        t = new Thread(this);
        t.start();
        background = getImage(getDocumentBase(), "background.gif");
        clubCantHandleMe = getAudioClip(getDocumentBase(), "ClubCantHandleMe.wav");
        leftarrow = getImage(getDocumentBase(), "leftarrow.png");
        uparrow = getImage(getDocumentBase(), "up-arrow.png");
        rightarrow = getImage(getDocumentBase(), "rightarrow.png");
        downarrow = getImage(getDocumentBase(), "downarrow.png");
        clubCantHandleMe.loop();
        inPlay = true;
    }

    public void paint(Graphics g){

        bg.clearRect(0, 0, 1000, 1000);

        bg.drawImage(background, 0, 0, 1000, 500, this);
        bg.setColor(Color.black);
        bg.drawString("SCORE: " + score, 20, 480);
        bg.drawString(phrase, 20, 440);
        if(score < 0){
            clubCantHandleMe.stop();
            inPlay = false;
            bg.setColor(Color.black);
            bg.drawString("YOU LOSE! Press '&' to restart the game", 400, 250);

        }
        if(arrows.size() != 0){
            for(int i = 0; i < arrows.size(); i++){
                //bg.drawOval(arrows.get(i).getX(), arrows.get(i).getY(), 50, 50);
                if(arrows.get(i).getY() == 0){
                    bg.drawImage(uparrow, arrows.get(i).getX(), arrows.get(i).getY(), 50, 50, this);
                }
                if(arrows.get(i).getY() == 100){
                    bg.drawImage(leftarrow, arrows.get(i).getX(), arrows.get(i).getY(), 50, 50, this);
                }
                if(arrows.get(i).getY() == 200){
                    bg.drawImage(rightarrow, arrows.get(i).getX(), arrows.get(i).getY(), 50, 50, this);
                }
                if(arrows.get(i).getY() == 300){
                    bg.drawImage(downarrow, arrows.get(i).getX(), arrows.get(i).getY(), 50, 50, this);
                }
            }
        }
        for(int k = 0; k < 4; k++){
            bg.drawOval(775, k * 100, 50, 50);
        }
        //50 pointer
        bg.drawLine(770, 0, 770, 500);
        bg.drawLine(770 + 60, 0, 770 + 60, 500);
        g.drawImage(img, 0, 0, this);
    }

    public void update(Graphics g) 
    { 
        paint(g); 
    } 

    public void run() {
        try {

            while(true) {
                if(inPlay == true){
                    if(Math.random() < .02){
                        arrows.add(new Arrow(0, (int)(Math.random()*4)* 100));
                    }
                    for(int i = 0; i < arrows.size(); i++){
                        arrows.get(i).move();
                    }
                }
                for(int j = 0; j < arrows.size(); j++){
                    if(arrows.get(j).getX() > 1000){
                        arrows.remove(j);
                        phrase = "missed!";
                        score = score - 5;
                        j--;
                        break;
                    }
                }
                repaint();
                t.sleep(timeStep);
            }
        }

        catch (InterruptedException e) {}

    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        s = s + e.getKeyChar();
        int k = e.getKeyCode();
        switch(k)
        {
            case KeyEvent.VK_UP:
            //up
            break;
            case KeyEvent.VK_DOWN:
            //down
            break;
            case KeyEvent.VK_LEFT:
            //left
            break;
            case KeyEvent.VK_RIGHT :
            //right
            break;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            type = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            type = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            type = 2;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            type = 3;
        }

        if(arrows.size() != 0){
            for(int i = 0; i < arrows.size(); i++){
                if(arrows.get(i).getX() >= 700 && arrows.get(i).getX() <= 850 && type == arrows.get(i).getY()/100){
                    arrows.remove(i);
                    score = score + 10;
                    phrase = "OK";
                    if(arrows.get(i).getX() > 725 && arrows.get(i).getX() < 825){
                        score = score + 5;
                        phrase = "nice!";
                    }
                    if(arrows.get(i).getX() > 750 && arrows.get(i).getX() < 800){
                        score = score + 10;
                        phrase = "great!!";
                    } 
                    if(arrows.get(i).getX() > 770 && arrows.get(i).getX() < 780){
                        score = score + 25;
                        phrase = "Perf";
                    }
                    i--;
                    break;
                }
                if(arrows.get(i).getX() < 700){
                    phrase = "pls stop";
                    score = score - 5;
                    break;
                }

            }
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == '&'){
            reset();
        }

    }

    public void reset(){
        clubCantHandleMe.stop();
        while(arrows.size() != 0){
            for(int i = 0; i < arrows.size(); i++){   
                arrows.remove(i);
                i--;          
            }
        }
        clubCantHandleMe.loop();
        score = 0;
        inPlay = true;
        phrase = "hello again";
    }

    public void mouseClicked(MouseEvent arg0) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

}

class Arrow {
    int x,y,v;
    public Arrow(int i, int j){
        v = 2;
        x = i;
        y = j;
    }

    public void move(){
        x = x + v;
    }

    public int getX()  
    {  
        return x;  
    }  

    public int getY()
    { 
        return y;
    }

}