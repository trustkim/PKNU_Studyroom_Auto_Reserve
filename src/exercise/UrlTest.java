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

		String strEncoding = URLEncoder.encode("�׷� ���͵�", "utf-8");
		String strDecoding = URLDecoder.decode(strEncoding,"utf-8");	

		System.out.println("���ڵ� �� ���ڿ�: "+strEncoding);
		System.out.println("���ڵ� �� ���ڿ�: "+strDecoding);
		System.out.println("�ΰ�벨�� ��: "+URLDecoder.decode("%B1%D7%B7%EC+%BD%BA%C5%CD%B5%F0","euc-kr"));
		// �ΰ��� euc-kr ���ڵ��� ��? �ƴϸ� ���� ��Ŷ ĸó �ߴ� ��ǻ�Ͱ� euc-kr ���ڵ��� ���� �������� ����?
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
