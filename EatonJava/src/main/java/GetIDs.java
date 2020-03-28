import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class GetIDs {
	public static void main(String[] args) {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder("https://api.developer.eatonem.com/lcm/v4/udp/keys");

			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjFMVE16YWtpaGlSbGFfOHoyQkVKVlhlV01xbyJ9.eyJ2ZXIiOiIyLjAiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vOTE4ODA0MGQtNmM2Ny00YzViLWIxMTItMzZhMzA0YjY2ZGFkL3YyLjAiLCJzdWIiOiJBQUFBQUFBQUFBQUFBQUFBQUFBQUFFdkNnUEJmZmU2RjZmSi1xVkNNdmNrIiwiYXVkIjoiZmNkOGNmOTItZTlmMC00ZjVlLWExNzQtODNmM2UxODgxMDA1IiwiZXhwIjoxNTgzNTMyNjY1LCJpYXQiOjE1ODM0NDU5NjUsIm5iZiI6MTU4MzQ0NTk2NSwibmFtZSI6Ik9taWQgWmlhZWUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJvbWlkLnppYWVlQG91dGxvb2suY29tIiwib2lkIjoiMDAwMDAwMDAtMDAwMC0wMDAwLWRiMTEtMDcwZDQ2NTgzNzljIiwiZW1haWwiOiJvbWlkLnppYWVlQG91dGxvb2suY29tIiwidGlkIjoiOTE4ODA0MGQtNmM2Ny00YzViLWIxMTItMzZhMzA0YjY2ZGFkIiwiYWlvIjoiRFRqeTZ0allLN045OWNhclk1bjRQaU1IV0JWb1hPT0lqU3dvVTEqWHpkNHBrQ1k1cWNBRnJVa21FV3JpcWluaGtKdFllckx5U3dXYVNmR0V4MVVodXY0ZHAqbjRLMDZzNyEybnVzRFVoS0dVbEVVanNTbndGNHlZNkI1UUNGVk5HWHhlQXFlMXA2RzJXUXN4ZVBZV25EbyQifQ.FSLZMjd-MS0lZwnN2h9zHxz5UNyEMjUJty_mQAiYOpQL7IULriANClWcJnfUvSdOlItT1IEIccc2nbnBUk-hTinT5Dvti-3nKMejXk2ojMosNpWSAMzR8KiezLpVhxkvVFcgOydoEVg2eaOE1Q3eVy4p-DbnmSc3a1AV3PmQHcArWEDU_AcVpwkl-8kX07HAUK6W4Khsk_Eoz1vOQ04mUkHi7qtrHTFeq_vu5Hx8Sr9nWW0v81peM1agvlR3CzxKpNWasfGbhvKCiZbrV2w_EZiS6dcCI2AZpCfB73Wsa68TEz20hm2FhLUf3jYKzaC1cXlPcqQVM9eRwYL9KzWu-w");
			request.addHeader("Ocp-Apim-Subscription-Key", "c6519d05c5af421b8dfb933b7c72a9f5");

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity);
			JsonFactory factory = new JsonFactory();
			JsonParser parser = factory.createParser(responseString);
//			Map<String, String> deviceSpec = new HashMap<String, String>();
//			while (parser.nextToken() != JsonToken.END_OBJECT) {
//				String key = parser.getCurrentName();
//				parser.nextToken();
//				String value = parser.getText();
//				deviceSpec.put(key, value);
//			}
			//System.out.println(deviceSpec);
			if (entity != null) {
				System.out.println(responseString);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
