package dev.peytob.rpg.server.server.service;

import dev.peytob.rpg.server.server.event.ServerEvent;

public interface ServerEventBus {

   void publishServerEvent(ServerEvent serverEvent);
}
