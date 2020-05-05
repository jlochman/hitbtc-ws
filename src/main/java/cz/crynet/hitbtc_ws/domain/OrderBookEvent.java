package cz.crynet.hitbtc_ws.domain;

import lombok.Data;

@Data
public class OrderBookEvent {

	private String jsonrpc;
	private String method;

	private OrderBookWs params;

}
