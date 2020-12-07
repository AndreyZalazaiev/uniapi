package andrew.projects.uniapi.Utils;

public class  StringUtils {
    public static String xml10Normalize(String input){
        String xml11pattern = "[^"
                + "\u0001-\uD7FF"
                + "\uE000-\uFFFD"
                + "\ud800\udc00-\udbff\udfff"
                + "]+";
        return input.replaceAll(xml11pattern,"");
    }
}
