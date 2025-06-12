package tilegame.util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class UserData extends JFrame{
	public static ArrayList<User> users;
	public UserData()
	{
		users = new ArrayList<User>();
		readData();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				updateData(); // khi đóng cửa sổ window thì đồng thời cập nhập lại list người chơi
			}
		});
	}
	public static void updateData()
	{
		BufferedWriter bw =null; // tương tự scanner 
		try {
			FileWriter fw = new FileWriter("res/UserInfo/Scores.txt");
			 bw = new BufferedWriter(fw);
			 for(User u: users)
			 {
				 bw.write(u.getName() +" " + u.getLevel());
				 bw.newLine();
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				bw.close(); // sử dụng finally để close cho an toàn 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void readData()
	{
		try {
			FileReader fr = new FileReader("res/UserInfo/Scores.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine() )!= null)
			{
				String[] str = line.split(" "); //split thành phần trong 1 line ra thành các phần cách nhau dấu " "
				users.add(new User(str[0], str[1]));
			}
			
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
