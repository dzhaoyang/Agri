package weixin.popular.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class EventMessage {
    
    /**开发者微信号*/
    private String toUserName;
    /**发送方帐号（一个OpenID）*/
    private String fromUserName;
    /**消息创建时间 （整型）*/
    private Integer createTime;
    /**消息类型，event*/
    private String msgType;
    /**事件类型，subscribe(订阅)、unsubscribe(取消订阅)*/
    private String event;
    
    /**----扫描带参数二维码事件*/
    /**事件KEY值，qrscene_为前缀，后面为二维码的参数值*/
    private String eventKey;
    /**二维码的ticket，可用来换取二维码图片*/
    private String ticket;
    
    /**----上报地理位置事件*/
    /**地理位置纬度*/
    private String latitude;
    /**地理位置经度*/
    private String longitude;
    /**地理位置精度*/
    private String precision;
    
    /**普通消息 消息ID号*/
    private String msgId;
    
    /**普通消息--文本 文本消息内容*/
    private String content;
    
    /**普通消息--图片 图片消息*/
    private String picUrl;
    
    /**普通消息--媒体 mediaId 可以调用多媒体文件下载接口拉取数据*/
    private String mediaId;
    
    /**普通消息--语音格式*/
    private String format;
    
    /**普通消息--语音识别 开通语音识别功能的识别结果*/
    private String recognition;
    
    /**普通消息--视频 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据*/
    private String thumbMediaId;
    
    // 
    /**普通消息--地理位置消息 地理位置维度*/
    private String location_X;
    /**地理位置经度*/
    private String location_Y;
    /**地图缩放大小*/
    private String scale;
    /**地理位置信息*/
    private String label;
    
    /**普通消息--链接消息*/
    private String title;
    /***/
    private String description;
    /***/
    private String url;
    
	
    private Beacon chosenBeacon;
	
	
	private List<Beacon> aroundBeacons;
    
    @XmlElement(name = "ToUserName")
    public String getToUserName() {
        return toUserName;
    }
    
    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
    
    @XmlElement(name = "FromUserName")
    public String getFromUserName() {
        return fromUserName;
    }
    
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
    
    @XmlElement(name = "CreateTime")
    public Integer getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    
    @XmlElement(name = "MsgType")
    public String getMsgType() {
        return msgType;
    }
    
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    
    @XmlElement(name = "Event")
    public String getEvent() {
        return event;
    }
    
    public void setEvent(String event) {
        this.event = event;
    }
    
    @XmlElement(name = "EventKey")
    public String getEventKey() {
        return eventKey;
    }
    
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
    
    @XmlElement(name = "Ticket")
    public String getTicket() {
        return ticket;
    }
    
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
    @XmlElement(name = "Latitude")
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    @XmlElement(name = "Longitude")
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    @XmlElement(name = "Precision")
    public String getPrecision() {
        return precision;
    }
    
    public void setPrecision(String precision) {
        this.precision = precision;
    }
    
    @XmlElement(name = "MsgId")
    public String getMsgId() {
        return msgId;
    }
    
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    @XmlElement(name = "Content")
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @XmlElement(name = "PicUrl")
    public String getPicUrl() {
        return picUrl;
    }
    
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    
    @XmlElement(name = "MediaId")
    public String getMediaId() {
        return mediaId;
    }
    
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    
    @XmlElement(name = "Format")
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    @XmlElement(name = "Recognition")
    public String getRecognition() {
        return recognition;
    }
    
    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }
    
    @XmlElement(name = "ThumbMediaId")
    public String getThumbMediaId() {
        return thumbMediaId;
    }
    
    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
    
    @XmlElement(name = "Location_X")
    public String getLocation_X() {
        return location_X;
    }
    
    public void setLocation_X(String locationX) {
        location_X = locationX;
    }
    
    @XmlElement(name = "Location_Y")
    public String getLocation_Y() {
        return location_Y;
    }
    
    public void setLocation_Y(String locationY) {
        location_Y = locationY;
    }
    
    @XmlElement(name = "Scale")
    public String getScale() {
        return scale;
    }
    
    public void setScale(String scale) {
        this.scale = scale;
    }
    
    @XmlElement(name = "Label")
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    @XmlElement(name = "Title")
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @XmlElement(name = "Description")
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @XmlElement(name = "Url")
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @XmlElement(name="ChosenBeacon")
    public Beacon getChosenBeacon() {
		return chosenBeacon;
	}

	public void setChosenBeacon(Beacon chosenBeacon) {
		this.chosenBeacon = chosenBeacon;
	}

	@XmlElementWrapper(name="AroundBeacons")
	@XmlElement(name="AroundBeacon")
	public List<Beacon> getAroundBeacons() {
		return aroundBeacons;
	}

	public void setAroundBeacons(List<Beacon> aroundBeacons) {
		this.aroundBeacons = aroundBeacons;
	}

	public final static String EVENT_KEY_PARK = "ACTION_PARK";
    
    public final static String EVENT_KEY_BOOKING = "ACTION_BOOKING";
    
    public final static String EVENT_KEY_JUDGE = "ACTION_JUDGE";
    
    public final static String EVENT_KEY_SHOW_BOOKING_RECORDS = "ACTION_SHOW_BOOKING_RECORDS";
    
    public final static String EVENT_KEY_SHOW_FAVORITE_RECORDS = "ACTION_SHOW_FAVORITE_RECORDS";
    
    public final static String EVENT_KEY_SHOW_PROFILE = "ACTION_SHOW_PROFILE";
    
    public final static String EVENT_KEY_DO_JUDGE = "ACTION_DO_JUDGE";
    /**
     * app下载
     */
    public final static String APP_DOWNLOAD = "APP_DOWNLOAD";
    
    /**
     * 弹出地理位置选择器的事件推送
     * @return
     */
    public boolean isMsgTypeLocation() {
        return "location".equalsIgnoreCase(getMsgType());
    }
    /**
     * 接收语音识别结果
     * @return
     */
    public boolean isMsgTypeVoice() {
        return "voice".equalsIgnoreCase(getMsgType());
    }
    /**
     * 接收普通消息
     * @return
     */
    public boolean isMsgTypeText() {
        return "text".equalsIgnoreCase(getMsgType());
    }
    /**
     * 接收事件推送
     * @return
     */
    public boolean isMsgTypeEvent() {
        return "event".equalsIgnoreCase(getMsgType());
    }
    
}
