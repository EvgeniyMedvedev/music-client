package ru.luxoft.client.model;

import lombok.Data;

import java.util.UUID;

/**
 * Album.
 *
 * @author Evgeniy_Medvedev
 */
@Data
public class Album {

    private UUID albumId;

    private String albumName;

    private Song song;
}