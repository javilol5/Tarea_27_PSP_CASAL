## Tarea 27 - La palabra más larga (UDP)

### 📋 Descripción

El cliente solicita al usuario varias palabras y las envía al servidor mediante el protocolo **UDP**.

El servidor responde indicando cuál es la palabra más larga y su longitud.

Si el cliente no recibe respuesta en un plazo de **2 segundos**, vuelve a intentarlo hasta un máximo de **3 veces**. Si después de los reintentos sigue sin recibir respuesta, muestra el mensaje:

> Servidor no disponible

### ✅ Requisitos para APTO

- [x] El servidor responde al remitente utilizando `getAddress()` y `getPort()`.
- [x] Si el servidor no está arrancado, el cliente realiza los **3 reintentos** y termina correctamente.
- [x] El README explica qué formato se ha elegido para enviar las palabras entre el cliente y el servidor.



## Formato elegido para enviar las palabras

Elegí enviar las palabras como una **línea de texto plano codificada en UTF-8**, separadas por espacios (`sergio nosergio fernandez nofernandez`).

### Motivos de la elección
- **Simplicidad y legibilidad:** Al tratarse de un conjunto de palabras sin estructura anidada compleja, una cadena de texto plano es directa de construir, leer y depurar.

- **Soporte de caracteres especiales:** Al codificar en `StandardCharsets.UTF_8` garantiza que tildes, eñes y caracteres especiales se transmitan correctamente entre distintos sistemas operativos.

- **Tamaño controlado:** Los datagramas UDP tienen límite de tamaño, por lo que una línea de texto con varias palabras se mantiene por debajo de 1.400 bytes.

## Funcionamiento y requisitos

- **Respuesta al remitente:** El servidor extrae la IP y el puerto de origen de cada datagrama recibido mediante `recibido.getAddress()` y `recibido.getPort()` para enviar la respuesta de vuelta.

- **Timeout y reintentos:** El cliente configura `socket.setSoTimeout(5000)` (5 segundos). Si no recibe respuesta, reintenta 3 veces y, si el servidor sigue sin responder (no está encendido), muestra `Servidor no disponible` y finaliza.