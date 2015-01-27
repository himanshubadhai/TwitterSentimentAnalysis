package edu.uic.ids594.logisticregression;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.LMClassifier;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;

public class TrainMyModel {

	@SuppressWarnings("unchecked")
	public void trainMyModel() throws IOException, ClassNotFoundException {

		File trainingDirectory;
		String[] trainingCategories;

		//Create a Language model classifier
		@SuppressWarnings("rawtypes")
		LMClassifier lmcClassifier;

		//String AbsolutePath = new File("").getAbsolutePath();
		String absolutePath = System.getProperty("user.dir");

		//Create Directory for training dataset
		trainingDirectory = new File(absolutePath+"/trainDirectory");
		trainingCategories = trainingDirectory.list();

		//nGram level (combination of words to consider for classification) any value between 7 and 12
		int nGramLevel = 8;

		//categories of the text i.e negative and positive text
		String[] modelCategories = { "Positive", "Negative" };

		lmcClassifier= DynamicLMClassifier.createNGramProcess(modelCategories, nGramLevel);

		//read all the files and train the model
		for (int i = 0; i < trainingCategories.length; ++i) {
			String category = trainingCategories[i];
			Classification classification = new Classification(category);
			File file = new File(trainingDirectory, trainingCategories[i]);
			File[] trainingFiles = file.listFiles();

			//read all the files until null
			if(trainingFiles!=null){
				for (int j = 0; j < trainingFiles.length; ++j) {
					File trainingFile = trainingFiles[j];
					String tweet =null;
					try {
						tweet = Files.readFromFile(trainingFile, "ISO-8859-1");
					} catch (IOException e) {
						e.printStackTrace();
					} 

					Classified<String> classified = new Classified<String>(tweet,
							classification);
					((ObjectHandler<Classified<String>>) lmcClassifier).handle(classified);
				}
			}
			try {
				//writing serialize object to classifier.txt file
				// model is created here
				AbstractExternalizable.compileTo((Compilable) lmcClassifier, new File(
						absolutePath+"/classifier.txt"));
				System.out.println("Succefully created a model");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}