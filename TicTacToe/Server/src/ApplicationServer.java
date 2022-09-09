import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ApplicationServer {
    public static List<Player> connectedPlayers = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8080);
        System.out.println("SERVER STARTED");
        while (true) {
            Socket sock = server.accept();
            if (connectedPlayers.size() < 2) {
                Player player = new Player(sock, connectedPlayers.size() + 1);
                connectedPlayers.add(player);
                System.out.println("client " + player.getIp() + " connected ");
            } else {
                System.out.println("Server can serve only 2 players");
            }
        }
    }
}
