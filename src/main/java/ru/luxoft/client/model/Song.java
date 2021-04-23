package ru.luxoft.client.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Song.
 *
 * @author Evgeniy_Medvedev
 */
@Data
public class Song {

    private UUID songId;

    private Singer singer;

    private Album album;

    private String title;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate releaseDate = LocalDate.now();

    private Genre genre;

}