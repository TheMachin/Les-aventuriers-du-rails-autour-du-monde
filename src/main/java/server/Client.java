package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.Socket;

public class Client {
	private String ip;
    private int port;
    private Socket socket;
	
    
    public Client(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
    
    public boolean connexion(){
    	try {
			socket = new Socket(ip, port);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
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
        JSONObject json = null;
        
        try {
			json = new JSONObject(line);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return json;

    }


    public void sendJSON(JSONObject jsonObject) throws IOException {
    	OutputStream out = socket.getOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(out);
        o.writeObject(jsonObject.toString());
        out.flush();
    }

}
