package PriceCrawler;

public class SimpleHash {
	private int cap;
	private int seed;

	public SimpleHash(int cap, int seed) {
		this.cap = cap;
		this.seed = seed;
	}

	public int Hash(String value) {
		int result = 0;
		int len = value.length();
		for (int i = 0; i < len; i++) {

			// Designed hash logic,
			result = seed * result + value.charAt(i);

		}

		return (cap - 1) & result;
	}
}
