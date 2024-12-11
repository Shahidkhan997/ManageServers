package com.example.manageServers.service;

import com.example.manageServers.model.server;

import java.util.Collection;

public interface ServerService {
    server create (server server);
    server ping(String ipAddress);
    Collection<server> List(int limit);
    server get(Long id);
    server update(server server);
    Boolean delete(Long id);

}
