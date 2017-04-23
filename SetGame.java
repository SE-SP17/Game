import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;




public class SetGame extends JPanel
implements ActionListener {
// Sets up each card with cardId and Image
	int[] idValues =  new int [0];
	String[] shape = {"a", "b", "c"};
	String[] color = {"blue", "green", "pink"};
	String[] fill = {"", "fill", "lines"};
	String[] amt = {"1", "2", "3"};
	Card[] deckList = new Card[81]; 
	JToggleButton [] cardButtons = new JToggleButton[21];
	Card[] select = new Card[3];
	JToggleButton[] selected = new JToggleButton[3];
	JToggleButton[] foundSet = new JToggleButton[3];
	int counter = 0;
	int sizeBoard = 12;
	JButton message;
	JButton b1; //Set checker
	//Card dCard;
	public SetGame()
	{
		getDeck();
		//unlucky(deckList);
		shuffleDeck(deckList);
		setLayout(null);
		addCards();
		buttonsAndTimers();

        
	}
	
	public void addCards()
	{
		//Insets used to position cards
        Insets insets = getInsets();
        

		for (int i = 0; i<12; i++)
		{
        cardButtons[i] = new JToggleButton(deckList[i].getCard());
        deckList[i].setBtn(cardButtons[i]);
        cardButtons[i].addActionListener(this);
        add(cardButtons[i]);
		}

		
        
        Dimension sizeT = cardButtons[0].getPreferredSize();

        for (int i = 0; i < 4; i++)
        {
        	for(int j = 0; j < 3; j++)
        	{
        		cardButtons[counter].setBounds(150+insets.left+i*(10+sizeT.width),50+insets.top+j*(10+sizeT.height), sizeT.width, sizeT.height);
        		counter ++;
        	}
        }
        
		b1 = new JButton("No Set?");
	    Dimension sizeB = b1.getPreferredSize();
	    b1.addActionListener(this);
	    b1.setBounds(25+insets.left,50+insets.top,sizeB.width,sizeB.height);
	    b1.setActionCommand("check");
	    add(b1);
	    

	    message = new JButton("                    ");
	    Dimension sizeL = message.getPreferredSize();
	    message.addActionListener(this);
	    message.setBounds(25+insets.left,100+insets.top,sizeL.width,sizeL.height);
	    message.setActionCommand("ok");
	    add(message);
	    
     
	}

	public void buttonsAndTimers()
	{
		//b1 = new JButton("Disable middle button");
	}
        
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SetGame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    //generates the deck in the order reflecting folder order
    protected void getDeck()
    {
    	String p = "";
    	ImageIcon testIcon;
    	Card dummy;
    	int h = 0;
    	//shape
    	for(int s = 0; s < 3; s++)
    	{
    		//color
    		for(int c = 0; c < 3; c++)
        	{
    			//fill
    			for(int f = 0; f < 3; f++)
    	    	{
    				//number
    				for(int n = 0; n < 3; n++)
    		    	{

    					p = "cards/"+shape[s]+color[c]+fill[f]+amt[n]+".png";
    					testIcon= createImageIcon(p);
    					dummy = new Card(s,c,f,n, testIcon);
    					deckList[h]= dummy;
    					h++;
    			    
    		    	}
    	    	}
        	}
    	}
    	
    }
    //shuffles deck
    static void shuffleDeck(Card[] deck)
    {
      
      Random rnd = ThreadLocalRandom.current();
      for (int i = deck.length - 1; i > 0; i--)
      {
        int index = rnd.nextInt(i + 1);
        // Simple swap
        Card a = deck[index];
        deck[index] = deck[i];
        deck[i] = a;
      }
    }
	//Checks each element of the iD array and verifies if the three cards are a set.
    protected boolean isASet(Card a, Card b, Card c)
    {
    	boolean isIt = false;
    	if ((a.getShape() == b.getShape() && b.getShape() == c.getShape()) || (a.getShape()!= b.getShape()&& b.getShape()!= c.getShape() && a.getShape()!= c.getShape()))
    	{
    		if((a.getColor() == b.getColor() && b.getColor() == c.getColor()) || (a.getColor()!= b.getColor()&& b.getColor()!= c.getColor() && a.getColor()!= c.getColor()))
    		{
    			if((a.getFill() == b.getFill() && b.getFill() == c.getFill()) || (a.getFill()!= b.getFill()&& b.getFill()!= c.getFill() && a.getFill()!= c.getFill()))
        		{
    				if((a.getNumber() == b.getNumber() && b.getNumber() == c.getNumber()) || (a.getNumber()!= b.getNumber()&& b.getNumber()!= c.getNumber() && a.getNumber()!= c.getNumber()))
    	    		{
    	    			isIt = true;
    	    		}
        		}
    			
    		}
    	}
    	return isIt;

    }
    //find card return null if card not in list.
    protected Card findCard(JToggleButton btn)
    {
    	for (int i = 0; i<81; i++)
    	{
    		if (deckList[i].getBtn() == btn)
    		{

    			return deckList[i];
    			
    		}
    	}
    	return null;
    }
    //findSet return null if there is no set.
    protected JToggleButton[] findSet()
    {
    	JToggleButton[] corSet = new JToggleButton[3];
    	for (int i = 0; i<sizeBoard; i++)
    	{
    		corSet[0]=cardButtons[i];
    		for (int j = i+1; j<sizeBoard; j++)
    		{
    			corSet[1]=cardButtons[j];
    			for(int k = j+1; k<sizeBoard ; k++)
    			{
			
					corSet[2]=cardButtons[k];
    				if(isASet(findCard(corSet[0]),findCard(corSet[1]),findCard(corSet[2])))
    				{
    					System.out.println(i+" "+j+" "+k);
    					return corSet;
    				}
    			}
    		}
    	}
    	System.out.println("hello");
    	return null;
    }
    
    static void unlucky(Card[]deck)
    {
    	Card dummy;
    	dummy = deck[66];
    	deck[66] = deck[0];
    	deck[0] = dummy;
    	dummy = deck[67];
    	deck[67] = deck[1];
    	deck[1] = dummy;
    	dummy = deck[60];
    	deck[60] = deck[2];
    	deck[2] = dummy;
    	dummy = deck[61];
    	deck[61] = deck[3];
    	deck[3] = dummy;
    	dummy = deck[57];
    	deck[57] = deck[4];
    	deck[4] = dummy;
    	dummy = deck[58];
    	deck[58] = deck[5];
    	deck[5] = dummy;
    	dummy = deck[31];
    	deck[31] = deck[6];
    	deck[6] = dummy;
    	dummy = deck[69];
    	deck[69] = deck[7];
    	deck[7] = dummy;
    	dummy = deck[70];
    	deck[70] = deck[17];
    	deck[17] = dummy;
    	dummy = deck[41];
    	deck[41] = deck[9];
    	deck[9] = dummy;
    	dummy = deck[50];
    	deck[50] = deck[10];
    	deck[10] = dummy;
    	dummy = deck[35];
    	deck[35] = deck[18];
    	deck[18] = dummy;
    	dummy = deck[29];
    	deck[29] = deck[12];
    	deck[12] = dummy;
    	dummy = deck[30];
    	deck[30] = deck[13];
    	deck[13] = dummy;
    	dummy = deck[26];
    	deck[26] = deck[19];
    	deck[19] = dummy;

    }
    
    protected void deselect()
    {
    	selected[0].setSelected(false);
		selected[1].setSelected(false);
		selected[2].setSelected(false);
		selected[0]=null;
		selected[1]=null;
		selected[2]=null;
    }
    
    protected void swapButtons(JToggleButton but1, JToggleButton but2)
    {
    	if(but1 != but2)
    	{
    	JToggleButton dummy;
    	ImageIcon fake1, fake2;
    	fake1 = findCard(but1).getCard();
    	fake2 = findCard(but2).getCard();
    	findCard(but1).setCard(fake2);
    	findCard(but2).setCard(fake1);
		but1.setIcon(fake2);
		but2.setIcon(fake1);
    	dummy = but1;
    	but1=but2;
    	but2=dummy;
    	}
    }
    
    
    private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("Set The Game");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //frame.setLayout(null);
	        

	        //Create and set up the content pane.
	        SetGame newContentPane = new SetGame();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);
	        //addCards(frame.getContentPane());
	        
	        Container c = frame.getContentPane();
            c.setBackground(Color.CYAN);
            // adjust to need.
            Dimension d = new Dimension(1900,1000);
            c.setPreferredSize(d);

            		

	        //Display the window.
	        frame.pack();
	        frame.setResizable(false);
	        frame.setVisible(true);
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("check".equals(e.getActionCommand())) 
		{
	          //JToggleButton[] foundSet = new JToggleButton[3];
			
	          
	          System.out.println();
	          if(findSet()==null)
	          {
	        	  message.setText("Right! +3");
	        	  Insets insets = getInsets();
	        	  Dimension sizeT = cardButtons[0].getPreferredSize();
	        	  int column = sizeBoard/3;
	        	  int row = 0;
	        	  for (int i = sizeBoard; i<sizeBoard+3; i++)
	      		{
	              cardButtons[i] = new JToggleButton(deckList[counter].getCard());
	              deckList[counter].setBtn(cardButtons[i]);
	              cardButtons[i].addActionListener(this);
	              add(cardButtons[i]);
	              cardButtons[i].setBounds(150+insets.left+column*(10+sizeT.width),50+insets.top+row*(10+sizeT.height), sizeT.width, sizeT.height);
	              counter++;
	              row++;
	      		}
	        	  sizeBoard=sizeBoard+3;
	        	  
	          }
	          else
	          {
	        	  foundSet = findSet().clone();
	        	  message.setText("Nope.");
	        	  foundSet[0].setSelected(true);
	        	  foundSet[1].setSelected(true);
	        	  foundSet[2].setSelected(true);
	        	  for(int i = 0; i<sizeBoard; i++)
	        	  {
	        		  cardButtons[i].setEnabled(false);
	        	  }
	          }
	    }
		else if ("ok".equals(e.getActionCommand()))
		{
		  if(message.getText().equals("Nope."))
		  {
		  foundSet[0].setSelected(false);
       	  foundSet[1].setSelected(false);
       	  foundSet[2].setSelected(false);
       	  for(int i = 0; i<sizeBoard; i++)
       	  {
       		  cardButtons[i].setEnabled(true);
       	  }
       	  message.setText("                   ");
		  }
			
		}
		else
		{		
		JToggleButton tBtn = (JToggleButton)e.getSource();
		if(tBtn.isSelected())
		{
			if(selected[0]==null)
			{
				selected[0]= tBtn;
			}
			else if(selected[1]==null)
			{
				selected[1]= tBtn;
			}
			else
			{
				selected[2]= tBtn;
				if(isASet(findCard(selected[0]),findCard(selected[1]),findCard(selected[2]))&&sizeBoard ==12&& counter <80)
				{
					for (int i = 0; i<3; i++)
					{
					selected[i].setIcon(deckList[counter].getCard());
					findCard(selected[i]).setBtn(null);
					deckList[counter].setBtn(selected[i]);
					counter++;
					}
					deselect();
					
				}
				// If size is larger than 12 the selected cards are swapped to the right and removed instead
				else if(isASet(findCard(selected[0]),findCard(selected[1]),findCard(selected[2]))&&(sizeBoard >=12 || counter >80))
				{
			
				
					ImageIcon dummyImg = new ImageIcon();
					boolean a =false;
					boolean b =false;
					boolean c =false;
					boolean d =false;
					boolean e2 =false;
					boolean f =false;
					cardButtons[sizeBoard-3].setSelected(true);
					cardButtons[sizeBoard-2].setSelected(true);
					cardButtons[sizeBoard-1].setSelected(true);
					
					for(int i =0; i<3; i++)
					{
						
						for (int j = 3; j>0; j--)
						{
						if(selected[i]==cardButtons[sizeBoard - (j)])
						{
							selected[i].setSelected(false);;
							cardButtons[sizeBoard - (j)].setSelected(false);;
						}
						}
					}
					for(int i =0; i<3; i++)
					{
						
						for (int j = 3; j>0; j--)
						{
						if(selected[i].isSelected()==true && cardButtons[sizeBoard - (j)].isSelected()==true)
						{
							swapButtons(selected[i],cardButtons[sizeBoard - (j)]);
							selected[i].setSelected(false);;
							cardButtons[sizeBoard - (j)].setSelected(false);;
							
						}
						}
					}
					
						
						

					//cardButtons[sizeBoard-3] = new JToggleButton();
					remove(cardButtons[sizeBoard-3]);
					cardButtons[sizeBoard-3]=null;
					//cardButtons[sizeBoard-2] = new JToggleButton();
					remove(cardButtons[sizeBoard-2]);
					cardButtons[sizeBoard-2]=null;
					//cardButtons[sizeBoard-1] = new JToggleButton();
					remove(cardButtons[sizeBoard-1]);
					cardButtons[sizeBoard-1]=null;

					/*
					dummyImg =findCard(cardButtons[sizeBoard-3]).getCard();
					findCard(cardButtons[sizeBoard-3]).setBtn(selected[0]);
					cardButtons[sizeBoard-3].setIcon(findCard(selected[0]).getCard());
					findCard(selected[0]).setBtn(null);
					selected[0].setIcon(dummyImg);
					remove(cardButtons[sizeBoard-3]);
					cardButtons[sizeBoard-3]=null;

					dummyImg =findCard(cardButtons[sizeBoard-2]).getCard();
					findCard(cardButtons[sizeBoard-2]).setBtn(selected[1]);
					cardButtons[sizeBoard-2].setIcon(findCard(selected[1]).getCard());
					findCard(selected[1]).setBtn(null);
					selected[1].setIcon(dummyImg);
					remove(cardButtons[sizeBoard-2]);
					cardButtons[sizeBoard-2]=null;
					

					dummyImg =findCard(cardButtons[sizeBoard-1]).getCard();
					findCard(cardButtons[sizeBoard-1]).setBtn(selected[2]);
					cardButtons[sizeBoard-1].setIcon(findCard(selected[2]).getCard());
					findCard(selected[2]).setBtn(null);
					selected[2].setIcon(dummyImg);
					remove(cardButtons[sizeBoard-1]);
					cardButtons[sizeBoard-1]=null;
					*/
					
					
					revalidate();
					repaint();
					
					selected[0]=null;
					selected[1]=null;
					selected[2]=null;
					sizeBoard = sizeBoard-3;
				}
				else
				{
					deselect();
				}
			}
		}
		
		else
		{
			for(int i = 0; i<3; i++)
			{
				if(tBtn == selected[i])
				{
					selected[i] = null;
					
				}
			}
		}
		}

		}
	}

