package dev.peytob.rpg.client.input.hid.service;

import dev.peytob.rpg.client.input.hid.event.HidEvent;

public interface HidEventQueue {
    boolean appendEvent(HidEvent hidEvent);

    HidEvent pollEvent();

    boolean isEventQueueEmpty();

    int getEventQueueSize();
}
