package exercise;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/*
 * java url class Test
 */
public class UrlTest {
	private void urlEncodingTest() throws UnsupportedEncodingException
	{

		String strEncoding = URLEncoder.encode("그룹 스터디", "utf-8");
		String strDecoding = URLDecoder.decode(strEncoding,"utf-8");	

		System.out.println("인코딩 된 문자열: "+strEncoding);
		System.out.println("디코딩 된 문자열: "+strDecoding);
		System.out.println("부경대꺼랑 비교: "+URLDecoder.decode("%B1%D7%B7%EC+%BD%BA%C5%CD%B5%F0","euc-kr"));
		// 부경대는 euc-kr 인코딩을 씀? 아니면 내가 패킷 캡처 했던 컴퓨터가 euc-kr 인코딩을 쓰는 웹브라우저 였나?
	}
	public static void main(String[] args)
	{
		UrlTest theApp = new UrlTest();
		try {
			theApp.urlEncodingTest();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
