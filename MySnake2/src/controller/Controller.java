package controller;

//控制器负责处理各组件的变化
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import entities.Food;
import entities.Ground;
import entities.Snake;
import listener.SnakeListener;
import view.BottonPanel;
import view.GameMenu;
import view.GamePanel;
//定义一个公共的Controller类继承KeyAdapter类，来实现SnakeListener
//Controller类继承KeyAdapter类，并且重写keyPressed（KeyEvent e)方法，根据用户的按键情况改变蛇的方向
public class Controller extends KeyAdapter implements SnakeListener{
	private Snake snake;//定义了几个对象的引用
	private Food food;
	private Ground ground;
	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private BottonPanel bottonPanel;
	
	//构造方法，并且参数赋值
	public Controller(Snake snake, Food food, Ground ground,GamePanel gamePanel,GameMenu gameMenu,BottonPanel bottonPanel) {
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		this.gamePanel = gamePanel;
		this.gameMenu = gameMenu;
		this.bottonPanel = bottonPanel;
		//初始化函数
		init();
	}

	//控制器的初始化，主要初始化对控件的监听
	public void init() {
		bottonPanel.getStartButton().addActionListener(new startHandler());
		bottonPanel.getPauseButton().addActionListener(new pauseHandler());
		bottonPanel.getEndButton().addActionListener(new endHandler());
		
		gameMenu.getItem1().addActionListener(new Item1Handler());
		gameMenu.getItem2().addActionListener(new Item2Handler());
		gameMenu.getItem3().addActionListener(new Item3Handler());
		gameMenu.getItem4().addActionListener(new Item4Handler());
		
		gameMenu.getSpItem1().addActionListener(new spItem1Handler());
		gameMenu.getSpItem2().addActionListener(new spItem2Handler());
		gameMenu.getSpItem3().addActionListener(new spItem3Handler());
		gameMenu.getSpItem4().addActionListener(new spItem4Handler());
		
		gameMenu.getMapItem1().addActionListener(new mapItem1Handler());
		gameMenu.getMapItem2().addActionListener(new mapItem2Handler());
		gameMenu.getMapItem3().addActionListener(new mapItem3Handler());
		
		gameMenu.getAbItem().addActionListener(new abortItemHandler());
		
		bottonPanel.getStartButton().setEnabled(true);
	}

	//获取各对象
	public Snake getSnake() {
		return snake;//蛇类
	}
	
	public Ground getGround() {
		return ground;//墙类
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;//游戏面板类
	}

	public GameMenu getGameMenu() {
		return gameMenu;//游戏菜单类
	}
	
	public BottonPanel getBottonPanel() {
		return bottonPanel;//按钮面板类
	}
	
	
	//键盘按键的监听，根据用户按键改变蛇运动的方向的具体实现
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) { //判断用户按键改变控制蛇运动的方向的具体实现
			case KeyEvent.VK_UP:			
				snake.changeDirection(Snake.UP);
				break;
			case KeyEvent.VK_DOWN:				
				snake.changeDirection(Snake.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				snake.changeDirection(Snake.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				snake.changeDirection(Snake.RIGHT);
				break;
			case KeyEvent.VK_W:
				snake.speedUp();
				break;
			case KeyEvent.VK_S:
				snake.speedDown();
				break;
			default:
				break;
		}
	}

	
	//实现蛇移动的接口，处理蛇移动过程发生的各种事情
	@Override
	public void snakeMoved(Snake snake) {
		//调用gamePanel中的显示方法，传入三个对象的引用，每移动一次，就更新一次面板
		gamePanel.display(snake, food, ground);			
		//if判断，如果蛇吃到食物，则蛇变长，生成新的食物
		if(food.isFoodEated(snake)) {
			snake.eatFood();
			food.newFood(ground.getPoint());
			
			//更新得分显示面板	
			bottonPanel.repaint();
			setScore();		
		}
		//如果蛇吃到墙，则蛇死掉（线程结束）
		if(ground.isGroundEated(snake)) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		//如果蛇吃到身体，则蛇死掉（线程结束）
		if(snake.isEatBody()) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
		
	}
	
	//定义一个分数的方法
	public void setScore() {
		int score = snake.getFoodCount() ;
		bottonPanel.setScore(score);
	}
	
	// 开始一个新游戏	，重新开始，障碍物清空，通过switch语句控制障碍物的不同形式
	public void newGame() {
		ground.clear();
		switch (ground.getMapType()) {
			case 1:
				ground.generateRocks1();
				break;
			case 2:
				ground.generateRocks2();
				break;
			case 3:
				ground.generateRocks3();
				break;
		}
		//按钮面板上的分数初始化
		food.newFood(ground.getPoint());	
		bottonPanel.setScore(0);		
		//调用JPanel的repaint方法重绘图像
		bottonPanel.repaint();
	}

	

	
	//开始游戏按钮的监听
	class startHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			gamePanel.requestFocus(true);	//!!使游戏面板区获得焦点
			snake.clear();
			snake.init();		
			snake.begin();
			newGame();
			bottonPanel.getStartButton().setEnabled(false);
		}
		
	}
	
	//暂停按钮的监听
	class pauseHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.requestFocus(true);
			snake.changePause();

			if(e.getActionCommand()=="暂停游戏")
				bottonPanel.getPauseButton().setText("继续游戏");
			else {
				bottonPanel.getPauseButton().setText("暂停游戏");
			}
		}
		
	}

	//结束游戏按钮的监听
	class endHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
	}
	
	
	//菜单栏各按钮的监听
	class Item1Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//java.swing.JColorChooser(颜色选择器)用于颜色的选择、编辑
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			gamePanel.backgroundColor = color;
			
		}
		
	}
	
	//设置食物颜色
	class Item2Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			food.setFoodColor(color);
		}
		
	}
	
	//设置蛇头颜色
	class Item3Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			snake.setHeadColor(color);
		}
		
	}
	//设置蛇身颜色
	class Item4Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "请选择颜色", Color.BLACK);	
			snake.setBodyColor(color);
		}
		
	}
	
	//难度，利用蛇休眠的时间来控制难度，数值越大，蛇运动的越慢
	class spItem1Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(600);
			
		}
		
	}
	
	class spItem2Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(350);
			
		}	
	}
	
	
	class spItem3Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(150);
		}	
	}
	
	class spItem4Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.setSleepTime(60);
			
		}	
	}
	//地图墙体难度
	
	class mapItem1Handler	implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ground.setMapType(1);
			
		}
		
	}
	
	class mapItem2Handler	implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ground.setMapType(2);
			
		}
		
	}
	
	class mapItem3Handler	implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ground.setMapType(3);
			
		}
		
	}
	//设置一个监听器的方法，利用内部类来监听事件源的产生，来控制快捷键的使用
	class abortItemHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuffer sb= new StringBuffer();
			sb.append("方向键控制方向\n");
			sb.append("w键、s键分别控制使其加速、减速\n");
			String message = sb.toString();
			JOptionPane.showMessageDialog(null, message, "使用说明",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
}
