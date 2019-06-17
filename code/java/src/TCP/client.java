package TCP;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {

    public static void main(String argv[]) throws Exception{
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Scanner cin = new Scanner(System.in);

        System.out.print("请输入服务器IP地址或主机名：");
        String address = cin.next();

        System.out.print("请输入服务器服务进程端口号：");
        int port = cin.nextInt();
        Socket clientSocket = new Socket(address, port);
        System.out.print("请输入要发送的内容：");
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();

        System.out.println("服务器返回的结果是:" + modifiedSentence);
        clientSocket.close();
        cin.close();
    }
}
