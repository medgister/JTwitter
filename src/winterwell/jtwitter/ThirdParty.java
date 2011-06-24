package winterwell.jtwitter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import winterwell.jtwitter.Twitter.IHttpClient;
import winterwell.jtwitter.Twitter.User;
import winterwell.utils.containers.ArrayMap;

/**
 * Status: experimental!
 * Access to 3rd party services:
 * 
 *  - Infochimp Trust Rank scores
 *  - Twitlonger: Actually, this is done via {@link Twitter#updateLongStatus(String, long)}
 *  
 * Note: These services typically require their own api-keys and may 
 * have there own terms and conditions of use.
 * @author daniel
 *
 */
public class ThirdParty {

	private IHttpClient client;

	public ThirdParty() {
		this(new URLConnectionHttpClient());
	}
	
	public ThirdParty(IHttpClient client) {
		this.client = client;
	}
	
	/**
	 * 
	 * @param user
	 * @param apiKey
	 * @return [0, 10]
	 */
	public double getInfochimpTrustRank(User user, String apiKey) {
		String json = client.getPage("http://api.infochimps.com/soc/net/tw/trstrank.json", 
				new ArrayMap<String, String>(
				"screen_name", user.screenName,
				"apikey",apiKey
		), false);
		try {
			JSONObject results = new JSONObject(json);
			Double score = results.getDouble("trstrank");
			return score;
		} catch (JSONException e) {
			throw new TwitterException.Parsing(json, e);
		}
	}

}