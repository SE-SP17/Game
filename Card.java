import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class Card {

int shape, color, fill, num;
private int[] cardId = new int[4];
private ImageIcon cardImage;
private JToggleButton cardBtn;


public Card(int s, int c, int f, int n, ImageIcon cImg)
{
	shape = s;
	color = c;
	fill = f;
	num = n;
	cardId[0] = s;
	cardId[1] = c;
	cardId[2] = f;
	cardId[3] = n;
	cardImage = cImg;
}

public int[] getCardiD()
{
	return cardId;
}

public ImageIcon getCard()
{
	return cardImage;
}

public void setCard(ImageIcon img)
{
	cardImage = img;
}
public int getShape()
{
	return shape;
}
public int getColor()
{
	return color;
}
public int getFill()
{
	return fill;
}
public int getNumber()
{
	return num;
}
public void setBtn(JToggleButton btn)
{
	cardBtn = btn;
}
public JToggleButton getBtn()
{
	return cardBtn;
}
}
