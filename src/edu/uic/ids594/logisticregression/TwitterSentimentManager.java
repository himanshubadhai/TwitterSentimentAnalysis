package edu.uic.ids594.logisticregression;

public class TwitterSentimentManager {

	TweetSentimentClassifier sentimentClassifier;

	//instantiate the sentimentClassifier object
	public TwitterSentimentManager() {
		sentimentClassifier = new TweetSentimentClassifier();
	}

	// calculate the sentiment
	public String calculateSentiment(String inputTweet) {

		String sentiment = sentimentClassifier.classify(inputTweet);
		System.out.println("Sentiment: " + sentiment);
		return sentiment;

	}
}
