/**
 * Shay Yosipov
 * id: 324124593
 */




import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.*;


public class HangmanLayout extends JFrame {

    private JButton newGameBt;
    private JPanel wordPanel;
    private JButton guessBt;
    private JPanel lettersPanel;
    private JTextField wordDisplay;
    private String textWordDisplay;
    private JTextField lettersBox;
    private String textLettersBox;
    private HangmanPanel hangmanPanel;
    private boolean first=true;
    final int maxWords=255;
    String[] words;
    private String currWord;
    private char[] chars;
    private int[] usedChars;
    private int mistakes=0;
    private int wordNum=0;
    private int numOfWords=0;
    private String wordInGame="";
    private boolean gameWon=false;
    private boolean gameOver=false;

    public HangmanLayout() throws Exception{
        super("Hangman");

        //Gets the words from the file words:
        if(first){
            first=false;

            usedChars = new int[26];
            for(int i=0; i<26; i++)
                usedChars[i]=0;

            chars = new char[26];
            for(int i=0; i<26; i++)
                chars[i]=(char)('a'+i);


            words = new String[maxWords];
            File file = new File("words.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i=0;
            while((words[i]=br.readLine())!=null)
                i++;
            numOfWords=i;
        }




        currWord = words[wordNum];


        hangmanPanel =new HangmanPanel();

        wordInGame=setUpNewWord(words[wordNum]);


        //A panel for guessing a letter
        guessBt = new JButton("Guess");
        lettersPanel = new JPanel();
        lettersPanel.setLayout(new GridLayout(1,2));
        lettersBox = new JTextField();
        lettersPanel.add(lettersBox);
        lettersBox.setEditable(true);
        lettersPanel.add(guessBt);


        //A panel for the word that needs to be guessed and the new game button
        newGameBt = new JButton("new game");
        wordPanel = new JPanel();
        wordPanel.setLayout(new GridLayout(1,2));
        wordDisplay = new JTextField();
        wordPanel.add(wordDisplay);
        wordDisplay.setEditable(false);
        wordPanel.add(newGameBt);

        wordDisplay.setText(wordInGame);

       guessBt.addActionListener(new ButtonListener());
       newGameBt.addActionListener(new ButtonListener());



        add(wordPanel,BorderLayout.NORTH);
        add(hangmanPanel,BorderLayout.CENTER);
        add(lettersPanel,BorderLayout.SOUTH);

    }


    //Setting up the word to be
    public String setUpNewWord(String regularWord)
    {
        String str="";
        for(int i=0; i<regularWord.length(); i++)
        {
            str=str+" _";
        }
        return str;
    }

    //Checking if the written string is a lower case letter
    public int isLetter(String str)
    {
        int index=0;
        while(index<26) {
            if (str.equals("" + chars[index]))
                return index;
            index++;
        }
        return -1;
    }


    //Checking if the letter is a letter that was not used before in the current game
    public boolean isNewLetter(int letterIndex)
    {
        if(usedChars[letterIndex]==0)
        {
            usedChars[letterIndex]=1;
            return true;
        }
        return false;
    }


    //Checking if the letter is in the word in the game
    public int letterInWord(char letter)
    {
        for(int i=0; i<currWord.length(); i++)
        {
            if(currWord.charAt(i)==letter)
                return i;
        }
        return -1;
    }

    //Presenting the char in every place it is in the string
    public void addChars(char letter)
    {
        if(currWord.equals(wordInGame))
        {
            gameWon=true;
            hangmanPanel.won();
            repaint();
        }



        String temp = new String(currWord);
        for(int i=0; i<26; i++)
        {
            if(usedChars[i]==0)
                temp=temp.replace(chars[i],'_'); //using the full word we just replace each letter that wasn't used in the game with '_'
        }
        wordInGame=temp;

        if(currWord.equals(wordInGame))
        {
            gameWon=true;
            hangmanPanel.won();
            repaint();
        }

        wordDisplay.setText(wordInGame);
    }


    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==newGameBt)
            {
                gameOver=false;
                hangmanPanel.notGameOver();
                gameWon=false;
                hangmanPanel.notWon();

                //checks if we got to the last word of the file
                if(numOfWords-1==wordNum)
                    wordNum=0;
                else
                    wordNum++;

                //setting up the new word
                wordInGame=setUpNewWord(words[wordNum]);
                wordDisplay.setText(wordInGame);
                currWord = words[wordNum];


                //setting all the used chars to unused
                for(int i=0; i<26; i++)
                    usedChars[i]=0;


                //removing the body parts
                mistakes=0;
                hangmanPanel.removeBody();
                hangmanPanel.removeHead();
                hangmanPanel.removeRightArm();
                hangmanPanel.removeLeftArm();
                hangmanPanel.removeLeftLeg();
                hangmanPanel.removeRightLeg();
                repaint();
            }
            else if(e.getSource()==guessBt)
            {
                int letterInd=isLetter(lettersBox.getText()+"");
                if(letterInd!=-1 && isNewLetter(letterInd) && !gameWon && !gameOver) //if the game is still on and the string is a new letter
                {
                    if(letterInWord(chars[letterInd])==-1) //if the letter is not in the word then we check the mistake number and add the body part or finish the game
                    {
                        mistakes++;
                        switch(mistakes)
                        {
                            case 1: {
                                hangmanPanel.addHead();
                                repaint();
                                break;
                            }
                            case 2: {
                                hangmanPanel.addBody();
                                repaint();
                                break;
                            }
                            case 3: {
                                hangmanPanel.addRightArm();
                                repaint();
                                break;
                            }
                            case 4: {
                                hangmanPanel.addLeftArm();
                                repaint();
                                break;
                            }
                            case 5:
                            {
                                hangmanPanel.addRightLeg();
                                repaint();
                                break;
                            }
                            case 6: {
                                gameOver=true;
                                hangmanPanel.gameOver();
                                hangmanPanel.addLeftLeg();
                                repaint();
                                break;
                            }
                            default: break;
                        }
                    }
                    else //if the letter is in the word then add it to the word
                    {
                        addChars(chars[letterInd]);
                    }
                }
            }
        }
    }

}
