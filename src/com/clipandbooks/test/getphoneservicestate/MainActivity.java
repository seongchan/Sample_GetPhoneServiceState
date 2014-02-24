/**
 * Copyright 2014 Hong,SeongChan (seongchan116@gmail.com)
 * 
 * 코드 작성에 대해서만 저작권한을 가지고 있습니다. 
 * 코드 참조에 대해서는 어떠한 관여하지 않습니다.
 * 
 * Create on $$Date$$
 * 
 * product : $(prject_name)
 * $$Source$$
 * $$Author$$
 * 
 * 안드로이드 세팅 페이지 이동에 대한 테스트용 코드입니다.
 */

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


/**
 * @version 1.0.0
 * @author Hong, SeongChan
 * 
 * 이 클래스는 메인 클래스이다. <br>
 * 
 */
public class MainActivity extends Activity {
    
    private Button mBtn1;                   // 테스트 시작 버튼
    private TextView mResultView;           // 결과 보기 View 
    private String mPhoneState;             // 단말 상태값
    private StateListener mStateListener;   // 단말 상태 리스너 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
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
    
    /**
     * 버튼 PhoneStateListener 클래스
     * @author Hong, SeongChan
     *
     */
    public class StateListener extends PhoneStateListener {

        private static final String TAG = "SEONGCHAN";
        
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            // TODO Auto-generated method stub
            super.onServiceStateChanged(serviceState);
            
            switch (serviceState.getState()) {
            case ServiceState.STATE_EMERGENCY_ONLY :                // Emergency State로 반환된 경우 
                Log.d(TAG, " onServiceStateChanged(STATE_EMERGENCY_ONLY)");
                setResultText("ServiceState is 2(STATE_EMERGENCY_ONLY)");
                break;
                
            case ServiceState.STATE_IN_SERVICE :                    // Servcie State로 반환된 경우 
                Log.d(TAG, " onServiceStateChanged(STATE_IN_SERVICE)");
                setResultText("ServiceState is 0(STATE_IN_SERVICE)");
                break;
            case ServiceState.STATE_OUT_OF_SERVICE :                // OUT-OF-Service State로 반환된 경우 
                Log.d(TAG, " onServiceStateChanged(STATE_OUT_OF_SERVICE)");
                setResultText("ServiceState is 1(STATE_OUT_OF_SERVICE)");
                break;
            case ServiceState.STATE_POWER_OFF :                     // Power Off State로 반환된 경우
                Log.d(TAG, " onServiceStateChanged(STATE_POWER_OFF)");
                setResultText("ServiceState is 3(STATE_POWER_OFF)");
                break;            
            }
        }
    }

}
