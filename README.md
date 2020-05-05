# hitbtc-ws
This java application demonstrates how to connect to HitBtc websocket API.

https://api.hitbtc.com/

It uses Spring Boot, Lombok and Retrofit.

After running `App`, websocket connection to obtain orderbook updates is opened once per second.

When some number of connections is reached, `ProtocolException` is thrown.

```
java.net.ProtocolException: Expected HTTP 101 response but was '429 Too Many Requests'
	at okhttp3.internal.ws.RealWebSocket.checkResponse(RealWebSocket.java:228) ~[okhttp-3.10.0.jar:na]
	at okhttp3.internal.ws.RealWebSocket$2.onResponse(RealWebSocket.java:195) ~[okhttp-3.10.0.jar:na]
	at okhttp3.RealCall$AsyncCall.execute(RealCall.java:153) [okhttp-3.10.0.jar:na]
	at okhttp3.internal.NamedRunnable.run(NamedRunnable.java:32) [okhttp-3.10.0.jar:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1135) [na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) [na:na]
	at java.base/java.lang.Thread.run(Thread.java:844) [na:na]
```
