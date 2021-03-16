package vezbe3;

import java.net.URL;

public class MyUrl {
    private final URL url;
    private final boolean searchForLinks;

    public MyUrl(URL url, boolean searchForLinks) {
        this.url = url;
        this.searchForLinks = searchForLinks;
    }

    public URL getUrl() {
        return url;
    }

    public boolean searchForLinks() {
        return searchForLinks;
    }
}
