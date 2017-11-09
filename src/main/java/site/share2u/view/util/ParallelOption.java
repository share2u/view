package site.share2u.view.util;

import java.util.List;

import com.github.abel533.echarts.axis.ParallelAxis;
import com.github.abel533.echarts.json.GsonOption;

public class ParallelOption extends GsonOption{
	private List<ParallelAxis> parallelAxis;

	public List<ParallelAxis> getParallelAxis() {
		return parallelAxis;
	}

	public void setParallelAxis(List<ParallelAxis> parallelAxis) {
		this.parallelAxis = parallelAxis;
	}

	
}
