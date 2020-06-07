package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import util.Global;

public class Ground {
	//定义一个二维数组存放多块墙体障碍物的信息，数组中置1就是墙体
	private boolean [][] rocks = new boolean[Global.WIDTH][Global.HEIGHT];
	private int mapType = 1;
	
	
	
	public int getMapType() {
		return mapType;
	}

	public void setMapType(int mapType) {
		this.mapType = mapType;
	}
	
	
	//初始化地面	 
	public void clear() {
		for (int x = 0; x < Global.WIDTH; x++)
			for (int y = 0; y < Global.HEIGHT; y++)
				rocks[x][y] = false;
	}
	
	//添加一个构造方法来初始化墙体，产生石头, 可以覆盖这个方法改变石头在地面上的布局
	public void generateRocks1() {
		for (int x = 0; x < Global.WIDTH; x++)
			rocks[x][0] = rocks[x][Global.HEIGHT - 1] = true;
		for (int y = 0; y < Global.HEIGHT; y++)
			rocks[0][y] = rocks[Global.WIDTH - 1][y] = true;
	}
	public void generateRocks2() {

		for (int y = 0; y < 6; y++) {
			rocks[0][y] = true;
			rocks[Global.WIDTH - 1][y] = true;
			rocks[0][Global.HEIGHT - 1 - y] = true;
			rocks[Global.WIDTH - 1][Global.HEIGHT - 1 - y] = true;
		}
		for (int y = 6; y < Global.HEIGHT - 6; y++) {
			rocks[6][y] = true;
			rocks[Global.WIDTH - 7][y] = true;
		}
	}
	public void generateRocks3() {
		for(int x=4;x<14;x++)
			rocks[x][5] = true;
		for(int j=5;j<15;j++)
			rocks[21][j] = true;
		for(int y=13;y<20;y++)
			rocks[14][y] = true;
		for(int x=2;x<10;x++)
			rocks[x][17] = true;
		for(int i=10;i<Global.WIDTH-3;i++)
			rocks[i][Global.HEIGHT-3] = true;
	}
	
	//判断蛇是否吃到了墙的方法
	public boolean isGroundEated(Snake snake) {
		for(int x=0; x<Global.WIDTH;x++) {
			for(int y=0; y<Global.HEIGHT;y++) {
				if(rocks[x][y] == true &&
					(x==snake.getHead().x &&y==snake.getHead().y)) {
					return true;
				}
			}
		}
		return false;
		
	}
	//获取随机坐标点的方法
	public Point getPoint() {
		Random random = new Random();
		int x=0,y=0;
		do {//直到生成一个不与墙重叠的坐标再结束循环
			x = random.nextInt(Global.WIDTH);
			y = random.nextInt(Global.HEIGHT);
		} while (rocks[x][y]==true);

		return new Point(x,y);
	}
	
	
	//墙体显示方法
	public void drawMe(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		
		for(int x=0; x<Global.WIDTH;x++) {
			for(int y=0; y<Global.HEIGHT;y++) {
				if(rocks[x][y] == true) {
					g.fill3DRect(x*Global.CELL_SIZE, y*Global.CELL_SIZE, 
							Global.CELL_SIZE,Global.CELL_SIZE, true);
				}
			}
		}
	}
}
