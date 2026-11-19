package javier.casal;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientePalabrasUDP {

    public static final String HOST = "localhost";
    public static final int PUERTO_SERVIDOR = 5555;
    public static final int TIMEOUT_MS = 5000;  //5 segundo de espera entre timeout
    public static final int MAX_REINTENTOS = 3;
    public static final int BUFFER_SIZE = 1024;

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String ORANGE = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce varias palabras separadas por espacios: ");
        String entrada = scanner.nextLine().trim();

        if (entrada.isEmpty()) {
            System.out.println("No pusiste ninguna palabra, " + ORANGE +  "( ͡° ͜ʖ ͡°)" + RESET);
            return;
        }

        byte[] datos = entrada.getBytes(StandardCharsets.UTF_8);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);
            InetAddress direccionServidor = InetAddress.getByName(HOST);

            boolean respondido = false;

            // Intento inicial
            for (int intento = 0; intento <= MAX_REINTENTOS; intento++) {
                if (intento > 0) {
                    System.out.println(BLUE + "Reintentando" + RESET + " (" + intento + "/" + MAX_REINTENTOS + ")" + ".".repeat(intento));
                }

                DatagramPacket envio = new DatagramPacket(
                        datos,
                        datos.length,
                        direccionServidor,
                        PUERTO_SERVIDOR
                );
                socket.send(envio);

                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(respuesta);
                    String textoRespuesta = new String(
                            respuesta.getData(),
                            0,
                            respuesta.getLength(),
                            StandardCharsets.UTF_8
                    );
                    System.out.println("Respuesta del servidor: " + textoRespuesta);
                    respondido = true;
                    break;
                } catch (SocketTimeoutException e) {
                    System.out.println("Sin respuesta en " + RED + TIMEOUT_MS + " ms." + RESET);
                }
            }

            if (!respondido) {
                System.out.println("Servidor no disponible");
            }

        } catch (IOException e) {
            System.err.println(RED + "Error" + RESET + " de red en clienteUDP: " + e.getMessage());
        }
    }
}