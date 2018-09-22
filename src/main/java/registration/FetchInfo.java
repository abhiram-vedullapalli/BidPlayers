   package registration;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class FetchInfo {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	static ImagesService imagesService = ImagesServiceFactory.getImagesService();
	static MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	//Fetch all details of User  from data store 
	public static Users getUserInfo(String email) {
		Entity userEntity = null;
		if(memcache.contains(email)) {
			userEntity = (Entity) memcache.get(email);
			
		}else {
			Key userKey = KeyFactory.createKey("Users", email);
			 try {
				userEntity = ds.get(userKey);
				memcache.put(email, userEntity);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BlobKey blobKey = (BlobKey) userEntity.getProperty("Image");
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey).secureUrl(true);
		String url = imagesService.getServingUrl(options);
		
		Users user = new Users(email,(String)userEntity.getProperty("Name"), (String)userEntity.getProperty("Team"),url, 
				(long)userEntity.getProperty("Balance"),(long) userEntity.getProperty("NumPlayers"),(long) userEntity.getProperty("Sold"),(long)userEntity.getProperty("Profit")); 
		return user;
		
	}
}
