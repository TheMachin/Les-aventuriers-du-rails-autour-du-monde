package metier;

public class Ville {
	private String name;
	private boolean port;
	/**
	 * @param name
	 * @param port
	 */
	public Ville(String name, boolean port) {
		super();
		this.name = name;
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPort() {
		return port;
	}
	public void setPort(boolean port) {
		this.port = port;
	}
	
	
}
