package vezbe3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Main {

    private static final int THREAD_COUNT = 6;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        WordCounter[] counters = new WordCounter[THREAD_COUNT];
        ConcurrentMap<URL, Integer> results = new ConcurrentHashMap<>();

        for(int i = 0; i < threads.length; i++){
            counters[i] = new WordCounter(counters, results);
            threads[i] = new Thread(counters[i]);
            threads[i].setDaemon(true);
        }

        for (Thread t: threads){
            t.start();
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int threadPointer = 0;

        while (true){
            System.out.println("Unesite putanju do fajla ili kucajte results za rezultate: ");

            try {
                String command = in.readLine();

                if(command.trim().equals("results")){
                    results.forEach((k, v) -> System.out.println(k + ": " + v));
                }else if(command.trim().equals("exit")){
                    break;
                }else {
                    BufferedReader fileReader = new BufferedReader(new FileReader(command));
                    String line = fileReader.readLine();

                    while (line != null){
                        counters[threadPointer % THREAD_COUNT].addUrl(new URL(line), true);
                        threadPointer++;
                        line = fileReader.readLine();
                    }
                    fileReader.close();
                    threadPointer = 0;
                }
            } catch (IOException e) {
                if(e instanceof MalformedURLException){
                    System.out.println("Format url-a nije dobar!");
                }else {
                    System.out.println("Fajl nije pronadjen!");
                }
            }
        }
    }
}