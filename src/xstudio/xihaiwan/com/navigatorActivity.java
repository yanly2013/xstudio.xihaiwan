package xstudio.xihaiwan.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class navigatorActivity extends Activity {
	private Button menu = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.navigatoractivity);

	    menu = (Button)findViewById(R.id.gotomenu);
	    
	    menu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(navigatorActivity.this,
						XihaiwanActivity.class);
				startActivity(it);	
			}
		});



	
	}
}
