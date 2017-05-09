package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import controlor.MenuController;
import controlor.PlateauController;
import vue.Plateau;

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends Thread{
	private String ip;
    private int port;
    private Socket socket;
    private MenuController menu; 
    private PlateauController plateau; 
    private Timer t;
    private TimerTask tt;
    private boolean menuBoolean;
	
    
    public Client(String ip, int port, MenuController menu) {
		super();
		this.ip = ip;
		this.port = port;
		this.menu=menu;
		this.menuBoolean=true;
	}
    
    public void setPlateauController(PlateauController plateau){
    	menuBoolean=false;
    	this.plateau=plateau;
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
    
    public synchronized JSONObject receiveJSON() throws IOException {
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
    

    public void sendJsonAtController(JSONObject json) throws JSONException{
    	if(menuBoolean){
    		menu.getJSONFromServer(json);
    	}
    }

    public void waitTimer(){
    	synchronized (t) {
    		try {
				t.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public void notifyTimer(){
    	t.notify();
    }
    
    public void cancelTimer(){
    	t.cancel();
    	System.out.println("stop");
    	tt.cancel();
    	System.out.println("stop");
    	t.cancel();
    	System.out.println("stop");
    	t=null;
    }
    
    
    public void timer(){
    	t = new Timer();
    	
    	tt = new TimerTask() {
   		 
    		public void run() {
    			try {
    				System.out.println("broadcast");
    				JSONObject json = receiveJSON();
    				System.out.println("broadcast2");
    				sendJsonAtController(json);
    				System.out.println("broadcast3");
    			} catch (IOException | JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	};
		t.schedule(tt, 0, 3000);
    }

}
