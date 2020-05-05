package cz.crynet.hitbtc_ws;

import cz.crynet.hitbtc_ws.domain.OrderBookWs;

public interface IOrderBookListener {

	void initOrderBook(OrderBookWs orderBookWs);

	void updateOrderBook(OrderBookWs orderBookWs);

	default void onFailure(Throwable cause) {
	}

}
