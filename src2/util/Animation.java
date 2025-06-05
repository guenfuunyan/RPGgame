package util;

public class Animation {
    private GameImage[] frames;
    private int currentFrame;
    private int frameDelay;
    private int frameCount;
    private int delayCounter;
    private boolean loop;

    public Animation(GameImage[] frames, int frameDelay, boolean loop) {
        this.frames = frames;
        this.frameDelay = frameDelay;
        this.loop = loop;
        this.currentFrame = 0;
        this.frameCount = frames.length;
        this.delayCounter = 0;
    }

    public void update() {
        delayCounter++;
        if (delayCounter >= frameDelay) {
            currentFrame++;
            if (currentFrame >= frameCount) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = frameCount - 1;
                }
            }
            delayCounter = 0;
        }
    }

    public GameImage getCurrentFrame() {
        return frames[currentFrame];
    }

    public void reset() {
        currentFrame = 0;
        delayCounter = 0;
    }
}