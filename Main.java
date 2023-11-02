import java.io.*;
import java.net.URL;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Main {
    public static void main(String[] args) {
        try {
            // Чтение ссылки на mp3-файл из текстового файла
            BufferedReader br = new BufferedReader(new FileReader("links.txt"));
            String mp3Url = br.readLine();
            br.close();

            // Путь для сохранения mp3-файла
            String mp3SavePath = "downloaded_audio.mp3";

            // Скачивание mp3-файла
            try {
                downloadFile(mp3Url, mp3SavePath);
                System.out.println("MP3-файл скачан");
                playMp3(mp3SavePath);
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private static void playMp3(String mp3FilePath) throws JavaLayerException {
        try (FileInputStream inputStream = new FileInputStream(mp3FilePath)) {
            Player player = new Player(inputStream);
            player.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
