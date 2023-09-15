package server;
//sDTOs left here, essentially doing nothing
import util.SocketWrapper;
import util.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class InputThreadServer implements Runnable{
    server s;
    Thread t;

    public InputThreadServer(server s){
        this.s = s;
        t = new Thread(this,"Input Thread of Server");
        t.start();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String next;
        while (true){
            next = scanner.nextLine();
            if (next.strip().equalsIgnoreCase("Stop")) {

                var sDTO = new stopDTO(true);

                for (String ss : s.getCompanyNetworkMap().keySet()) {
                    try {
                        s.getCompanyNetworkMap().get(ss).write(sDTO);
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.out.println("Client abruptly closed previously");
                    }
                }
            }

            try {
                s.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
