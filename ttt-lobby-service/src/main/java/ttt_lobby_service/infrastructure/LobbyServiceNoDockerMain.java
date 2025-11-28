package ttt_lobby_service.infrastructure;

import io.vertx.core.Vertx;
import ttt_lobby_service.application.*;

public class LobbyServiceNoDockerMain {

	static final int LOBBY_SERVICE_PORT = 9001;
	
	static final String ACCOUNT_SERVICE_ADDRESS = "http://localhost:9000";
	static final String GAME_SERVICE_CHANNELS_LOCATION = "localhost:9092";

	public static void main(String[] args) {
		
		var vertx = Vertx.vertx();
		var lobby = new LobbyServiceImpl();
		
		AccountService accountService =  new AccountServiceProxy(ACCOUNT_SERVICE_ADDRESS);
		lobby.bindAccountService(accountService);

		GameService gameService =  new GameServiceEventBasedProxy(vertx, GAME_SERVICE_CHANNELS_LOCATION);
		lobby.bindGameService(gameService);
		
		var server = new LobbyServiceController(lobby, LOBBY_SERVICE_PORT);
		vertx.deployVerticle(server);	
	}

}

