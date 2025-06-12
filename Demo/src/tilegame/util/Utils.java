package tilegame.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class Utils {
	// add file text, nếu còn file thì còn load theo từng dòng, add vào bộ đệm
	public static String loadFileAsString(String path)
	{
		StringBuilder builder = new StringBuilder(); //string builder là string có thể thay đổi đc.
		try {
			BufferedReader br = new BufferedReader (new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
			{
				builder.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
	// trả ra 1 number
	public static int parseInt(String number) // kiểu trả về là int
	{
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
