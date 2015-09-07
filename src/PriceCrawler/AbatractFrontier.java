package PriceCrawler;

import java.io.File;
import java.io.FileNotFoundException;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.dbi.EnvConfigObserver;

public abstract class AbatractFrontier {

	private Environment env;
	private static final String CLASS_CATLOG = "java";
	protected StoredClassCatalog javaCatalog;
	protected Database catalogDatabase;
	protected Database database;

	public AbatractFrontier(String homeDirectory) throws DatabaseException,
			FileNotFoundException {

		System.out.println("Opening environment in:" + homeDirectory);
		EnvironmentConfig envconfig = new EnvironmentConfig();

		// 事务支持
		
		envconfig.setTransactional(true);
		envconfig.setAllowCreate(true);

		// open env
		env = new Environment(new File(homeDirectory), envconfig);

		// Data base config
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);

		// open database
		catalogDatabase = env.openDatabase(null, CLASS_CATLOG, dbConfig);
		javaCatalog = new StoredClassCatalog(catalogDatabase);

		DatabaseConfig dbconfig0 = new DatabaseConfig();
		dbconfig0.setTransactional(true);
		dbconfig0.setAllowCreate(true);

		// open database
		database = env.openDatabase(null, "URL", dbconfig0);

	}

	public void close() throws DatabaseException {
		database.close();
		javaCatalog.close();
		env.close();
	}

	protected abstract void put(Object key, Object value);

	protected abstract Object get(Object key);

	protected abstract Object delete(Object key);
}
