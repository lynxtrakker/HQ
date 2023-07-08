package lynxtrakker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Secrets {
    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    static String json = "secrets.json";
    @SerializedName("token")
    String token;
    @SerializedName("prefix")
    String prefix;
    @SerializedName("ownerid")
    String ownerid;
    @SerializedName("lynxmain")
    String lynxmain;

    public String getToken(){
        return token;
    }
    public String getPrefix(){
        return prefix;
    }
    public Long getOwnerID(){
        return Long.valueOf(ownerid);
    }
    public Long getLynxmain(){
        return Long.valueOf(lynxmain);
    }


}

