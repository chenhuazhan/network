package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class bankclient {

    public static void main(String argv[]) throws Exception {

        Scanner cin = new Scanner(System.in);

        System.out.print("请选择银行网点（对应IP地址或主机名）：");
        String address = cin.next();

        System.out.print("请选择服务类型（对应端口号）：");
        int port = cin.nextInt();
        String sendData, respondData;
        Socket clientSocket = new Socket(address, port);
        OutputStream os = clientSocket.getOutputStream();
        InputStream is = clientSocket.getInputStream();
        while (true) {
            //客户端发送信息
            System.out.print("客户：");
            sendData = cin.next();
            os.write(sendData.getBytes());
            os.flush();
            if(sendData.equals("再见")){
                clientSocket.close();
                break;
            }
            //客户端接收信息
            if(clientSocket.isConnected()){
                byte[] by = new byte[1024];
                int index = is.read(by);
                respondData = new String(by, 0, index);
                System.out.println("工作人员：" + respondData);
                if(respondData.equals("再见")){
                    clientSocket.close();
                    System.out.println("对方已离线！！！");
                    break;
                }
            }

        }
        cin.close();
    }
}
