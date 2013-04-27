package my.rental.mainP.controllers;

import my.rental.mainP.services.TwitterService;
import my.rental.mainP.twitter.TwitterTemplateCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/twitter")
public class TwitterContoller {

	   @Autowired
	   private TwitterService twitterService;
	   @Autowired
	   private TwitterTemplateCreator twitterCreator;
	   //
	
	   @RequestMapping(value="/tweet")
	   public String sendTweet() {
	      Twitter twitterTemplate = twitterCreator.getTwitterTemplate("SpringAtSO");
	      twitterService.tweet(twitterTemplate, "second Tweet");
	      return "home";
	   }
	   
	   @RequestMapping(value="/getTimeline")
	   public String getTimelineForTwitter() {
	      Twitter twitterTemplate = twitterCreator.getTwitterTemplate("SpringAtSO");
	      
	      twitterService.getAllTweets(twitterTemplate);
	      return "home";
	   }
	   
	  
	}


