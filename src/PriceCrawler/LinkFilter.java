package PriceCrawler;

public interface LinkFilter {
	public boolean accept(String url);
}