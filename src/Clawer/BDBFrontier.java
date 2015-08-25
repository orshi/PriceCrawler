package Clawer;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.rep.stream.Protocol.Entry;

public class BDBFrontier extends AbatractFrontier implements Frontier {

	private StoredMap pendingUrisDB = null;

	public BDBFrontier(String homeDirectory) throws DatabaseException,
			FileNotFoundException {
		super(homeDirectory);

		EntryBinding valueBinding = new SerialBinding(javaCatalog,
				CrawlUrl.class);
		EntryBinding keyBinding = new SerialBinding(javaCatalog, String.class);

		pendingUrisDB = new StoredMap(database, keyBinding, valueBinding, true);
	}

	// Get next record
	public CrawlUrl GetNext() throws Exception {
		CrawlUrl result = null;
		if (!pendingUrisDB.isEmpty()) {
			Set entries = pendingUrisDB.entrySet();
			System.out.println(entries);
			Map.Entry<String, CrawlUrl> entry = (Map.Entry<String, CrawlUrl>) pendingUrisDB
					.entrySet().iterator().next();
			result = entry.getValue();
			delete(entry.getKey());
		}

		return result;
	}

	public boolean PutUrl(CrawlUrl url) throws Exception
	{
		put(url.oriUrl, url);
		return true;
	}
	protected void put(Object key, Object value) {

		pendingUrisDB.put(key, value);
	}

	protected Object get(Object key) {

		return pendingUrisDB.get(key);
	}

	protected Object delete(Object key) {
		return pendingUrisDB.remove(key);
	}
}
