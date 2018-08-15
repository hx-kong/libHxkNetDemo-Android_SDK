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

public class GenPasswordEncrypt extends AppCompatActivity {

    SearchDev sd;
    MSDSearchDev msdSDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lan_dev);

        //empty password
        byte[]p= HXPassword.genPasswordKey("");
        System.out.printf("HXPassword 生成密码 =%2x %2x %2x %2x %2x %2x\n",p[0],p[1],p[2],p[3],p[4],p[5]);

        //ascii or utf-8 password
        p= HXPassword.genPasswordKey("123abc");
        System.out.printf("HXPassword 生成密码 =%2x %2x %2x %2x %2x %2x\n",p[0],p[1],p[2],p[3],p[4],p[5]);

    }
}
