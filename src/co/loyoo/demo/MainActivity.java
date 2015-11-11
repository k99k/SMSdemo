package co.loyoo.demo;


import com.acying.dsms.DSms;
import com.acying.dsms.InitCallBack;
import com.acying.dsms.SMSListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "dsms_demo";
	
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4;
	private TextView txt1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.bt1 = (Button) this.findViewById(R.id.bt1);
		this.bt2 = (Button) this.findViewById(R.id.bt2);
		this.bt3 = (Button) this.findViewById(R.id.bt3);
		this.bt4 = (Button) this.findViewById(R.id.bt4);
		this.txt1 = (TextView) this.findViewById(R.id.txt1);
		this.bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DSms.init(MainActivity.this, new InitCallBack() {
					
					@Override
					public void initRe(int result) {
						String re = "init re:"+result;
						Log.i(TAG, re);
						if (result != 0 && result != 1 && result != 91 && result != 109) {
							//建议不使用本sdk支付方式
						}else{
							//可以使用
						}
					}
				});
			}
		});
		this.bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DSms.pay(MainActivity.this,100,"购买100个钻石,将花费您1元,从您的手机话费中支付,点击确定开始支付.",false,0,new SMSListener() {
					
					@Override
					public void smsOK() {
						String re = "pay OK";
						Log.i(TAG, re);	
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						
					}
					
					@Override
					public void smsFail(int errorCode) {
						String re = "pay failed:"+errorCode;
						Log.i(TAG, re);	
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						
					}
					
					@Override
					public void smsClose(int reCode) {
						//如果reCode为1,则为用户取消支付,如果其他值,则为窗口被关闭
						String re = "sms Close"+reCode;
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						Log.e(TAG, "pay close:"+reCode);							
					}
				});
			}
		});
		this.bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DSms.pay(MainActivity.this,200,"购买100个钻石,将花费您2元,从您的手机话费中支付,点击确定开始支付.",true,0,new SMSListener() {
					
					@Override
					public void smsOK() {
						String re = "pay OK";
						Log.i(TAG, re);	
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						
					}
					
					@Override
					public void smsFail(int errorCode) {
						String re = "pay failed:"+errorCode;
						Log.i(TAG, re);	
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						
					}
					
					@Override
					public void smsClose(int reCode) {
						String re = "sms Close"+reCode;
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						Log.e(TAG, "payV close:"+reCode);							
					}
				});
			}
		});
		
		this.bt4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int fee = 0;
				String feeStr = txt1.getText().toString();
//				Toast.makeText(MainActivity.this, "fee:"+feeStr, Toast.LENGTH_SHORT).show();
				if(feeStr.matches("\\d+")){
					fee = Integer.parseInt(feeStr);
					if(fee < 100 || fee > 3000){
						
						Toast.makeText(MainActivity.this, "费用不正确", Toast.LENGTH_SHORT).show();
						return ;
					}
					
				}else{
					Toast.makeText(MainActivity.this, "费用不正确", Toast.LENGTH_SHORT).show();
				}
				
				
				DSms.pay(MainActivity.this,fee,"购买100个钻石,将花费您"+(fee/100)+"元,从您的手机话费中支付,点击确定开始支付.",true,0,new SMSListener() {
					
					@Override
					public void smsOK() {
						String re = "pay OK";
						Log.i(TAG, re);	
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						
					}
					
					@Override
					public void smsFail(int errorCode) {
						String re = "pay failed:"+errorCode;
						Log.i(TAG, re);	
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						
					}
					
					@Override
					public void smsClose(int reCode) {
						String re = "sms Close"+reCode;
						Toast.makeText(MainActivity.this, re, Toast.LENGTH_SHORT).show();
						Log.e(TAG, "payV close:"+reCode);							
					}
				});
			}
		});
	}

	@Override
	protected void onDestroy() {
		//对短信监听器的清理,务必加上
		DSms.exit(this);
		super.onDestroy();
	}
	
	
	
}
