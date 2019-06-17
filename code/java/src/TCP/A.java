package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class A {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8000);
        System.out.println("服务器启动！");
        final Socket s = ss.accept();
        boolean b = true;
        InputStream is = s.getInputStream();
        //服务器把输入放入线程里面执行，不会影响主线程的接收信息功能
        Thread t = new Thread(){
            OutputStream os = s.getOutputStream();
            Scanner sc  = new Scanner(System.in);
            @Override
            public void run() {
                // TODO Auto-generated method stub
                boolean b = true;
                while(b){
                    try {
                        System.out.println("请输入：");
                        os.write(sc.next().getBytes());
                        os.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        while(b){
            byte[] bt = new byte[1024];
            int length = is.read(bt);
            String str = new String(bt,0,length);
            System.out.println("收到应答：" + str);
            System.out.println("请输入：");
        }


        is.close();
        s.shutdownInput();
        s.close();
        ss.close();

    }
}
