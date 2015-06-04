package exercise;

import java.util.List;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * ��Ű �׽�Ʈ ���� �ڵ��Դϴ�!
 */
public class CookieTest {
	private final static String URL_STRING = "http://libweb.pknu.ac.kr/dlsearch/dlservice/Login/SSLwLogin.asp";
	private URL myUrl;
	private HttpURLConnection httpUrlConn;
	private CookieManager myCookieManager;
	private CookieHandler myCookieHandler;
	private CookieStore myCookieStore;
	public CookieTest(String strUrl)
	{
		try {
			myUrl = new URL(strUrl);
			httpUrlConn = (HttpURLConnection) myUrl.openConnection();	// ������ url�� ������ URLConnection Ŭ���� ��ü ����.
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// url���� �ش� �ʵ� ã�� ĸó �ϴ� ���
	private String captureTheCookie() throws IOException
	{
		String headerName = null;
		String cookieField = null;
		for(int i=1;(headerName=httpUrlConn.getHeaderFieldKey(i))!=null; i++)	// i=1���� ����
		{
			//System.out.println(headerName+": "+httpUrlConn.getHeaderField(i));	// URLConnection�����ε� ��Ű�� ���� �� �� �ִ�!
			if(headerName.equals("Set-Cookie")) {
				System.out.println("ã�Ҵ� ��Ű ���! ��Ű������Ű��Ű��Ű! �� ��Ű ��Ű�W��ŰŰ���W ���Ű���Ű��Ű!!");
				cookieField = httpUrlConn.getHeaderField(i);
			}
		}
		return cookieField;
	}
	
	// cookie ����� Ȱ�� ���
	@SuppressWarnings("static-access")
	private void captureTheCookie2() throws IOException
	{
		System.out.println("��Ű ����� �׽�Ʈ");
		myCookieManager = new CookieManager();			// ��Ű �Ŵ��� ����
		myCookieHandler.setDefault(myCookieManager);	// ��Ű �ڵ鷯�� ��Ű �Ŵ����� ȣ���ϸ鼭 ����?
		
		// �� �κ��� ��Ű �Ŵ���, �ڵ鷯 ���� ������ �;� ��.
		myUrl = new URL(URL_STRING);
		httpUrlConn = (HttpURLConnection) myUrl.openConnection();	// ������ url�� ������ URLConnection Ŭ���� ��ü ����.
		httpUrlConn.getContent();
		
		myCookieStore = myCookieManager.getCookieStore();
		List<HttpCookie> cookieList = myCookieStore.getCookies(); // ���� ��Ű�� ���� �� �� ����.
		for(HttpCookie cookie : cookieList)
		{
			System.out.println("Domain: " + cookie.getDomain());
			System.out.println("name of cookie: " + cookie.getName());
			System.out.println("value of cookie: " + cookie.getValue());
		}// ���� �̸��� ���� ��ũ����¡�� �ʿ䰡 ��������.
	}
	
	public static void main(String[] args)
	{
		CookieTest cookie = new CookieTest(URL_STRING);
		try {
			System.out.println(cookie.captureTheCookie());
			cookie.captureTheCookie2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
