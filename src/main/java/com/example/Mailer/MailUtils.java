package com.example.Mailer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MailUtils {

	static URI uri;
	static URL url;

	/**
	 *
	 * @param employeeId
	 * @param s_month
	 * @return
	 */
	public static URL getUrl(String employeeId,String s_month) {
		try {
			String empid=Encoding.getCode(employeeId);
			String month=Encoding.getCode(s_month);
            String urlString= "http://172.16.94.85:8080/start/?a="+empid+"&b="+month;
			uri = new URI(urlString);
			url = uri.toURL();
			System.out.println(url);
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}
		return url;
	}
}