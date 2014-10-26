package xstudio.xihaiwan.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class XihaiwanActivity extends Activity {
	/** Called when the activity is first created. */
	private HttpResponse httpreponse = null;
	private HttpEntity httpentity = null;
	private TextView content = null;
	private String msgs = null;
	private int linecount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final Runnable updateThread = new Runnable() {

			public void run() {
				// 更新UI
				if (msgs != null) {
					content.setText(msgs);
				} else {
					content.setText("数据错误！");
				}
			}

		};

		new Thread() {
			public void run() {
				//
				content = (TextView) findViewById(R.id.content);
				HttpGet httpget = new HttpGet(
						"http://www.cnblogs.com/agileyanly/p/4052627.html");
				HttpClient httpclient = new DefaultHttpClient();
				InputStream Inputstream = null;
				try {
					httpreponse = httpclient.execute(httpget);
					httpentity = httpreponse.getEntity();
					Inputstream = httpentity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(Inputstream,"GB2312"));
					String result = "";
					String line = "";
					while ((line = reader.readLine()) != null) {
						result = result + line;
						msgs = line;
						linecount++;
						XihaiwanActivity.this.runOnUiThread(updateThread);
					}
					msgs = linecount+"";
					XihaiwanActivity.this.runOnUiThread(updateThread);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						Inputstream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 等等之类的
			}
		}.start();
		

	}
}