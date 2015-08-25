package Clawer;

import java.io.Serializable;
import java.util.Date;

import com.sleepycat.je.utilint.Timestamp;
import com.sun.jna.platform.win32.OaIdl.DATE;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

public class CrawlUrl implements Serializable {

	private static final long serialVersionUID = 7964542334889072037L;

	public CrawlUrl(){
		
	}
	
	public String oriUrl;
	public String url;
	public int urlNum;
	public int statusCode;
	public int hitNum;
	public String charSet;
	public String abstractText;
	public String author;
	public int weight;
	public String description;
	public int fileSize;
	public Timestamp timestamp;
	public Date timeToLive;
	public String title;
	public String type;
	public String[]urlReferences;
	public int layer;
	
	
	

}
