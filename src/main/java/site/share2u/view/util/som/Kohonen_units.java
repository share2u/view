package site.share2u.view.util.som;

import org.apache.log4j.Logger;

import java.util.Random;


/**
 * 描述：神经元
 *
 * @author CWM
 * @date 2017-6-4 下午5:19:27
 */
public class Kohonen_units {
    private Logger log = Logger.getLogger(Kohonen_units.class);
    
    /**
     * 输入样本的维数
     */
    int number_of_inputs;
    /**
     * 输出簇个数.
     */
    int number_of_outputs;
    
    double[] input_weight_vector;
    double[] input_value;
    double[] output_value;
    int row;
    int col;
    
    double transfer_function_width;                 // RBFN
    double Gaussian_transfer_output;                // RBFN
    
    Kohonen_units() {
        number_of_outputs = 1;
    }
    
    /**
     * 建立输入输出数组
     */
    void establish_input_output_arrays() {
        input_value = new double[number_of_inputs];
        output_value = new double[number_of_outputs];
    }
    
    /**
     * 初始化簇的位置
     *
     * @param row
     * @param col
     */
    void establish_location(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * 建立输入权值数组
     */
    void establish_input_weight_vector_array() {
        input_weight_vector = new double[number_of_inputs];
    }
    
    /**
     * 随机初始化权值数组
     */
    void initialize_inputs_and_weights() {
        Random random = new Random();
        for (int k = 0; k < number_of_inputs; k++) {
            input_weight_vector[k] = random.nextDouble();
        }
    }
    
    /**
     * 计算一个样本的所有输入向量（维度）和权值向量相似度
     * 欧式距离
     */
    void calculate_sum_square_Euclidean_distance() {
        //TODO: 距离公式
        double sumsquare;
        double ss1;
        int ci;
        output_value[0] = 0.0;
        for (int k = 0; k < number_of_inputs; k++) {
            ci = k;
            //输入权重-输入值的平方
            sumsquare = Math.pow(Math.abs(input_weight_vector[ci] - input_value[ci]), 2.0);
            //几个维度相似度的平方和
            output_value[0] += sumsquare;
        }
        ss1 = output_value[0];
        //相似度
        output_value[0] = Math.sqrt(Math.abs(ss1));
    }
    
    /**
     * 更新权值。根据更新权值公式
     */
    //TODO:权重更新公式
    void update_the_weights(double learning_rate) {
        for (int k = 0; k < number_of_inputs; k++) {
            input_weight_vector[k] = input_weight_vector[k] + (learning_rate * (input_value[k] - input_weight_vector[k]));
        }
    }
    
    /**
     * 根据权值公式，有领域元素
     *
     * @param learning_rate
     * @param linyu
     */
    void update_the_weights1(double learning_rate, double linyu) {
        for (int k = 0; k < number_of_inputs; k++) {
            input_weight_vector[k] = input_weight_vector[k] + (learning_rate * linyu * (input_value[k] - input_weight_vector[k]));
        }
    }
    
    //RBFN //
    void execute_Gaussian_transfer_function() {
        double transfer_ratio = (-1.0) * Math.pow((output_value[0] / transfer_function_width), 2.0);
        Gaussian_transfer_output = Math.exp(transfer_ratio);
    }
}
