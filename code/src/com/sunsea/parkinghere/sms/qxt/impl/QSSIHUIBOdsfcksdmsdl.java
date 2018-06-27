package com.sunsea.parkinghere.sms.qxt.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunsea.parkinghere.Configuration;
import com.sunsea.parkinghere.sms.qxt.QSS;

@Service
public class QSSIHUIBOdsfcksdmsdl implements QSS {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");

	static Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
	
	public void fdsfdsfeewf(final String fdsfdf, final String hghthtrhb) {
		new Thread(new Runnable() {
			public void run() {
				try{
					fdsfsdf(fdsfdf, hghthtrhb);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		
	}
	
	private void fdsfsdf(String fdfgfdgf, String ytryhb5){
		
		StringBuffer str = new StringBuffer();
		String timestamp="";
		timestamp=df.format(new Date());
    	
    	String signature = md5PasswordEncoder.encodePassword(Configuration.getInstance().getProperty("md5")+timestamp, null);
    	
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		str.append("<MtPacket>");
    	str.append("<cpid>"+Configuration.getInstance().getProperty("cpid")+"</cpid>");
    	str.append("<mid>0</mid>");
    	str.append("<cpmid>"+timestamp+"_"+String.valueOf(0)+"</cpmid>");
    	str.append("<mobile>"+fdfgfdgf+"</mobile>");
    	str.append("<port>"+Configuration.getInstance().getProperty("port")+"</port>");
    	str.append("<msg>【巴中农业科技园数据监测及控制系统】"+ytryhb5+"</msg>");
    	str.append("<msgtype>1</msgtype>");
    	str.append("<signature>"+signature+"</signature>");
    	str.append("<timestamp>"+timestamp+"</timestamp>");
    	str.append("<validtime>0</validtime>");
	    str.append("</MtPacket>");
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams paras = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(paras, 20 * 1000);
		HttpConnectionParams.setSoTimeout(paras, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(paras, 1024);
		StatusLine status = null;
		int statuscode = 0;
		String responseContent = null;
		HttpPost httppost = null;
		try {
			StringEntity reqEntity = new StringEntity(str.toString(),"utf-8");
			httppost = new HttpPost(Configuration.getInstance().getProperty("url"));
			httppost.setEntity(reqEntity);
			httppost.addHeader("Content-Type", "text/xml");
			httppost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			HttpResponse res = httpclient.execute(httppost);
			status = res.getStatusLine();
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity,"gb2312");
			/*InputStream in = new ByteArrayInputStream(responseContent.getBytes());
	        org.w3c.dom.Document document = XMLUtils.parse(in);
	        Node node = XMLUtils.getChildNodeByPath(document, "/MtResponse");
	        sendFlag = XMLUtils.getChildNodeValue(node,"result");
	        if(!"0".equals(sendFlag)){
	        	throw new Exception("短信信息发送失败！");
	        }*/
			System.out.println("responseContent ===== "+responseContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			statuscode = status.getStatusCode();
			if (statuscode != HttpStatus.SC_OK) {
				if(httppost != null)
					httppost.abort();
			}
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 获取剩余短信条数
	 * @return
	 */
	public String dsvdsvsdf(){
		StringBuffer str = new StringBuffer();
		String timestamp="";
		timestamp=df.format(new Date());
    	
    	String signature = md5PasswordEncoder.encodePassword(Configuration.getInstance().getProperty("md5")+timestamp, null);
    	
    	str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    	str.append("<BalanceRequest>");
    	str.append("	<ecpid>").append(Configuration.getInstance().getProperty("cpid")).append("</ecpid>");
    	str.append("	<bcpid>").append(Configuration.getInstance().getProperty("cpid")).append("</bcpid>");
    	str.append("	<msgtype>").append("1").append("</msgtype>");
    	str.append("	<timestamp>").append(timestamp).append("</timestamp>");
    	str.append("	<signature>").append(signature).append("</signature>");
    	str.append("	<reserve>").append("0").append("</reserve>");
    	str.append("</BalanceRequest>");
    	
    	HttpClient httpclient = new DefaultHttpClient();
		HttpParams paras = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(paras, 20 * 1000);
		HttpConnectionParams.setSoTimeout(paras, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(paras, 1024);
		StatusLine status = null;
		int statuscode = 0;
		String responseContent = null;
		HttpPost httppost = null;
		try {
			StringEntity reqEntity = new StringEntity(str.toString(),"utf-8");
			httppost = new HttpPost(Configuration.getInstance().getProperty("balanceurl"));
			httppost.setEntity(reqEntity);
			httppost.addHeader("Content-Type", "text/xml");
			httppost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			HttpResponse res = httpclient.execute(httppost);
			status = res.getStatusLine();
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity,"gb2312");
			/*InputStream in = new ByteArrayInputStream(responseContent.getBytes());
	        org.w3c.dom.Document document = XMLUtils.parse(in);
	        Node node = XMLUtils.getChildNodeByPath(document, "/MtResponse");
	        sendFlag = XMLUtils.getChildNodeValue(node,"result");
	        if(!"0".equals(sendFlag)){
	        	throw new Exception("短信信息发送失败！");
	        }*/
			System.out.println("responseContent ===== "+responseContent);
			JAXBContext context = JAXBContext.newInstance(BalanceResponse.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			BalanceResponse r = (BalanceResponse)unmarshaller.unmarshal(new StringReader(responseContent));
			return r.getBalance();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			statuscode = status.getStatusCode();
			if (statuscode != HttpStatus.SC_OK) {
				if(httppost != null)
					httppost.abort();
			}
			httpclient.getConnectionManager().shutdown();
		}
		return "";
	}
	
	@XmlRootElement(name="BalanceResponse")
	@XmlAccessorType(XmlAccessType.FIELD)
	private class BalanceResponse{
		
		private String balance;
		
		private String desc;

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
