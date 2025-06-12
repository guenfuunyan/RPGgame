package tilegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.items.Item;

public class Inventory {

	private Handler handler;
	private boolean active = false;
	protected boolean isBloodUsed = false;
	protected boolean isEnergyUsed = false;
	private ArrayList<Item> inventoryItems;

	private int invX = 64, invY = 48, // inventoryScreen
			invWidth = 512, invHeight = 384, invListCenterX = invX + 171, invListCenterY = invY + invHeight / 2 + 5,
			invListSpacing = 30;

	private int invImageX = 452, invImageY = 82, // vi tri hinh anh item trong inventory
			invImageWidth = 64, invImageHeight = 64;

	private int invCountX = 484, invCountY = 172; // vi tri hinh anh countItem trong inventory

	private int selectedItem = 0;

	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();
	}

	public void tick() {
		// cu nhan e thi hop thoai inventory se mo hoac dong
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
			active = !active;
		if (!active)
			return;
		// di chuyen len xuong trong inventory
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedItem--;
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedItem++;
		// hieu ung quay vong cho inventory
		if (selectedItem < 0)
			selectedItem = inventoryItems.size() - 1;
		else if (selectedItem >= inventoryItems.size())
			selectedItem = 0;
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_R))
		{
			if(usingBloodItem())
			{
				isBloodUsed = true;
			}
			else isBloodUsed = false;
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_T))
		{
			if(usingEnergyItem())
			{
				isEnergyUsed = true;
			}
			else isEnergyUsed = false;
		}
	}
	public void render(Graphics g) {
		if (!active)
			return;

		g.drawImage(Assets.inventoryScreen, invX, invY, invWidth, invHeight, null);

		int len = inventoryItems.size();
		if (len == 0)
			return;
		// cho phep toi da 11 item co the hien thi
		for (int i = -5; i < 6; i++) {
			if (selectedItem + i < 0 || selectedItem + i >= len)
				continue; // vuot qua so luong item toi da thi ta bo
			if (i == 0) { // item duoc lua chon
				Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
						invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);
			} else { // cac item khac duoc hien thi
				Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
						invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
			}
		}

		Item item = inventoryItems.get(selectedItem);
		g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
		Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);
	}

	// Inventory methods

	public void addItem(Item item) {
		for (Item i : inventoryItems) {
			if (i.getId() == item.getId()) {
				i.setCount(i.getCount() + item.getCount());
				return;
			}
		}
		inventoryItems.add(item);
	}
	
	public boolean usingBloodItem() {
		for (Item i : inventoryItems) {
			if (i.getId() == 1 && !isBloodUsed && i.getCount() > 1) {
				i.setCount(i.getCount() - 1);
				return true;
			} else if (i.getId() == 1 && !isBloodUsed && i.getCount() == 1) {
				inventoryItems.remove(i);
				return true;
		}
		
	}
		return false;
	}
	
	public boolean usingEnergyItem() {
		for (Item i : inventoryItems) {
			if (i.getId() == 0 && !isEnergyUsed && i.getCount() > 1) {
				i.setCount(i.getCount() - 1);
				return true;
			} else if (i.getId() == 0 && !isEnergyUsed && i.getCount() == 1) {
				inventoryItems.remove(i);
				return true;
			} 
		}
		return false;
	}
	
	// GETTERS SETTERS

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isBloodUsed() {
		return isBloodUsed;
	}

	public void setBloodUsed(boolean isBloodUsed) {
		this.isBloodUsed = isBloodUsed;
	}

	public boolean isEnergyUsed() {
		return isEnergyUsed;
	}

	public void setEnergyUsed(boolean isEnergyUsed) {
		this.isEnergyUsed = isEnergyUsed;
	}

}