package android.zh.hxmcuframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import hxkong.yun.McuWebDevItem;
import hxkong.yun.McuWebDevListener;
import hxkong.yun.McuWebDevice;

public class McuWebDeviceDemo extends AppCompatActivity {

    McuWebDevice mwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcu_web_device);

        mwd=new McuWebDevice();
        mwd.addListener(weblisterner);
        mwd.getDevices("ab1122");
      //  mwd.deleteDevice("ab1122","30AEA44B0F1130AEA44B0F10");
    }

    McuWebDevListener weblisterner= new McuWebDevListener() {
        @Override
        public void deviceList(List<McuWebDevItem> devList) {
            Log.v("McuWebDeviceDemo","devList count="+devList.size());
        }

        @Override
        public void removeDevice(String del_caid, String del_uuid) {
            Log.v("McuWebDeviceDemo","removeDevice caid="+del_caid +" , uuid="+del_uuid);
        }

        @Override
        public void getDevListFail() {

        }

        @Override
        public void removeDeviceFail() {

        }
    };
}
