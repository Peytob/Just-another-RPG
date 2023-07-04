package dev.peytob.rpg.client.module.level.context.component.loading;

import dev.peytob.rpg.core.context.component.template.AsyncTaskComponent;
import dev.peytob.rpg.ecs.component.SingletonComponent;

import java.util.concurrent.Future;

public class TilemapAsyncLoadingComponent extends AsyncTaskComponent implements SingletonComponent {

    public TilemapAsyncLoadingComponent(Future<?> future) {
        super(future);
    }
}
