package site.share2u.view.util.som;

import java.util.List;

public class Kohonen_Test_Data extends Kohonen_Training_Data {
    
    public String resultsname;
    @Override
    void request_Kohonen_data(int net_no, List<List<Double>> optionData) {
        specify_signal_sample_size(optionData);
        normalize_data_in_array();
    }
}
