package edu.uic.ids594.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uic.ids594.bean.TwitterBean;
import edu.uic.ids594.logisticregression.LogisticRegressionSentimentCalculator;
import edu.uic.ids594.naivebayes.NaiveBayesSentimentCalculator;

/**
 * Servlet implementation class SentimentServlet
 */
@WebServlet("/SentimentServlet")
public class SentimentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SentimentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		
		// set the bean class
		TwitterBean twitterBean = (TwitterBean) session.getAttribute("twitterBean");
        session.setAttribute("twitterBean", twitterBean);

        // get the input tweet from the webpage
		String tweet=request.getParameter("tweet");
		twitterBean.setTweet(tweet); 
		
		String sentiment;

		System.out.println("tweet: "+request.getParameter("tweet"));
		System.out.println("Classifier: "+request.getParameter("classifier"));

		// call the respective classifier based on user selected input
		if(request.getParameter("classifier").equals("1")){
			NaiveBayesSentimentCalculator naiveBayes = new NaiveBayesSentimentCalculator();	
			sentiment= naiveBayes.nbSentiment(request.getParameter("tweet"));
			
			twitterBean.setMessage(sentiment);			
			System.out.println("Naive Bayes: "+ sentiment);

		}else if(request.getParameter("classifier").equals("2")){
			LogisticRegressionSentimentCalculator logisticRegression = new LogisticRegressionSentimentCalculator();
			sentiment= logisticRegression.lpSentiment("tweet");

			twitterBean.setMessage(sentiment);
			System.out.println("Logistic Regression: "+sentiment);
		}
		
		// refresh and stay on the same jsp page to display the result to the user
		RequestDispatcher dispatcher = request.getRequestDispatcher("sentiment.jsp");
        dispatcher.forward(request, response);
	}

}
