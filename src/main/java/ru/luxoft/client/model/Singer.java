package ru.luxoft.client.model;

import lombok.Data;

import java.util.UUID;

/**
 * Singer.
 *
 * @author Evgeniy_Medvedev
 */
@Data
public class Singer {

    private UUID singerId;

    private Song song;

    private String singerName;

}