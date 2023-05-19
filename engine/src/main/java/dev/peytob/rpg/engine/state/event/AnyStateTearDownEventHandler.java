package dev.peytob.rpg.engine.state.event;

import dev.peytob.rpg.engine.state.EngineState;

/**
 * Recommend separated interface for tearDown engine event handlers, that allows any engine state event class.
 */
public abstract class AnyStateTearDownEventHandler extends StateTearDownEventHandler<EngineState> {
}
