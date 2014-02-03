package com.clipandbooks.test.getphoneservicestate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private Button mBtn1;
    private TextView mResultView;
    private String mPhoneState;
    
    private StateListener mStateListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /**
         * ServiceState는 Service State 변경이 될때에 값을 얻을 수 있다.
         * ServiceState.getState() 호출만 했을 경우는 STATE_OUT_OF_SERVICE 으로만 반환된다.
         */
        
        mPhoneState = "ServiceState checking...";
        TelephonyManager teleManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mStateListener = new StateListener();
        teleManager.listen(mStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
        
        mBtn1 = (Button)findViewById(R.id.btn1);
        mResultView = (TextView)findViewById(R.id.result_value);
        
        mBtn1.setOnClickListener(new OnClickListener() {           
            @Override
            public void onClick(View v) {
                Log.d("LTS", "ServiceState is "+mPhoneState);
                mResultView.setText(mPhoneState);
            }
        });
    }

    public void setResultText(String text) {
        mPhoneState = text;
    }
    
    public class StateListener extends PhoneStateListener {

        private static final String TAG = "SEONGCHAN";
        
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            // TODO Auto-generated method stub
            super.onServiceStateChanged(serviceState);
            
            switch (serviceState.getState()) {
            case ServiceState.STATE_EMERGENCY_ONLY : 
                Log.d(TAG, " onServiceStateChanged(STATE_EMERGENCY_ONLY)");
                setResultText("ServiceState is 2(STATE_EMERGENCY_ONLY)");
                break;
            case ServiceState.STATE_IN_SERVICE : 
                Log.d(TAG, " onServiceStateChanged(STATE_IN_SERVICE)");
                setResultText("ServiceState is 0(STATE_IN_SERVICE)");
                break;
            case ServiceState.STATE_OUT_OF_SERVICE : 
                Log.d(TAG, " onServiceStateChanged(STATE_OUT_OF_SERVICE)");
                setResultText("ServiceState is 1(STATE_OUT_OF_SERVICE)");
                break;
            case ServiceState.STATE_POWER_OFF : 
                Log.d(TAG, " onServiceStateChanged(STATE_POWER_OFF)");
                setResultText("ServiceState is 3(STATE_POWER_OFF)");
                break;            
            }
        }
    }

}
