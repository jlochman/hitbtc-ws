package cz.crynet.hitbtc_ws;

@FunctionalInterface
public interface WsCallback {

	void onResponse(String response);

	default void onFailure(Throwable cause) {
	}

}
