package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class bankserver {

    public static void main(String argv[]) throws Exception{
        Scanner cin = new Scanner(System.in);

        System.out.print("请输入服务类型（对应端口号）：");
        int port = cin.nextInt();
        ServerSocket welcomeSocket = new ServerSocket(port);

        System.out.println("等待连接···");
        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            OutputStream os = connectionSocket.getOutputStream();
            InputStream is = connectionSocket.getInputStream();

            while (true){
                if(connectionSocket.isConnected()){
                    byte[] bt = new byte[1024];
                    int length = is.read(bt);
                    String respondData = new String(bt, 0, length);
                    System.out.println("客户：" + respondData);
                    if(respondData.equals("再见")){
                        connectionSocket.close();
                        System.out.println("对方已离线！！！");
                        break;
                    }
                }

                if(connectionSocket.isConnected()){
                    System.out.print("工作人员：");
                    String sendData = cin.next();
                    os.write(sendData.getBytes());
                    os.flush();
                    if(sendData.equals("再见")){
                        connectionSocket.close();
                        break;
                    }
                }

            }
            System.out.println("会话结束，等待新连接···");
        }
    }
}
