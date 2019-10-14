package pratofiorito.utils;

public class SimpleTimer {
    private long millisSinceReset;
    
    private boolean stopped;
    private long stopMillis;
    
    public SimpleTimer() {
        reset();
    }
    
    public void reset() {
        stopped = false;
        millisSinceReset = currentMillis();
    }
    
    public void stop() {
        stopMillis = currentMillis();
        stopped = true;
    }
    
    public long currentMillis() {
        return System.nanoTime() / 1000000;
    }
    
    public long elapsedMillis() {
        if(stopped)
            return stopMillis - millisSinceReset;
        return currentMillis() - millisSinceReset;
    }
}
