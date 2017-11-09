package site.share2u.view.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 继承的这个类是干什么用的？
 * @author CWM 2017年11月8日
 * 
 */
public class ThreadLocalRountingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getDatabaseContext();
	}

}
