package me.hannsi.melyclient.util.system.math.time;

import java.time.Duration;
import java.time.Instant;

public class TimerUtil {

    private long time;

    public TimerUtil() {
        time = -1;
    }

    public static Instant now() {
        return Instant.now();
    }

    public static long getSecondsPassed(Instant start, Instant current) {
        return Duration.between(start, current).getSeconds();
    }

    public static boolean haveSecondsPassed(Instant start, Instant current, long seconds) {
        return TimerUtil.getSecondsPassed(start, current) >= seconds;
    }

    public static long getMilliSecondsPassed(Instant start, Instant current) {
        return Duration.between(start, current).toMillis();
    }

    public static boolean haveMilliSecondsPassed(Instant start, Instant current, long milliseconds) {
        return TimerUtil.getMilliSecondsPassed(start, current) >= milliseconds;
    }

    public void resetTimeSkipTo(int ms) {
        this.time = System.currentTimeMillis() + ms;
    }

    public boolean passed(long ms) {
        return (System.currentTimeMillis() - this.time >= ms);
    }

    public boolean passedMs(long ms) {
        return passed(ms);
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }

    public boolean passedSec(long second) {
        return passed(second * 1000);
    }

    public boolean passedTick(long tick) {
        return passed(tick * 50);
    }

    public boolean passedMin(long minutes) {
        return passed(minutes * 1000 * 60);
    }

    public long getPassed() {
        return (System.currentTimeMillis() - this.time);
    }

    public TimerUtil reset() {
        this.time = System.currentTimeMillis();
        return this;
    }

    public TimerUtil restart() {
        this.time = -1;
        return this;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long hasPassed() {
        return System.currentTimeMillis() - time;
    }
}