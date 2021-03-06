package ru.luxoft.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MusicClient.
 *
 * @author Evgeniy_Medvedev
 */
@SpringBootApplication
//liqubase or flyway read about kafka
public class MusicClient {

    public static void main(String[] args) {
        SpringApplication.run(MusicClient.class, args);
    }
}