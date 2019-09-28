package com.azhansy.aidl_library;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.azhansy.aidl.ILoginAidlInterface;


/**
 * @author dashu
 * @date 2019-09-28
 * describe:
 */
public class LoginAidlService extends Service {

    private static final String TAG = "LoginAidlService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private IBinder binder = new ILoginAidlInterface.Stub() {
        @Override
        public String getLoginToken() {
            return "third app token!!!";
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
