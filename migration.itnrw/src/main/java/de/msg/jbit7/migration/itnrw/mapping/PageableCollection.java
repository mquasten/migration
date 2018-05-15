package de.msg.jbit7.migration.itnrw.mapping;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

public class PageableCollection<T> {
	
	private final Collection<T> dataCollection; 
	
	private final int pageSize;
	public PageableCollection(final Collection<T> dataCollection, final int pageSize) {
		Assert.notNull(dataCollection, "DataCollection is required.");
		Assert.isTrue(pageSize > 0 , "PageSize should be > 0.");
		this.dataCollection = dataCollection;
		this.pageSize=pageSize;
	}
	
	
	public final int  maxPages() {
		return (int) Math.ceil((double) dataCollection.size() / pageSize);
	}
	

	public final Collection<T> page(final int page) {
		return Collections.unmodifiableList(dataCollection.stream().skip( (page *pageSize )).limit(pageSize).collect(Collectors.toList()));
	}
}
