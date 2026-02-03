import java.util.Currency;
import java.util.Locale;

public class Traductor {

    public String traducir(String code){
        var spanish = Locale.of("es", "MX");
        try {
            var moneda = Currency.getInstance(code);
            return moneda.getDisplayName(spanish).toUpperCase(Locale.ROOT);
        } catch (Exception e) {
            return null;
        }
    }
}
