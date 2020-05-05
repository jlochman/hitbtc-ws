package cz.crynet.hitbtc_ws;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.springframework.lang.Nullable;

@Slf4j
public class WsListener extends WebSocketListener {

	private WsCallback callback;
	private boolean closing = false;

	public WsListener(WsCallback callback) {
		this.callback = callback;
	}

	@Override
	public void onMessage(WebSocket webSocket, String text) {
		if (text.contains("\"result\":true")) {
			log.debug("obtained subscribe info: " + text);
		} else {
			callback.onResponse(text);
		}
	}

	@Override
	public void onClosing(WebSocket webSocket, int code, String reason) {
		closing = true;
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
		if (!closing) {
			callback.onFailure(t);
		}
	}
}
