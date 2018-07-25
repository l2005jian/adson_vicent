package cn.com.lws.androidknowlogion.socket;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/19 . 8:55
 * 描述
 */

public class UdpSocketClient {
    private static UdpSocketClient mUdpSocketClient;
    private ConnectListener mListener;
    private Thread mSendThread;
    private byte recevierData[] =new byte[1024];
    private String mSendHexString;
    private boolean isSend=false;
    public interface ConnectListener {
        void onReceiverData(String data);
    }
    public void setOnConnectionListener(ConnectListener listener){
        this.mListener=listener;
    }
    public static UdpSocketClient getInstance(){
        if (mUdpSocketClient == null){
            mUdpSocketClient=new UdpSocketClient();
        }
        return mUdpSocketClient;
    }
    Handler mHandler=new Handler() {
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
    public void onCreateConnector(final String ip,final int port,final int timeout){
        if (mSendThread == null){
            mSendThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if (!isSend){
                            continue;
                        }
                        DatagramSocket socket = null;
                            try {
                                socket=new DatagramSocket();
                                socket.setSoTimeout(timeout);
                                InetAddress serverAddress=InetAddress.getByName(ip);
                                byte data[]=mSendHexString.getBytes("utf-8");
                               DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverAddress, port);
                                DatagramPacket receivePacket=new DatagramPacket(recevierData,recevierData.length);
                                socket.send(sendPacket);
                                socket.receive(receivePacket);
                                Message msg=new Message();
                                msg.what=100;
                                Bundle bundle=new Bundle();
                                bundle.putString("data",new String(receivePacket.getData()));
                                msg.setData(bundle);
                                mHandler.sendMessage(msg);
                                socket.close();
                            } catch (SocketException e) {
                                e.printStackTrace();
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            isSend=false;
                     
                    }
                }
            });
            mSendThread.start();
        }
    }
    public void sendStr(final String str){
        mSendHexString=str;
        isSend=true;
    }
    
    
}
