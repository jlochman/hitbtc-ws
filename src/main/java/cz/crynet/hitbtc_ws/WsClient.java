package cz.crynet.hitbtc_ws;

import cz.crynet.hitbtc_ws.domain.OrderBookEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WsClient {

	private static final String WS_API_BASE_URL = "wss://api.hitbtc.com/api/2/ws";

	private static int idCounter = 1;
	private static final OkHttpClient client;

	static {
		Dispatcher publicWsDispatcher = new Dispatcher();
		publicWsDispatcher.setMaxRequestsPerHost(100);
		publicWsDispatcher.setMaxRequests(1000);
		client = new OkHttpClient.Builder()
				.dispatcher(publicWsDispatcher)
				.pingInterval(10, TimeUnit.SECONDS)
				.connectTimeout(500, TimeUnit.MILLISECONDS)
				.build();
	}

	public Closeable onOrderBookEvent(String symbolName, IOrderBookListener listener) {
		WsRequest req = new WsRequest();
		req.setMethod("subscribeOrderbook");
		req.setId(idCounter++);
		req.addParam("symbol", symbolName);

		return createNewWebSocket(req, new WsListener(new WsCallback() {
			@Override
			public void onResponse(String response) {
				OrderBookEvent event = JsonUtils.stringToObject(response, OrderBookEvent.class);
				if (event == null || event.getMethod() == null) {
					log.error("obEvent or method not parsed from response: " + response);
					return;
				}
				if ("snapshotOrderbook".equals(event.getMethod())) {
					listener.initOrderBook(event.getParams());
				} else if ("updateOrderbook".equals(event.getMethod())) {
					listener.updateOrderBook(event.getParams());
				} else {
					log.error("unhandled event method: " + event.getMethod());
					log.info(response);
				}
			}

			@Override
			public void onFailure(Throwable cause) {
				listener.onFailure(cause);
			}
		}));
	}

	private Closeable createNewWebSocket(WsRequest wsRequest, WsListener listener) {
		Request request = new Request.Builder().url(WS_API_BASE_URL).build();
		final WebSocket ws = client.newWebSocket(request, listener);

		String toSend = JsonUtils.objectToString(wsRequest);
		if (toSend == null) {
			return null;
		}
		ws.send(toSend);
		return () -> {
			final int code = 1000;
			listener.onClosing(ws, code, null);
			ws.close(code, null);
			listener.onClosed(ws, code, null);
		};
	}

	@Data
	private static class WsRequest {
		private String method;
		private int id;
		private Map<String, Object> params = new HashMap<>();

		public void addParam(String key, Object value) {
			params.put(key, value);
		}
	}

}
