package android.zh.hxmcuframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.SocketException;

import hxkong.password.HXPassword;
import hxkong.msd.SearchDev;
import hxkong.msd.MSDSearchDev;
import hxkong.msd.MSDSearchDevInfo;
import hxkong.msd.MSDSearchDevListener;

public class SearchLanDevDemo extends AppCompatActivity {
    MSDSearchDev msdSDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lan_dev);
        //
        try {
            msdSDev=new MSDSearchDev(this);
            msdSDev.addListener(sdl);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msdSDev.quickSearch();
            }
        });
        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msdSDev.stopService();
            }
        });
        ((Button)findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msdSDev.startService(0);
            }
        });
    }

    MSDSearchDevListener sdl=new MSDSearchDevListener() {
        @Override
        public void Online(String ipv4, int port, MSDSearchDevInfo info) {
            Log.d("SearchDevListener","Online devame="+info.devname + " addr="+ipv4+":"+port);
        }
        @Override
        public void Update(String ipv4, int port, MSDSearchDevInfo info) {
            Log.d("SearchDevListener","Update devame="+info.devname + " addr="+ipv4+":"+port);
        }
        @Override
        public void Offline(String ipv4, int port, MSDSearchDevInfo info) {
            Log.d("SearchDevListener","Offline devame="+info.devname + " addr="+ipv4+":"+port);
        }
        @Override
        public void errmsg(String ipv4, int port, String msg, byte[] data, int datalen) {
            Log.d("SearchDevListener","msg"+msg + " addr="+ipv4+":"+port);
        }
    };
}
