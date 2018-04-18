package site.share2u.view.util.som;

import java.io.*;
import java.util.ArrayList;

/**
 * 描述：拓扑结构
 */
public class Kohonen_Topology {
    
    int kluster_champ;//激活神经元
    int dimensions_of_signal;//维度
    int maximum_number_of_clusters;//神经元个数
    double max_learning_rate;//最大学习率
    double min_learning_rate;//最小学习率
    double interim_learning_rate;
    ArrayList<Double> minDistances;
    Kohonen_units[] node_in_cluster_layer;
    
    Kohonen_Topology() {
        interim_learning_rate = 1.0;
        minDistances = new ArrayList<>();
    }
    
    /**
     * 建立神经网络拓扑结构
     *
     * @param netuse 样本维度
     */
    void establish_Kohonen_topology(int netuse) {
        dimensions_of_signal = netuse;
        //TODO:样本二维大小 拓扑结构
        int x = 10;
        int y = 10;
        //簇个数
        maximum_number_of_clusters = x * y;
        // 建立SOM竞争层，将这个竞争层改为两维的，使得最后结果分布在二维平面上
        //行
        int row = 0;
        //列
        int col = 0;
        node_in_cluster_layer = new Kohonen_units[maximum_number_of_clusters];
        for (int c = 0; c < maximum_number_of_clusters; c++, col++) {
            if (c % y == 0.0 && c != 0) {
                row++;
                col = 0;
            }
            node_in_cluster_layer[c] = new Kohonen_units();
            node_in_cluster_layer[c].establish_location(row, col);
            node_in_cluster_layer[c].number_of_inputs = dimensions_of_signal;
            node_in_cluster_layer[c].establish_input_output_arrays();
            node_in_cluster_layer[c].establish_input_weight_vector_array();
            node_in_cluster_layer[c].initialize_inputs_and_weights();
        }
        
    }
    
    void upload_network() {
        String getname;
        FileReader get_ptr;
        int netid = 0;
        int nodes, dim;
        int dolock = 0;
        
        do {
            System.out.println();
            System.out.println("Please enter the name of the file which holds the Kohonen Map");
            getname = MyInput.readString();
            System.out.println();
            
            try {
                get_ptr = new FileReader(getname);
                BufferedReader br = new BufferedReader(get_ptr);
                String string;
                
                while ((string = br.readLine()) != null) {
                    if (string.trim().equalsIgnoreCase("")) {
                        continue;
                    }
                    netid = Integer.parseInt(string);
                    break;
                }
                
                if (netid == 3) {
                    dolock = 1;
                    br.close();
                    get_ptr.close();
                } else {
                    System.out.println("Error** file contents do not match Kohonen specifications");
                    System.out.println("try again");
                    br.close();
                    get_ptr.close();
                }
            } catch (IOException exc) {
                String str = exc.toString();
                System.out.println(str);
            }
        } while (dolock <= 0);
        
        try {
            get_ptr = new FileReader(getname);
            BufferedReader br = new BufferedReader(get_ptr);
            String string;
            int row = 0;
            
            while ((string = br.readLine()) != null) {
                if (string.trim().equalsIgnoreCase("")) {
                    continue;
                }
                if (!string.trim().equalsIgnoreCase("")) {
                    row++;
                }
                if (row == 2) {
                    dimensions_of_signal = Integer.parseInt(string);
                }
                if (row == 3) {
                    maximum_number_of_clusters = Integer.parseInt(string);
                    break;
                }
            }
            
            node_in_cluster_layer = new Kohonen_units[maximum_number_of_clusters];
            for (nodes = 0; nodes < maximum_number_of_clusters; nodes++) {
                node_in_cluster_layer[nodes] = new Kohonen_units();
                
                node_in_cluster_layer[nodes].number_of_inputs = dimensions_of_signal;
                node_in_cluster_layer[nodes].establish_input_output_arrays();
                node_in_cluster_layer[nodes].establish_input_weight_vector_array();
            }
            
            while ((string = br.readLine()) != null) {
                if (!string.trim().equalsIgnoreCase("")) {
                    row++;
                }
                int indexOfSpace = 0;
                int tempIndexOfSpace = 0;
                if (row >= 4) {
                    for (nodes = 0; nodes < maximum_number_of_clusters; nodes++) {
                        for (dim = 0; dim < dimensions_of_signal; dim++) {
                            if (dim == 0) {
                                indexOfSpace = string.indexOf(" ");
                                node_in_cluster_layer[nodes].input_weight_vector[dim] =
                                        Double.parseDouble(string.trim().substring(0, indexOfSpace).trim());
                                tempIndexOfSpace = indexOfSpace;
                            } else {
                                indexOfSpace = string.indexOf(" ", indexOfSpace + 1);
                                node_in_cluster_layer[nodes].input_weight_vector[dim] =
                                        Double.parseDouble(string.trim().substring(tempIndexOfSpace, indexOfSpace).trim());
                                tempIndexOfSpace = indexOfSpace;
                            }
                            
                        }
                    }
                }
            }
            
            br.close();
            get_ptr.close();
        } catch (IOException exc) {
            String str = exc.toString();
            System.out.println(str);
        }
    }
    
    /**
     * 神经元节点竞争，计算每个簇和权值向量的相似度，找到最大的相似度（距离最近）的簇，
     */
    void kluster_nodes_compete_for_activation() {
        double minimum_distance = 0;
        for (int m = 0; m < maximum_number_of_clusters; m++) {
            node_in_cluster_layer[m].calculate_sum_square_Euclidean_distance();
            if (m == 0) {
                kluster_champ = m;
                minimum_distance = node_in_cluster_layer[m].output_value[0];
            } else {
                if (node_in_cluster_layer[m].output_value[0] < minimum_distance) {
                    kluster_champ = m;
                    minimum_distance = node_in_cluster_layer[m].output_value[0];
                }
            }
        }
        //将最大相似度的最近距离保存起来
      //  minDistances.add(minimum_distance);
    }
    
    /**
     * 更新神经网络，包括更新学习率，更新权重向量
     */
    //TODO:学习率的调整放在哪个部分？而且跟距离不受影响
    void update_the_Kohonen_network(int epoch_count, int max_epochs) {
        int maxepoch;
        if (max_epochs == 1) {
            maxepoch = 1;
        } else {
            maxepoch = max_epochs - 1;
        }
        //学习率调整
        double adjusted_learning_rate = max_learning_rate - (((max_learning_rate - min_learning_rate) / maxepoch) * epoch_count);
        interim_learning_rate = adjusted_learning_rate * interim_learning_rate;
        System.out.println("学习率：" + interim_learning_rate);
        node_in_cluster_layer[kluster_champ].update_the_weights(interim_learning_rate);
    }
    
    void update_the_Kohonen_network1() {
        
        //获胜节点更新
        node_in_cluster_layer[kluster_champ].update_the_weights(interim_learning_rate);
    }
    
    /**
     * 更新权值，带优胜领域
     */
    void update_the_Kohonen_network2(int epoch_count, int max_epochs) {
        //获胜节点更新
        for (int i = 0; i < node_in_cluster_layer.length; i++) {
            node_in_cluster_layer[i].update_the_weights1(interim_learning_rate, linyu(i, kluster_champ, epoch_count, max_epochs));
        }
    }
    
    /**
     * 计算领域通过高斯函数
     *
     * @param epoch_count
     * @param max_epochs
     * @return
     */
    private double linyu(int i, int j, int epoch_count, int max_epochs) {
        double dij = distanceij(i, j);
        int c = (int) Math.sqrt(node_in_cluster_layer.length);
        double kuandu = (c) * Math.pow(0.5 / (c / 2), epoch_count / max_epochs);
        return Math.pow(Math.E, -Math.pow(dij, 2) / (2 * (kuandu)));
    }
    
    private double distanceij(int i, int j) {
        int c = (int) Math.sqrt(node_in_cluster_layer.length);
        int x1 = i / c;
        int y1 = i % c;
        int x2 = j / c;
        int y2 = j % c;
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
    
    public void savenet() {
        String savename;
        FileWriter save_ptr;
        StringBuffer s = new StringBuffer("");
        int node, dim;
        savename = "E:\\model\\model.txt";
        //TODO :保存模型
        try {
            save_ptr = new FileWriter(savename);
            // network identifier number
            s.append(3).append("\r\n");
            s.append(dimensions_of_signal).append("\r\n");
            s.append(maximum_number_of_clusters).append("\r\n");
            for (node = 0; node < maximum_number_of_clusters; node++) {
                for (dim = 0; dim < dimensions_of_signal; dim++) {
                    //权值向量
                    s.append(node_in_cluster_layer[node].row + "-" + node_in_cluster_layer[node].col + "-" + node_in_cluster_layer[node].input_weight_vector[dim]).
                            append(" ");
                }
                s.append("\r\n");
            }
            
            save_ptr.write(s.toString());
            save_ptr.close();
        } catch (IOException exc) {
            String str = exc.toString();
            System.out.println(str);
        }
    }
    
    /**
     * 最大相似度的最近距离
     */
    public void saveMinDistances() {
        try {
            FileWriter fileWriter = new FileWriter(new File("distances.txt"));
            ArrayList<Double> minmin = new ArrayList<Double>();
            double sum = 0.0;
            for (int i = 0; i < minDistances.size(); i++) {
                if (i == 0) {
                    continue;
                }
                if (i % 150 == 0) {
                    minmin.add(sum / 150);
                    sum = 0.0;
                }
                sum += minDistances.get(i).doubleValue();
                
            }
            fileWriter.write(minmin.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}