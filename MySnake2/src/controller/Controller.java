package controller;

//�����������������ı仯
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
//����һ��������Controller��̳�KeyAdapter�࣬��ʵ��SnakeListener
//Controller��̳�KeyAdapter�࣬������дkeyPressed��KeyEvent e)�����������û��İ�������ı��ߵķ���
public class Controller extends KeyAdapter implements SnakeListener{
	private Snake snake;//�����˼������������
	private Food food;
	private Ground ground;
	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private BottonPanel bottonPanel;
	
	//���췽�������Ҳ�����ֵ
	public Controller(Snake snake, Food food, Ground ground,GamePanel gamePanel,GameMenu gameMenu,BottonPanel bottonPanel) {
		this.snake = snake;
		this.food = food;
		this.ground = ground;
		this.gamePanel = gamePanel;
		this.gameMenu = gameMenu;
		this.bottonPanel = bottonPanel;
		//��ʼ������
		init();
	}

	//�������ĳ�ʼ������Ҫ��ʼ���Կؼ��ļ���
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

	//��ȡ������
	public Snake getSnake() {
		return snake;//����
	}
	
	public Ground getGround() {
		return ground;//ǽ��
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;//��Ϸ�����
	}

	public GameMenu getGameMenu() {
		return gameMenu;//��Ϸ�˵���
	}
	
	public BottonPanel getBottonPanel() {
		return bottonPanel;//��ť�����
	}
	
	
	//���̰����ļ����������û������ı����˶��ķ���ľ���ʵ��
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) { //�ж��û������ı�������˶��ķ���ľ���ʵ��
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

	
	//ʵ�����ƶ��Ľӿڣ��������ƶ����̷����ĸ�������
	@Override
	public void snakeMoved(Snake snake) {
		//����gamePanel�е���ʾ����������������������ã�ÿ�ƶ�һ�Σ��͸���һ�����
		gamePanel.display(snake, food, ground);			
		//if�жϣ�����߳Ե�ʳ����߱䳤�������µ�ʳ��
		if(food.isFoodEated(snake)) {
			snake.eatFood();
			food.newFood(ground.getPoint());
			
			//���µ÷���ʾ���	
			bottonPanel.repaint();
			setScore();		
		}
		//����߳Ե�ǽ�������������߳̽�����
		if(ground.isGroundEated(snake)) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		//����߳Ե����壬�����������߳̽�����
		if(snake.isEatBody()) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
		
	}
	
	//����һ�������ķ���
	public void setScore() {
		int score = snake.getFoodCount() ;
		bottonPanel.setScore(score);
	}
	
	// ��ʼһ������Ϸ	�����¿�ʼ���ϰ�����գ�ͨ��switch�������ϰ���Ĳ�ͬ��ʽ
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
		//��ť����ϵķ�����ʼ��
		food.newFood(ground.getPoint());	
		bottonPanel.setScore(0);		
		//����JPanel��repaint�����ػ�ͼ��
		bottonPanel.repaint();
	}

	

	
	//��ʼ��Ϸ��ť�ļ���
	class startHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			gamePanel.requestFocus(true);	//!!ʹ��Ϸ�������ý���
			snake.clear();
			snake.init();		
			snake.begin();
			newGame();
			bottonPanel.getStartButton().setEnabled(false);
		}
		
	}
	
	//��ͣ��ť�ļ���
	class pauseHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gamePanel.requestFocus(true);
			snake.changePause();

			if(e.getActionCommand()=="��ͣ��Ϸ")
				bottonPanel.getPauseButton().setText("������Ϸ");
			else {
				bottonPanel.getPauseButton().setText("��ͣ��Ϸ");
			}
		}
		
	}

	//������Ϸ��ť�ļ���
	class endHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			snake.die();
			bottonPanel.getStartButton().setEnabled(true);
		}
		
	}
	
	
	//�˵�������ť�ļ���
	class Item1Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//java.swing.JColorChooser(��ɫѡ����)������ɫ��ѡ�񡢱༭
			Color color = JColorChooser.showDialog(gamePanel, "��ѡ����ɫ", Color.BLACK);	
			gamePanel.backgroundColor = color;
			
		}
		
	}
	
	//����ʳ����ɫ
	class Item2Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "��ѡ����ɫ", Color.BLACK);	
			food.setFoodColor(color);
		}
		
	}
	
	//������ͷ��ɫ
	class Item3Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "��ѡ����ɫ", Color.BLACK);	
			snake.setHeadColor(color);
		}
		
	}
	//����������ɫ
	class Item4Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Color color = JColorChooser.showDialog(gamePanel, "��ѡ����ɫ", Color.BLACK);	
			snake.setBodyColor(color);
		}
		
	}
	
	//�Ѷȣ����������ߵ�ʱ���������Ѷȣ���ֵԽ�����˶���Խ��
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
	//��ͼǽ���Ѷ�
	
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
	//����һ���������ķ����������ڲ����������¼�Դ�Ĳ����������ƿ�ݼ���ʹ��
	class abortItemHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			StringBuffer sb= new StringBuffer();
			sb.append("��������Ʒ���\n");
			sb.append("w����s���ֱ����ʹ����١�����\n");
			String message = sb.toString();
			JOptionPane.showMessageDialog(null, message, "ʹ��˵��",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
}
