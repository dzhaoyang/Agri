package weixin.popular.client;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateClient {
    
    private static RestTemplate restTemplate;
    
    static {
    	
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] certificate, String authType) {
                return true;
            }
        };
        try {
            SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, sf));
            restTemplate = new RestTemplate(requestFactory);
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
        }
        catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
    	
    	
    	/*HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { new X509TrustManager(){
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {}

				
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {}

				
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}} }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", 443, sf);
			httpClient.getConnectionManager().getSchemeRegistry().register(https);
            restTemplate = new RestTemplate(requestFactory);
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
        
    }
    
    public static RestTemplate restTemplate() {
        return restTemplate;
    }
}
