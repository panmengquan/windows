package cn.tedu.media_player_v4.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import cn.tedu.media_player_v4.R;
import cn.tedu.media_player_v4.util.HttpUtils;

public class DownloadService extends IntentService{
	private static final int NOTIFICATION_ID = 100;
	private NotificationManager manager;
	private Builder builder;
	
	public DownloadService() {
		super("xiaoming");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.builder = new Builder(this);
	}
	
	/**
	 * �ڹ����߳���ִ��
	 * ������startService����ʱ�������
	 * onHandleIntent�е�ҵ���߼���ӵ�
	 * ������еȴ�ִ�С����ֵ�������ʱ
	 * ��ִ�и÷����еĴ��롣
	 */
	protected void onHandleIntent(Intent intent) {
		//��ȡ�������
		String url=intent.getStringExtra("url");
		String filename=intent.getStringExtra("filename");
		String bitrate = intent.getStringExtra("bitrate");
		int totalsize = intent.getIntExtra("total", 0);
		//������Ҫд���Ŀ��File����
		//targetFile:   /mnt/sdcard/Music/64/xxx.mp3
		File targetFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "_"+bitrate+"/"+filename);
		//�ж��ļ��Ƿ��Ѿ����ع�  �ļ��Ƿ��Ѵ���
		if(targetFile.exists()){
			return;
		}
		if(!targetFile.getParentFile().exists()){
			targetFile.getParentFile().mkdirs();
		}
		//����ļ������ڣ�����http����
		try {
			FileOutputStream fos = new FileOutputStream(targetFile);
			InputStream is = HttpUtils.getInputStream(url);
			//��ȡis��ִ�С��߶���д���Ĳ�����
			byte[] buffer = new byte[1024*200];
			int length=0;
			//��֪ͨ��ʾ���ֿ�ʼ����
			sendNotification("���ֿ�ʼ����..", "���ֿ�ʼ����");
			int progress = 0;
			while((length=is.read(buffer)) != -1){
				fos.write(buffer, 0, length);
				fos.flush();
				progress += length;
				//����֪ͨ��ʾ���ؽ���
				String text = "��ǰ���ؽ���Ϊ:"+(Math.floor(1000.0*progress/totalsize)/10)+"%";
				sendNotification(text, "���ֿ�ʼ����");
			}
			fos.close();
			//������Ϻ�ʹ��notification֪ͨ�û���
			clearNotification();
			sendNotification("�����������", "�������");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** ����֪ͨ */
	public void sendNotification(String text,String ticker){
		builder.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle("��������")
			.setContentText(text)
			.setTicker(ticker);
		Notification n = builder.build();
		manager.notify(NOTIFICATION_ID, n);
	}
	/** ���֪ͨ */
	public void clearNotification(){
		manager.cancel(NOTIFICATION_ID);
	}
	
}






