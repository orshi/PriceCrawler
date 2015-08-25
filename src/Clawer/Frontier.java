package Clawer;

public interface Frontier {
	public CrawlUrl GetNext()throws Exception;
	public boolean PutUrl(CrawlUrl url)throws Exception;

}
