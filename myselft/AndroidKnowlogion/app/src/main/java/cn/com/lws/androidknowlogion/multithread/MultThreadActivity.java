package cn.com.lws.androidknowlogion.multithread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import cn.com.lws.androidknowlogion.R;

public class MultThreadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_thread);












































/*
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d("AA",Thread.currentThread().getName()+"启动,time"+System.currentTimeMillis());
                Thread.sleep(2000);
                return "AA";
            }
        };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Log.d("AA",Thread.currentThread().getName()+"启动,time:"+System.currentTimeMillis());
        }
    };

        FutureTask<String> futureTask=new FutureTask<String>(callable);

        Thread thread1=new Thread(futureTask);
        Thread thread2=new Thread(runnable);

        thread1.start();

        try{
            futureTask.get();
            thread2.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

*/





      /*  MyRunnable runnable=new MyRunnable();
        Thread thread=new Thread(runnable);
        thread.start();*/

       /* MyThread thread=new MyThread();
        thread.start();*/
        
    }

   /* class MyThread extends Thread {

        private String name;
        public MyThread(){
            name="AA";
        }

        @Override
        public void run() {
            System.out.println(name);
        }
    }*/
    /*class MyRunnable implements Runnable{

        public MyRunnable() {
        }

        @Override
        public void run() {
            System.out.println("子线程ID："+Thread.currentThread().getId());
        }
    }*/
}
