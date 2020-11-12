package exception;

import java.util.HashMap;
import java.util.Map;

public class UnauthorizeException extends Exception {
    private String errorMessage;
    private Map<String, Object> map;

    public UnauthorizeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getMap() {
        map = new HashMap<>();
        map.put("error", new HashMap<String, Object>() {{
            put("message", errorMessage);
        }});
        return map;
    }
}
