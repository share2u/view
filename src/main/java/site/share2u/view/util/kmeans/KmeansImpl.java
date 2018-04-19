package site.share2u.view.util.kmeans;

import java.util.List;

public class KmeansImpl extends KMeansClustering<XYbean>{

	@Override
	public double similarScore(XYbean o1, XYbean o2) {
	    double distance = Math.sqrt((o1.getX() - o2.getX()) * (o1.getX() - o2.getX()) + (o1.getY() - o2.getY()) * (o1.getY() - o2.getY()));
	    return distance * -1;
	}
	@Override
	public boolean equals(XYbean o1, XYbean o2) {
	    return o1.getX() == o2.getX() && o1.getY() == o2.getY();
	}

	@Override
	public XYbean getCenterT(List<XYbean> list) {
	    int x = 0;
	    int y = 0;
	    try {
	        for (XYbean xy : list) {
	            x += xy.getX();
	            y += xy.getY();
	        }
	        x = x / list.size();
	        y = y / list.size();
	    } catch(Exception e) {
	    
	    }
	    return new XYbean(x, y);
	}
}
