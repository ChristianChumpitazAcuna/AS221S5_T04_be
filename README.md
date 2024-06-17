# API Cognitive Services - GEMINI
## SERVICIO COGNITIVO CHATBOT 📚
 
  > ***Servicio Cognitivo de Gemini, implementado para establecer una conversacion entre el cliente y el bot, es decir, si la persona manda algun tipo de consulta,el bot va a generar una respuesta adecuada con la consulta que se ha realizado. .***

---
## LENGUAJES Y HERRAMIENTAS 💻

-&#160;![Spring](http://img.shields.io/badge/-Spring-6DB33F?style=flat&logo=spring&logoColor=ffffff)
-&#160;![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white)
-&#160; ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
-&#160;![Gemini](https://img.shields.io/badge/Gemini-00DCFA?style=flat&logoColor=white&labelColor=00DCFA&logoWidth=20&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABFklEQVR42mL8//8/AwAIgbA5j4URIBPzBwCJ8yMlPgG+ENoG2hyyGyGgD6P8ZKIrHwGE4TgCrBTKdkRgg/IjNxBOEC0E7I3lQ6J+mAp5C6kGe0PsoD1QC7P6kniTQzsgTktR+hhsINmFgTlohl+pB10E5HZZBvTkg7nZhF3ySx/gaOjo6hpZGRQwFY0YhE0NoIgPqqwLK6UAc3YxmGm5QCrZtQmiqgBbLZmNG2dQGpXWg9nLSBxyqkNsHVUgG0dhDMWsQdlI8FEX2CkllAem/2QKgE0uLYE5f8gPCKUIOSF1AsMclNsQ5gOm9BEOB6UIbKiPipbgUouHDEZQQ5UIF7JTofgSR4FgNOocIBk1uC0XGVCF4UkkzF0soF80Qe50F3YChRiABDyx9BXplrUgAAAABJRU5ErkJggg==)
-&#160; ![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
-&#160; ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&link=hhttps://github.com/Quananhle/Java-Web-Developer)
-&#160;![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)

## CREACIÓN EL SERVICIO CHATBOT
### 1. API KEY - GEMINI 💻

Para poder emplear los servicio de Gemini de Microsoft.Es importante seguir estos pasos:_

1. _Inicia sesión en tu cuenta de google._
2. _Investiga en tu navegador acerca del Api Key Gemini._
3. _Haz clic en "Obtener una clave de API" y esto te enviara a una pagina donde generaras el servicio._
4. _Finalmente, confirma la genera la clave del API._

_¡Listo! Ahora tienes tu api key de Gemini para ser utilizado en tu aplicación._

<img src="https://www.dropbox.com/scl/fi/e48w1ru81fph9ojfps8u5/API-KEY.png?rlkey=jbmq6ijpvvxyncalo802ifd5p&st=u5vkzr1u&dl=0">

<div style="text-align:center;">
    <img src="https://github.com/ChristianChumpitazAcuna/Assistant/assets/111783609/dd5b15c8-feb0-4f20-ade7-ab4746cff5f0" alt="Get-Claude-api-access" width="600" height="320">
</div>

1. **Agregar dependencias**:

_Agregamos las dependencias necesarias para R2DBC y Spring Web_
  ```
  <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
  </dependency>
  <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>r2dbc-postgresql</artifactId>
       <scope>runtime</scope>
  </dependency>    
  ```
2. **Configurar la conexión a la base de datos**:
   
_En el archivo application.yml configuramos lo necesario para la conexion a la base de datos._
```
       server:
        port: 8080
    
    spring:
        r2dbc:
            url: r2dbc:pool:postgresql://aws-0-sa-east-1.pooler.supabase.com:5432/postgres
            username: postgres.fapeflbyihcviewrjreu
            password: ************************
```
_Supabase utiliza claves de API para autenticar y asegurar el acceso a su servicioutiliza para autenticar las solicitudes de administración de la cuenta, como la creación de tablas y la gestión de usuarios, mientras que las claves de API específicas se pueden generar para acceder a bases de datos específicas._


<div style="text-align:center;">
<img src="https://github.com/ChristianChumpitazAcuna/Assistant/assets/111783609/9c624f92-cf94-42fb-89f9-bd6c8ee04c6b" alt="Supabase" width="800" height="210">
</div>

### 2.CONEXIÓN A UNA BASE DE DATOS NUBE POSTGRESQL EN SUPABASE 🔐

 _Utilizamos una base de datos PostgreSQL en la nube proporcionada por Supabase para almacenar datos de manera segura. A continuación, se detallan los pasos para configurar la conexión a esta base de datos:_
 
1. **Registro en Supabase**:
   
  _Accede al sitio web de Supabase y [regístrate](https://supabase.com/dashboard/sign-up) para obtener una cuenta si aún no tienes una.Lo recomendable es iniciar con una cuenta de Github_

3. **Creación de un proyecto**:
   
  _Después de iniciar sesión, crea un nuevo proyecto en Supabase donde se menciona New Project.Para luego elegir una organizacion_

5. **Agregar una base de datos PostgreSQL**:
   
  _Una vez dentro del proyecto, completamos con los datos necesarios para la creacion en Supabase._
  _Elegimos la region que deseamos , lo optimo seria el que se encuentra mas cerca._
  _Configura las opciones según tus necesidades (tamaño de la base de datos, región, etc.) y luego le damos click en crear el proyecto._

7. **Obtención de las credenciales de la base de datos**:
   
  _Una vez que la base de datos esté creada, haz clic en connect y se abrira una ventana._
  _Encuentra las credenciales de conexión, que generalmente incluyen el host, el puerto, el nombre de usuario y la contraseña._

9. **Configuración en el proyecto**
    
_Abre el archivo de configuración del proyecto application.yml y realiza la conexion a la base de datos._

_Una vez que hayas completado estos pasos, tu aplicación estará configurada para conectarse a la base de datos PostgreSQL en la nube en Railway._

-Gemini
_Gemini, puedes generar claves de API que te permitirán interactuar con su API y realizar operaciones automatizadas, como la colocación de órdenes , la obtención de información.
La clave pública se utiliza para identificar tu cuenta y es segura de compartir, mientras que la clave privada se utiliza para firmar tus solicitudes y debe mantenerse 
confidencial para evitar accesos no autorizados a tu cuenta._

<div style="text-align:center;">
<img src="https://github.com/ChristianChumpitazAcuna/Assistant/assets/111783609/da8f681a-fbb6-4a3c-8db0-9992818a5fc8" alt="Get-Claude-api-access" width="800" height="400">
</div>

---
### 3. APLICACIONES Y ARCHIVOS RECOMENDADOS 💻
-Java: JDK 17

-IDE: Intellij idea IDE 

-Maven: Apache Maven 3.6.3

# AUTORES 🧑‍💻
[![Amir Arbieto Contreras](https://img.shields.io/badge/GitHub-Amir%20Arbierto%20Contreras-blue?logo=github)](https://github.com/AmirArbieto)
[![Christian Chumpitaz Acuña](https://img.shields.io/badge/GitHub-Christian%20Chumpitaz%20Acuña-blue?logo=github)](https://github.com/ChristianChumpitazAcuna)
[![Hebert Rivera Perez](https://img.shields.io/badge/GitHub-Hebert%20Rivera%20Perez-blue?logo=github)](https://github.com/Harp09)
