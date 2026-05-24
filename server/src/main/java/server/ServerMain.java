package server;

import dataaccess.DataAccessException;
import dataaccess.MySQLAuthDAO;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();
        server.run(8080);
        try {
            new MySQLAuthDAO();
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }


        System.out.println("♕ 240 Chess Server");
    }
}
