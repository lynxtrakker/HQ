package lynxtrakker.utils;

import java.util.HashMap;

public class textUtil {

    public static String getInfo(String key){
        HashMap<String, String> info = new HashMap<>();
        info.put("HQ", "");
        return info.get(key);
    }

}
