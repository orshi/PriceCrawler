package PriceCrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

import org.apache.http.HttpVersion;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.util.NodeList;

//Get price pattern;http://p.3.cn/prices/mgets?skuIds=J_'+skuid+'&type=1, e.g. http://p.3.cn/prices/mgets?skuIds=J_1529841389&type=1,

public class PriceAnalyzer {

	public static String GetPrice(String commodityUrl) throws Exception {

		String returnResult=null;
		// Get price related information
		String skuId = commodityUrl.substring(
				commodityUrl.indexOf("item.jd.com/") + 12,
				commodityUrl.indexOf(".html"));

		String priceResult = null;

		String priceUrl = "http://p.3.cn/prices/mgets?skuIds=J_" + skuId
				+ "&type=1";

		try {

			String line = null;
			StringBuilder sb = new StringBuilder();
			URL localURL = new URL(priceUrl);

			URLConnection connection = localURL.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			if (httpURLConnection.getResponseCode() > 300) {
				throw new Exception("Error response from server,response code"
						+ httpURLConnection.getResponseCode());
			} else {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								httpURLConnection.getInputStream()));

				line = reader.readLine();

				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}

				priceResult = sb.toString();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		String jsonData = priceResult.replace("[", "").replace("]", "");
		JSONObject jsonObject = JSONObject.fromObject(jsonData);

		String price = (String) jsonObject.get("p");
		String id = (String) jsonObject.get("id");

		// Get commodity name
		Parser parser = new Parser(commodityUrl);
		parser.setEncoding("utf-8");
		NodeFilter headFilter = new NodeClassFilter(HeadingTag.class);

		TagNameFilter nameFilter = new TagNameFilter("h1");

		Node[] headings = parser.extractAllNodesThatMatch(nameFilter)
				.toNodeArray();

		String commodityName = headings[0].toPlainTextString();

		return "Price:"+price+"id:"+id+"Name:"+commodityName;
	}

}
