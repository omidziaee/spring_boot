import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CreateUDPKey {

	public static void main(String[] args) {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder("https://api.developer.eatonem.com/lcm/v4/udp/keys");

			URI uri = builder.build();
			HttpGet request = new HttpGet(uri);
			request.addHeader("Authorization",
					"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjFMVE16YWtpaGlSbGFfOHoyQkVKVlhlV01xbyJ9.eyJ2ZXIiOiIyLjAiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vOTE4ODA0MGQtNmM2Ny00YzViLWIxMTItMzZhMzA0YjY2ZGFkL3YyLjAiLCJzdWIiOiJBQUFBQUFBQUFBQUFBQUFBQUFBQUFFdkNnUEJmZmU2RjZmSi1xVkNNdmNrIiwiYXVkIjoiZmNkOGNmOTItZTlmMC00ZjVlLWExNzQtODNmM2UxODgxMDA1IiwiZXhwIjoxNTgyMjQ1MjE4LCJpYXQiOjE1ODIxNTg1MTgsIm5iZiI6MTU4MjE1ODUxOCwibmFtZSI6Ik9taWQgWmlhZWUiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJvbWlkLnppYWVlQG91dGxvb2suY29tIiwib2lkIjoiMDAwMDAwMDAtMDAwMC0wMDAwLWRiMTEtMDcwZDQ2NTgzNzljIiwiZW1haWwiOiJvbWlkLnppYWVlQG91dGxvb2suY29tIiwidGlkIjoiOTE4ODA0MGQtNmM2Ny00YzViLWIxMTItMzZhMzA0YjY2ZGFkIiwiYWlvIjoiRFRXOGI2ZXVjTyFadlMxR3dZT2lEbmE3aGlwKm1TaXF1UkkzVHZ0Tk9Dc0hPblhZMU13dXF6NTN3WDMqOG9GTmtLS05sUEdHR1Z6Z0tUdTZ2YkNZMVkhek5Vb1JaRFdlMUdpSXpqR0xNQUJHV3RQQm82WXdXb0JvSVE0R3VYMXBUTFoyZm9rVkphN1NDVWVTOTJBc2xzdyQifQ.ng9J-YgldRNTPU8dYWxFKuIbAlSHJFOe1F84lfIqJpxRWqSIgDhKl1rBiIyz2KKLRHg7mrUfBnGlBHgg9V4i2AA09VKvUI0eY98BUxQfXattCA89-Czsd6CWyxJSBgeoPC9CyKOreCnrHag9vJeJhyIKmaxTwg5XrK0B60G0ubxh7BnRLq9cvpKlTAssCXpTkZn88EBrOAled6jnMQ72d1rdbLFh14-XzwR2QuZCmPnOh7_AgumYhnp5JwcQoEOp5wRHElGlZ2szKqYoJqLjoPDEemoNxjd9dPIwtz9LoReuzOlLmE1xJ8bldkmFRoEwOQ2R5vKkTadma1OzhH1ELA");
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Ocp-Apim-Subscription-Key", "c6519d05c5af421b8dfb933b7c72a9f5");

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
