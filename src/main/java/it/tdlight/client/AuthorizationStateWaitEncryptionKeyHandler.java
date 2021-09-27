package it.tdlight.client;

import it.tdlight.common.ExceptionHandler;
import it.tdlight.common.TelegramClient;
import it.tdlight.jni.TdApi.AuthorizationStateWaitEncryptionKey;
import it.tdlight.jni.TdApi.AuthorizationStateWaitTdlibParameters;
import it.tdlight.jni.TdApi.CheckDatabaseEncryptionKey;
import it.tdlight.jni.TdApi.SetTdlibParameters;
import it.tdlight.jni.TdApi.TdlibParameters;
import it.tdlight.jni.TdApi.UpdateAuthorizationState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class AuthorizationStateWaitEncryptionKeyHandler implements GenericUpdateHandler<UpdateAuthorizationState> {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationStateWaitEncryptionKeyHandler.class);

	private final TelegramClient client;
	private final ExceptionHandler exceptionHandler;

	public AuthorizationStateWaitEncryptionKeyHandler(TelegramClient client, ExceptionHandler exceptionHandler) {
		this.client = client;
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public void onUpdate(UpdateAuthorizationState update) {
		if (update.authorizationState.getConstructor() == AuthorizationStateWaitEncryptionKey.CONSTRUCTOR) {
			client.send(new CheckDatabaseEncryptionKey(), ok -> {}, ex -> {
				logger.error("Failed to manage TDLight database encryption key!", ex);
				exceptionHandler.onException(ex);
			});
		}
	}
}
