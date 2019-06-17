package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class server {

    public static void main(String argv[]) throws Exception{
        String clientSentence;
        String capitalizedSentence;
        Scanner cin = new Scanner(System.in);

        System.out.print("请输入进程端口号：");
        int port = cin.nextInt();
        ServerSocket welcomeSocket = new ServerSocket(port);

        System.out.println("等待连接···");
        while(true){
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("接收到一个TCP请求\n客户机地址是：" + connectionSocket.getInetAddress().getHostAddress());
            System.out.println("客户机端口号是：" + connectionSocket.getPort());
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println("接收到的字符串为：" + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
            System.out.println("会话结束，等待新连接···");
        }
    }
}
