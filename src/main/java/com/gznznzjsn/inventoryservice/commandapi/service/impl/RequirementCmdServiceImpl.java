package com.gznznzjsn.inventoryservice.commandapi.service.impl;

import com.gznznzjsn.inventoryservice.commandapi.command.RequirementCreateCommand;
import com.gznznzjsn.inventoryservice.commandapi.service.RequirementCmdService;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequirementCmdServiceImpl implements RequirementCmdService {

    private final ReactorCommandGateway gateway;

    @Override
    public Mono<UUID> create(final RequirementCreateCommand command) {
        return Mono.just(command)
                .doOnNext(c -> c.setRequirementId(UUID.randomUUID()))
                .flatMap(gateway::send);
    }

}
