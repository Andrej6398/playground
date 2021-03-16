package vezbe3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter implements Runnable{

    private final BlockingDeque<MyUrl> deque;
    private final WordCounter[] counters;
    private final ConcurrentMap<URL, Integer> results;

    public WordCounter(WordCounter[] counters, ConcurrentMap<URL, Integer> results) {
        this.results = results;
        this.deque = new LinkedBlockingDeque<>();
        this.counters = counters;
    }

    @Override
    public void run() {
        while (true){
            try {
                if(deque.isEmpty()){
                    for(WordCounter wc: counters){
                        MyUrl u = wc.getDeque().pollLast();
                        if(u != null){
                            deque.add(u);
                            break;
                        }
                    }
                }
                MyUrl url = deque.takeFirst();
                int words = countWords(url);

                if(words > 0){
                    results.putIfAbsent(url.getUrl(), words);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int countWords(MyUrl url){
        int words = 0;

        try (BufferedReader in = fetchHtmlFromUrl(url.getUrl())) {
            if(in != null){
                String line = in.readLine();

                while (line != null) {
                    words += countWordsInLine(line);
                    line = in.readLine();
                    if(url.searchForLinks()){
                        searchForLinks(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private int countWordsInLine(String s){
        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    private void searchForLinks(String s){
        if(s == null){
            return;
        }
        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);

        while (m.find()){
            String url = m.group();
            if (url.startsWith("(") && url.endsWith(")"))
            {
                url = url.substring(1, url.length() - 1);
            }
            if(url.startsWith("www")){
                url = "https://" + url;
            }
            try {
                deque.add(new MyUrl(new URL(url), false));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUrl(URL url, boolean searchForUrls){
        deque.add(new MyUrl(url, searchForUrls));
    }

    private BufferedReader fetchHtmlFromUrl(URL url){
        try {
            return new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException ignored) {

        }
        return null;
    }

    public BlockingDeque<MyUrl> getDeque() {
        return deque;
    }
}