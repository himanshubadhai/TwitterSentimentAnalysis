package edu.uic.ids594.logisticregression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class BuildDirectory {

	FileInputStream fis;
	BufferedReader bReader;
	String line;

	public void readData(String fileName){
		try {
			//FileReader fileReader = new FileReader(fileName);
			fis = new FileInputStream(fileName);
			bReader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			int count1=0; 
			int count2=0; 
			int count3=0;

			String path=System.getProperty("user.dir");
            String directoryPath=path+"/trainDirectory";
            
            // create the directory in the path specified
            new File(directoryPath).mkdir();
            new File(directoryPath+"/Positive").mkdir();
            new File(directoryPath+"/Negative").mkdir();
            new File(directoryPath+"/Neutral").mkdir();
            
			while((line = bReader.readLine()) != null){
				String[] tokens = line.split("\t",0);

//				System.out.println("token1: "+tokens[0]);
//				System.out.println("token2: "+tokens[1]);
				
				int sentiment = Integer.parseInt(tokens[0].toString().trim());
				String tweet = tokens[1].trim();

				// 1= positive tweet, 0= Negative tweet
				// Store the tweets as text files in respective folders 
				if(sentiment==1){
					PrintWriter writer = new PrintWriter(path+"/trainDirectory/Positive/tweetFile"+count1+".txt", "UTF-8");				
					writer.println(tweet);
					writer.close();
					count1++;
				}else if(sentiment==0){
					PrintWriter writer = new PrintWriter(path+"/trainDirectory/Negative/tweetFile"+count2+".txt", "UTF-8");				
					writer.println(tweet);
					writer.close();
					count2++;
				}else{
					PrintWriter writer = new PrintWriter(path+"/trainDirectory/Neutral/tweetFile"+count3+".txt", "UTF-8");				
					writer.println(tweet);
					writer.close();
					count3++;
				}
				
			}

			System.out.println("Files generated successfully");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
