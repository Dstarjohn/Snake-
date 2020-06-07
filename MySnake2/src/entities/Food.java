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
    //得到一个食物
	public void newFood(Point p) {
		setLocation(p);
	}
	//判断食物被蛇吃了
	public boolean isFoodEated(Snake snake) {	
		//食物坐标与蛇头坐标重合即吃到了，比较食物坐标和蛇头坐标是否重合
		return 	this.equals(snake.getHead());
	}
	//食物的显示方法
	public void drawMe(Graphics g) {
		if(foodColor==null) {
			g.setColor(Color.RED);
		}else {
			g.setColor(foodColor);
		}
		//填充食物的矩形
		g.fill3DRect(x*Global.CELL_SIZE, y*Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, true);
	}
}
