package xstudio.xihaiwan.com;

import java.io.BufferedReader;

import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class XihaiwanActivity extends Activity {
	/** Called when the activity is first created. */
	private HttpResponse httpreponse = null;
	private HttpEntity httpentity = null;
	private TextView content = null;
	private String msgs = null;
	private int linecount = 0;
	private int linknum = 0;
	private String[] linkstr = null;
	private String[] week = null;
	private String[] url = null;
	private Spinner spinner = null;
	private List<CharSequence> adapteweek = null;
	private TextView breakfast = null;
	private TextView morningtaste = null;
	private TextView lunch = null;
	private TextView noontaste = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Runnable updateThread = new Runnable() {

			public void run() {
				// 更新UI
				if (msgs != null) {
					adapteweek = new ArrayList<CharSequence>();
					for (int i =0; i<linknum; i++)
					{
						adapteweek.add(week[i]);
					}

					// 建立Adapter并且绑定数据源
					ArrayAdapter<CharSequence> _Adapter = new ArrayAdapter<CharSequence>(
							XihaiwanActivity.this,
							android.R.layout.simple_spinner_item, adapteweek);
					_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					// 绑定 Adapter到控件
					spinner.setAdapter(_Adapter);
					breakfast.setText(msgs);
					content.setText(linkstr[0]);
				} else {
					content.setText("数据错误！");
				}



			}

		};



		new Thread() {
			public void run() {
				//
				content = (TextView) findViewById(R.id.content);
				spinner = (Spinner) findViewById(R.id.spinner1);
				breakfast = (TextView)findViewById(R.id.breakfast);
				morningtaste = (TextView)findViewById(R.id.morningtaste);
				lunch = (TextView)findViewById(R.id.lunch);
				noontaste = (TextView)findViewById(R.id.noontaste);
				String myString = null;
				StringBuffer sff = new StringBuffer();// 一定要new一个，我刚开始搞忘了，出不来。
				try {
					Document doc = Jsoup.connect(
							"http://www.cnblogs.com/agileyanly/p/4052627.html")
							.get();
					Elements links = doc.select("span[style]");
					linknum = links.size();
					int i = 0;
					linkstr = new String[linknum];
					week = new String[linknum];
					url = new String[linknum];
					// 注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
					for (Element link : links) {
						// 这里没有什么好说的。
						linkstr[i] = link.text();
						String[] aa = linkstr[i].split(" ");
						week[i] = aa[0];
						url[i] = aa[1];
						i++;
					}
					msgs= url[0];
					XihaiwanActivity.this.runOnUiThread(updateThread);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				
				
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view,
							final int position, long id) {
						String str = parent.getItemAtPosition(position).toString();
						Toast.makeText(XihaiwanActivity.this, "你点击的是:" + position, 2000).show();
						new Thread() {
							public void run() {
						try {
							Document doc = Jsoup.connect(url[position]).get();
							Elements links = doc.select("td[style]");
							int linknum = links.size();
							int i = 0;
							linkstr = new String[linknum];
							// 注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
							for (Element link : links) {
								// 这里没有什么好说的。
								linkstr[i++] = link.text();
							}
							msgs= url[0];
							XihaiwanActivity.this.runOnUiThread(updateThread);
						} catch (Exception e) {
							e.printStackTrace();
						}
							}}.start();
					}
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub    
						}
				});

				}
		}.start();
		

	
//				HttpGet httpget = new HttpGet(
//						"http://www.cnblogs.com/agileyanly/p/4052627.html");
//				HttpClient httpclient = new DefaultHttpClient();
//				InputStream Inputstream = null;
//				try {
//					httpreponse = httpclient.execute(httpget);
//					httpentity = httpreponse.getEntity();
//					Inputstream = httpentity.getContent();
//					BufferedReader reader = new BufferedReader(
//							new InputStreamReader(Inputstream, "GB2312"));
//					String result = "";
//					String line = "";
//					while ((line = reader.readLine()) != null) {
//						result = result + line;
//						msgs = line;
//						linecount++;
//						XihaiwanActivity.this.runOnUiThread(updateThread);
//					}
//					msgs = linecount + "";
//					XihaiwanActivity.this.runOnUiThread(updateThread);
//
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					try {
//						Inputstream.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//				// 等等之类的


	}
}