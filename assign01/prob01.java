package assign01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class prob01 {
    public static int capacity = 1000000;
	
    public static int size = 0;
    public static int index = 0;

	public static int precnt = 0;
    public static int postcnt = 0;
	public static int samecnt = 0;
	
    public static Scanner inFile;
	public static words [] words = new words[capacity];	
	
   
    public static void main(String[] args) {
    	
    	Scanner kb = new Scanner(System.in);
    	
    	while(true) {
    		System.out.print("$ ");
    		String command = kb.next();
    		String str;
    		
    		if(command.equals("read")) {
    			str = kb.next();
    			readFile();
    		}
    		else if(command.equals("size"))
    			System.out.println(size);	
    		else if(command.equals("find")) {
    			str = kb.nextLine();
    			str = str.replaceAll("'", "").replaceAll(" ", "");
    			index = 0;
    			int cnt = wordFind(str, 0, size-1);
    			
    			if(index == -1)
    				notFound(cnt);
				else {
					next(cnt);
					prev(cnt);
					System.out.println("Founds " + (samecnt+1) + " items.");
					
					for (int i = cnt - postcnt; i <= cnt + precnt; i++)
						System.out.println(words[i].word + " " + words[i].speech + " " + words[i].meaning);
				}
    		}
    		else if(command.equals("exit"))
    			break;
    	}
    	kb.close();
    }
  
	private static void notFound(int cnt) {
		if(cnt < 0) {
			System.out.println("Not Found.");
			System.out.println("- - -");
			System.out.println(words[cnt+1].word + " " + words[cnt+1].speech);
		}
		else {
			System.out.println("Not Found.");
			System.out.println(words[cnt].word + " " + words[cnt].speech);
			System.out.println("- - -");
			System.out.println(words[cnt+1].word + " " + words[cnt+1].speech);
		}
	}

	public static void prev(int correctWord) {
		int i = correctWord + 1;
		for(int x = i; x < size; x++) {
			if(words[x].word.compareToIgnoreCase(words[correctWord].word) == 0) {
				samecnt++;
				precnt++;
			}
		}
	}

	public static void next(int correctWord) {
		int i = correctWord - 1;
		for(int y = i; y >= 0; y--) {
			if(words[y].word.compareToIgnoreCase(words[correctWord].word) == 0 ) {
				samecnt++;
				postcnt++;
			}
		}
	}

	public static int wordFind(String str, int start, int end) {
		int mid = (start + end) / 2;
		
		if(start > end) {
			index = -1;
			return end;
		}
		if (words[mid].word.compareToIgnoreCase(str) == 0)
			return mid;
		else if (words[mid].word.compareToIgnoreCase(str) > 0)
			return wordFind(str, start, mid - 1);
		else
			return wordFind(str, mid + 1, end);
	}

	public static void readFile() {
		String str;
		
		try {
			inFile = new Scanner(new File("dict.txt"));
			
			while(inFile.hasNext()) {
				str = inFile.nextLine();
				
				if(str.equals( "" ))
					continue;
				
				int start = str.indexOf( "(" );
				int end = str.indexOf( ")" );
				
				if(start<0 || end < 0)
					return;
				
				String word = str.substring(0, start - 1);
				String speech = str.substring(start - 1, end + 1);
				String meaning = str.substring(end + 1);
				words[size] = new words(word, speech, meaning);
				size++;
			}
			inFile.close();
			System.out.println("Read successfully.");
			
		} catch (FileNotFoundException e) {
			System.out.println( "No such file." );
			System.exit(0);
		}
	}
}