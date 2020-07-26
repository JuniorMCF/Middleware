import socket
import threading
import sys
import hashlib
from rc4 import encrypt
# configuracion del socket en el servidor

#configuracion para el socket en el middleware
#host_middleware = '192.168.0.106' # ip del middleware
host_middleware = '18.219.234.80'
port_middleware     = 9999       # puerto mediante el cual nos conectaremos al middleware

key = '6e6f742d736f2d72616e646f6d2d6b6579'

try:
	connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # abrimos el socket 
except socket.error:
	sys.exit()


def send_message(socket,message):
	socket.send(message.encode('utf-8'))

def filter_message(message):
	pos = message.find("$")
	cli = message[0:pos+1]
	text = message[pos+1:]
	#text = encrypt(key,text)
	new = cli + text
	return str(new)

try:
	print("Conectando servidor al middleware, mediante el puerto {}",port_middleware)
	connection.connect((host_middleware,port_middleware))
except :
	print("Error de conexion")
	sys.exit()
	
condition = True

while condition:
	#key = 'not-so-random-key'  # plaintext
	message = str(input())
	#message = encrypt(key,message)
	#message = filter_message(message)
	messageLine = message+"\n"
	
	send_message(connection,messageLine)
		
	if  message == "adios":
		condition = False
		connection.close()#cerramos la conexion al middleware
		
   
	
