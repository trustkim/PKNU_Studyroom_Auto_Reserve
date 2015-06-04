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
 * 쿠키 테스트 샘플 코드입니다!
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
			httpUrlConn = (HttpURLConnection) myUrl.openConnection();	// 생성한 url에 연결할 URLConnection 클래스 객체 생성.
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// url에서 해당 필드 찾아 캡처 하는 방식
	private String captureTheCookie() throws IOException
	{
		String headerName = null;
		String cookieField = null;
		for(int i=1;(headerName=httpUrlConn.getHeaderFieldKey(i))!=null; i++)	// i=1부터 시작
		{
			//System.out.println(headerName+": "+httpUrlConn.getHeaderField(i));	// URLConnection만으로도 쿠키에 접근 할 수 있다!
			if(headerName.equals("Set-Cookie")) {
				System.out.println("찾았다 쿠키 요놈! 쿠키쿠쿠쿠키쿠키쿠키! 셋 쿠키 쿠키큌쿠키키퀴큌 쿠우키쿠우키쿠키!!");
				cookieField = httpUrlConn.getHeaderField(i);
			}
		}
		return cookieField;
	}
	
	// cookie 스토어 활용 방식
	@SuppressWarnings("static-access")
	private void captureTheCookie2() throws IOException
	{
		System.out.println("쿠키 스토어 테스트");
		myCookieManager = new CookieManager();			// 쿠키 매니저 생성
		myCookieHandler.setDefault(myCookieManager);	// 쿠키 핸들러는 쿠키 매니저를 호출하면서 생성?
		
		// 이 부분이 쿠키 매니저, 핸들러 생성 다음에 와야 함.
		myUrl = new URL(URL_STRING);
		httpUrlConn = (HttpURLConnection) myUrl.openConnection();	// 생성한 url에 연결할 URLConnection 클래스 객체 생성.
		httpUrlConn.getContent();
		
		myCookieStore = myCookieManager.getCookieStore();
		List<HttpCookie> cookieList = myCookieStore.getCookies(); // 여러 쿠키를 저장 할 수 있음.
		for(HttpCookie cookie : cookieList)
		{
			System.out.println("Domain: " + cookie.getDomain());
			System.out.println("name of cookie: " + cookie.getName());
			System.out.println("value of cookie: " + cookie.getValue());
		}// 따로 이름과 값을 토크나이징할 필요가 없어졌다.
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
