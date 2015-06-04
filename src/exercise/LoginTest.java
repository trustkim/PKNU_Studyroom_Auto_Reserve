package exercise;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LoginTest {
	private static final String LOGIN_URL_STRING = "http://libweb.pknu.ac.kr/dlsearch/Common/CommonWLogin.asp";
	private static final String LOGIN_TEXT_DATA = "TransferHref=http://libweb.pknu.ac.kr/dlsearch/TGUI/Theme/PKNU/mainspace.asp?frameName=IndexFrame&ID=student_number&Password=password&image3.x=0&image3.y=0";	// 지파니가 따준 코드!
	private static String RESERVE_DATE;
	private CookieManager myCookieManager;
	private CookieHandler myCookieHandler;
	private CookieStore myCookieStore;
	@SuppressWarnings("unused")
	private HttpCookie cookie;
	private URL myUrl;
	private HttpURLConnection httpUrlConn;
	@SuppressWarnings("static-access")
	public LoginTest(String reserveDate)
	{
		RESERVE_DATE = reserveDate;
		myCookieManager = new CookieManager();			// 쿠키 매니저 생성
		myCookieHandler.setDefault(myCookieManager);	// 쿠키 핸들러는 쿠키 매니저를 호출하면서 생성?
	}
	public void getSessionID()
	{
		try {
			myUrl = new URL("http://libweb.pknu.ac.kr/dlsearch/TGUI/Theme/PKNU/index.asp");
			httpUrlConn = (HttpURLConnection) myUrl.openConnection();
			
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpUrlConn.setRequestProperty("Host", "libweb.pknu.ac.kr");
			httpUrlConn.setRequestProperty("Connection", "keep-alive");
			httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
			httpUrlConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			httpUrlConn.setRequestProperty("Accep-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			
			httpUrlConn.getContent();
			myCookieStore = myCookieManager.getCookieStore();	// 여러 쿠키를 저장 할 수 있음.
			//HttpCookie cookie = myCookieStore.getCookies().get(0);
//			List<HttpCookie> cookieList = myCookieStore.getCookies(); // 쿠키 순회 할 때만 생성해 쓰고 버림.
//			cookie = cookieList.get(0);

			
//			System.out.println("name of cookie: " + cookie.getName());
//			System.out.println("value of cookie: " + cookie.getValue());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void login()
	{	// 지파니 짱짱맨!
		try {			
			myUrl = new URL(LOGIN_URL_STRING);
			httpUrlConn = (HttpURLConnection) myUrl.openConnection();
			
			// add request header
			httpUrlConn.setRequestMethod("POST");
			httpUrlConn.setRequestProperty("Host", "libweb.pknu.ac.kr");
			httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
			httpUrlConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpUrlConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			httpUrlConn.setRequestProperty("Accep-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			//httpUrlConn.setRequestProperty("Referer", "http://libweb.pknu.ac.kr/dlsearch/dlservice/Login/SSLwLogin.asp");
			//httpUrlConn.setRequestProperty("Cookie", "UserID=; "+cookie.getName()+"="+cookie.getValue());
			//httpUrlConn.setRequestProperty("Cookie", "Lib1Proxy2Ssn=76617670; LP1121SID=76617670");
			httpUrlConn.setRequestProperty("Connection", "keep-alive");
			httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpUrlConn.setRequestProperty("Content-Length", "81");
			
			// send post request
			httpUrlConn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpUrlConn.getOutputStream());
			wr.writeBytes(LOGIN_TEXT_DATA);
			wr.flush();
			wr.close();
			
			httpUrlConn.getInputStream();	// 이거 안해주면 로그인 유지가 안되는데?
			//System.out.println("login complete");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		LoginTest theApp = new LoginTest("20150604");
		theApp.getSessionID();
		theApp.login();
		System.out.println("!!");
		theApp.loginTest();
	}
	
	private void testResponsePrint(HttpURLConnection con)
	{
//		String headerName = null;
//		for(int i=1;(headerName=con.getHeaderFieldKey(i))!=null; i++)	// i=1부터 시작
//		{
//			System.out.println(headerName+": "+con.getHeaderField(i));	// URLConnection만으로도 쿠키에 접근 할 수 있다!
//		}
		
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while((line=bf.readLine())!=null)
			{
				System.out.println(line);
			}
			bf.close();
		}catch(IOException e) {e.printStackTrace();}
	}
	@SuppressWarnings("unused")
	private void testCookieStoreIterativePrint()
	{
		List<HttpCookie> cookieList = myCookieStore.getCookies(); // 쿠키 순회 할 때만 생성해 쓰고 버림.
		for(HttpCookie cookie : cookieList)
		{
			System.out.println("name of cookie: " + cookie.getName());
			System.out.println("value of cookie: " + cookie.getValue());
		}// 따로 이름과 값을 토크나이징할 필요가 없어졌다.
	}
	private void loginTest()
	{		
		try {
			// Read HTTP POST Prams from File
			String reserveParameters = getFileParams("reserve_params.txt");
			
			myUrl = new URL("http://libweb.pknu.ac.kr/dlsearch/dlservice/wr_reserve/reserve_proc.asp");
			httpUrlConn = (HttpURLConnection) myUrl.openConnection();
			
			httpUrlConn.setRequestMethod("POST");
			httpUrlConn.setRequestProperty("Host", "libweb.pknu.ac.kr");
			httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
			httpUrlConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpUrlConn.setRequestProperty("Accep-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			httpUrlConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			httpUrlConn.setRequestProperty("Referer", "http://libweb.pknu.ac.kr/dlsearch/dlservice/wr_reserve/reserve.asp");
			//httpUrlConn.setRequestProperty("Cookie", "UserID=; "+cookie.getName()+"="+cookie.getValue());
			//httpUrlConn.setRequestProperty("Cookie", "UserID=; ");
			httpUrlConn.setRequestProperty("Connection", "keep-alive");
			httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpUrlConn.setRequestProperty("Content-Length", Integer.toString(reserveParameters.length()));
			
			// Send post request
			httpUrlConn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpUrlConn.getOutputStream());
			wr.writeBytes(reserveParameters+"&reserveDate="+RESERVE_DATE);
			wr.flush();
			wr.close();
			
			testResponsePrint(httpUrlConn);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String getFileParams(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String inputParams="";
		String temp="";
		while((temp = in.readLine())!=null){
			inputParams += temp;
		}
		in.close();
		return inputParams;
	}
}
