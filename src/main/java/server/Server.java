package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import controlor.MenuController;
import controlor.PlateauController;

public class Server implements Runnable{
	private ServerSocket serverSocket;
	private int port;
	private String ip;
	private Map<Integer,Thread> threads=new HashMap<Integer,Thread>();
	private MenuController menu;
	private PlateauController plateau;
	
	int nbClientMax=5;
	static List<Integer> noDispo = new ArrayList<Integer>();
	static int nbClient=1;
	
	public Server(int port,MenuController menu) {
		super();
		this.port=port;
		this.menu=menu;
		noDispo.add(1);
		noDispo.add(2);
		noDispo.add(3);
		noDispo.add(4);
	}
	
	public void etablirConnexionServer(int port){
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("server start avec succès");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Server non start");
		}
	}
	
	public void getIpAdress(){
		InetAddress ipAddr = null;
		try {
			ipAddr = Inet4Address.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(ipAddr.getHostAddress());
	}
	
	public void accept() throws IOException{
		
		while(true){
			Socket socket = serverSocket.accept();
			MyThreadHandler r = new MyThreadHandler(socket,menu);
			Thread t=new Thread(r);
			System.out.println("lancement");
			t.setDaemon(true);
			t.start();
			
			menu.addNewPlayer(nbClient, r);
			System.out.println("id mit");
			threads.put(nbClient, t);
		}
	}
	
	public void sendMessage(MyThreadHandler t, JSONObject json) throws IOException{
		t.sendJSON(json);
	}
	
	public void Broadcast(Map<Integer,MyThreadHandler> listClient,JSONObject json) throws IOException{
		Set cles = listClient.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next(); // tu peux typer plus finement ici
		   System.out.println(cle+" braodcast");
		   listClient.get(cle).sendJSON(json);
		}
	}
	
	public void closeAllSocket(Map<Integer,MyThreadHandler> listClient) throws IOException{
		Set cles = listClient.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next(); // tu peux typer plus finement ici
		   listClient.get(cle).closeSocket(); // tu peux typer plus finement ici
		}
	}
	
	public void closeAllThread() throws InterruptedException{
		Set cles = threads.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next(); 
		   if(!threads.get(cle).isInterrupted()){
			   threads.get(cle).interrupt();
			   threads.get(cle).join();
		   }
		   
		}
	}
	
	public static class MyThreadHandler implements Runnable {
        private Socket socket;
        private MenuController menu;
        private int no=0;
        
        MyThreadHandler(Socket socket, MenuController menu) {
            this.socket = socket;
            this.menu=menu;
        }
        
        @Override
        public void run() {
        	
            nbClient++;
            System.out.println(no + " JSONClient(s) connected on port: " + socket.getPort()+ " "+nbClient);
            System.out.println(noDispo.toString());
            if(noDispo.size()>=1){
        		no=noDispo.get(0);
        		noDispo.remove(noDispo.get(0));
        	}else{
            	try {
					closeSocket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            try {
                // For JSON Protocol
            	JSONObject json = new JSONObject();
            	try {
					json.put("id", no);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                sendJSON(json);
                while(true){
                	JSONObject jsonFromClient = receiveJSON();
                	JSONObject jsonSendAtClient = menu.getJSONFromClient(jsonFromClient);
                	sendJSON(jsonSendAtClient);
                }
                
            } catch (IOException e) {

            } finally {
                try {
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void closeSocket() throws IOException {
        	System.out.println("socket fermé");
        	if(no!=0){
        		System.out.println(noDispo.toString());
        		menu.clientDeconnecter(no);
        		noDispo.add(no);
        		System.out.println(noDispo.toString());
        	}
            socket.close();
        }

        public JSONObject receiveJSON() throws IOException {
            InputStream in = socket.getInputStream();
            ObjectInputStream i = new ObjectInputStream(in);
            String line = null;
            try {
            	line = (String) i.readObject();

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();

            }

            JSONObject jsonObject =null;
            try {
            	jsonObject = new JSONObject(line);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return jsonObject;
        }


        public void sendJSON(JSONObject jsonObject) throws IOException {

             OutputStream out = socket.getOutputStream();
              ObjectOutputStream o = new ObjectOutputStream(out);
             o.writeObject(jsonObject.toString());
              out.flush();
              System.out.println("Sent to client: " + " " + jsonObject.toString());
        }
    }

    public void start(int port) throws IOException{
    	etablirConnexionServer(port);
        accept();
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.start(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
