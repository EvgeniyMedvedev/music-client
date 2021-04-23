package ru.luxoft.client.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import ru.luxoft.client.model.Song;

import java.util.List;
import java.util.UUID;

/**
 * CRUDController.
 *
 * @author Evgeniy_Medvedev
 */
@RestController
@Data
@Slf4j
public class CRUDController {

    private final JmsTemplate jmsTemplate;

    @PostMapping("/create")
    public ResponseEntity<?> createSong(@RequestBody Song song) {
        jmsTemplate.convertAndSend("java:jms/queue/music/create", song);

        Object o = jmsTemplate.receiveAndConvert("java:jms/queue/music/create-answer");

        log.info("Song {} has been sent into queue - \"java:jms/queue/music/create\"", song);

        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping("/get/{parameter}")
    public List<Song> getSong(@PathVariable String parameter) {
        jmsTemplate.convertAndSend("java:jms/queue/music/askedBy", parameter);

        Object obj = jmsTemplate.receiveAndConvert("java:jms/queue/music/askedBy-answer");

        log.info("Song {} has been got from queue", obj);

        return (List<Song>) obj;
    }

    @GetMapping("/getAll")
    public List<Song> getAll() {
        jmsTemplate.convertAndSend("java:jms/queue/music/askedBy", "findAll");

        Object obj = jmsTemplate.receiveAndConvert("java:jms/queue/music/askedBy-answer");

        return (List<Song>) obj;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSong(@ModelAttribute("song") Song song) {
        jmsTemplate.convertAndSend("java:jms/queue/music/update", song);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSong(@RequestBody UUID uuid) {
        jmsTemplate.convertAndSend("java:jms/queue/delete/music", uuid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}