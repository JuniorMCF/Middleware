# Middleware

_Middleware desarollado en java, para paso de mensajes entre un servidor desarrollado en python, hacia clientes java.

##Instrucciones

_Ejecutar el middleware para que se abran 2 sockets que cumpliran la funcion de servidor en el middleware._
Previamente debe de compilar los archivos   middleware.java con todos sus paquetes  middleware.LP1 y middleware.LP2, o ejecutarlo en algun IDE como netbeans o eclipse.

```
java middleware.class
```

_Conectar el servidor python hacia el middleware, por el puerto 4444.

```
python3 servidor.py
```

_Por ultimo conectar los clientes hacia el middleware, por el puerto 9999._
Previamente compilando los archivos.

```
java cliente.class
```
