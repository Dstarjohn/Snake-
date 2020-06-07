package view;
//��Ϸ�����
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import entities.Food;
import entities.Ground;
import entities.Snake;
import util.Global;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	//�����������ã����ڵ���drawMe()��ʾ����
	private Snake snake;
	private Food food;
	private Ground ground;	
	public Color backgroundColor;
	
	public GamePanel() {
		setLocation(0, 0);		
		//���ô�С�Ͳ��� 
		this.setSize(Global.WIDTH * Global.CELL_SIZE, Global.HEIGHT
				* Global.CELL_SIZE);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setFocusable(true);
		
	}

    //��ʾ����ķ���
	public void display(Snake snake,Food food,Ground ground) {
		this.snake = snake;//������ֵ
		this.food = food;
		this.ground = ground;
		//���ջ����paintComponent(Graphics g)����������ʾ
		repaint();
	}

	
	//���ƶ���Ҫ����֮ǰ����Ϸͼ�Σ�ͨ������֮ǰ��ʾ������ͬ��С�ľ���ʵ��
	//�����Ϸ��壨����֮ǰЧ����
	public void clearDraw(Graphics g) {//�����ػ棨������ʾ��
			if(backgroundColor==null) {
				g.setColor(new Color(0x00FFFF));//������ɫ
			}
			else {
				g.setColor(backgroundColor);
			}
			//���
			g.fillRect(0, 0, Global.WIDTH*Global.CELL_SIZE, Global.HEIGHT*Global.CELL_SIZE);
	}
	
	
	@Override
	public void paint(Graphics g) {
			clearDraw(g);
			//������ʾ
            //�˴�paint���������ڳ��ִ���ʱ�ͱ�����
			//��ʱ����snake��food,ground����û�������������һ���жϡ�
			if(ground != null && snake != null && food != null) {
					ground.drawMe(g);
					food.drawMe(g);
					snake.drawMe(g);
			}
			if(snake!=null && snake.isLife()==false)  {
				recover(g);
			}
	
		}

	
	//�ָ�����
	public void recover(Graphics g) {
		clearDraw(g);
		
		//����Ϸ����������ơ�game over��
		g.setColor(Color.GREEN);
		g.setFont(new Font("Serif",Font.BOLD,50));
		g.drawString("Game Over", 130, 210);
		
	}
	
	
}
