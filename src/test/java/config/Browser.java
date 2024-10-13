package config;

public enum Browser {

    FIREFOX,

    CHROME;

    public String getLowerCase() {
        return this.name().toLowerCase();
    }

}
