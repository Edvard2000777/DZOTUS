import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class Player implements Runnable {

    public static int counter;
    private Player enemy;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private final int id;
    private String ip;


    public Player(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.id = id;
        counter++;
       
        ip = socket.getInetAddress().getLocalHost().getHostAddress();
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
        new Thread(this).start();
    }

    @Override
    public void run() {
        String str = "";
        try {
            while (true) {
                str = bufferedReader.readLine();
                System.out.println("client " + ip + " sent " + str);
                if (checkForIp(str)) {
                    System.out.println("IP recognized");
                    handleIp(); // handleIp(str)
                } else if (checkForCommand(str)) {
                    System.out.println("Command recognized");
                    handleCommand(str);
                } else {
                    System.out.println("Invalid command");
                    handlerErrorCommand(str);
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("client  " + ip + " discount ");
    }

   
    private boolean checkForIp(String s) {
        /// String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return s.contains("ip:::");
    }

    private boolean checkForCommand(String s) {
        // O (1->9) O8
        // X (1->9) X3
        if (s.length() == 2 && (s.charAt(0) == 'X' || s.charAt(0) == 'O')) {
            return 0 <= (s.charAt(1) - '0') && (s.charAt(1) - '0') <= 9;
        }
        return false;
    }

    private void handlerErrorCommand(String s) {
        notifyEnemy("err" + s);
    }

    private void handleIp() throws Exception {
        List<Player> players = ApplicationServer.connectedPlayers;
        if (players.size() == 1) return;
        Player enemy = players.get(players.size() - id);
        setEnemy(enemy);
        // Если закоммитить то оба игрока будут вводить ip
        enemy.setEnemy(this);
    }

    private void handleCommand(String s) {
        notifyEnemy(s);
    }

    public void setEnemy(Player player) {
        enemy = player;

    }

    private void notifyEnemy(String msg) {
        enemy.printWriter.println(msg);
        enemy.printWriter.flush();
    }

    public String getIp() {
        return ip;
    }
}

