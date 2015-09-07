package PriceCrawler;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PriceWritter {

	
	public static boolean WritePrice(String name, String id, String price, String url) {
		try {
			String driverString = "com.mysql.jdbc.driver";
			String urlString = "jdbc:mysql://localhost/PriceCrawler";
			String userString = "root";
			String password = "123456";

			Class.forName(driverString);
			Connection connection = DriverManager.getConnection(urlString,
					userString, password);

			if (!connection.isClosed())
				System.out.print("Connected!");

			Statement statement = connection.createStatement();

			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String dateTimeNowString = format.format(new Date());

			String commandString = "insert into PriceInfo values(" + name + ","
					+ id + "," + price + "," + dateTimeNowString + "," + url
					+ ")";
			boolean result = statement.execute(commandString);

			return result;

		} catch (Exception e) {
			return false;
		}
	}

}
