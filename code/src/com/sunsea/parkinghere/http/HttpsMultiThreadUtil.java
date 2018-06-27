package com.sunsea.parkinghere.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import weixin.popular.client.RestTemplateClient;

public class HttpsMultiThreadUtil {
	
	private static HttpClient httpClient = DefaultMultiThreadHttpsClient();
	
	public static String send(String url) {  
        HttpGet httpget = new HttpGet(url);  
        HttpResponse response = null;  
        try {  
            response = httpClient.execute(httpget);  
        } catch (IOException e) {  
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
        }  
        System.out.println(response.getStatusLine());  
        HttpEntity entity = response.getEntity();  
        StringBuilder sb = new StringBuilder();  
        if (entity != null) {  
            BufferedReader reader = null;  
            try {  
                reader = new BufferedReader(new InputStreamReader(entity.getContent()));  
            } catch (IOException e) {  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
            }  
            try {  
                sb.append(reader.readLine());  
                sb.append("\r\n");  
            } catch (IOException e) {  
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.x  
            } catch (RuntimeException e) {  
                httpget.abort();  
                throw e;  
  
            } finally {  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.  
                }  
            }  
        }  
        return sb.toString();  
    }  
	
	public static DefaultHttpClient DefaultMultiThreadHttpsClient() {  
        try {  
              
            X509TrustManager trustManager = new X509TrustManager() {  
                public void checkClientTrusted(X509Certificate[] chain, String authType)  
                        throws CertificateException {  
                    
                }  
  
                public void checkServerTrusted(X509Certificate[] chain, String authType)  
                        throws CertificateException {  
                    
                }  
  
                public X509Certificate[] getAcceptedIssuers() {  
                    
                    return null;  
                }  
            };  
  
            // Now put the trust manager into an SSLContext.  
            SSLContext sslcontext = SSLContext.getInstance("TLS");  
            sslcontext.init(null, new TrustManager[]{trustManager}, null);  
  
            // Use the above SSLContext to create your socket factory  
            // (I found trying to extend the factory a bit difficult due to a  
            // call to createSocket with no arguments, a method which doesn't  
            // exist anywhere I can find, but hey-ho).  
            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);  
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
  
  
            HttpParams params = new BasicHttpParams();  
            ConnManagerParams.setMaxTotalConnections(params, 100);  
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
  
            // Create and initialize scheme registry  
            SchemeRegistry schemeRegistry = new SchemeRegistry();  
            schemeRegistry.register(new Scheme("https", sf, 443));  
  
            // Create an HttpClient with the ThreadSafeClientConnManager.  
            // This connection manager must be used if more than one thread will  
            // be using the HttpClient.  
            ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);  
  
  
            return new DefaultHttpClient(cm, params);  
  
  
        } catch (Throwable t) {
            t.printStackTrace();  
            return null;  
        }  
    }  
	
	public static void main(String[] a){
		try{
			RestTemplate restTemplate = RestTemplateClient.restTemplate();
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx567a5055544b8f6f&secret=534091adfb0bc69d777bc177422f8a66&code=0214894d967570f29f4ffa6025ec83bx&grant_type=authorization_code";
			System.out.println(System.currentTimeMillis());
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,null,String.class);
			System.out.println(System.currentTimeMillis());
			System.out.println("responseEntity === "+responseEntity);
			System.out.println("responseEntity.getBody() === "+responseEntity.getBody());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
