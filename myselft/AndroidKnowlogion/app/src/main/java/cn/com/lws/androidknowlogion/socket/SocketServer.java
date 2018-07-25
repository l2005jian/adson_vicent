package cn.com.lws.androidknowlogion.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 创建者 Ferry
 * 创建时间  2018/6/15 . 16:03
 * 描述
 */

public class SocketServer {
    //报存不同的客户端
    private static Map<String,Socket> mClient=new LinkedHashMap<>();
    public static void main(String[] args) throws IOException {
        int port = 9999;
        //创建服务器
        ServerSocket serverSocket=new ServerSocket(port);
//        UdpSocketClient mUdpclient=UdpSocketClient.getInstance();
//        mUdpclient.onCreateConnector("server_ip", port,timeout);
 /*       mUdpclient.setOnConnectionListener(new UdpSocketClient.ConnectListener() {
            @Override
            public void onReceiverData(String data) {
                //接收服务器返回的数据
            }
        });*/
        while (true){
            final Socket socket=serverSocket.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream=socket.getInputStream();
                        byte[]  buffer=new byte[1024];
                        int len= -1;
                        while ((len=inputStream.read(buffer)) != -1){
                            String data=new String(buffer,0,len);
                            if (data.startsWith("#")){
                                mClient.put(data,socket);
                            }else {
                                String[] split=data.split("#");
                                Socket c=mClient.get("#"+split[0]);
                                OutputStream outputStream=c.getOutputStream();
                                outputStream.write(split[1].getBytes());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            
            
        }
        
        
    }

    
}
