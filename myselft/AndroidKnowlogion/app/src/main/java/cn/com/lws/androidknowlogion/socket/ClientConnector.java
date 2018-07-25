package cn.com.lws.androidknowlogion.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/15 . 15:18
 * 描述
 */

public class ClientConnector {
    
    private Socket mClient;
    //服务端Ip
    private String mDstName;
    //服务端口号
    private int mDesPort;
    
    private ConnectLinstener mListener;

    public ClientConnector(String mDstName, int mDesPort) {
        this.mDstName = mDstName;
        this.mDesPort = mDesPort;
    }
    public void connect() throws IOException {
        if (mClient == null){
            mClient=new Socket(mDstName,mDesPort);
        }
        InputStream inputStream=mClient.getInputStream();
        byte[] buffer=new byte[1024];
        int len = -1;
        while ((len=inputStream.read(buffer)) != -1){
            String data=new String(buffer,0,len);
            if (mListener != null){
                mListener.onReceiveData(data);
            }
        }
    }
    //认证方法
    public void auth(String authname) throws IOException {
        if (mClient != null){
            OutputStream outputStream=mClient.getOutputStream();
            outputStream.write(("#"+authname).getBytes());
        }
    }
    //发送指定客户端
    public void send(String receiver,String data) throws IOException {
        OutputStream outputStream=mClient.getOutputStream();
        outputStream.write((receiver+"#"+data).getBytes());
    }
    //断开连接
    public void disconnect() throws IOException {
        if (mClient != null){
            mClient.close();
            mClient=null;
        }
    }
    public void setOnConnectLinstener(ConnectLinstener linstener){
        this.mListener=linstener;
    }
}
