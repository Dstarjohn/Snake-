package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import util.Global;


public class Food extends Point{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color foodColor;
	
	

	public void setFoodColor(Color foodColor) {
		this.foodColor = foodColor;
	}

	public Color getFoodColor() {
		return foodColor;
	}
    //�õ�һ��ʳ��
	public void newFood(Point p) {
		setLocation(p);
	}
	//�ж�ʳ�ﱻ�߳���
	public boolean isFoodEated(Snake snake) {	
		//ʳ����������ͷ�����غϼ��Ե��ˣ��Ƚ�ʳ���������ͷ�����Ƿ��غ�
		return 	this.equals(snake.getHead());
	}
	//ʳ�����ʾ����
	public void drawMe(Graphics g) {
		if(foodColor==null) {
			g.setColor(Color.RED);
		}else {
			g.setColor(foodColor);
		}
		//���ʳ��ľ���
		g.fill3DRect(x*Global.CELL_SIZE, y*Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, true);
	}
}
