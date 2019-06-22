/**
 * Shay Yosipov
 * id: 324124593
 */



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class HangmanPanel extends JPanel {



    private boolean head=false;
    private boolean body=false;
    private boolean rightArm=false;
    private boolean leftArm=false;
    private boolean rightLeg=false;
    private boolean leftLeg=false;
    private boolean won=false;
    private boolean gameOver=false;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = getHeight();
        int w = getWidth();
        g.drawLine(100, 350, 250, 350);
        g.drawLine(175, 350, 175, 175);
        g.drawLine(175, 175, 325, 175);
        g.drawLine(325, 230, 325, 175);

        if(head)
            g.fillOval(315, 230, 20, 20);
        if(body)
            g.drawLine(325, 250, 325, 290);
        if(rightArm)
            g.drawLine(325, 265, 340, 280);
        if(leftArm)
            g.drawLine(325, 265, 310, 280);
        if(rightLeg)
            g.drawLine(325, 290, 345, 310);
        if(leftLeg)
            g.drawLine(325, 290, 305, 310);
        if(won)
            g.drawString("Congratulations, you won the game",100,100);
        if(gameOver)
            g.drawString("Game Over",100,100);

    }



    //Methods to control the body of the hangman:
    public void won() { won=true;}
    public void notWon() { won=false;}

    public void gameOver(){gameOver=true;}
    public void notGameOver() {gameOver=false;}

    public void addHead(){
        head=true;
    }
    public void removeHead(){
        head=false;
    }

    public void addBody(){
        body=true;
    }
    public void removeBody(){
        body=false;
    }

    public void addRightArm(){
        rightArm=true;
    }
    public void removeRightArm(){
        rightArm=false;
    }

    public void addLeftArm(){
        leftArm=true;
    }
    public void removeLeftArm(){
        leftArm=false;
    }

    public void addRightLeg(){
        rightLeg=true;
    }
    public void removeRightLeg(){
        rightLeg=false;
    }

    public void addLeftLeg(){
        leftLeg=true;
    }
    public void removeLeftLeg(){
        leftLeg=false;
    }







}