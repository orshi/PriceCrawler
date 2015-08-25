package PriceCrawler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import javax.management.loading.PrivateClassLoader;
import javax.xml.ws.http.HTTPException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sun.jna.platform.win32.WinDef.INT_PTR;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Clawer {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		String tempString = MD5.GetMD5("test".getBytes());
		Clawer clawer = new Clawer();
		clawer.Clawing(new String[] { "http://www.ifeng.com/" });

	}

	private void InitClawerWithSeed(String[] seeds) {
		for (int i = 0; i < seeds.length; i++) {
			LinkedQueue.UnVisitedUrlEnque(seeds[i]);
		}
	}

	public void Clawing(String[] seeds) {
		LinkFilter filter = new LinkFilter() {

			// Only accept links in youku
			public boolean accept(String url) {
				if (url.contains(".ifeng.com"))
					return true;
				else {
					return false;
				}
			}
		};

		InitClawerWithSeed(seeds);
		while (!LinkedQueue.IsUnvisiedEmpty()
				&& LinkedQueue.GetVisitedNumber() < 1000) {
			String urlString = (String) LinkedQueue.UnVisitedUrlDeque();

			DownloadFile downloadFile = new DownloadFile();
			downloadFile.DownloadFile(urlString);
			LinkedQueue.AddVisitedUrl(urlString);

			Set<String> linksSet = HTMLParserTool.ExtractLinks(urlString,
					filter);

			for (String UnvisitString : linksSet) {
				LinkedQueue.UnVisitedUrlEnque(UnvisitString);

			}
		}
	}

	// public static class RetrivePage{
	//
	// private static CloseableHttpClient httpclient =
	// HttpClients.createDefault();
	//
	// public static Boolean DownLoadPage(String path)throws HTTPException
	// {
	// InputStream inputStream=null;
	// OutputStream outputStream=null;
	// HttpPost post =new HttpPost(path);
	// // ArrayList<NameValuePair> paras=new ArrayList<NameValuePair>();
	// // //paras.add(new BasicNameValuePair("name","xxx"));
	// // UrlEncodedFormEntity entity =new
	// UrlEncodedFormEntity(paras,Consts.UTF_8);
	// // post.setEntity(entity);
	// try {
	// CloseableHttpResponse response = httpclient.execute(post);
	//
	// StatusLine statusLine=response.getStatusLine();
	// int statusCode=statusLine.getStatusCode();
	// if(statusCode==HttpStatus.SC_OK)
	// {
	// HttpEntity responseEntity =response.getEntity();
	// inputStream=responseEntity.getContent();
	// String fileNameString=path.substring(path.lastIndexOf('/')+1);
	// System.out.print(fileNameString);
	// outputStream=new
	// FileOutputStream("/home/oxie/Downloads/Clawer/"+fileNameString);
	// int tempByte=-1;
	// while((tempByte=inputStream.read())>0)
	// {
	// outputStream.write(tempByte);
	// }
	// if(inputStream!=null)
	// {
	// inputStream.close();
	// }
	// if(outputStream!=null)
	// {
	// outputStream.close();
	// }
	// return true;
	// }
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// }
	// return false;
	// }

	// public static Boolean DownLoadPageGet(String path)throws HTTPException
	// {
	//
	// InputStream inputStream=null;
	// OutputStream outputStream=null;
	// HttpGet get =new HttpGet(path);
	// try {
	// CloseableHttpResponse response = httpclient.execute(get);
	// StatusLine statusLine=response.getStatusLine();
	// int statusCode=statusLine.getStatusCode();
	// if(statusCode==HttpStatus.SC_OK)
	// {
	// HttpEntity responseEntity =response.getEntity();
	// inputStream=responseEntity.getContent();
	// String fileNameString=path.substring(path.lastIndexOf('/')+1);
	// System.out.print(fileNameString);
	// outputStream=new
	// FileOutputStream("/home/oxie/Downloads/Clawer/"+fileNameString);
	// int tempByte=-1;
	// while((tempByte=inputStream.read())>0)
	// {
	// outputStream.write(tempByte);
	// }
	// if(inputStream!=null)
	// {
	// inputStream.close();
	// }
	// if(outputStream!=null)
	// {
	// outputStream.close();
	// }
	// return true;
	// }
	// if(statusCode==HttpStatus.SC_MOVED_TEMPORARILY||statusCode==HttpStatus.SC_MOVED_PERMANENTLY||statusCode==HttpStatus.SC_TEMPORARY_REDIRECT)
	// {
	// //TODO Redirect
	// }
	// } catch (ClientProtocolException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// }
	// return false;
	// }
	// }
}
