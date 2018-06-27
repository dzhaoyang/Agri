package weixin.test;

import weixin.popular.bean.EventMessage;
import weixin.popular.util.XMLConverUtil;

public class WeixinTest {

	public static void main(String[] args) {
		String xml =  "<xml>"
					+ " <ToUserName><![CDATA[]]></ToUserName>"
					+ " <FromUserName><![CDATA[fromUser]]></FromUserName>"
					+ " <CreateTime>1433332012</CreateTime>"
					+ " <MsgType><![CDATA[event]]></MsgType>"
					+ " <Event><![CDATA[ShakearoundUserShake]]></Event>"
					+ " <ChosenBeacon>"
					+ "  <Uuid><![CDATA[uuid]]></Uuid>"
					+ "  <Major>major</Major>"
					+ "  <Minor>minor</Minor>"
					+ "  <Distance>0.057</Distance>"
					+ " </ChosenBeacon>"
					+ " <AroundBeacons>"
					+ "  <AroundBeacon>"
					+ "   <Uuid><![CDATA[uuid]]></Uuid>"
					+ "   <Major>major</Major>"
					+ "   <Minor>minor</Minor>"
					+ "   <Distance>166.816</Distance>"
					+ "  </AroundBeacon>"
					+ "  <AroundBeacon>"
					+ "   <Uuid><![CDATA[uuid]]></Uuid>"
					+ "   <Major>major</Major>"
					+ "   <Minor>minor</Minor>"
					+ "   <Distance>15.013</Distance>"
					+ "  </AroundBeacon>"
					+ " </AroundBeacons>"
					+ "</xml>";
		EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, xml);
		System.out.println("eventMessage === "+eventMessage);
	}

}
