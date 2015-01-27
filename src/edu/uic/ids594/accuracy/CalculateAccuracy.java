package edu.uic.ids594.accuracy;

import com.aliasi.util.Files;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramProcessLM;

import java.io.File;
import java.io.IOException;

public class CalculateAccuracy 
{
	//Directory containing the positive and negative tweets in different folders
    File directory;
    String[] sentimentCategories;
    String[] modelCategories={"Positive","Negative"};;
    
    DynamicLMClassifier<NGramProcessLM> mClassifier;
    String path=System.getProperty("user.dir");
    
    public CalculateAccuracy() 
    {
    	directory = new File(path+"/directory");
    	System.out.println(directory);
    	sentimentCategories = directory.list();
    	
    	//Setting the gram levels
        int nGram = 7;
        mClassifier = DynamicLMClassifier.createNGramProcess(modelCategories,nGram);
    }

    void run() throws ClassNotFoundException, IOException 
    {
        trainMyModel();
        evaluateMyModel();
    }

    boolean isTrainingFile(File file) {
    	
    	//Segregating the training data and test data
        return ((file.getName().charAt(9) != '1') && (file.getName().charAt(10) != '1'));
    }

    void trainMyModel() throws IOException 
    {
        int numberOfTrainCases = 0;
       
        System.out.println("\nTraining.");
        for (int i = 0; i < sentimentCategories.length; ++i)
        {
            String category = sentimentCategories[i];
            Classification classification
                = new Classification(category);
            File file = new File(directory,sentimentCategories[i]);
            File[] trainingFiles = file.listFiles();
            if(trainingFiles!=null)
            {
            for (int j = 0; j < trainingFiles.length; ++j) 
            {
                File trainFile = trainingFiles[j];
                if (isTrainingFile(trainFile)) 
                {
                	numberOfTrainCases++;
                    String tweets = Files.readFromFile(trainFile,"ISO-8859-1");
              
                    Classified<CharSequence> classified
                        = new Classified<CharSequence>(tweets,classification);
                    mClassifier.handle(classified);
                }
            }
            }
        }
        System.out.println("# Training Cases: " + numberOfTrainCases);
    }

    void evaluateMyModel() throws IOException 
    {
        System.out.println("\nEvaluating.");
        int numberOfTests = 0;
        int numberOfCorrect = 0;
        for (int i = 0; i < sentimentCategories.length;i++) 
        {
            String category = sentimentCategories[i];
            File file = new File(directory,sentimentCategories[i]);
            File[] trainingFiles = file.listFiles();
            if(trainingFiles!=null)
            {
            for (int j = 0; j < trainingFiles.length;j++) 
            {
                File trainFile = trainingFiles[j];
                if (!isTrainingFile(trainFile)) 
                {
                    String tweet = Files.readFromFile(trainFile,"ISO-8859-1");
                    numberOfTests++;
                    Classification classification = mClassifier.classify(tweet);
                    if (classification.bestCategory().equals(category))
                    	numberOfCorrect++;
                }
            }
          }
        }
        
        System.out.println("Number of Test Cases: " + numberOfTests);
        System.out.println("Number of Correct: " + numberOfCorrect);
        System.out.println("% Correct: " + (((double)numberOfCorrect)/(double)numberOfTests)*100);
    }

    public static void main(String[] args) {
        try 
        {
            CalculateAccuracy calculateAccuracy = new CalculateAccuracy();
        	calculateAccuracy.run();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}