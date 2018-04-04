package com.example.aidlclient;



import com.example.aidlserver.aidl.Calc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener{

	private EditText input1,input2;
	private Button sure;
	private TextView result;
	private Calc calc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
        
    }
	private void initView() {
		input1=(EditText) findViewById(R.id.input1);
        input2=(EditText) findViewById(R.id.input2);
        sure=(Button) findViewById(R.id.sure);
        result=(TextView) findViewById(R.id.result);
        sure.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:
			int num1,num2;
			String n1 = input1.getText().toString();
			if (TextUtils.isEmpty(n1)) {
				return;
			}else{
				num1 = Integer.parseInt(n1);
			}
			String n2 = input2.getText().toString();
			if (TextUtils.isEmpty(n2)) {
				return;
			}else{
				num2 = Integer.parseInt(n2);
			}
			try {
				//客户端调用aidl接口
				int num = calc.add(num1, num2);
				result.setText(num+"");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		
	}
	private void bindService() {
		//获取服务端的服务
		Intent intent =new Intent();
		intent.setComponent(new ComponentName("com.example.aidlserver","com.example.aidlserver.AddService"));
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	ServiceConnection conn=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			//断开连接释放资源
			calc=null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			//得到服务端接口对象
			calc = Calc.Stub.asInterface(binder);  
		}
	};
	
	 @Override
	protected void onDestroy() {
		unbindService(conn);
	};
	
}
