package cz.crynet.hitbtc_ws.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrderBookWs {

	private List<OrderBookItem> ask;
	private List<OrderBookItem> bid;
	private String symbol;
	private long sequence;
	private String timestamp;

}
