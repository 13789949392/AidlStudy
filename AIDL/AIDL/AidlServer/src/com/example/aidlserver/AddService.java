package com.example.aidlserver;

import com.example.aidlserver.aidl.Calc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class AddService extends  Service{
    /**
     * 当绑定该服务时调用该方法
     */
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return binder;
	}

	public IBinder  binder=new Calc.Stub() {
		//实现aidl接口
		public int add(int num1, int num2) throws RemoteException {
			Log.d("Tag", "收到远程传来的参数num1="+num1+"  ,num2="+num2);
			return num1+num2;
		}
	};
	
}
