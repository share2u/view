package site.share2u.view.util.som;

import org.apache.log4j.Logger;
import site.share2u.view.pojo.PageData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Kohonen_Training_Data {
    Logger log = Logger.getLogger(Kohonen_Training_Data.class);
    
    /**
     * 样本中包含的维数
     */
    int signal_dimensions;
    
    double[] max_output_value;
    double[] min_output_value;
    /**
     * 测试集输出的维度
     */
    int nodes_in_output_layer;
    
    //Pointer to the array containing signals
    Sample_data[] number_of_samples;
    /**
     * Number of signals in training set，训练集中的样本数量
     */
    int sample_number;
    
    String filename;
    List<List<Double>> optionData;
    
    /**
     * 获取当前网络的输入维度
     */
    void acquire_net_info(int signal) {
        signal_dimensions = signal;
    }
    
    /**
     * 归一化数组中的数据
     */
    void normalize_data_in_array() {
        int i, j;
        double min = 0, max = 0;
        double sum = 0;
        max_output_value = new double[signal_dimensions];
        min_output_value = new double[signal_dimensions];
        for (j = 0; j < signal_dimensions; j++) {
            // 找出每一个维度的最大和最小值，
            for (i = 0; i < sample_number; i++) {
                if (i == 0) {
                    sum = number_of_samples[i].data_in_sample[j];
                    max = number_of_samples[i].data_in_sample[j];
                    min = number_of_samples[i].data_in_sample[j];
                } else {
                    sum += number_of_samples[i].data_in_sample[j];
                    if (number_of_samples[i].data_in_sample[j] < min) {
                        min = number_of_samples[i].data_in_sample[j];
                    }
                    
                    if (number_of_samples[i].data_in_sample[j] > max) {
                        max = number_of_samples[i].data_in_sample[j];
                    }
                }
            }
            
            // 归一化信号中的每一个维度
            max_output_value[j] = max;
            min_output_value[j] = min;
            //均值
            double mean = sum / sample_number;
            //方差
            double s2 = 0;
            for (i = 0; i < sample_number; i++) {
                s2 += Math.pow((number_of_samples[i].data_in_sample[j]) - mean, 2.0) / sample_number;
            }
            //标准差
            double s = Math.sqrt(s2);
            for (i = 0; i < sample_number; i++) {
                // number_of_samples[i].data_in_sample[j] = (number_of_samples[i].data_in_sample[j] - min)/(max - min);
                //加权欧式距离
                number_of_samples[i].data_in_sample[j] = (number_of_samples[i].data_in_sample[j] - mean) / s;
                // number_of_samples[i].data_in_sample[j] = weightw[j] * number_of_samples[i].data_in_sample[j];
            }
        }
        
    }
    
    /**
     * 明确样本数量，加载数据
     */
    
    void specify_signal_sample_size() {
        determine_sample_number();
        load_data_into_array();
    }
    
    /**
     * 取得数据，明确样本数量，归一化样本数据
     */
    void request_Kohonen_data(int net_no, List<List<Double>> optionData) {
        this.optionData = optionData;
        specify_signal_sample_size();
        //归一化数据集
        normalize_data_in_array();
    }
    
    /**
     * 计算出全部的样本数量
     */
    void determine_sample_number() {
        sample_number = optionData.size();
        log.info("样本数量是：" + sample_number);
    }
    
    /**
     * 加载文件中的数据到一个二维数组中
     */
    void load_data_into_array() {
        //创建一个数组来包含样本的数量
        number_of_samples = new Sample_data[sample_number];
        //创建一个数组来容纳每一个样本的维度
        for (int i = 0; i < sample_number; i++) {
            number_of_samples[i] = new Sample_data();
            number_of_samples[i].data_in_sample = new double[signal_dimensions + nodes_in_output_layer];
        }
        //TODO dimensions 有用没
        int dimensions = signal_dimensions + nodes_in_output_layer;
        for (int i = 0; i < optionData.size(); i++) {
            for (int j = 0; j < optionData.get(i).size(); j++) {
                number_of_samples[i].data_in_sample[j] = optionData.get(i).get(j);
            }
        }
    }
    
    public void delete_signal_array() {
        number_of_samples = null;
    }
}
