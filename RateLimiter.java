import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private static final int MAX_REQUESTS = 10;
    private static final long TIME_WINDOW_MS = 60*60*1000L;

    private final Map<String, Deque<Long>> userRequestMap = new HashMap<>();

    public boolean allowRequest(String userId){
        long currentTime = System.currentTimeMillis();
        userRequestMap.putIfAbsent(userId, new ArrayDeque<>());
        Deque<Long> timestamps = userRequestMap.get(userId);

        while(!timestamps.isEmpty() && currentTime - timestamps.peekFirst() >= TIME_WINDOW_MS) {
            timestamps.pollFirst();
        }
        if(timestamps.size()  < MAX_REQUESTS){
            timestamps.offerLast(currentTime);
            return true;
        }
        return false;
  }

}
