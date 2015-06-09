package models;

public class Group {
	private String name;
	private int ID;
	
	public Group(String name, int ID) {
		this.setName(name);
		this.setID(ID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase().trim();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
