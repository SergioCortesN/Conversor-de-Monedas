# Conversor de Monedas 💱

Aplicación de consola en Java para convertir entre más de 160 monedas del mundo en tiempo real, utilizando tasas de cambio actualizadas mediante la API de ExchangeRate-API.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Cómo Funciona](#-cómo-funciona)
- [Requisitos](#-requisitos)
- [Configuración](#-configuración)
- [Uso](#-uso)
- [Implementación Técnica](#-implementación-técnica)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Consideraciones de Uso](#-consideraciones-de-uso)

## ✨ Características

- 🌍 **Soporte para más de 160 monedas** de todo el mundo
- 🔄 **Tasas de cambio en tiempo real** mediante API REST
- 📊 **Interfaz de menú en grid** de 4 columnas para mejor visualización
- ✅ **Validación de entradas** con manejo de errores y mensajes informativos
- 🔐 **Gestión segura de API Key** mediante archivo de configuración
- ⚡ **Cliente HTTP moderno** con soporte para Virtual Threads (Java 21+)
- 🎯 **Navegación intuitiva** con menús numerados y opciones claras

## 🚀 Cómo Funciona

### Flujo Principal

1. **Inicio de la aplicación**: El programa carga todas las monedas disponibles desde la API
2. **Selección de moneda origen**: Se muestra un menú grid con todas las monedas disponibles
3. **Selección de moneda destino**: Se muestra otro menú excluyendo la moneda origen seleccionada
4. **Ingreso del monto**: El usuario ingresa la cantidad a convertir
5. **Conversión**: La aplicación consulta la API y muestra el resultado
6. **Continuar o salir**: Opción de realizar otra conversión o terminar el programa

### Ejemplo de Uso

```
******************************************
Bienvenido al Conversor de Moneda
¿Que moneda desea convertir?'
1) UAE Dirham           2) Afghan Afghani      3) Albanian Lek        4) Armenian Dram
5) Netherlands Antillian Guilder  6) Angolan Kwanza  7) Argentine Peso  8) Australian Dollar
...
161) South African Rand  162) Salir

Elija una opción válida: 8
******************************************
¿A qué Moneda desea convertir el valor
1) UAE Dirham           2) Afghan Afghani      3) Albanian Lek        4) Armenian Dram
...

Elija una opción válida: 50
******************************************
Se convertira de Australian Dollar a Mexican Peso
******************************************
Ingrese el valor a convertir: 
100
100 Australian Dollar son 1234.56 Mexican Peso
```

## 📦 Requisitos

- **Java 21 o superior** (requiere soporte para Virtual Threads)
- **Conexión a Internet** (para consultar las tasas de cambio)
- **API Key de ExchangeRate-API** (gratuita)
- **Biblioteca Gson 2.13.2** (incluida en `lib/`)

## ⚙️ Configuración

### 1. Obtener API Key

1. Visita [ExchangeRate-API](https://www.exchangerate-api.com/)
2. Regístrate para obtener una API Key gratuita
3. Copia tu API Key

### 2. Configurar la API Key

Crea un archivo `config.properties` en la raíz del proyecto:

```bash
cp config.properties.example config.properties
```

Edita el archivo `config.properties` y agrega tu API Key:

```properties
# Configuración de API Keys
exchange.api.key=TU_API_KEY_AQUI
```

⚠️ **Importante**: El archivo `config.properties` está en `.gitignore` y NO se subirá a Git por seguridad.

### 3. Compilar y Ejecutar

#### Usando IntelliJ IDEA:
1. Abre el proyecto en IntelliJ IDEA
2. Asegúrate de que la biblioteca Gson esté en el classpath
3. Ejecuta `Main.java`

#### Usando línea de comandos:
```bash
# Compilar
javac -cp lib/gson-2.13.2.jar -d out src/**/*.java

# Ejecutar
java -cp out:lib/gson-2.13.2.jar Main
```

## 💡 Uso

### Navegación por Menús

- Los menús se presentan en formato **grid de 4 columnas**
- Cada opción está numerada secuencialmente
- Ingresa el número de la opción deseada y presiona Enter

### Validaciones

- ✅ **Opciones numéricas**: Solo acepta números válidos
- ✅ **Rango de opciones**: Valida que la opción esté dentro del rango disponible
- ✅ **Reintentos automáticos**: Si ingresas un dato inválido, el menú se muestra nuevamente
- ✅ **Mensajes informativos**: Indica claramente qué salió mal y qué se espera

### Salir de la Aplicación

Tienes dos formas de salir:
1. Seleccionar la opción **"Salir"** en el menú principal
2. Elegir **"No"** cuando se pregunta si deseas convertir otro valor

## 🛠️ Implementación Técnica

### Arquitectura

El proyecto sigue una arquitectura en capas con separación de responsabilidades:

```
├── Main.java                    # Lógica de presentación y control de flujo
└── DataClasses/
    ├── ExchangeDAO.java         # Capa de acceso a datos (API)
    └── response/
        ├── CodesResponse.java   # Modelo para lista de monedas
        └── ExchangeResponse.java # Modelo para conversión
```

### Componentes Principales

#### 1. **ExchangeDAO** (Data Access Object)
- Gestiona todas las comunicaciones con la API REST
- Utiliza `HttpClient` moderno de Java con Virtual Threads
- Métodos principales:
  - `getCodes()`: Obtiene todas las monedas disponibles
  - `getConverterValue()`: Realiza la conversión entre monedas
- Lee la API Key de forma segura desde `config.properties`

#### 2. **Main** (Controlador y Vista)
- Gestiona el flujo de la aplicación y la interacción con el usuario
- Métodos clave:
  - `main()`: Loop principal de la aplicación
  - `mostrarMenuGrid()`: Renderiza menús en formato grid de 4 columnas
  - `solicitarOpcionValida()`: Valida entradas del usuario con reintentos
  - `initMap()`: Carga y organiza las monedas disponibles
  - `getOption()`: Maneja el proceso de selección y conversión

#### 3. **Modelos de Respuesta**
- `CodesResponse`: Deserializa la lista de códigos de moneda
- `ExchangeResponse`: Deserializa el resultado de conversión

### Tecnologías y Librerías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 25 | Lenguaje base con Virtual Threads |
| Gson | 2.13.2 | Serialización/deserialización JSON |
| HttpClient | Java 11+ | Cliente HTTP moderno y asíncrono |
| ExchangeRate-API | v6 | Fuente de tasas de cambio |

### Características Técnicas Destacadas

- **Virtual Threads**: Uso de `Executors.newVirtualThreadPerTaskExecutor()` para mejor rendimiento
- **HTTP/2**: Cliente configurado para usar HTTP/2 con tiempos de timeout
- **Try-with-resources**: Gestión automática de recursos en lectura de archivos
- **LinkedHashMap**: Mantiene el orden de inserción de las monedas
- **Formateo dinámico**: Cálculo automático del ancho de columnas según el texto más largo

### Patrón de Diseño

El proyecto implementa el patrón **DAO (Data Access Object)**:
- Separa la lógica de acceso a datos de la lógica de negocio
- Facilita el cambio de fuente de datos sin afectar el resto del código
- Mejora la testabilidad y mantenibilidad

## 📁 Estructura del Proyecto

```
Conversor-Moneda/
├── lib/
│   └── gson-2.13.2.jar              # Biblioteca JSON
├── src/
│   ├── Main.java                     # Punto de entrada
│   └── DataClasses/
│       ├── ExchangeDAO.java          # Acceso a API
│       └── response/
│           ├── CodesResponse.java    # Modelo de códigos
│           └── ExchangeResponse.java # Modelo de conversión
├── config.properties                 # Configuración local (NO en Git)
├── config.properties.example         # Plantilla de configuración
├── CONFIGURACION_API.md              # Guía de configuración detallada
├── .gitignore                        # Archivos excluidos de Git
└── README.md                         # Este archivo
```

## ⚠️ Consideraciones de Uso

### Límites de la API Gratuita

La versión gratuita de ExchangeRate-API tiene los siguientes límites:
- **1,500 solicitudes por mes**
- Tasas actualizadas diariamente
- Sin necesidad de tarjeta de crédito

### Manejo de Errores

La aplicación maneja los siguientes escenarios:

| Escenario | Comportamiento |
|-----------|----------------|
| Sin internet | Lanza `RuntimeException` con el error de conexión |
| API Key inválida | Lanza `RuntimeException` al intentar cargar monedas |
| Entrada no numérica | Muestra mensaje de error y vuelve a solicitar |
| Opción fuera de rango | Muestra mensaje con rango válido y vuelve a solicitar |
| Archivo config.properties no existe | Lanza `RuntimeException` al inicializar DAO |

### Recomendaciones

✅ **DO's:**
- Mantén tu API Key en secreto
- Verifica tu conexión a Internet antes de ejecutar
- Usa la opción "Salir" para cerrar correctamente
- Revisa el límite mensual de tu API Key

❌ **DON'Ts:**
- No subas `config.properties` a Git
- No compartas tu API Key públicamente
- No modifiques el código del DAO sin entender el flujo
- No uses la API en producción sin plan de pago (para apps comerciales)

### Mejoras Futuras

Posibles extensiones del proyecto:
- [ ] Historial de conversiones realizadas
- [ ] Exportación de resultados a archivo
- [ ] Gráficos de tendencias de cambio
- [ ] Modo batch para conversión múltiple
- [ ] Cache de tasas de cambio para reducir llamadas a API
- [ ] Interfaz gráfica (GUI)
- [ ] Soporte multiidioma

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos como parte del programa ALURA LATAM.

## 👨‍💻 Autor

Desarrollado como proyecto de práctica de Java y consumo de APIs REST.

---

⭐ Si te gustó este proyecto, dale una estrella en GitHub!
