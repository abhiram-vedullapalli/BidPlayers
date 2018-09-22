package registration;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import javax.servlet.http.HttpSession;

public class GoogleSigninVerifier {
	static ImagesService imagesService = ImagesServiceFactory.getImagesService();
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

	public static Users user;
	public static boolean signinVerifier(String idTokenString, String teamName,BlobKey blobKey) {

		boolean userExists = false, invalidToken = false, exceptionThrown = false;
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				// Specify the CLIENT_ID of the app that accesses the backend:
				.setAudience(Collections
						.singletonList("1097688824452-sahp286og0evom6coidb82hid5tmvate.apps.googleusercontent.com"))
				// Or, if multiple clients access the backend:
				// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
				.build();

		// (Receive idTokenString by HTTPS POST)

		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(idTokenString);
			if (idToken != null) {
				Payload payload = idToken.getPayload();

				// Print user identifier
				String userId = payload.getSubject();
				System.out.println("User ID: " + userId);

				// Get profile information from payload
				String email = payload.getEmail();
				boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
				String name = (String) payload.get("name");
				String pictureUrl = (String) payload.get("picture");
				String locale = (String) payload.get("locale");
				String familyName = (String) payload.get("family_name");
				String givenName = (String) payload.get("given_name");

		
				// Use or store profile information
				// ...

				System.out.println("Email : " + email);
				System.out.println("emailverified : " + emailVerified);
				System.out.println("Name : " + name);
				System.out.println("Locale : " + locale);
				System.out.println("Family name : " + familyName);
				System.out.println("Given name : " + givenName);
				Key userkey = KeyFactory.createKey("Users", email);
				Entity userEntity;
				
				//Already existing users. They will not enter Team Name. So, Team Name is null
				if (teamName == null) {
					try {
						userEntity = ds.get(userkey);
						userExists = true;
						BlobKey blobKey1 = (BlobKey) userEntity.getProperty("Image");
						ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey1).secureUrl(true);
						String url = imagesService.getServingUrl(options);
						memcache.put(userkey.getName(), userEntity  );
						user = new Users(userkey.getName(),(String)userEntity.getProperty("Name"), (String)userEntity.getProperty("Team"),url, 
								(long)userEntity.getProperty("Balance"),(long) userEntity.getProperty("NumPlayers"),(long) userEntity.getProperty("Sold"),(long)userEntity.getProperty("Profit")); 
					} catch (EntityNotFoundException e) {
						userExists = false;
					}
				} 
				
				//First Time users registration. They will enter team Name
				
				else { 
					try {
						userEntity = ds.get(userkey);
						userExists = true;
						
					} catch (EntityNotFoundException e) {
						long balance = 100 ; long numPlayers = 0; long sold = 0; ; long profit = 0;

						Entity userEntityDS = new Entity("Users", email);
						userEntityDS.setProperty("Name", name);
						userEntityDS.setProperty("Team", teamName);
						userEntityDS.setProperty("Balance", balance);
						userEntityDS.setProperty("NumPlayers", numPlayers);
						userEntityDS.setProperty("Sold", sold);
						userEntityDS.setProperty("Profit",profit);
						userEntityDS.setProperty("Image", blobKey);
						Key createUserKey = KeyFactory.createKey("Users", email);
						memcache.put(createUserKey.getName(), userEntityDS);
						ds.put(userEntityDS);
						ServingUrlOptions option = ServingUrlOptions.Builder.withBlobKey(blobKey).secureUrl(true);
						String image = imagesService.getServingUrl(option);
						user = new Users(email, name, teamName, image, 100, 0, 0,0);
						userExists = false;
					}
				}

			} else {
				invalidToken = true;
				System.out.println("Invalid ID token.");
			}

		} catch (GeneralSecurityException e) {
			exceptionThrown = true;
			System.out.println("General security exception");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			exceptionThrown = true;
			System.out.println("Input Output Exception");
		}

		if (userExists == true && (invalidToken == false && exceptionThrown == false)) {
			return true;
		} else {
			return false;
		}
	}

}
