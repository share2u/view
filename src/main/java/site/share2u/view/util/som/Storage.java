package site.share2u.view.util.som;

/**
 * 描述：用于存储神经网络
 */
public class Storage {
    
    /**
     * 用于存储神经网络,就是存储input_weight权重数组
     */
    public void save_neural_network(Kohonen_Topology Kohonen_Design) {
        Kohonen_Design.savenet();
    }
}
