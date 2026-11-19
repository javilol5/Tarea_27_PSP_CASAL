## Tarea 27 - La palabra más larga (UDP)

### 📋 Descripción

El cliente solicita al usuario varias palabras y las envía al servidor mediante el protocolo **UDP**.

El servidor responde indicando cuál es la palabra más larga y su longitud.

Si el cliente no recibe respuesta en un plazo de **2 segundos**, vuelve a intentarlo hasta un máximo de **3 veces**. Si después de los reintentos sigue sin recibir respuesta, muestra el mensaje:

> Servidor no disponible

### ✅ Requisitos para APTO

- [ ] El servidor responde al remitente utilizando `getAddress()` y `getPort()`.
- [ ] Si el servidor no está arrancado, el cliente realiza los **3 reintentos** y termina correctamente.
- [ ] El README explica qué formato se ha elegido para enviar las palabras entre el cliente y el servidor.