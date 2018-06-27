package com.sunsea.parkinghere.wechat.receiver.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.popular.bean.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage;
import weixin.popular.bean.xmlmessage.XMLNewsMessage.Article;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import com.sunsea.parkinghere.Configuration;
/*import com.sunsea.parkinghere.biz.model.Favorites;
import com.sunsea.parkinghere.biz.model.Parking;
import com.sunsea.parkinghere.biz.model.ParkingBooking;
import com.sunsea.parkinghere.biz.repository.ParkingBookingRepository;
import com.sunsea.parkinghere.biz.service.ParkingService;*/
import com.sunsea.parkinghere.framework.utils.IOUtils;
/*import com.sunsea.parkinghere.module.carowner.repository.CarOwnerUserRepository;*/
import com.sunsea.parkinghere.module.carowner.service.CarOwnerNonceService;
/*import com.sunsea.parkinghere.module.carowner.model.CarOwnerUser;
import com.sunsea.parkinghere.module.carowner.model.CarOwnerUserActivity;
import com.sunsea.parkinghere.module.carowner.model.CarOwnerUserActivityName;*/

@Controller
@RequestMapping("/wechat/receiver/v1")
public class WeChatReceiver {

	public static final String SUBSCRIBE = "subscribe";

	public static final String UNSUBSCRIBE = "unsubscribe";

	static final Log logger = LogFactory.getLog(WeChatReceiver.class);

	private String token = "xxxxxxxxxxxxxxxxxxx";

	@Autowired
	private CarOwnerNonceService weChatNonceService;


	

	

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public String ping() {
		return "Parkinghere receiver for wechat running...";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void validate4Firsttime(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String echostr = request.getParameter("echostr");
		
		
		if (echostr != null) {
			logger.info("Validate the token first time from WeChat server, return echostr directly.");
			if (logger.isDebugEnabled()) {
				logger.debug("echostr=" + echostr);
			}
			outputStreamWrite(response.getOutputStream(), echostr);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public String receive(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		ServletInputStream inputStream = request.getInputStream();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		if (null == signature) {
			logger.warn("no signature, it's invaild request from wechat");
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Validate the message signature");
			logger.debug("request signature=" + signature);
			logger.debug("generated signature="+ SignatureUtil.generateEventMessageSignature(token,timestamp, nonce));
		}

		
		if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
			logger.warn("The request signature is invalid!");
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validate request signature success.");
		}

		if (inputStream != null) {
			String xml = IOUtils.copyToString(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
			if (logger.isDebugEnabled()) {
				logger.debug("request xml content is: ");
				logger.debug(xml);
			}
			EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, xml);

			XMLMessage responseMsg = handleEventMessage(request, eventMessage);
			if (null != responseMsg) {
				if (logger.isDebugEnabled()) {
					logger.debug("response xml content is:");
					logger.debug(responseMsg.toXML());
				}
				
				return responseMsg.toXML();
			} else {
				return "";
			}
		} else {
			throw new ServletException("invalidate input from wechat server!");
		}
	}

	protected XMLMessage handleEventMessage(HttpServletRequest request,EventMessage eventMsg) throws UnsupportedEncodingException {return null;}



	protected XMLMessage buildHelpSendLocationXMLMessage(EventMessage eventMsg) {
		List<Article> articles = new ArrayList<Article>();
		Article mainArti = new Article();
		mainArti.setTitle("如何发送地址信息");
		mainArti.setUrl(Configuration.getInstance().getServerIpUrl()
				+ "/img/help_send_location.png");
		mainArti.setPicurl(Configuration.getInstance().getServerIpUrl()
				+ "/img/help_send_location.png");
		mainArti.setDescription("点击左下键盘按钮,进入聊天模式,再点击右下+按钮,点击位置,发送目的地地址,则可以查询附近停车场");
		articles.add(mainArti);
		return new XMLNewsMessage(eventMsg.getFromUserName(),
				eventMsg.getToUserName(), articles);
		
	}

	


	private boolean outputStreamWrite(OutputStream outputStream, String text) {
		try {
			outputStream.write(text.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	
	/**下载界面图文消息*/
	static XMLNewsMessage appDownloadXMLMessage = null;
	
	
	
	/**关注图文消息*/
	static XMLNewsMessage subscribeXMLMessage = null;
	
}
