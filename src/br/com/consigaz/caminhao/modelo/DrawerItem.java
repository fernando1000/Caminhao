package br.com.consigaz.caminhao.modelo;

public class DrawerItem {

	String ItemName;	
	String title;
	int imgResID;	

	
	public DrawerItem(String itemName, int imgResID) {
		
		this.ItemName = itemName;
		this.imgResID = imgResID;
	}
	
	public DrawerItem(String titulo) {
		this.title = titulo;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public int getImgResID() {
		return imgResID;
	}
	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}	
}
