package edu.uic.ids594.logisticregression;

import java.io.IOException;
import java.net.URL;

public class LogisticRegressionSentimentCalculator {

	public String lpSentiment(String inputTweet) {

		URL url= LogisticRegressionSentimentCalculator.class.getResource("input.txt");
		//String AbsolutePath = new File("").getAbsolutePath();
		
		//String fileName = AbsolutePath+"/input.txt";
		
//		BuildDirectory directory = new BuildDirectory();
//		//System.out.println(url.getPath()+":"+ url.getFile());
//		directory.readData(url.getPath());
//
//		TrainMyModel trinMyModel;
//		
//		// train your model
//		try {
//			trinMyModel = new TrainMyModel();
//			trinMyModel.trainMyModel();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// calculate sentiment
		TwitterSentimentManager manager = new TwitterSentimentManager(); 
		String sentiment =manager.calculateSentiment(inputTweet);

		return sentiment;
	}

}
