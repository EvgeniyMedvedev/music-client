package ru.luxoft.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import ru.luxoft.client.model.Song;
import ru.luxoft.client.property.QueueProperty;

import java.util.List;
import java.util.UUID;

/**
 * CRUDController.
 *
 * @author Evgeniy_Medvedev
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CRUDController {

    private final JmsTemplate jmsTemplate;

    private final QueueProperty property;

    @PostMapping("/create")
    public ResponseEntity<?> createSong(@RequestBody Song song) {
        jmsTemplate.convertAndSend(property.getCreate(), song);

        Object o = jmsTemplate.receiveAndConvert(property.getCreateAnswer());

        log.info("Song {} has been sent into queue - \"{}\"", song, property.getCreate());

        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping("/get/{parameter}")
    public List<Song> getSong(@PathVariable String parameter) {
        jmsTemplate.convertAndSend(property.getRead(), parameter);

        Object obj = jmsTemplate.receiveAndConvert(property.getReadAnswer());

        log.info("Song {} has been got from queue", obj);

        return (List<Song>) obj;
    }

    @GetMapping("/getAll")
    public List<Song> getAll() {
        log.debug("connecting is going {}", property.getRead());
        jmsTemplate.convertAndSend(property.getRead(), "findAll");

        Object obj = jmsTemplate.receiveAndConvert(property.getReadAnswer());

        return (List<Song>) obj;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSong(@ModelAttribute("song") Song song) {
        jmsTemplate.convertAndSend(property.getUpdate(), song);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSong(@RequestBody UUID uuid) {
        jmsTemplate.convertAndSend(property.getDelete(), uuid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}