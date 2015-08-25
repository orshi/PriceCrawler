package PriceCrawler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.ws.http.HTTPException;

import org.apache.commons.codec.language.bm.Rule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownloadFile {

	public String GetFileNameByUrl(String url, String contentType) {
		// Remove http://
		url = url.substring(7);
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		} else {
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "."
					+ contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	// Save downloaded file to local
	public void SaveToLocal(String stringData, String filePath) {
		byte[] data = stringData.getBytes();
		try {
			DataOutputStream outputStream = new DataOutputStream(
					new FileOutputStream(new File(filePath)));
			for (int i = 0; i < data.length; i++) {
				outputStream.write(data[i]);
			}
			outputStream.flush();
			outputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String DownloadFile(String url) {
		String filePathString = null;
		HttpGet get = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(5000).build();
		get.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = httpclient.execute(get);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method fail:" + statusLine);
				filePathString = null;
			}

			// Get file content
			InputStream responseBodyContent = response.getEntity().getContent();
			StringBuilder sbBuilder = new StringBuilder();

			// Utf-8 reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					responseBodyContent, "UTF-8"));

			int i = 0;
			// while ((i = responseBodyContent.read()) != -1) {
			while ((i = reader.read()) != -1) {
				sbBuilder.append((char) i);
			}
			String contentString = sbBuilder.toString();

			// Get file path
			filePathString = "/home/oxie/Downloads/Clawer/"
					+ GetFileNameByUrl(url,
							response.getFirstHeader("Content-Type").toString());
			System.out.print("file path" + filePathString + "/r/n");

			// Save file to local path.
			SaveToLocal(contentString, filePathString);
		} catch (HTTPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return filePathString;
	}

}
