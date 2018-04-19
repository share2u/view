package site.share2u.view.util.som;

import org.apache.log4j.Logger;
import site.share2u.view.pojo.PageData;
import site.share2u.view.util.TxtUtil;

import java.io.*;
import java.util.List;

/**
 * 类包含SOM神经网络，以及训练和测试数据
 */
public class NeuralK {
    Logger log = Logger.getLogger(Neural_Window.class);
    /**
     * 训练集
     */
    private Kohonen_Training_Data Kohonen_Train = new Kohonen_Training_Data();
    // number of tests is variable
    private Kohonen_Test_Data[] Kohonen_Test;
    private int number_of_Kohonen_tests;
    
    /**
     * 竞争层拓扑结构
     */
    public Kohonen_Topology Kohonen_Design = new Kohonen_Topology();
    
    /**
     * 构造神经网络拓扑结构，建立权值数组
     */
    public void construct_Kohonen_network(int dimCount) {
        Kohonen_Design.establish_Kohonen_topology(dimCount);
    }
    
    /**
     * 加载，归一化训练集数组
     */
    private void initialize_Kohonen_training_storage_array(int KN, List<List<Double>> optionData) {
        Kohonen_Train.acquire_net_info(Kohonen_Design.dimensions_of_signal);
        Kohonen_Train.request_Kohonen_data(KN, optionData);
    }
    
    private void establish_Kohonen_test_battery_size() {
        number_of_Kohonen_tests = 1;
        // create testing array
        Kohonen_Test = new Kohonen_Test_Data[number_of_Kohonen_tests];
        for (int t = 0; t < number_of_Kohonen_tests; t++) {
            Kohonen_Test[t] = new Kohonen_Test_Data();
            Kohonen_Test[t].acquire_net_info(Kohonen_Design.dimensions_of_signal);
        }
    }
    
    /**
     * 训练神经网络
     *
     * @param KOHN
     * @throws IOException
     */
    private void train_Kohonen_network(int KOHN) {
        int dim, ep, k_epochs, pattern, knodes, dolock;
        //最大学习率
        Kohonen_Design.max_learning_rate = 1.0;
        //最小学习率
        Kohonen_Design.min_learning_rate = 0.01;
        //训练次数
        k_epochs = 10000;
        ep = 0;
        dolock = 0;
        do {
            // 样本数量
            for (pattern = 0; pattern < Kohonen_Train.sample_number; pattern++) {
                // 聚簇个数
                for (knodes = 0; knodes < Kohonen_Design.maximum_number_of_clusters; knodes++) {
                    // 样本维度
                    for (dim = 0; dim < Kohonen_Design.dimensions_of_signal; dim++) {
                        // 取出每个样本中的归一化维度数据放到竞争层中每个簇的输入数组中
                        Kohonen_Design.node_in_cluster_layer[knodes].input_value[dim] = Kohonen_Train.number_of_samples[pattern].data_in_sample[dim];
                    }
                }
                Kohonen_Design.kluster_nodes_compete_for_activation();
                Kohonen_Design.update_the_Kohonen_network2(ep, k_epochs);
            }
            //修改学习率的部分
            update_learning(ep, k_epochs);
            if (ep % 100 == 0) {
                log.debug("第 " + (ep + 1) + " 次训练已完成" + "---学习率：" + Kohonen_Design.interim_learning_rate);
            }
            
            // 学习率等于0或者训练次数到达
            if ((ep == k_epochs - 1)
                    || (Kohonen_Design.interim_learning_rate < Kohonen_Design.min_learning_rate)) {
                dolock = 1;
            }
            ep = ep + 1;
        } while (dolock <= 0);
        //Kohonen_Design.saveMinDistances();
        Kohonen_Train.delete_signal_array();
    }
    
    /**
     * 原本学习率下降函数
     *
     * @param epoch_count 当前的训练次数
     * @param max_epochs  最大的训练次数
     */
    private void update_learning(int epoch_count, int max_epochs) {
        int maxepoch;
        if (max_epochs == 1) {
            maxepoch = 1;
        } else {
            maxepoch = max_epochs - 1;
        }
        // 学习率调整
        double adjusted_learning_rate = Kohonen_Design.max_learning_rate
                - (((Kohonen_Design.max_learning_rate - Kohonen_Design.min_learning_rate) / maxepoch) * epoch_count);
        Kohonen_Design.interim_learning_rate = adjusted_learning_rate
                * Kohonen_Design.interim_learning_rate;
    }
    
    /**
     * 单调下降
     *
     * @param epoch_count 当前的训练次数
     * @param max_epochs  最大的训练次数
     */
    private void update_learning1(int epoch_count, int max_epochs) {
        int maxepoch;
        if (max_epochs == 1) {
            maxepoch = 1;
        } else {
            maxepoch = max_epochs - 1;
        }
        // 学习率调整
        double adjusted_learning_rate = Kohonen_Design.max_learning_rate
                - (((Kohonen_Design.max_learning_rate - Kohonen_Design.min_learning_rate) / maxepoch) * epoch_count);
        // 单调下降
        Kohonen_Design.interim_learning_rate = adjusted_learning_rate * 1;
    }
    
    private void update_learning2(int epoch_count, int max_epochs) {
        int maxepoch;
        if (max_epochs == 1) {
            maxepoch = 1;
        } else {
            maxepoch = max_epochs - 1;
        }
        // 学习率调整
        double adjusted_learning_rate = Kohonen_Design.max_learning_rate
                - (((Kohonen_Design.max_learning_rate - Kohonen_Design.min_learning_rate) / Math
                .log(maxepoch + 1)) * (Math.log(epoch_count + 1)));
        // 单调下降
        Kohonen_Design.interim_learning_rate = adjusted_learning_rate * 1;
    }
    
    private void test_Kohonen_network(int KNET,String pathName, List<List<Double>> optionData) {
        try{
            StringBuilder sb = new StringBuilder();
            int tnet, dim, pattern, knodes;
            tnet = KNET;
            double realvalue;
            File file = new File("D:\\SOM\\"+pathName+".txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String tableName = br.readLine();
            String[] dims = br.readLine().split("\t");
            String[] dimTmp =new String[optionData.size()];
            for(int i=0;i<dimTmp.length;i++){
                dimTmp[i]=br.readLine();
            }
            br.close();
            sb.append(tableName+"\n");
            for (int i=0;i<dims.length;i++){
                sb.append(dims[i]+"\t");
            }
            sb.append("\n");
            for (int ktest = 0; ktest < number_of_Kohonen_tests; ktest++) {
                //加载测试集
                Kohonen_Test[ktest].request_Kohonen_data(tnet,optionData);
                //使用模型
                for (pattern = 0; pattern < Kohonen_Test[ktest].sample_number; pattern++) {
                    for (knodes = 0; knodes < Kohonen_Design.maximum_number_of_clusters; knodes++) {
                        for (dim = 0; dim < Kohonen_Design.dimensions_of_signal; dim++) {
                            Kohonen_Design.node_in_cluster_layer[knodes].input_value[dim] = Kohonen_Test[ktest].number_of_samples[pattern].data_in_sample[dim];
                        }
                    }
                    Kohonen_Design.kluster_nodes_compete_for_activation();
            
                    //读取第二行数据，hash 排序，读取后续数据，清空，重新插入
                   sb.append(dimTmp[pattern]+"\t");
                   for(int i=0;i<optionData.get(pattern).size();i++){
                       sb.append(optionData.get(pattern).get(i)+"\t");
                   }
                    sb.append(Kohonen_Design.node_in_cluster_layer[Kohonen_Design.kluster_champ].row + "\t"
                            + Kohonen_Design.node_in_cluster_layer[Kohonen_Design.kluster_champ].col+"\t");
                    sb.append("\n");
                }
                Kohonen_Test[ktest].delete_signal_array();
            }
            TxtUtil.writeStr(pathName,sb.toString(),false);
        }catch (Exception e){
            e.printStackTrace();
        }
       
    }
    
    /**
     * 选择神经网络训练|测试
     *
     * @param tt 建立神经网络的数量
     */
    public void network_training_testing(int tt, List<List<Double>> optionData,String pathName) {
        log.info("2.1、加载、归一化训练集");
        initialize_Kohonen_training_storage_array(tt, optionData);
        log.info("2.2、训练SOM模型");
        train_Kohonen_network(tt);
        log.info("2.3、使用SOM模型");
        establish_Kohonen_test_battery_size();
        test_Kohonen_network(tt,pathName,optionData);
    }
}
