package com.example.manageServers.resource;


import com.example.manageServers.model.Response;
import com.example.manageServers.model.server;
import com.example.manageServers.service.implementation.ServerServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.manageServers.enumeration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
    private final ServerServiceImp ServerService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder().timStamp(now()).data(Map.of("servers",ServerService.List(30))).message("servers retrieved").status(OK).statusCode(OK.value()).build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServers(@PathVariable("ipAddress") String ipAddress){
        server server = ServerService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder().timStamp(now()).data(Map.of("server",ServerService.List(30))).message(server.getStatus() == SERVER_UP ? "PING SUCCESS":"NOT pinged").status(OK).statusCode(OK.value()).build()
        );
    }

    @GetMapping("/save")
    public ResponseEntity<Response> saveServer(@Valid server server){
        return ResponseEntity.ok(
                Response.builder().timStamp(now()).data(Map.of("server",ServerService.create(server))).message("Server Created").status(CREATED).statusCode(CREATED.value()).build()
        );
    }
}
