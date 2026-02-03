package DataClasses.response;

import com.google.gson.annotations.SerializedName;

public class ExchangeResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("conversion_result")
    private String conversion_result;

    public String getResult() {
        return result;
    }

    public String getConversion_result() {
        return conversion_result;
    }
}
