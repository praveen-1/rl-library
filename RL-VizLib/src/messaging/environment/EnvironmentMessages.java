package messaging.environment;
import messaging.AbstractMessage;
import messaging.GenericMessage;
import visualization.QueryableEnvironment;


	public class EnvironmentMessages extends AbstractMessage{
	
		public EnvironmentMessages(GenericMessage theMessageObject){
			super(theMessageObject);
		}

		
		public String handleAutomatically(QueryableEnvironment theEnvironment){
			return "no response";
		}

	};


