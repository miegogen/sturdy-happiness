package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {

        // Готов получать сообщения
        try(ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server has started.\nListening for messages");

            while (true) {
                // Обработка нового входящего сообщения

                try(Socket client = serverSocket.accept()) {
                    // client <-- сообщения стоят в очереди в нём
                    System.out.println("Debug: got new message " + client.toString());
                    // Читать запрос - слушать сообщение
                    InputStreamReader streamReader = new InputStreamReader(client.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(streamReader);

                    // Читает первый запрос от пользователя
                    StringBuilder request = new StringBuilder();

                    String line = bufferedReader.readLine();    // держит одно сообщение за раз

                    while (!line.isBlank()) {
                        request.append(line + "\r\n");      // собирает входящее сообщение
                        line = bufferedReader.readLine();
                    }

                    System.out.println("--REQUEST--");
                    System.out.println(request);

                    // Загрузить изображение с диска
                    FileInputStream image2 = new FileInputStream("fav.png");
//
                    OutputStream clientOutput = client.getOutputStream();
                    clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                    clientOutput.write(("\r\n").getBytes());

                    clientOutput.write(image2.readAllBytes());  // Перевод изображения в байты и отправка клиенту
//                    clientOutput.write(("IT IS NOT WRONG\r\n").getBytes());   // Перевод текста в байты и отправка клиенту
                    clientOutput.flush();





                    client.close();
                }
            }
        }
    }
}
