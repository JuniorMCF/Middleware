import socket
import threading
import sys
import hashlib
# configuracion del socket en el servidor

#configuracion para el socket en el middleware
#host_middleware = '192.168.0.106' # ip del middleware
host_middleware = '18.219.234.80'
port_middleware     = 9999       # puerto mediante el cual nos conectaremos al middleware

try:
    connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # abrimos el socket 
except socket.error:
    sys.exit()

def encrypt(text):
    result = hashlib.md5(text.encode())
    result = result.hexdigest()
    return result


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
    message = encrypt(message)
    messageLine = message+"\n"
    
    send_message(connection,messageLine)
        
    if  message == "adios":
        condition = False
        connection.close()#cerramos la conexion al middleware
        
   
    
