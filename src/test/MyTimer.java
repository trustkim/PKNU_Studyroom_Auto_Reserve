package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

import test.HttpUrlConnector;

class MyTimer extends Thread
{	
	private final String OUTPUT_FILE_STRING = "./src/test/monitoring.txt";
	private Calendar reserveTime;	// time for running reserve
	private RandomAccessFile rd;	// file for result print

	public MyTimer()
	{
		reserveTime = Calendar.getInstance();
//		reserveTime.roll(Calendar.DAY_OF_YEAR, true);
//		reserveTime.set(Calendar.HOUR_OF_DAY,0);
//		reserveTime.set(Calendar.MINUTE,0);
		reserveTime.set(Calendar.SECOND,0);
		reserveTime.roll(Calendar.MINUTE,true);	// test per one minute

		rd = null;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				fileOpen();
				sleep(findSleepTime());
				rd.setLength(207);	// delete text which is over the header
				work();
//				reserveTime.roll(Calendar.DAY_OF_YEAR, true);	// set reserveTime for tomorrow
				reserveTime.roll(Calendar.MINUTE, true);		// test per one minute
				rd.close();
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void work()
	{
		String STUDY_TIME = String.copyValueOf(getStudyTime().toCharArray());
		Print("STUDY TIME: "+STUDY_TIME);
		HttpUrlConnector conn = new HttpUrlConnector(STUDY_TIME, rd);
		conn.login();
		conn.reserve("0004");
	}
	private String getStudyTime()
	{
		Calendar studyTime = Calendar.getInstance();	// create now calendar
		studyTime.roll(Calendar.DAY_OF_YEAR, 7);		// there is critical problem over 7
		studyTime.set(Calendar.HOUR_OF_DAY,0);
		studyTime.set(Calendar.MINUTE,0);
		studyTime.set(Calendar.SECOND,0);

		String STUDY_TIME = "";
		STUDY_TIME += studyTime.get(Calendar.YEAR);
		int month = studyTime.get(Calendar.MONTH)+1;
		STUDY_TIME += month<10?"0"+month:month;
		int date = studyTime.get(Calendar.DATE);
		STUDY_TIME += date<10?"0"+date:date;
		return STUDY_TIME;
	}
	
	private long findSleepTime()
	{
		Date now = new Date();	// Now
		Date until = reserveTime.getTime();	// when reserving
		PrintHeader("===== Time Board =====");
		Print("CURRENT TIME: "+now);
		Print("NEXT RESERVING TIME: "+until);
		PrintHeader("===== Result =====");
		
		return until.getTime() - now.getTime();	// Time Gap
	}
	
	private void fileOpen()
	{
		try {
			rd = new RandomAccessFile(OUTPUT_FILE_STRING,"rws");
			rd.readLine();
			Print("TIMER THREAD IS RUNNING!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	private void PrintHeader(String str)
	{
		try {
			rd.writeUTF(str+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
