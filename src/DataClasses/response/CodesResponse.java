package DataClasses.response;

public class CodesResponse {
    private String result;
    private String[][] supported_codes;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String[][] getSupported_codes() {
        return supported_codes;
    }

    public void setSupported_codes(String[][] supported_codes) {
        this.supported_codes = supported_codes;
    }

}
