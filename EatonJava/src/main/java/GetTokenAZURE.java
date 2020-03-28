import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetTokenAZURE
{
    public static void main(String[] args) 
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://login.microsoftonline.com/common/oauth2/v2.0/token");


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.addHeader("auth_url", "https://login.microsoftonline.com/common/oauth2/v2.0/authorize");
            request.addHeader("grant_type","authorization_code");
            request.addHeader("client_iD", "fcd8cf92-e9f0-4f5e-a174-83f3e1881005");
            request.addHeader("client_secret", "De:1sh:]h6ve43brC?=TLdke4vVH5it6");
            request.addHeader("scope", "openid_profile_email");

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
