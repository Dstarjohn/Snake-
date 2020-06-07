package games;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.Controller;
import entities.Food;
import entities.Ground;
import entities.Snake;
import util.Global;
import view.BottonPanel;
import view.GameMenu;
import view.GamePanel;
//����һ��GameFrame������̳�JFrame�࣬����ʹ��javax.swing.JFrame�е�����ķ���
public class GameFrame extends JFrame {

	/**
	 * 
	 */
	//����һ��˽�еĳ�������ID��serialVersionUID ��������ʵ�����л���Ĳ�ͬ�汾��ļ�����
	private static final long serialVersionUID = 1L;
    //main��������ڣ�������һ���ɴӿ���̨���ܵ����ݵ�����ΪString����
	public static void main(String[] args) {
		new GameFrame(new Controller(new Snake(), new Food(), new Ground(), 
				new GamePanel(), new GameMenu(),new BottonPanel()));

	}

	
	//����ĸ�����
	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private Snake snake;
	//private Food food;
	//private Ground ground;
	private Controller controller;	
	private JPanel buttonPanel;

	
	//����һЩ��������Ĳ���
	public GameFrame(Controller c) {
		this.controller = c;
		snake = controller.getSnake();
		gameMenu = controller.getGameMenu();
		gamePanel = controller.getGamePanel();
		buttonPanel = controller.getBottonPanel();
		
		setTitle("̰����");
		setBounds(300,100,Global.WIDTH*Global.CELL_SIZE+250,Global.HEIGHT*Global.CELL_SIZE+60);
		//���ò���Ϊ���Բ���
		setLayout(null);
		//����һ��Ĭ�ϹرյĲ�����Ҳ���ǹر�JFrame���ڵĹرգ�������Ҳ���Թرգ�����û�н���������̡�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//��ʼ��������JFrame�޷�ֱ������������ҪgetContentPane()��ȡ��壬Ȼ���������������������
		Container contentPane = this.getContentPane(); 		
		this.setJMenuBar(gameMenu);
		//����������ӿؼ�
		contentPane.add(gamePanel);
		contentPane.add(buttonPanel);
		//���ô��ڴ�С���ɸı䣬����GUI����ɼ�
		setResizable(false);
		setVisible(true);

		
		//�ô��ھ���
		this.setLocation(this.getToolkit().getScreenSize().width / 2
				- this.getWidth() / 2, this.getToolkit().getScreenSize().height
				/ 2 - this.getHeight() / 2);
		
		//�����Ϸ����ϵİ����¼�����
		gamePanel.addKeyListener(controller);
		snake.addSnakeListener(controller);	
		//���ÿ�ʼ����Ϸ�ķ���
		controller.newGame();
		
		
	}
	
}
