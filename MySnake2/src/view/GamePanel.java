package view;
//游戏面板类
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
	//定义对象的引用，用于调用drawMe()显示函数
	private Snake snake;
	private Food food;
	private Ground ground;	
	public Color backgroundColor;
	
	public GamePanel() {
		setLocation(0, 0);		
		//设置大小和布局 
		this.setSize(Global.WIDTH * Global.CELL_SIZE, Global.HEIGHT
				* Global.CELL_SIZE);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setFocusable(true);
		
	}

    //显示窗体的方法
	public void display(Snake snake,Food food,Ground ground) {
		this.snake = snake;//参数赋值
		this.food = food;
		this.ground = ground;
		//最终会调用paintComponent(Graphics g)用于重新显示
		repaint();
	}

	
	//蛇移动后要擦除之前的游戏图形，通过填充和之前显示区域相同大小的矩形实现
	//清空游戏面板（擦除之前效果）
	public void clearDraw(Graphics g) {//用于重绘（重新显示）
			if(backgroundColor==null) {
				g.setColor(new Color(0x00FFFF));//设置颜色
			}
			else {
				g.setColor(backgroundColor);
			}
			//填充
			g.fillRect(0, 0, Global.WIDTH*Global.CELL_SIZE, Global.HEIGHT*Global.CELL_SIZE);
	}
	
	
	@Override
	public void paint(Graphics g) {
			clearDraw(g);
			//重新显示
            //此处paint方法可能在出现窗体时就被调用
			//此时可能snake，food,ground对象还没产生，所以添加一个判断。
			if(ground != null && snake != null && food != null) {
					ground.drawMe(g);
					food.drawMe(g);
					snake.drawMe(g);
			}
			if(snake!=null && snake.isLife()==false)  {
				recover(g);
			}
	
		}

	
	//恢复工作
	public void recover(Graphics g) {
		clearDraw(g);
		
		//在游戏主面板区绘制“game over”
		g.setColor(Color.GREEN);
		g.setFont(new Font("Serif",Font.BOLD,50));
		g.drawString("Game Over", 130, 210);
		
	}
	
	
}
