package my.rental.mainP.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class TwitterTemplateCreator {
	
	
	 //  @Autowired
	 //  private Environment env;
	   //
	
	   public Twitter getTwitterTemplate(String accountName) {
//	      String consumerKey = env.getProperty(accountName + ".consumerKey");
//	      String consumerSecret = env.getProperty(accountName + ".consumerSecret");
//	      String accessToken = env.getProperty(accountName + ".accessToken");
//	      String accessTokenSecret = env.getProperty(accountName + ".accessTokenSecret");
		   String consumerKey = "IwAWcPItdIOfyLwRxmUXog";
		      String consumerSecret = "tpYJucnnMF7F77kuQ6MZ0AC3sVtKPHQPk18BA4XA";
		      String accessToken = "843505068-mQj2RMbe2sCjQ9mjvoCCn81InehO2Y6EXmdNef8";
		      String accessTokenSecret="1KckiLR2l4l6Fjn4dLfrX1enSfs98Ju0vtt7QsSFzqw";
		   
		   Preconditions.checkNotNull(consumerKey);
	      Preconditions.checkNotNull(consumerSecret);
	      Preconditions.checkNotNull(accessToken);
	      Preconditions.checkNotNull(accessTokenSecret);
	      //
	      
	      TwitterTemplate twitterTemplate =
	         new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	      return twitterTemplate;
	   }
	}


