package com.example.manageServers.service.implementation;

import com.example.manageServers.model.server;
import com.example.manageServers.repo.ServerRepository;
import com.example.manageServers.service.ServerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;

import static com.example.manageServers.enumeration.Status.SERVER_DOWN;
import static com.example.manageServers.enumeration.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImp implements ServerService {
    private final ServerRepository serverRepository;

    @Override
    public server create(server server) {
        log.info("Saving new server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }


    @Override
    public server ping(String ipAddress) {
        log.info("Pinging server IP: {}", ipAddress);
        server server = serverRepository.findByIoAddress(ipAddress);
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        } catch (IOException e) {
            log.error("Error pinging IP address: {}", ipAddress, e);
            server.setStatus(SERVER_DOWN);  // Set server status to down if an exception occurs
        }
        serverRepository.save(server);
        return server;
    }


    @Override
    public Collection<server> List(int limit) {
        log.info("Fetching All Servers");
        return serverRepository.findAll(PageRequest.of(0 ,limit)).toList();
    }

    @Override
    public server get(Long id) {
        log.info("Fetching Server By id:{}",id);
        return serverRepository.findById(id).get();
    }

    @Override
    public server update(server server) {
        log.info("Updating server: {}",server.getName());
        return serverRepository.save(server);
    }

    @Override
    public Boolean delete(Long id) {

        log.info("Deleting server: {}",id);
        serverRepository.deleteById(id);
        return Boolean.TRUE;
   }

    private String setServerImageUrl(){
        return null;
    }
}
