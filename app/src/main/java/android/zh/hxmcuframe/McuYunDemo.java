package android.zh.hxmcuframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import hxkong.base.TzhNetFrame_Cmd;
import hxkong.cacahe.HxNetCacheCtrl;
import hxkong.cacahe.HxNetCacheDelegate;
import hxkong.yun.McuYun;
import hxkong.yun.McuYunListener;
import hxkong.yun.McuYunParameter;

public class McuYunDemo extends AppCompatActivity {

    McuYun mcuy = new McuYun("demo_dpid_test");
    HxNetCacheCtrl hxc=new HxNetCacheCtrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("mcuyun MainActivity begin");

        mcuy.addListener(mculis);
        //缓存服务
        hxc.addListener(hxls);
        //获取分配入口
        mcuy.getIotDispath();
}

    McuYunListener mculis = new McuYunListener() {

        @Override
        public void dispatchServer(String ipv4, int port) {
            Log.v("mcuyun dispatchServer", ipv4 + ":" + port);
            mcuy.connect_start(ipv4, port);
        }

        @Override
        public void dispatchError(int errID, String msg) {
            Log.v("mcuyun dispatchError", "msg=" + msg);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mcuy.getIotDispath();
        }

        @Override
        public void connect_cb(boolean b) {
            Log.v("mcuyun connect_cb", "b="+b);
        }

        @Override
        public void connectedDTRS() {
            String uid="D7E177-1AFE34D7E177";
            Log.v("mcuyun connectedDTRS","subscr dev="+uid);
            //订阅
            mcuy.iotSubscr(uid);
        }

        @Override
        public void eventKeep(long rtt) {
            Log.v("mcuyun eventKeep","rtt="+rtt+"ms");
        }

        @Override
        public void eventRecvData(String devUUID, byte[] buf) {
            hxc.recvCache(buf,buf.length,devUUID);
        }

        @Override
        public void eventSubscrDev(String devUUID) {
            Log.v("mcuyun eventSubscrDev","devUUID="+devUUID);
            String uid="D7E177-1AFE34D7E177";
            mcuy.iotIsOnline(uid);
            mcuy.iotIsOnline("123123123");
        }

        @Override
        public void eventUnsubscrDev(String devUUID) {

        }

        @Override
        public void eventIsOnline(String devUUID, boolean online) {
            Log.v("mcuyun eventIsOnline","devUUID="+devUUID + "  online="+online);
        }

        @Override
        public void disconnect() {
            Log.v("mcuyun disconnect","disconnect");
        }
    };

    //HXNET数据缓存调度
    HxNetCacheDelegate hxls=new HxNetCacheDelegate() {
        @Override
        public void HxNetDataRecv(TzhNetFrame_Cmd frame, String devUUID) {
            Log.v("mcuyun MSDService","HxNetDataRecv devUUID="+devUUID+" flag="+frame.flag);
        }

        @Override
        public void HxNetDataRecv(TzhNetFrame_Cmd frame, String fromIP, int port) {
        }

    };

}
