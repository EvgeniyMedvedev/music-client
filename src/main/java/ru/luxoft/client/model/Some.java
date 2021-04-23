package ru.luxoft.client.model;

/**
 * Some.
 *
 * @author Evgeniy_Medvedev
 */
public class Some {

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Some{" +
                "info='" + info + '\'' +
                '}';
    }
}