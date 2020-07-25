import socket
import threading
import sys
# configuracion del socket en el servidor

#configuracion para el socket en el middleware
host_middleware = '192.168.1.12' # ip del middleware
port_middleware     = 9999       # puerto mediante el cual nos conectaremos al middleware

try:
    connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # abrimos el socket 
except socket.error:
    sys.exit()

def send_message(socket,message):
    socket.send(message.encode('utf-8'))

try:
    print("Conectando servidor al middleware, mediante el puerto {}",port_middleware)
    connection.connect((host_middleware,port_middleware))
except :
    print("Error de conexion")
    sys.exit()
    
condition = True

while condition:
    message = str(input())
    messageLine = message+"\n"
    
    send_message(connection,messageLine)
        
    if  message == "adios":
        condition = False
        connection.close()#cerramos la conexion al middleware
        
   
    
    