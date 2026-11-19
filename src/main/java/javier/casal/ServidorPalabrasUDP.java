package javier.casal;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class ServidorPalabrasUDP {

    public static final int PUERTO = 5555;
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
    public static final String SUBRAYADO = "\033[4m";



    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("ServidorUDP escuchando en el puerto " + PUERTO + "...");

            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                DatagramPacket recibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(recibido);

                String mensaje = new String(
                        recibido.getData(),
                        0,
                        recibido.getLength(),
                        StandardCharsets.UTF_8
                ).trim();

                System.out.println("Recibido de " + recibido.getAddress() + ":" + recibido.getPort()
                        + " -> \"" + mensaje + "\"");

                String respuestaTexto = calcularPalabraMasLarga(mensaje);
                byte[] respBytes = respuestaTexto.getBytes(StandardCharsets.UTF_8);

                DatagramPacket respuesta = new DatagramPacket(
                        respBytes,
                        respBytes.length,
                        recibido.getAddress(),
                        recibido.getPort()
                );
                socket.send(respuesta);
            }
        } catch (IOException e) {
            System.err.println(RED + "Error" + RESET + " en el servidorUDP: " + e.getMessage());
        }
    }

    private static String calcularPalabraMasLarga(String mensaje) {
        if (mensaje == null || mensaje.isBlank()) {
            return "No pusiste ninguna palabra, " + ORANGE +  "( ͡° ͜ʖ ͡°)" + RESET;
        }

        String[] tokens = mensaje.split(" ");
        String masLarga = "";

        for (String token : tokens) {
            if (!token.isEmpty() && token.length() > masLarga.length()) {
                masLarga = token;
            }
        }

        return GREEN + "Palabra mas larga: " + RESET + SUBRAYADO + masLarga + RESET + " (longitud: " + CYAN + masLarga.length() + RESET + ")";
    }
}
