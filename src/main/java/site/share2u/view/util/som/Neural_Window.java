package site.share2u.view.util.som;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * 这个类包含不同类型的神经网络
 */
public class Neural_Window {
   private static Logger log = Logger.getLogger(Neural_Window.class);
    /**
     * 建立神经网络，训练，保存
     */
    public static void establish_network_type(int dimCount,List<List<Double>> optionData) {
        NeuralK KOH= new NeuralK();
        Storage Kstore = new Storage();
        log.info("1、构造神经网络拓扑结构，建立权值数组");
        KOH.construct_Kohonen_network(dimCount);
        log.info("2、归一化、训练，使用SOM模型");
        KOH.network_training_testing(1,optionData);
        log.info("3、存储模型");
        Kstore.save_neural_network(KOH.Kohonen_Design);
    }
}