package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import listener.SnakeListener;
import util.Global;


public class Snake {

	//定义一些代表方向的常量
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int LEFT = 2;
	public static final int RIGHT = -2;
	
	//定义监听器组，用于注册多个监听器组
	private Set<SnakeListener> listeners = new HashSet<SnakeListener>();
	//存储蛇的链表结构，定义一个集合字段来保存蛇身体的所有坐标，用awt中的point类表示
	private LinkedList<Point> body = new LinkedList<Point>();
	
	private boolean life;					//定义一个布尔变量判断蛇是否活着（线程的生命）
	private boolean pause;					//是否暂停游戏
	private int oldDirection,newDirection;	//新，旧方向的引入（避免一次移动时间内的无效方向）
	private Point oldTail;					//旧的尾巴（在吃掉食物的时候使用）
	private int foodCount = 0;				//吃掉食物的数量
	private Color headColor;
	private Color bodyColor;
	private int sleepTime;
	
	public boolean isLife() {
			return life;
	}
	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}


	public void setHeadColor(Color headColor) {
		this.headColor = headColor;
	}


	public void setBodyColor(Color bodyColor) {
		this.bodyColor = bodyColor;
	}

    //初始化蛇身的方法
	public void init() {
		//找出屏幕正中心点的坐标
		int x = Global.WIDTH/2;
		int y = Global.HEIGHT/2;
		//依次添加身体节点（3个节点，蛇头在右）
		for(int i=0;i<3;i++) {
			body.addLast(new Point(x--,y));
		}
		//因为蛇头方向在右，所以设置默认蛇移动的方向向右
		oldDirection = newDirection = RIGHT;
		foodCount = 0;	
		life = true;
		pause = false;
		
		if(sleepTime==0) {
			sleepTime = 300;
		}
	}
	
	
	//清空蛇的节点的方法（用来开始一次新的游戏）
	public void clear() {
		body.clear();
	}
	

	public void setLife(boolean life) {
		this.life = life;
	}


	public boolean getPause() {
		return pause;
	}	
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	
	//用来改变pause常量的状态
	public void changePause() {
		pause = !pause;
	}

	
	//蛇死掉的方法
	public void die() {
		life = false;
	}
	
	
	//蛇移动的方法
	public void move() {
		//如果旧的移动方向和新的移动方向不想反，就把新的方向赋值给旧方向
		if(!(oldDirection + newDirection==0)) {
			oldDirection = newDirection ;
		}
		
		//去尾
		oldTail = body.removeLast();
		//获得当前蛇头坐标
		int x = body.getFirst().x;
		int y = body.getFirst().y;
		//根据蛇头的方向来计算蛇头新坐标
		switch(oldDirection) {
			case UP:
				y--;
				if(y<0) {//判断蛇的位置超出显示区域边界
					y = Global.HEIGHT -1 ;
				}
				break;//如果蛇头向上，y坐标-1
			case DOWN:
				y++;
				if(y >= Global.HEIGHT) {
					y = 0;
				}
				break;//如果蛇头向上，y坐标+1
			case LEFT:
				x--;
				if(x<0) {
					x = Global.WIDTH-1;
				}
				break;//如果蛇头向上，x坐标-1
			case RIGHT:
				x++;
				if(x >= Global.WIDTH) {
					x = 0;
				}
				break;//如果蛇头向上，x坐标+1
		}
		//使用新蛇头坐标得到新蛇头
		Point newHead = new Point(x, y);
		//加头
		body.addFirst(newHead);//将新蛇头添加到首节点
	}
	
	
	//定义蛇的改变方向放法
	public void changeDirection(int direction) {
			newDirection = direction;	//将输入的方向参数赋值给保存新的移动方向的变量	
	}
	
	
	//蛇吃食物的方法
	public void eatFood() {		
		body.addLast(oldTail);//吃到食物时把原来去掉的尾巴加上
		foodCount++;//食物数量加1
	}
	
	
	//获取吃到的食物数量
	public int getFoodCount() {
		return foodCount;
	}
	
	
	//蛇是否吃到了自己的身体
	//遍历身体所有节点，如果与蛇头重合，则为吃到了身体，因为i=0时表示身体第一个节点，是蛇头，所以i从1开始循环
	public boolean isEatBody() {
		for(int i=1;i<body.size();i++) {
			if(body.get(i).equals(this.getHead())) 
				return true;
		}
		
		return false;	
	}
	
	
	//获取蛇头的坐标节点方法
	public Point getHead() {
		return body.getFirst();
	}
	
	
	//蛇身显示方法
	public void drawMe(Graphics g) {
		if(bodyColor==null) {
			g.setColor(new Color(0x3333FF));
		} else {
			g.setColor(bodyColor);
		}
		
		for(Point p : body) {//遍历集合，填充所有属于蛇身体的矩形
			
			g.fillRoundRect(p.x*Global.CELL_SIZE, p.y*Global.CELL_SIZE,
					Global.CELL_SIZE, Global.CELL_SIZE, 15,12 );
		}
		drawHead(g);
	}
	
	//画蛇头
	public void drawHead(Graphics g) {
		if(headColor==null)
			g.setColor(Color.YELLOW);
		else {
			g.setColor(headColor);
		}
		
		g.fillRoundRect(getHead().x * Global.CELL_SIZE, getHead().y* Global.CELL_SIZE, 
				Global.CELL_SIZE, Global.CELL_SIZE, 15,12);
	}
	
	
	//控制蛇移动的线程内部类，创建线程
	private class SnakeDriver implements Runnable {
		public void run() {
			while(life) {//如果蛇活着就一直运行这个线程
				if(pause==false) {
					move();		
					//这里的for循环将会遍历listeners中所有元素依次调用snakeMoved(),触发事件
					for(SnakeListener l : listeners)
						l.snakeMoved(Snake.this);
				}
					
				try {	
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//启动线程的方法
	public void begin() {
		new Thread(new SnakeDriver()).start();
		
	}

	
	//添加监听器
	public void addSnakeListener(SnakeListener l) {
		if(l != null) {
			listeners.add(l);
		}
	}

	//加速
	public void speedUp() {
		if(sleepTime>50) {
			sleepTime-=20;
		}
	}
	
	//减速
	public void speedDown() {
		if(sleepTime<700) {
			sleepTime+=20;
		}
	}

}
