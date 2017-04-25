package controlor;

import java.io.IOException;

import server.Client;
import server.Server;
import vue.InterfaceMenu;

public class MenuController {

	private InterfaceMenu interfaceMenu;
	Server server;
	Client client;
	
	public void createServer(){
		server = new Server(7777);
		System.out.println("Lancement");
		Thread t = new Thread(server);
		t.start();
		System.out.println("Lancé");
		
	}
	
	public void clientJoinServer(String ip){
		client = new Client(ip, 7777);
		if(client.connexion()){
			System.out.println("Client connecté !");
		}
	}
}
