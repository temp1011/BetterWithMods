package betterwithmods.manual.client.manual.segment.render;

import betterwithmods.manual.api.manual.ImageRenderer;

public class NoImageRender implements ImageRenderer {
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(int mouseX, int mouseY) {

    }
}
