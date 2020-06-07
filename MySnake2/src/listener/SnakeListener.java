package listener;
//监听蛇移动的接口
import entities.Snake;
public interface SnakeListener {
	void snakeMoved(Snake snake);//定义蛇移动的方法，蛇是时间源，作为参数传入
}
