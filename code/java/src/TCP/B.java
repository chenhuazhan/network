package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class B {
    public static void main(String[] args) throws UnknownHostException, IOException {
        final Socket client = new Socket("127.0.0.1",8000);
        Scanner sc = new Scanner(System.in);
        System.out.println("客户端启动");
        OutputStream os = client.getOutputStream();
        //新建一个线程用来接收信息，不影响主线程的输入发送信息。
        Thread t = new Thread(){
            InputStream is = client.getInputStream();
            @Override
            public void run() {
                // TODO Auto-generated method stub
                boolean b = true;
                while(b){
                    try {
                        byte[] bt = new byte[1024];
                        int length = is.read(bt);
                        String str = new String(bt,0,length);
                        System.out.println("收到应答：" + str);
                        System.out.println("请输入：");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        boolean b = true;
        while(b){
            System.out.println("请输入：");
            os.write(sc.next().getBytes());
            os.flush();
        }
        os.close();
        client.shutdownOutput();
        client.close();
    }
}