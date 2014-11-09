package xstudio.xihaiwan.com;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class contentlistActivity extends ListActivity {
	private String[] mListTitle = null;
	private String[] mlistUrl = null;
	private String[] mListdate = null;
	private SimpleAdapter adapter = null;
	ListView mListView = null;
	private ImageButton backimg = null;
	private TextView title = null;
	ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mListView = getListView();

		final Runnable selectThread = new Runnable() {

			public void run() {
				// 更新UI
				setListAdapter(adapter);

			}

		};
		new Thread() {
			public void run() {
				try {
					Document doc = Jsoup.connect(
							"http://www.cnblogs.com/agileyanly/p/4075136.html")
							.get();
					Elements links = doc.select("span[style]");
					int linknum = links.size();
					int i = 0;
					mListTitle = new String[linknum];
					mlistUrl = new String[linknum];
					mListdate = new String[linknum];
					// 注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
					for (Element link : links) {
						// 这里没有什么好说的。
						String content = link.text();
						String[] aa = content.split(" ", 3);
						mListTitle[i] = aa[0];
						mlistUrl[i] = aa[1];
						mListdate[i] = aa[2];
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("title", mListTitle[i]);
						item.put("text", mListdate[i]);
						mData.add(item);
						i++;
					}
					contentlistActivity.this.runOnUiThread(selectThread);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		adapter = new SimpleAdapter(this, mData,
				android.R.layout.simple_list_item_2, new String[] { "title",
						"text" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
//				Toast.makeText(
//						contentlistActivity.this,
//						"您选择了标题：" + mlistUrl[position] + "内容："
//								+ mListdate[position], Toast.LENGTH_LONG).show();
				Intent it = new Intent();
				it.putExtra("selecturl", mlistUrl[position]);
				it.setClass(contentlistActivity.this, WebcontentActivity.class);
				startActivity(it);
			}
		});
		super.onCreate(savedInstanceState);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.menutitlebar); // titlebar为自己标题栏的布局

		ExitApplication.getInstance().addActivity(this);
		title = (TextView)findViewById(R.id.title);
		title.setText("校园资讯");
		
		backimg = (ImageButton)findViewById(R.id.backimagebtn);
		backimg.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(contentlistActivity.this,
						navigatorActivity.class);
				startActivity(it);	
			}
		});
		
//		TextView text1 = (TextView)mListView.findViewById(android.R.id.text1);
//		text1.setTextColor(Color.BLACK);
	}
}
