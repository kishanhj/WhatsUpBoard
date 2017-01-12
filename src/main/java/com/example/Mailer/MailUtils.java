package com.example.Mailer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.example.Helpers.PropertyUtils;

public class MailUtils {

	static URI uri;
	static URL url;

	/**
	 *Returns a unique URL
	 * @param employeeId
	 * @param s_month
	 * @return
	 */
	public static URL getUrl(String employeeId,String s_month) {
		try {
			String empid=Encoding.getCode(employeeId);
			String month=Encoding.getCode(s_month);
			PropertyUtils properties = new PropertyUtils();
			String urlHost = properties.getproperty("RunInUrl");
            String urlString= urlHost+"/?a="+empid+"&b="+month;
            uri = new URI(urlString);
			url = uri.toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}
		return url;
	}
}