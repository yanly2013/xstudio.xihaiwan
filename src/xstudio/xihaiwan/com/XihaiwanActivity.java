package xstudio.xihaiwan.com;

import java.io.BufferedReader;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	private Button firstday = null;
	private Button secondday = null;
	private Button thirdday = null;
	private Button forthday = null;
	private Button fifthday = null;
	private Button sixthday = null;
	private Button seventhday = null;
	private ImageButton backimg = null;
	private String[] dayseveryweek = new String[8];
	private String[] breakfastmenu = new String[8];
	private String[] morningtastemenu = new String[8];
	private String[] lunchmenu = new String[8];
	private String[] noontastemenu = new String[8];
	private String[] daysinweekinString = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };
	private int daysinweek = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.menutitlebar); // titlebar为自己标题栏的布局
		
		content = (TextView) findViewById(R.id.content);
		spinner = (Spinner) findViewById(R.id.spinner1);
		firstday = (Button) findViewById(R.id.button1);
		secondday = (Button) findViewById(R.id.button2);
		thirdday = (Button) findViewById(R.id.button3);
		forthday = (Button) findViewById(R.id.button4);
		fifthday = (Button) findViewById(R.id.button5);
		breakfast = (TextView) findViewById(R.id.breakfast);
		morningtaste = (TextView) findViewById(R.id.morningtaste);
		lunch = (TextView) findViewById(R.id.lunch);
		noontaste = (TextView) findViewById(R.id.noontaste);
		backimg = (ImageButton)findViewById(R.id.backimagebtn);
		
		backimg.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(XihaiwanActivity.this,
						navigatorActivity.class);
				startActivity(it);	
			}
		});

		firstday.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				breakfast.setText(breakfastmenu[1]);
				morningtaste.setText(morningtastemenu[1]);
				lunch.setText(lunchmenu[1]);
				noontaste.setText(noontastemenu[1]);
			}
		});

		secondday.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				breakfast.setText(breakfastmenu[2]);
				morningtaste.setText(morningtastemenu[2]);
				lunch.setText(lunchmenu[2]);
				noontaste.setText(noontastemenu[2]);
			}
		});

		thirdday.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				breakfast.setText(breakfastmenu[3]);
				morningtaste.setText(morningtastemenu[3]);
				lunch.setText(lunchmenu[3]);
				noontaste.setText(noontastemenu[3]);
			}
		});

		forthday.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				breakfast.setText(breakfastmenu[4]);
				morningtaste.setText(morningtastemenu[4]);
				lunch.setText(lunchmenu[4]);
				noontaste.setText(noontastemenu[4]);
			}
		});

		fifthday.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				breakfast.setText(breakfastmenu[5]);
				morningtaste.setText(morningtastemenu[5]);
				lunch.setText(lunchmenu[5]);
				noontaste.setText(noontastemenu[5]);
			}
		});

		final Runnable updateThread = new Runnable() {

			public void run() {
				// 更新UI
				if (msgs != null) {
					adapteweek = new ArrayList<CharSequence>();
					for (int i = 0; i < linknum; i++) {
						adapteweek.add(week[i]);
					}
					// 建立Adapter并且绑定数据源
					ArrayAdapter<CharSequence> _Adapter = new ArrayAdapter<CharSequence>(
							XihaiwanActivity.this,
							android.R.layout.simple_spinner_item, adapteweek);
					_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					// 绑定 Adapter到控件
					spinner.setAdapter(_Adapter);
					content.setText("加载成功！");
					msgs = null;
				} else {
					content.setText("数据错误！");
				}

			}

		};
		final Runnable selectThread = new Runnable() {

			public void run() {
				// 更新UI
				if (msgs != null) {
					Calendar cal = Calendar.getInstance();
					int today = cal.get(Calendar.DAY_OF_WEEK);
					content.setText("今天是" + daysinweekinString[today-1] + "");
					int index = 0;
					for (index = 0; index < daysinweek; index++) {
						if (daysinweekinString[daysinweek]
								.equals(dayseveryweek[index].trim())) {
							break;
						}
					}
					if (index == daysinweek) {
						index = daysinweek - 1;
					}

					breakfast.setText(breakfastmenu[index]);
					morningtaste.setText(morningtastemenu[index]);
					lunch.setText(lunchmenu[index]);
					noontaste.setText(noontastemenu[index]);
					firstday.setText(dayseveryweek[1]);
					secondday.setText(dayseveryweek[2]);
					thirdday.setText(dayseveryweek[3]);
					forthday.setText(dayseveryweek[4]);
					fifthday.setText(dayseveryweek[5]);
					msgs = null;
				} else {
					content.setText("数据错误！");
				}

			}

		};
		new Thread() {
			public void run() {
				//

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
					msgs = url[0];
					XihaiwanActivity.this.runOnUiThread(updateThread);
				} catch (Exception e) {
					e.printStackTrace();
				}

				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, final int position, long id) {
						String str = parent.getItemAtPosition(position)
								.toString();
						Toast.makeText(XihaiwanActivity.this,
								"你点击的是:" + position, 2000).show();
						new Thread() {
							public void run() {
								try {
									Document doc = Jsoup.connect(url[position])
											.get();
									Elements links = doc.select("td[style]");
									int linknum = links.size();
									daysinweek = linknum / 5;
									int i = 0;
									linkstr = new String[linknum];
									// 注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
									for (Element link : links) {
										// 这里没有什么好说的。
										if (i / daysinweek == 0) {
											dayseveryweek[i] = link.text().trim();
										} else if (i / daysinweek == 1) {
											breakfastmenu[i - daysinweek * 1] = link
													.text().trim();
										} else if (i / daysinweek == 2) {
											morningtastemenu[i - daysinweek * 2] = link
													.text().trim();
										} else if (i / daysinweek == 3) {
											lunchmenu[i - daysinweek * 3] = link
													.text().trim();
										} else if (i / daysinweek == 4) {
											noontastemenu[i - daysinweek * 4] = link
													.text().trim();
										} else {
										}
										i++;
									}
									msgs = url[position];
									XihaiwanActivity.this
											.runOnUiThread(selectThread);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}.start();
					}

					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
					}
				});

			}
		}.start();

		// HttpGet httpget = new HttpGet(
		// "http://www.cnblogs.com/agileyanly/p/4052627.html");
		// HttpClient httpclient = new DefaultHttpClient();
		// InputStream Inputstream = null;
		// try {
		// httpreponse = httpclient.execute(httpget);
		// httpentity = httpreponse.getEntity();
		// Inputstream = httpentity.getContent();
		// BufferedReader reader = new BufferedReader(
		// new InputStreamReader(Inputstream, "GB2312"));
		// String result = "";
		// String line = "";
		// while ((line = reader.readLine()) != null) {
		// result = result + line;
		// msgs = line;
		// linecount++;
		// XihaiwanActivity.this.runOnUiThread(updateThread);
		// }
		// msgs = linecount + "";
		// XihaiwanActivity.this.runOnUiThread(updateThread);
		//
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// try {
		// Inputstream.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// // 等等之类的

	}
}