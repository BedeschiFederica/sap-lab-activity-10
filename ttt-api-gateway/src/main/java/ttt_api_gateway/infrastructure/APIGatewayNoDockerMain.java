package ttt_api_gateway.infrastructure;

import io.vertx.core.Vertx;
import ttt_api_gateway.application.*;

/**
 * 
 * Implementing a simple API Gateway for the TTT Game Server case study
 * 
 */
public class APIGatewayNoDockerMain {

	static final int BACKEND_PORT = 8080;
	
	static final String ACCOUNT_SERVICE_ADDRESS = "http://localhost:9000";
	static final String LOBBY_SERVICE_ADDRESS = "http://localhost:9001";
	static final String GAME_SERVICE_CHANNELS_LOCATION = "locahlost:9092";

	static final int METRICS_SERVER_EXPOSED_PORT = 9401;

			
	public static void main(String[] args) {
				
 		var vertx = Vertx.vertx();

 		AccountService accountService = new AccountServiceProxy(ACCOUNT_SERVICE_ADDRESS);
		LobbyService lobbyService = new LobbyServiceProxy(LOBBY_SERVICE_ADDRESS);
		GameService gameService = new GameServiceEventBasedProxy(vertx, GAME_SERVICE_CHANNELS_LOCATION);

		var service = new APIGatewayController(accountService, lobbyService, gameService, BACKEND_PORT);
		vertx.deployVerticle(service);	
	}

}

