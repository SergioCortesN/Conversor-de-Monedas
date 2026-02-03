import DataClasses.ExchangeDAO;
import java.util.Scanner;

private final ExchangeDAO converter = new ExchangeDAO();
private final Scanner scanner = new Scanner(System.in);
private Boolean finish = false;
private final Map<String, String> codeOption = new LinkedHashMap<>();
private List<String> listaMoneda = new ArrayList<>();
private String code1;
private String code2;
private int value;
void main() {

    initMap();
   while (!finish){
       System.out.println("******************************************");
       System.out.println("Bienvenido al Conversor de Moneda");
       System.out.println("¿Que moneda desea convertir?'");

       mostrarMenuGrid(listaMoneda);

       int option = solicitarOpcionValida(1, codeOption.size());

       if (option == codeOption.size()){
           finish = true;
       }else{
           getOption(option);
       }


   }
    System.out.println("******************************************");
   System.out.println("Gracias por usar el Conversor de Moneda");
   System.out.println("******************************************");
   System.out.println("Programa terminado");
    System.out.println("******************************************");
}


private void mostrarMenuGrid(List<String> opciones){
    int totalOpciones = opciones.size();
    int columnas = 4;
    int filas = (totalOpciones + columnas - 1) / columnas;

    int longitudMaximaCelda = 0;
    for (int indice = 0; indice < totalOpciones; indice++) {
        String textoCelda = (indice + 1) + ") " + opciones.get(indice);
        if (textoCelda.length() > longitudMaximaCelda) longitudMaximaCelda = textoCelda.length();
    }
    int anchoCelda = longitudMaximaCelda + 4; // padding extra

    for (int fila = 0; fila < filas; fila++) {
        for (int columna = 0; columna < columnas; columna++) {
            int indiceCelda = fila + columna * filas;
            if (indiceCelda < totalOpciones) {
                String textoCelda = (indiceCelda + 1) + ") " + opciones.get(indiceCelda);
                System.out.printf("%-" + anchoCelda + "s", textoCelda);
            }
        }
        System.out.println();
    }
}

private int solicitarOpcionValida(int minimo, int maximo){
    int opcion = -1;
    boolean esValida = false;

    while (!esValida) {
        try {
            System.out.print("Elija una opción válida: ");
            opcion = scanner.nextInt();

            if (opcion >= minimo && opcion <= maximo) {
                esValida = true;
            } else {
                System.out.println("Opción fuera de rango. Por favor, elija entre " + minimo + " y " + maximo);
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.nextLine();
        }
    }

    return opcion;
}

public void initMap(){
    try {
        String[][] codes = converter.getCodes();
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes[i].length; j += 2) {
                if (j + 1 < codes[i].length) {
                    codeOption.put(codes[i][j + 1], "/" + codes[i][j]); // código
                } else {
                    System.out.println(codes[i][j]);     // elemento suelto
                }
            }
        }
    } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
    }
    codeOption.put("Salir", "");

    listaMoneda.addAll(codeOption.keySet());
}

public String getValueConverter(){
    System.out.println("******************************************");
    System.out.println("Ingrese el valor a convertir: ");
    value = scanner.nextInt();
    String response;
    try {
        response = converter.getConverterValue(value, code1, code2);
    } catch (Exception e) {
        response = e.getMessage();
    }
    return response;
}

public void getOption(int option){
    String auxMoneda = listaMoneda.get(option-1);
    code1 = codeOption.get(auxMoneda);
    Map<String, String> codeConverter = new LinkedHashMap<>();
    for (String moneda : codeOption.keySet()){
        if(!moneda.equals("Salir") && !moneda.equals(auxMoneda)){
            codeConverter.put(moneda, codeOption.get(moneda));
        }
    }
    List<String> listConverter = new ArrayList<>(codeConverter.keySet());

    System.out.println("******************************************");
    System.out.println("¿A qué Moneda desea convertir el valor");

    mostrarMenuGrid(listConverter);

    int option2 = solicitarOpcionValida(1, listConverter.size());
    String auxMoneda2 = listConverter.get(option2-1);
    code2 = codeConverter.get(auxMoneda2);
    System.out.println("******************************************");
    System.out.println("Se convertira de " + auxMoneda + " a " + auxMoneda2);
    System.out.println("******************************************");
    String valueConverter = getValueConverter();
    System.out.println(value + " " + auxMoneda + " son " + valueConverter + " " + auxMoneda2);


    System.out.println("******************************************");
    System.out.println("¿Desea convertir algun otro valor?");
    System.out.println("1) Si");
    System.out.println("2) No");
    System.out.println("Elija una opción válida:");
    System.out.println("******************************************");
    int option3 = scanner.nextInt();
    if (option3 == 2){
        finish = true;
    }



}
