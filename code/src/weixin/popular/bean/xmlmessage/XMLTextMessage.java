package weixin.popular.bean.xmlmessage;

public class XMLTextMessage extends XMLMessage {
    
    private String content;
    
    public XMLTextMessage(String toUserName, String fromUserName, String content) {
        super(toUserName, fromUserName, "text");
        
        this.content = content.replaceAll("&", "&amp;")
                              .replaceAll("<", "&lt;")
                              .replaceAll(">", "&gt;")
                              .replaceAll("\"", "&quot;")
                              .replaceAll("\\n", "&#x000A;");
    }
    
    @Override
    public String subXML() {
        // return "<Content><![CDATA[" + content + "]]></Content>";
        return "<Content>" + content + "</Content>";
    }
    
}
