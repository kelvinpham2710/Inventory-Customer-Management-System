/*
 * The program creates a class TVReturn
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class TVReturn {
	
	private static Set<TV> tvSet;
	private static String tvSoldFilePath;	//tvsold.txt
	
	public TVReturn(String filePath) {
		
		if(tvSet == null) {
			tvSet = new HashSet<>();
			
			tvSoldFilePath = filePath;
			
			try {
				Scanner sc = new Scanner(new File(tvSoldFilePath));
				
				while(sc.hasNextLine()) {
					String id = sc.nextLine();
					String brand = sc.nextLine();
					String model = sc.nextLine();
					double price = Double.parseDouble(sc.nextLine());
					
					TVType tvType = new TVType(brand, model, price);
					TV tv = new TV(id, tvType);
					
					tvSet.add(tv);	//add TV object to the set
					
					
				}
				
			} catch(FileNotFoundException e) {
				System.out.println("ERROR - File not found!\n");
			}
			
		}
		
	}
	
	
	public TV returnFoundTV(String key) {
		
		for(Iterator<TV> iterator = tvSet.iterator(); iterator.hasNext();) {
			TV tv = iterator.next();
			
			if(tv.getID().equalsIgnoreCase(key)) {
				iterator.remove();
				removeTVFromFile(tv);
				return tv;
			}
		}
		return null;	
	}
	
	private void removeTVFromFile(TV tv) {
		
		//create a temporary Set that automatically sort by ID number when elements being added
		Set<TV> sortedSet = new TreeSet<>(Comparator.comparing(TV::getID));
		sortedSet.addAll(tvSet);	//add all elements from original Set to sorted Set
		
		try {
			
			//open the file and clear all contents
			BufferedWriter writer = new BufferedWriter(new FileWriter(tvSoldFilePath, false));
			
			Iterator<TV> iterator = sortedSet.iterator();
			
			while(iterator.hasNext()) {
				TV tempTV = iterator.next();
				writer.write(tempTV.getID());
				writer.newLine();
				writer.write(tempTV.getTVType().getBrand());
				writer.newLine();
				writer.write(tempTV.getTVType().getModel());
				writer.newLine();
				String price = Double.toString(tempTV.getTVType().getPrice());	//convert Double to String
				writer.write(price);
				writer.newLine();
			}
			
			
			writer.close();
			
		} catch(IOException e) {
			System.out.println("Something went wrong!!");
		}
		
	}
	
}
