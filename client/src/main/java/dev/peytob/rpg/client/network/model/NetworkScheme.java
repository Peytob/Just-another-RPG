package dev.peytob.rpg.client.network.model;

public enum NetworkScheme {

    HTTP("http"),
    HTTPS("https"),
    WEB_SOCKET("ws");

    private final String schemeName;

    NetworkScheme(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeName() {
        return schemeName;
    }
}
