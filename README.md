## Documento de decisiones

He decidido optar por una arquitectura hexagonal ya que he deducido que la aplicación podría tener bastante concurrencia en cuanto a peticiones, acceso a datos... En cuanto a metodología de desarrollo he optado por Test Driven Development a parte de las ventajas que tiene también me resulta más fácil pensar primero en lo que tiene que hacer la aplicación pasito a pasito.

El problema más dificl de afrontar fue el de pensar la propia arquitectura, ya que no tengo mucha experiencia en arquitectura hexagonal, pero tomé un curso y estos fueron los resultados.

# Bounded context
Tengo un anuncio, que tiene imágenes relacionadas esas imágenes tienen, su url, y calidad, también tiene una descripción que tiene relacionadas palabras claves. Tiene también una tipología, metros cuadrados de la casa o piso, encaso de tener jardin también los metros cuadrados, tiene un score y por ultimo una fecha que indica desde cuando ese anuncio ha dejado de ser relevante en función del score.

Acontinuación muestro como he mapeado estos conceptos a código.

# Capa de Dominio

Creé la Clase Ad la cual tiene los siguientes ValueObjects.

- DescriptionVO: Tiene toda la lógica para validar la descripción, tiene un método para coger las palabras del texto de descripción y guardarlas en una lista llamada words, gracias a esto pude luego comprobar si la descripcion tiene palabras o esta vacia y poder devolver el número de palabras.
- GardenSizeVO: Este VO lanza una excepción si se la pasa como argumente un valor menor a 0, tambien tiene un método para devolver el valor y otro para devolver si el valor está vacio.
- HouseVO igual a GardenSize VO, con los mismos métodos.
- IrrelevantSinceVO: En esta clase opté por tener por defecto una fecha que fuese  January 1, 1970, 00:00:00 para poder luego mapear este campo sin que lanzará un nullPointerException, también tiene un constructor que se le pasa una fecha.
- Typology: Es un enumerado con las tipologías correspondientes.

Creé también la clase Score que para mi no entran en la definición de valueObjects ya que no son inmutables. Esta clase tiene toda la lógica para calcular añadir puntos o restar, sin que se pase del límite definido(0-100), tiene un método llamado increase el cual se le pasa un entero como argumento, si este entero es negativo se lanzará una excepción. Tiene el metodo decrease que tambien se le pasa un entero como argumento, este metodo comprueba si el score actual es más grande o igual que el valor pasado por argumento asi se evita poder bajar el score a números negativos, por último tiene un método llamado value que devuelve el valor del score.

Por otro lado creé la clase Picture con los siguientes ValueObjects
- Quality: Es un enumerado con los valors HD y SD.
- UrlVO: La cual recibe tiene un constructor que recibe por parámetro un url de tipo String, aquí utilicé una librería externa para validar si un URL está bien formado o no en caso de no estarlo se lanza una excepción.

He optado por hacer ValueObjects ya que así puedo tener toda esta pequeña lógica de validación centralizada, tambien me permite tener mis propios tipos que tienen que ver con el negocio.

La clase Ad tiene los siguientes métodos:
- IsRelevant que comprueba si el anuncio tiene un score mayor a 40.
- IsComplete que devuelve si el anuncio es completo en función de los requisitos del funcional. Este método se apoya de otros 3 métodos **isApartamentComplete**,**IsChaletComplete**,**IsGagrageComplete**, quí unas de las desventajas de mi solución es tener que coger la tipología para utilizar un método u otro, talvez una manera mas elegante de hacer esto sería tener una clase por cada tipología que hereden de la clase Ad, también se podria hacer Ad abstracta poniendo el método isComplete abastracto dejando así la implementación para cada clase concreta.

## Reglas para la puntuación

Para implementar las reglas de validación he utilicé el patrón strategy, primero creé la interfaz RatingRule con un método getScore que recibe por parametro un anuncio, despues creé 3 implementaciones, **CompletenessRule**,**DescriptionRule** y **ImageRule** cada una de estas clases cálcula la puntuación que se le dará al anuncio y será devuelta por el método getScore.
Si vamos clase por clase:
- CompletenessRule sobreescribe el metodo getScore en este método se llama al método isComplete definido en la clase Ad, en caso de que lo sea devuelve una constante llamada MAX_COMPLETENESS_SCORE que tiene como valor 40.
- DescriptionRule: el método getScore se apoya de varios métodos privados, primero compruebo si el Ad tiene una descripción, despues obtengo las palabras y la cantidad de palabras para luego utilizarlo en los metodos, addFlatScore que devuelve el score si cumplen las reglas pertinentes apoyandose tambien en 2 métodos privados llamados isDescLenthInRange y isWordLengthGreaterThanMaxSize.
Tambien addChaletScore que se apoya en el método privado is isWordLengthGreaterThanMaxSize.
Aquí he decidido utilizar las palabras claves hardcodeadas, pero creo que sería mejor tenerlas en base de datos y obtenerlas de ahí.
-ImageRule: Aquí el método getScore comprueba si el anuncio tiene imágenes, en caso de no tenerla devuelve -10, en el caso contrario recorre el array de imágenes y comprueba si es HD o SD y le suma los puntos en cada caso.

Despues para finalmente asignar los puntos totales al anuncio, creé un servicio llamado PointsCalculatorImp que implementa la interfaz PointsCalculator que tiene una firma llamada calculate, he querido hacerlo así pensando en que podrían haber otras maneras de calcular los puntos y para mantener la L y la D de SOLID. La implementación instancia en cada método la regla que va a utilizar, de cada una de ellas coge los puntos y llama al método increase de la clase Score para sumarle los puntos obtenidos en el caso de que sean puntos negativos se llama al método decrease, en este caso la unica regla que resta puntos es la de las imágenes, lo que hice fue comprobar si el resultado de ratingRule.getScore es negativo, en caso afirmativo llamo al método decrease de Score.

#Capa de Aplicación

En esta capa creé los casos de usos de la Apliación, ListIrrelevantAds,ListRelevantAds,CalculateAdScore.
- ListIrrelevantAds y ListRelevantAds usan la interfaz AdRepository de dominio, llaman al método getAll y aplico un filtro utilizando streams y llamando al método isRelevant de la clase Ad.
- CalculateAdScore: Usa la interfaz PointsCalculator de dominio y llama al método calculate.

Después creé un servicio llamado RankingService y su implementación utiliza los casos de uso previamente mencionados.

# Capa de infraestructura
En el paquete de persistencia cree las 2 clases modelo Ad y Picture para que se mapeen y persistan en base de datos, no utilicé ningún mapper externo aunque lo mejor hubiese sido usar mapstruct. Luego para PublicAd y QualityAd tambien utilicé el mapeador que creé.

## Preguntas
- ¿Que arquitectura has usado? He usado arquitectura hexagonal ya que ofrece una manera más ágil de construir y testear software.
    - ¿Se te ocurre alguna otra? Podría haber utilizado una arquitectura por capas más simple tipo MVC.
    - ¿Que ventajas y desventajas ofrecería esa alternativa? La ventaja principal que le veo es que a la hora de implementarla es más sencilla y rápida, por contraparte no es tan fácil de escalar si se hace muy grande.
        
    -   En el esqueleto te dejamos preparadas algunas clases, entre ellas  `PublicAd`  y  `QualityAd`¿Que te aportan? Aportan una manera de transportar los datos que quieres que se envien al exterior(DTO)
    - ¿Si lo hicieras desde cero, las eliminarías? Probablemente hubiese usado proyecciones para no tener que usar algun mapper.
        
    -   ¿Que cambiarías sobre el esqueleto que te hemos proporcionado? La clase AdVO
    -  ¿Por qué? Según tengo entendido los ValueObject se identifican por los valores y no por el ID, había una clase llamada AdVO la cual tenía una ID y además no se reescribian los métodos equals y hashcode tal vez hubiera cambiado eso. Tambien hubiera preferido calcular las puntuaciones al vuelo cuando se crean los anuncios o cuando sean editados ya que al tener un solo endpoint para realizar todos los calculos, lo veo un cuello de botella en ese punto.
        
    -   A continuación te presentamos algunos escenarios en los que podría ejecutarse tu aplicación. Cuéntanos en cada uno que puntos fuertes y débiles ves en tu solución:
        
        -   **Gran cantidad de peticiones para listar los anuncios:** Gracias a que cada capa no esta acoplada una a la otra se podría utilizar un balanceador de carga, y distribuir la aplicacion en varias instancias, tambien una buena manera de optimizar las peticiones es implementar un almacenamiento en caché.
        -   **Muchos cambios en los anuncios, por lo que hay que recalcular a menudo su puntuación:** En esta solución se ofrece un solo endpoint para calcular las puntuaciones porlo que supondria un cuello de botella, una posible solucion sería ejecutar esta operación a la hora de crear el anuncio o actualizar el anuncio dejando esa responsabilidad a la aplicación sin tener que llamar al endpoint.
        -   **Alta concurrencia en el acceso a los datos:** Al implementar una base de datos en mémoria no tendría mucha escalabilidad así que se podría implementar una base de datos para escritura y otra para lectura. Otra cosa que se podría hacer es poner un caché entre la base de datos y la applicación así se liberará de carga la base de datos.
        -   **Gran cantidad de anuncios:** Como ya he mencionado dado a tener una base de datos en memoria supondría un problema, pero al utilizar arquitectura hexagonal dónde las implementaciones de base de datos no implican un gran problema, se podría cambiar a un MongoDB por ejemplo por la escalabilidad horizontal que tiene.
