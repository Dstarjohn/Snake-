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
//定义一个GameFrame的主类继承JFrame类，可以使用javax.swing.JFrame中的里面的方法
public class GameFrame extends JFrame {

	/**
	 * 
	 */
	//定义一个私有的程序化序列ID，serialVersionUID 用来表明实现序列化类的不同版本间的兼容性
	private static final long serialVersionUID = 1L;
    //main主函数入口，声明了一个可从控制台接受的数据的类型为String数组
	public static void main(String[] args) {
		new GameFrame(new Controller(new Snake(), new Food(), new Ground(), 
				new GamePanel(), new GameMenu(),new BottonPanel()));

	}

	
	//定义的各对象
	private GamePanel gamePanel;
	private GameMenu gameMenu;
	private Snake snake;
	//private Food food;
	//private Ground ground;
	private Controller controller;	
	private JPanel buttonPanel;

	
	//定义一些基本组件的布局
	public GameFrame(Controller c) {
		this.controller = c;
		snake = controller.getSnake();
		gameMenu = controller.getGameMenu();
		gamePanel = controller.getGamePanel();
		buttonPanel = controller.getBottonPanel();
		
		setTitle("贪吃蛇");
		setBounds(300,100,Global.WIDTH*Global.CELL_SIZE+250,Global.HEIGHT*Global.CELL_SIZE+60);
		//设置布局为绝对布局
		setLayout(null);
		//设置一个默认关闭的操作，也就是关闭JFrame窗口的关闭，不设置也可以关闭，但是没有结束程序进程。
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//初始化容器，JFrame无法直接添加组件，需要getContentPane()获取面板，然后再内容面板上添加组件。
		Container contentPane = this.getContentPane(); 		
		this.setJMenuBar(gameMenu);
		//在容器上添加控件
		contentPane.add(gamePanel);
		contentPane.add(buttonPanel);
		//设置窗口大小不可改变，设置GUI组件可见
		setResizable(false);
		setVisible(true);

		
		//让窗口居中
		this.setLocation(this.getToolkit().getScreenSize().width / 2
				- this.getWidth() / 2, this.getToolkit().getScreenSize().height
				/ 2 - this.getHeight() / 2);
		
		//添加游戏面板上的按键事件监听
		gamePanel.addKeyListener(controller);
		snake.addSnakeListener(controller);	
		//调用开始新游戏的方法
		controller.newGame();
		
		
	}
	
}
