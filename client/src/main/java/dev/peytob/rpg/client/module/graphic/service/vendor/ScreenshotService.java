package dev.peytob.rpg.client.module.graphic.service.vendor;

import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.math.geometry.RectI;

public interface ScreenshotService {

    Image makeScreenshot(RectI area);
}
