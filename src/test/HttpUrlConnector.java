package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUrlConnector {
	private static final String RESERVE_PARAMS_FILENAME = "./src/test/reserve_params.txt";
	private static final String LOGIN_URL_STRING = "http://libweb.pknu.ac.kr/dlsearch/Common/CommonWLogin.asp";
	private static final String LOGIN_TEXT_DATA = "TransferHref=http://libweb.pknu.ac.kr/dlsearch/TGUI/Theme/PKNU/mainspace.asp?frameName=IndexFrame&ID=student_number&Password=password&image3.x=0&image3.y=0";
	private static final String ALREADY_FULL = "alert"; // already full result code
	private static String RESERVE_DATE;
	private CookieManager myCookieManager;
	//private CookieHandler myCookieHandler;
	@SuppressWarnings("unused")
	private CookieStore myCookieStore;
	private URL myUrl;
	private HttpURLConnection httpUrlConn;
	private RandomAccessFile rd;
	public HttpUrlConnector(String reserveDate, RandomAccessFile rd)
	{
		RESERVE_DATE = reserveDate;
		myCookieManager = new CookieManager();			// create cookie manager
		CookieHandler.setDefault(myCookieManager);	// create cookie handler
		this.rd = rd;
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
			myCookieStore = myCookieManager.getCookieStore();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void login()
	{
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
			httpUrlConn.setRequestProperty("Connection", "keep-alive");
			httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpUrlConn.setRequestProperty("Content-Length", "81");

			// send post request
			httpUrlConn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpUrlConn.getOutputStream());
			wr.writeBytes(LOGIN_TEXT_DATA);
			wr.flush();
			wr.close();

			httpUrlConn.getInputStream();	// it must be need for holding login information
			//System.out.println("login complete");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Reserving Methods */
	private final String[] roomID = {"0001", "0002", "0003", "0004"};
	public void reserve(String ROOM_ID)
	{		
			// Read HTTP POST Prams from File
			String reserveParameters="";
			try {
				reserveParameters = getFileParams(RESERVE_PARAMS_FILENAME);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int[] states = new int[4];
			int i=getPriorityRoomIndex(ROOM_ID);	// the room index real room number is i+1
			while(states[i]<2) {
				switch(states[i]=sendReservePostRequest(ROOM_ID,reserveParameters)){
				case 1:{
					Print("ROOM ID - " + roomID[i] + "IS FULL");
					break;
				}
				case 2:{
					break;
				}
				case 3:{
					Print("ROOM ID - " + roomID[i] + "IS RESERVED");
					break;
				}
				}
				if(i+1<4) i+=1;
				else i=0;
			}
	}
	private void Print(String str)
	{
		try {
			rd.writeUTF(str+"\\\\\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int getPriorityRoomIndex(String ROOM_ID)
	{
		int result=0;
		for(int i=0;i<4;i++)
		{
			if(ROOM_ID.equals(roomID[i]))
				return i;
		}
		return result;
	}
	private int sendReservePostRequest(String ROOM_ID, String reserveParameters)
	{
		try {
			myUrl = new URL("http://libweb.pknu.ac.kr/dlsearch/dlservice/wr_reserve/reserve_proc.asp");

			httpUrlConn = (HttpURLConnection) myUrl.openConnection();

			httpUrlConn.setRequestMethod("POST");
			httpUrlConn.setRequestProperty("Host", "libweb.pknu.ac.kr");
			httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
			httpUrlConn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpUrlConn.setRequestProperty("Accep-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			httpUrlConn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			httpUrlConn.setRequestProperty("Referer", "http://libweb.pknu.ac.kr/dlsearch/dlservice/wr_reserve/reserve.asp");
			httpUrlConn.setRequestProperty("Connection", "keep-alive");
			httpUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpUrlConn.setRequestProperty("Content-Length", Integer.toString(reserveParameters.length()));

			// Send post request
			httpUrlConn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(httpUrlConn.getOutputStream());
			wr.writeBytes("roomID="+ROOM_ID+reserveParameters+"&reserveDate="+RESERVE_DATE);
			wr.flush();
			wr.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkResultState(httpUrlConn);
	}
	private String getFileParams(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String[] inputParams= new String[12];
		for(int j=0;j<12;j++)
			inputParams[j]="";
		String temp="";
		int i=0;
		while((temp = in.readLine())!=null){
			if(i<5 || i>9)
				inputParams[i] += temp;
			else
				inputParams[i] += URLEncoder.encode(temp,"euc-kr") + "%0D";
			i++;
		}
		in.close();
		
		String result = "";
		for(int j=0;j<12;j++) {
			result += inputParams[j];
		}
		return result;
	}
	private int checkResultState(HttpURLConnection con)
	{
		/*
		 * state
		 * 0: reserve task not start
		 * 1: this room is already full
		 * 2: you have another room
		 * 3: this room reserve complete
		 */
		int state=0;
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			rd.writeUTF("RESPONSE: \\\\\n");
			//System.out.println("RESPONSE: ");
			while((line=bf.readLine())!=null)
			{
				rd.writeUTF(line.trim()+"\\\\\n");
				System.out.println(line.trim());
				if(line.trim().equals(ALREADY_FULL))
				{
					//rd.writeUTF("\\\\\n");
					//System.out.println();
					state = 1;
				}else {
					state = 3;
				}
			}
			//bf.close();	// is it safety?
		}catch(IOException e) {e.printStackTrace();}
		return state;
	}
}
