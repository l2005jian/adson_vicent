package cn.com.lws.androidknowlogion.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/19 . 8:55
 * 描述
 */

public class TcpSocketClient {
    private static TcpSocketClient mTcpSocketClient;
    private Socket mClient;
    private  ConnectLinstener mListener;
    private Thread mConnectThread;
    public interface ConnectLinstener {
        void onReceiverData(String data);
    }
    
    public  void setOnConnectLinstener (ConnectLinstener linstener){
        this.mListener=linstener;
    }
    
    public static TcpSocketClient getInstance(){
        if (mTcpSocketClient == null){
            mTcpSocketClient=new TcpSocketClient();
        }
        return mTcpSocketClient;
    }
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    if (mListener != null){
                        mListener.onReceiverData(msg.getData().getString("data"));
                    }
                    break;
            }
        }
    };
    public void createConnect(final String mSerIP,final int mSerPort){
        if (mConnectThread == null){
            mConnectThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        connect(mSerIP,mSerPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void connect(String mSerIP, int mSerPort) throws IOException {
        if (mClient == null){
            try {
                mClient=new Socket(mSerIP,mSerPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream inputStrea=mClient.getInputStream();
        byte[] buffer=new byte[1024];
        int len=-1;
        while ((len=inputStrea.read(buffer)) != -1){
            String data=new String(buffer,0,len);
            Message message=new Message();
            message.what=100;
            Bundle bundle=new Bundle();
            bundle.putInt("data", Integer.parseInt(data));
            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }
    public void send(String data) throws IOException {
        OutputStream outputStream=mClient.getOutputStream();
        outputStream.write(data.getBytes());
    }
    public void disconnect() throws IOException {
        if (mClient != null){
            mClient.close();;
            mClient =null;
        }
    }

}
