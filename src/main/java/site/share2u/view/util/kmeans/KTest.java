package site.share2u.view.util.kmeans;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class KTest {
	public void test1() throws Exception {
		// 初始化一个Kmean对象，将k置为10
		Kmeans k = new Kmeans(3);
		ArrayList<float[]> dataSet = new ArrayList<float[]>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("e:/iris.data")));
		String s =null;
		String[] split;
		while((s=bufferedReader.readLine()) != null){
			split = s.split(",");
			float[] f =new float[split.length];
			for (int i = 0; i < split.length-1; i++) {
				f[i]=Float.parseFloat(split[i]);
			}
			dataSet.add(f);
		}
		
		// 设置原始数据集
		k.setDataSet(dataSet);
		// 执行算法
		k.execute();
		// 得到聚类结果
		ArrayList<ArrayList<float[]>> cluster = k.getCluster();
		
		
		// 查看结果
		for (int i = 0; i < cluster.size(); i++) {
			k.printDataArray(cluster.get(i), "cluster[" + i + "]");
		}

	}

}
