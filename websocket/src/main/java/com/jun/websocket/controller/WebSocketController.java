package com.jun.websocket.controller;

import com.jun.websocket.server.WebSocketServer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author songjun
 * @date 2021-04-22
 * @desc
 */
@RequestMapping("/api")
@Validated
@RestController
public class WebSocketController {

    @GetMapping("/ws/sendOne")
    public void get(String message , String id) throws IOException {
        WebSocketServer.SendMessage(id,message);
    }

    @GetMapping("/ws/sendAll")
    public void get(String message) throws IOException {
        WebSocketServer.BroadCastInfo(message);
    }
}
