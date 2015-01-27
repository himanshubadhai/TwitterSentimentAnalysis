package edu.uic.ids594.logisticregression;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class TweetSentimentClassifier {

	String[] sentimentCategories;

	@SuppressWarnings("rawtypes")
	LMClassifier lmClassifier;

	@SuppressWarnings("rawtypes")
	public TweetSentimentClassifier() {

		//String AbsolutePath = new File("").getAbsolutePath();
		String absolutePath = System.getProperty("user.dir");

		try {
			//read the model classifier
			lmClassifier= (LMClassifier) AbstractExternalizable.readObject(new File(absolutePath+"/classifier.txt"));
			sentimentCategories = lmClassifier.categories();
			
			System.out.println("Sentiment Categories: ");
			for (String sentimentCategory: sentimentCategories){
				System.out.println(sentimentCategory);
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	//perform classification using lmClassifier
	public String classify(String text) {
		ConditionalClassification classification = lmClassifier.classify(text);
		return classification.bestCategory();
	}
}
