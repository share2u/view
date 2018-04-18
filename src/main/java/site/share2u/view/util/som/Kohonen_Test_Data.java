package site.share2u.view.util.som;

public class Kohonen_Test_Data extends Kohonen_Training_Data {
    
    public String resultsname;
    
    void request_Kohonen_data(int net_no) {
        specify_signal_sample_size();
        normalize_data_in_array();
    }
}
