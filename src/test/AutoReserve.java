package test;
import test.MyTimer;
/*
 * PKNU ���͵�� �ڵ� ���� ���α׷��Դϴ�. �� �׽�Ʈ �ڵ�.
 */
public class AutoReserve
{
	public static void main(String[] args)
	{
		//AutoReserve theApp = new AutoReserve(); // ������ �ʿ� ����.

		// Ÿ�̸� �׽�Ʈ �ڵ�
		MyTimer mTimer = new MyTimer();
		mTimer.start();
	}
}