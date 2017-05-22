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
	static Map<Integer,Thread> threads=new HashMap<Integer,Thread>();
	private MenuController menu;
	private PlateauController plateau;
	
	int nbClientMax=5;
	static List<Integer> noDispo = new ArrayList<Integer>();
	static int nbClient=1;
	static boolean menuBoolean=true;
	
	public Server(int port,MenuController menu) {
		super();
		this.port=port;
		this.menu=menu;
		noDispo.add(1);
		noDispo.add(2);
		noDispo.add(3);
		noDispo.add(4);
	}
	
	
	
	public PlateauController getPlateau() {
		return plateau;
	}



	public void setPlateau(PlateauController plateau) {
		this.plateau = plateau;
		menuBoolean=false;
	}
	
	public void closeAccess(){
		menuBoolean=false;
	}
	
	public void setPlateauControllerAtThread(Map<Integer,MyThreadHandler> listClient, PlateauController plateau){
		Set cles = listClient.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
		   int cle = (int) it.next(); // tu peux typer plus finement ici
		   listClient.get(cle).setPlateau(plateau);
		}
	}


	/**
	 * Création du serveur
	 * @param port
	 */
	public void etablirConnexionServer(int port){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeServer(){
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getIpAdress(){
		InetAddress ipAddr = null;
		try {
			ipAddr = Inet4Address.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ipAddr.getHostAddress();
	}
	
	public void accept(){
		
		while(menuBoolean){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MyThreadHandler r = new MyThreadHandler(socket,menu);
			Thread t=new Thread(r);
			t.setDaemon(true);
			t.start();
			
			menu.addNewPlayer(nbClient, r);
			threads.put(nbClient, t);
		}
	}
	
	public void sendMessage(MyThreadHandler t, JSONObject json) throws IOException{
		t.sendJSON(json);
	}
	
	public void broadcast(Map<Integer,MyThreadHandler> listClient,JSONObject json) throws IOException{
		if(listClient!=null){
			Set cles = listClient.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()){
			   int cle = (int) it.next(); // tu peux typer plus finement ici
			   listClient.get(cle).sendJSON(json);
			}
		}
	}
	
	public void broadcastExceptOne(Map<Integer,MyThreadHandler> listClient,JSONObject json, int no) throws IOException{
		if(listClient!=null){
			Set cles = listClient.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()){
			   int cle = (int) it.next(); // tu peux typer plus finement ici
			   if(cle!=no){
				   listClient.get(cle).sendJSON(json);
			   }
			}
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
        private PlateauController plateau;
        private int no=0;
        
        
        MyThreadHandler(Socket socket, MenuController menu) {
            this.socket = socket;
            this.menu=menu;
        }
        
        
        
        public void setPlateau(PlateauController plateau) {
			this.plateau = plateau;
			menuBoolean=false;
		}



		@Override
        public void run() {
        	
            nbClient++;
            if(noDispo.size()>=1){
        		no=noDispo.get(0);
        		if(no!=0){
        			noDispo.remove(noDispo.get(0));
        		}else{
        			try {
						refuseConnection("La partie est pleine");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}else{
            	try {
            		refuseConnection("La partie est pleine");
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
                	if(menuBoolean){
                		menu.getJSONFromClient(jsonFromClient, this);
                	}else{
                		jsonFromClient = plateau.getJSONFromClient(jsonFromClient);
                		if(jsonFromClient!=null){
                			sendJSON(jsonFromClient);
                		}
                	}
                	
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
		
		public void refuseConnection(String msg) throws IOException{
			JSONObject json = new JSONObject();
			try {
				json.put("refus", msg);
				sendJSON(json);
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeSocket();
		}

        public void closeSocket() throws IOException {
        	if(no!=0){
        		if(menuBoolean){
        			menu.clientDeconnecter(no);
        		}else{
        			if(plateau!=null){
        				plateau.clientDeconnecter(no);
        			}
        		}

    			noDispo.add(no);
        		threads.remove(no);
        		socket.close();
        		
        	}else{
        		socket.close();
        	}
            
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
			if(menuBoolean){
				this.start(port);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
