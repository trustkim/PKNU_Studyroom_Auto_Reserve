package exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/*
 * URLConnection Test
 */
public class UrlConnectionTest {
	private void test() throws IOException
	{
		URL myUrl = new URL("http://libweb.pknu.ac.kr/dlsearch/dlservice/Login/SSLwLogin.asp");
		URLConnection urlConn = myUrl.openConnection();	// 생성한 url에 연결할 URLConnection 클래스 객체 생성.
		//urlConn.connect();
		String headerName = "";
		for(int i=1;(headerName=urlConn.getHeaderFieldKey(i))!=null; i++)	// i=1부터 시작
		{
			System.out.println(headerName+": "+urlConn.getHeaderField(i));	// URLConnection만으로도 쿠키에 접근 할 수 있다!
			if(headerName.equals("Set-Cookie"))
				System.out.println("찾았다 쿠키 요놈! 쿠키쿠쿠쿠키쿠키쿠키! 셋 쿠키 쿠키큌쿠키키퀴큌 쿠우키쿠우키쿠키!!");
		}

		BufferedReader bf = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line;
		while((line=bf.readLine())!=null)
		{
			System.out.println(line);
		}
		bf.close();
	}
	public static void main(String[] args)
	{
		UrlConnectionTest theApp = new UrlConnectionTest();
		try {
			theApp.test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
