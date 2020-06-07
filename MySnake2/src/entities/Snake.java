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

	//����һЩ������ĳ���
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int LEFT = 2;
	public static final int RIGHT = -2;
	
	//����������飬����ע������������
	private Set<SnakeListener> listeners = new HashSet<SnakeListener>();
	//�洢�ߵ�����ṹ������һ�������ֶ���������������������꣬��awt�е�point���ʾ
	private LinkedList<Point> body = new LinkedList<Point>();
	
	private boolean life;					//����һ�����������ж����Ƿ���ţ��̵߳�������
	private boolean pause;					//�Ƿ���ͣ��Ϸ
	private int oldDirection,newDirection;	//�£��ɷ�������루����һ���ƶ�ʱ���ڵ���Ч����
	private Point oldTail;					//�ɵ�β�ͣ��ڳԵ�ʳ���ʱ��ʹ�ã�
	private int foodCount = 0;				//�Ե�ʳ�������
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

    //��ʼ������ķ���
	public void init() {
		//�ҳ���Ļ�����ĵ������
		int x = Global.WIDTH/2;
		int y = Global.HEIGHT/2;
		//�����������ڵ㣨3���ڵ㣬��ͷ���ң�
		for(int i=0;i<3;i++) {
			body.addLast(new Point(x--,y));
		}
		//��Ϊ��ͷ�������ң���������Ĭ�����ƶ��ķ�������
		oldDirection = newDirection = RIGHT;
		foodCount = 0;	
		life = true;
		pause = false;
		
		if(sleepTime==0) {
			sleepTime = 300;
		}
	}
	
	
	//����ߵĽڵ�ķ�����������ʼһ���µ���Ϸ��
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
	
	
	//�����ı�pause������״̬
	public void changePause() {
		pause = !pause;
	}

	
	//�������ķ���
	public void die() {
		life = false;
	}
	
	
	//���ƶ��ķ���
	public void move() {
		//����ɵ��ƶ�������µ��ƶ������뷴���Ͱ��µķ���ֵ���ɷ���
		if(!(oldDirection + newDirection==0)) {
			oldDirection = newDirection ;
		}
		
		//ȥβ
		oldTail = body.removeLast();
		//��õ�ǰ��ͷ����
		int x = body.getFirst().x;
		int y = body.getFirst().y;
		//������ͷ�ķ�����������ͷ������
		switch(oldDirection) {
			case UP:
				y--;
				if(y<0) {//�ж��ߵ�λ�ó�����ʾ����߽�
					y = Global.HEIGHT -1 ;
				}
				break;//�����ͷ���ϣ�y����-1
			case DOWN:
				y++;
				if(y >= Global.HEIGHT) {
					y = 0;
				}
				break;//�����ͷ���ϣ�y����+1
			case LEFT:
				x--;
				if(x<0) {
					x = Global.WIDTH-1;
				}
				break;//�����ͷ���ϣ�x����-1
			case RIGHT:
				x++;
				if(x >= Global.WIDTH) {
					x = 0;
				}
				break;//�����ͷ���ϣ�x����+1
		}
		//ʹ������ͷ����õ�����ͷ
		Point newHead = new Point(x, y);
		//��ͷ
		body.addFirst(newHead);//������ͷ��ӵ��׽ڵ�
	}
	
	
	//�����ߵĸı䷽��ŷ�
	public void changeDirection(int direction) {
			newDirection = direction;	//������ķ��������ֵ�������µ��ƶ�����ı���	
	}
	
	
	//�߳�ʳ��ķ���
	public void eatFood() {		
		body.addLast(oldTail);//�Ե�ʳ��ʱ��ԭ��ȥ����β�ͼ���
		foodCount++;//ʳ��������1
	}
	
	
	//��ȡ�Ե���ʳ������
	public int getFoodCount() {
		return foodCount;
	}
	
	
	//���Ƿ�Ե����Լ�������
	//�����������нڵ㣬�������ͷ�غϣ���Ϊ�Ե������壬��Ϊi=0ʱ��ʾ�����һ���ڵ㣬����ͷ������i��1��ʼѭ��
	public boolean isEatBody() {
		for(int i=1;i<body.size();i++) {
			if(body.get(i).equals(this.getHead())) 
				return true;
		}
		
		return false;	
	}
	
	
	//��ȡ��ͷ������ڵ㷽��
	public Point getHead() {
		return body.getFirst();
	}
	
	
	//������ʾ����
	public void drawMe(Graphics g) {
		if(bodyColor==null) {
			g.setColor(new Color(0x3333FF));
		} else {
			g.setColor(bodyColor);
		}
		
		for(Point p : body) {//�������ϣ������������������ľ���
			
			g.fillRoundRect(p.x*Global.CELL_SIZE, p.y*Global.CELL_SIZE,
					Global.CELL_SIZE, Global.CELL_SIZE, 15,12 );
		}
		drawHead(g);
	}
	
	//����ͷ
	public void drawHead(Graphics g) {
		if(headColor==null)
			g.setColor(Color.YELLOW);
		else {
			g.setColor(headColor);
		}
		
		g.fillRoundRect(getHead().x * Global.CELL_SIZE, getHead().y* Global.CELL_SIZE, 
				Global.CELL_SIZE, Global.CELL_SIZE, 15,12);
	}
	
	
	//�������ƶ����߳��ڲ��࣬�����߳�
	private class SnakeDriver implements Runnable {
		public void run() {
			while(life) {//����߻��ž�һֱ��������߳�
				if(pause==false) {
					move();		
					//�����forѭ���������listeners������Ԫ�����ε���snakeMoved(),�����¼�
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
	
	
	//�����̵߳ķ���
	public void begin() {
		new Thread(new SnakeDriver()).start();
		
	}

	
	//��Ӽ�����
	public void addSnakeListener(SnakeListener l) {
		if(l != null) {
			listeners.add(l);
		}
	}

	//����
	public void speedUp() {
		if(sleepTime>50) {
			sleepTime-=20;
		}
	}
	
	//����
	public void speedDown() {
		if(sleepTime<700) {
			sleepTime+=20;
		}
	}

}
