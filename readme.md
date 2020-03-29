
La network en docker

```
docker network create springcloud
```


### MYSQL

## Crear contenedor

```
docker run --name mysql-servicio-items -p 3306:3306  -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=items_cloud
-d —network springcloud mysql:8.0.19
```


Entrar al contenedor (si antes no creamos la base de datos):

```
docker exec -it mysql-servicio-items bash
```

Ya dentro del contenedor:

```
mysql -u root -p
```

Ingresar el password

Create la base:

```
CREATE DATABASE items_cloud;
```

Data base name: items_cloud
-u root -p root


Para ver las tablas:

```
SHOW TABLES;
```

Y luego, por ejemplo:

```
SELECT * FROM PRODUCTOS
```



#### ZIPKIN

```
CREATE DATABASE zipkin;
```

Crear un usuario:

```
use nombre_de_la_base;
```

```
CREATE USER ‘zipkin’@%’ IDENTIFIED BY ‘zipkin’;

GRANT ALL PRIVILEGES ON zipkin.* TO 'zipkin@%';

```

En el caso de crear la base de zipkin [ejecutar](https://raw.githubusercontent.com/openzipkin/zipkin/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql)


Y luego ejecutar (tenemos que tener el mysql ya configurado y el rabbit):

```
docker run -p 9411:9411 --name zipkin-server --network springcloud -e RABBIT_ADDRESSES=microservicios-rabbitmq:5672 -e STORAGE_TYPE=mysql -e MYSQL_USER=zipkin -e MYSQL_PASS=zipkin -e MYSQL_HOST=mysql-servicio-items  -d openzipkin/zipkin:2
````


Sin Mysql:

```
docker run -p 9411:9411 --name zipkin-server --network springcloud -e RABBIT_ADDRESSES=microservicios-rabbitmq:5672 -d openzipkin/zipkin:2
```


### POSTGRES

```
docker run --name postgress-servicio-usuarios -p 5432:5432 -e POSTGRES_PASSWORD=root -e POSTGRES_DB=db_usuarios -d —network springcloud postgres:12.2-alpine
```

Ingresamos:

```
docker exec  -it ea879d83af66 psql -U postgres
```


### RabbitMQ

```
docker run -rm -p 5672: 5672 --hostname my-rabbit --name some-rabbit rabbitmq:3
```

```
docker run -p 15672:15672 -p 5672:5672 -p 4369:4369 -p 5671:5671  --name microservicios-rabbitmq --network springcloud -d rabbitmq:3.8-management-alpine
```



Y el plugin del management

```
docker run -d  —rm —hostname my-rabbit --name rabbit-management -p 15672:15672 rabbitmq:3-management
```


Microservicios:

```
docker build -t config-server:1.0.0 .
docker run -p 8888:8888 --name config-server --network springcloud config-server:1.0.0
```

Probamos

* http://localhost:8888/servicio-item/default
* http://localhost:8888/servicio-productos/default
* http://localhost:8888/servicio-productos/dev
* http://localhost:8888/servicio-usuarios/dev


```
docker build -t eureka-server:1.0.0 .
docker run -p 8761:8761 --name eureka-server --network springcloud eureka-server:1.0.0	
```

```
docker build -t servicio-productos:1.0.0 .
docker run -P -d --name servicio-productos --network springcloud -e SPRING_PROFILES_ACTIVE=prod  -e spring.cloud.config.uri=http://config-server:8888 servicio-productos:1.0.0	
```

```
docker build -t zuul-server:1.0.0 .
docker run -p 8090:8090 -d --name zuulserver --network springcloud -e SPRING_PROFILES_ACTIVE=prod  -e spring.cloud.config.uri=http://config-server:8888 zuul-server:1.0.0
```

```
docker build -t servicio-usuarios:1.0.0 .
docker run -P -d --name servicio-usuarios --network springcloud -e SPRING_PROFILES_ACTIVE=prod -e spring.cloud.config.uri=http://config-server:8888  servicio-usuarios:1.0.0
```
```
docker build -t servicio-oauth:1.0.0 .
docker run -p 9100:9100 -d --name servicio-oauth --network springcloud -e SPRING_PROFILES_ACTIVE=prod  -e spring.cloud.config.uri=http://config-server:8888 servicio-oauth:1.0.0
```
```
docker build -t servicio-items:1.0.0 .
docker run -P -d --name servicio-items --network springcloud -e SPRING_PROFILES_ACTIVE=prod  -e spring.cloud.config.uri=http://config-server:8888 servicio-items:1.0.0
```

### RabbitMQ






