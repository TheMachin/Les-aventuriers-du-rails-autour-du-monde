package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

public class Server implements Runnable{
	private ServerSocket serverSocket;
	private int port;
	private String ip;
	private List<Thread> threads;
	int nbClientMax=5;
	static int nbClient=1;
	
	public Server(int port) {
		super();
		this.port=port;
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
	
	public void accept() throws IOException{
		
		threads = new ArrayList<Thread>();
		
		int i=0;
		while(true){
			Socket socket = serverSocket.accept();
			Runnable r = new MyThreadHandler(socket);
			Thread t=new Thread(r);
			System.out.println("lancement");
			t.start();
			threads.add(t);
			
		}
	}
	
	
	
	private static class MyThreadHandler implements Runnable {
        private Socket socket;

        MyThreadHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            nbClient++;
            System.out.println(nbClient + " JSONClient(s) connected on port: " + socket.getPort());
            if(nbClient>5){
            	try {
					closeSocket();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            try {
                // For JSON Protocol
                JSONObject jsonObject = receiveJSON();
                sendJSON(jsonObject);

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
				System.out.println("Got from client on port " + socket.getPort() + " " + jsonObject.get("key").toString());
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
              System.out.println("Sent to server: " + " " + jsonObject.toString());
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
