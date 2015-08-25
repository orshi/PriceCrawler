package PriceCrawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class LinkedQueue {

	private static Set<Object> visitedUrl = new HashSet<Object>();
	private static PriorityQueue unVisited = new PriorityQueue();

	public static void AddVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	public static void RemoveVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	public static Object UnVisitedUrlDeque() {
		return unVisited.poll();
	}

	public static void UnVisitedUrlEnque(String url) {
		if ((!visitedUrl.contains(url)) && url != null
				&& (!unVisited.contains(url))) {
			unVisited.add(url);
		}
	}

	public static int GetVisitedNumber() {
		return visitedUrl.size();
	}

	public static int GetUnvisitedNumber() {
		return unVisited.size();
	}

	public static boolean IsVisiedEmpty() {
		return visitedUrl.size() <= 0;
	}

	public static boolean IsUnvisiedEmpty() {
		return unVisited.isEmpty();
	}

}
