package edu.uic.ids594.naivebayes;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.bayes.TrainClassifier;
import org.apache.mahout.classifier.bayes.algorithm.BayesAlgorithm;
import org.apache.mahout.classifier.bayes.common.BayesParameters;
import org.apache.mahout.classifier.bayes.datastore.InMemoryBayesDatastore;
import org.apache.mahout.classifier.bayes.exceptions.InvalidDatastoreException;
import org.apache.mahout.classifier.bayes.interfaces.Algorithm;
import org.apache.mahout.classifier.bayes.interfaces.Datastore;
import org.apache.mahout.classifier.bayes.model.ClassifierContext;
import org.apache.mahout.common.nlp.NGrams;

public class NaiveBayesSentimentCalculator 
{
	
	public String nbSentiment( String args ) 
	{
		URL url = NaiveBayesSentimentCalculator.class.getResource("input.txt");
		
		BayesParameters bayesParameters = setBayesParameters();

		try {

			String inputPath = url.getPath();
			
			//Start the training 
			//TrainClassifier.trainNaiveBayes(new Path(inputPath), new Path("Output"),bayesParameters);

			Algorithm algo = new BayesAlgorithm();//Here we define which alogorithm to use

			Datastore ds = new InMemoryBayesDatastore(bayesParameters);//Defining the mahout datastore to use...the path of hadoops database
			
			/*Initializing the mahout context*/
			ClassifierContext classContext = new ClassifierContext(algo,ds);
			classContext.initialize();

			int gramSize=Integer.parseInt(bayesParameters.get("gramSize"));
			/*
			 * Storing the input text in the form of a string
			 * List has been used because we can have multiple lines of tweets in the single file
			 * The classifier will classify each tweet as positive or negative
			 */
			List< String > listDocument = new NGrams(args,gramSize).generateNGramsWithoutLabel();

			//"Result" stores the final result after classification
			ClassifierResult result = classContext.classifyDocument( 
					listDocument.toArray(new String[listDocument.size()]), 
					bayesParameters.get("defaultCat"));  
		    

			if(Integer.parseInt(result.getLabel())==1)//In the input text 1 represents "Positive"
			{
				System.out.println("Positive");
				return "Positive";
			}
			else if(Integer.parseInt(result.getLabel())==0)//In the input text 0 represents "Negative"
			{
				System.out.println("Negative");
				return "Negative";
			}
			else
			{
				System.out.println("Neutral");
				return "Neutral";
			}
		} 
//		catch(IOException ex ) 
//		{
//			ex.printStackTrace();
//		} 
		catch(InvalidDatastoreException iex ) 
		{
			iex.printStackTrace();
		}
		return "Invalid Input";
	}
	
	public static BayesParameters setBayesParameters()
	{
		BayesParameters bayesParameters = new BayesParameters();
		bayesParameters.setGramSize(7);
		bayesParameters.set("verbose","true");//To see what happens in the back-end on the console
		bayesParameters.set("classifierType","bayes");//Specifying the classification method
		bayesParameters.set("defaultCat","Neutral");//The default category to return if a label is not found for a specified text.
		bayesParameters.set("encoding","UTF-8");//Charset Encoding
		bayesParameters.set("alpha_i","1.0");//Smoothing parameter for all words in the vocabulary
		bayesParameters.set("dataSource","hdfs");
		bayesParameters.set("basePath","Output");
		
		return bayesParameters;
	}
}
