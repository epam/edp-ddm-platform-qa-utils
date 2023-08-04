package platform.qa.content;

public class EtagUtils {

    public static String getETagFromContent(String content) {
        return "\""+content.hashCode() + "\"";
    }
}
