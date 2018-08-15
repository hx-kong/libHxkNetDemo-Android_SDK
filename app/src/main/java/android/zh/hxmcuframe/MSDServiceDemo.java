package android.zh.hxmcuframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import hxkong.base.HXNetProtocol;
import hxkong.base.TzhNetFrame_Cmd;
import hxkong.cacahe.HxNetCacheCtrl;
import hxkong.cacahe.HxNetCacheDelegate;
import hxkong.msd.MSDListener;
import hxkong.msd.MSDPacket;
import hxkong.msd.MSDService;

public class MSDServiceDemo extends AppCompatActivity {

    MSDService mudp=null;
    HxNetCacheCtrl hxc=null; //串口转HXNET协议缓存处理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msdservice);

        //启动例子
        mudp=new MSDService(0);
        mudp.addListener(ml);
        //缓存服务
        hxc=new HxNetCacheCtrl();
        hxc.addListener(hxls);

        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                byte[] data = HXNetProtocol.hxNetCreateFrame("1",0,null,true);
                String[] astr=new String[]{"123","456","HX-KONG@1A9F63","666"};
                mudp.sendDUDP(data,"228.0.0.8", 2288,astr);
            }
        });
        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取状态
                ctrl_getStatus();
            }
        });
        ((Button)findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //广播获取搜索硬件
                    byte[] s2 = new byte[]{(byte) 0xaa,(byte) 0,(byte) 0,(byte) 0xba};
                    mudp.sendto(s2, "228.0.0.8", 2288);
            }
        });
    }

    //获取LED设备状态
    private void ctrl_getStatus() {
        byte[] key = new byte[6];
        byte[] para = new byte[7];
        System.arraycopy(key,0,para,0,6);//密码钥匙
        para[6]=0x00;
        byte[] data = HXNetProtocol.hxNetCreateFrame("LED", para.length, para, true);
        mudp.sendto(data, "192.168.0.105", 12345);
        System.out.println("get status.....");
    }

    //MSD服务回调
    MSDListener ml=new MSDListener() {

        @Override
        public void send_cb(MSDPacket packet) {

        }

        @Override
        public void recvfrom(byte[] buff, int len, String ipv4, int port)
        {
            Log.v("MSDService","recvfrom ip="+ipv4 + " , len="+len);
            hxc.recvCache(buff,len,ipv4,port);
        }

        @Override
        public void err(int codeID, String msg, MSDPacket packet) {
            Log.v("MSDService","recvfrom codeID="+codeID + " , msg="+msg);
        }
    };

    //HXNET数据缓存调度
    HxNetCacheDelegate hxls=new HxNetCacheDelegate() {
        @Override
        public void HxNetDataRecv(TzhNetFrame_Cmd frame, String devUUID) {
        }

        @Override
        public void HxNetDataRecv(TzhNetFrame_Cmd frame, String fromIP, int port) {
            Log.v("MSDService","lan HxNetDataRecv fromIP="+fromIP +" FLAG="+new String(frame.flag));
        }

    };
}
