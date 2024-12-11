package com.example.manageServers.repo;

import com.example.manageServers.model.server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<server,Long> {
    server findByIoAddress(String ipAddress);
}
