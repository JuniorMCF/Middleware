import codecs

MOD = 256


def KSA(key):
	key_length = len(key)
	S = list(range(MOD))  # [0,1,2, ... , 255]
	j = 0
	for i in range(MOD):
		j = (j + S[i] + key[i % key_length]) % MOD
		S[i], S[j] = S[j], S[i]  # swap values

	return S


def PRGA(S):
	i = 0
	j = 0
	while True:
		i = (i + 1) % MOD
		j = (j + S[i]) % MOD

		S[i], S[j] = S[j], S[i]  # swap values
		K = S[(S[i] + S[j]) % MOD]
		yield K


def get_keystream(key):
	S = KSA(key)
	return PRGA(S)


def encrypt_logic(key, text):
	#key = [ord(c) for c in key]
	# If key is in hex:
	key = codecs.decode(key, 'hex_codec')
	key = [c for c in key]
	keystream = get_keystream(key)

	res = []
	for c in text:
		val = ("%02X" % (c ^ next(keystream)))  # XOR and taking hex
		res.append(val)
	return ''.join(res)


def encrypt(key, plaintext):
	''' :key -> encryption key used for encrypting, as hex string
		:plaintext -> plaintext string to encrpyt
	'''
	plaintext = [ord(c) for c in plaintext]
	return encrypt_logic(key, plaintext)


def decrypt(key, ciphertext):
	ciphertext = codecs.decode(ciphertext, 'hex_codec')
	res = encrypt_logic(key, ciphertext)
	return codecs.decode(res, 'hex_codec').decode('utf-8')

'''
def main():
	key = 'not-so-random-key'  # plaintext
	plaintext = 'Good work! Your implementation is correct'  # plaintext
	# encrypt the plaintext, using key and RC4 algorithm
	ciphertext = encrypt(key, plaintext)
	print('plaintext:', plaintext)
	print('ciphertext:', ciphertext)
	# ..
	# Let's check the implementation
	# ..
	ciphertext = '2D7FEE79FFCE80B7DDB7BDA5A7F878CE298615'\
		'476F86F3B890FD4746BE2D8F741395F884B4A35CE979'
	# change ciphertext to string again
	decrypted = decrypt(key, ciphertext)
	print('decrypted:', decrypted)

	if plaintext == decrypted:
		print('Congrats ! You made it.')
	else:
		print('Shit! You pooped your pants ! .-.')
'''