package my.rental.mainP.facebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

//@Configuration
public class FacebookConfig  {

/*

  private static final Logger logger = LoggerFactory.getLogger(FacebookConfig.class);



  private static final String appId = "193749590776179";

  private static final String appSecret = "8928f3d3d4ba96cfb3b170cbd558ce6c";




  public Facebook setUp(){
	  Facebook f =new FacebookTemplate();
	  FacebookConnectionFactory fb = new FacebookConnectionFactory(appId, appSecret);
	  Facebook facebook = fb.createConnection(accessGrant)
			  
	 ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

	    registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));

	   
  }

*/
/*
  public ConnectionFactoryLocator connectionFactoryLocator() {

Facebook fb = new FacebookTemplate();
fb.

    logger.info("getting connectionFactoryLocator");

    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

    registry.addConnectionFactory(new FacebookConnectionFactory(appId, appSecret));

    return registry;
    

  }
*/


  /**

   * Singleton data access object providing access to connections across all

   * users.

   */
/*
  @Bean

  public UsersConnectionRepository usersConnectionRepository() {



    return usersConnectionRepositiory;

  }*/



  /**

   * Request-scoped data access object providing access to the current user's

   * connections.

   */
/*
  @Bean

  @Scope(value = 'request', proxyMode = ScopedProxyMode.INTERFACES)
*//*
  public ConnectionRepository connectionRepository() {

    //String userId = socialContext.getUserId();
String userId = "100002038936654";
	  
    logger.info("Createung ConnectionRepository for user: " + userId);

    return usersConnectionRepository().createConnectionRepository(userId);

  }
*/


  /**

   * A proxy to a request-scoped object representing the current user's

   * primary Facebook account.

   * 

   * @throws NotConnectedException

   *             if the user is not connected to facebook.

   */

 
/*
  public Facebook facebook() {

    return connectionRepository().getPrimaryConnection(Facebook.class).getApi();

  }


*/
  /**

   * Create the ProviderSignInController that handles the OAuth2 stuff and

   * tell it to redirect back to /posts once sign in has completed

   */
/*
  @Bean

  public ProviderSignInController providerSignInController() {

    ProviderSignInController providerSigninController = new ProviderSignInController(connectionFactoryLocator(),

        usersConnectionRepository(), socialContext);

    providerSigninController.setPostSignInUrl('/posts');

    return providerSigninController;

  }



  @Override

  public void afterPropertiesSet() throws Exception {



    JdbcUsersConnectionRepository usersConnectionRepositiory = new JdbcUsersConnectionRepository(dataSource,

        connectionFactoryLocator(), Encryptors.noOpText());



    socialContext = new SocialContext(usersConnectionRepositiory, new UserCookieGenerator(), facebook());



    usersConnectionRepositiory.setConnectionSignUp(socialContext);

    this.usersConnectionRepositiory = usersConnectionRepositiory;

  }*/

}