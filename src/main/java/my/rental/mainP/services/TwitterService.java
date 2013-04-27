package my.rental.mainP.services;

import java.util.List;

import org.apache.lucene.index.Term;
import org.h2.constant.SysProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {
   private Logger logger = LoggerFactory.getLogger(getClass());
   //
   public void tweet(Twitter twitter, String tweetText) {
      try {
         twitter.timelineOperations().updateStatus(tweetText);
         
         
      } catch (RuntimeException ex) {
         logger.error("Unable to tweet" + tweetText, ex);
      }
      logger.info("twitter succesfully send : " + tweetText);
   }
   
   public List<Tweet> getAllTweets(Twitter twitter) {
	     // try {
	         List<Tweet> tweets = twitter.timelineOperations().getUserTimeline();
	         //List<Tweet> tweets2 = twitter.timelineOperations().getUserTimeline("tomekl007");
	         
	        for(Tweet t :  tweets )
	        System.out.println(t.getText());
	         return tweets;
	         
	    //  } catch (RuntimeException ex) {
	    //     logger.error("Unable to retrive userTimeline;");
	    //  }
	     
	      
	   }
}