package messages;


import messaging.AbstractResponse;
import messaging.MessageUser;
import messaging.MessageValueType;
import messaging.environment.EnvMessageType;

public class MCStateResponse extends AbstractResponse{
	double position;
	double velocity;
	double height;
	double deltaheight;
	

	public MCStateResponse(double position, double velocity, double height,double deltaheight) {
		this.position=position;
		this.velocity=velocity;
		this.height=height;
		this.deltaheight=deltaheight;
	}

	@Override
	public String toString() {
		String theResponse="MCStateResponse: not implemented ";
		return theResponse;
	}


	@Override
	public String makeStringResponse() {
		StringBuffer theResponseBuffer= new StringBuffer();
		theResponseBuffer.append("TO=");
		theResponseBuffer.append(MessageUser.kBenchmark.id());
		theResponseBuffer.append(" FROM=");
		theResponseBuffer.append(MessageUser.kEnv.id());
		theResponseBuffer.append(" CMD=");
		theResponseBuffer.append(EnvMessageType.kEnvResponse.id());
		theResponseBuffer.append(" VALTYPE=");
		theResponseBuffer.append(MessageValueType.kStringList.id());
		theResponseBuffer.append(" VALS=");
		
		theResponseBuffer.append(position);
		theResponseBuffer.append(":");
		theResponseBuffer.append(velocity);
		theResponseBuffer.append(":");
		theResponseBuffer.append(height);
		theResponseBuffer.append(":");
		theResponseBuffer.append(deltaheight);
		theResponseBuffer.append(":");
		
		
		return theResponseBuffer.toString();
	}

	public double getPosition() {
		return position;
	}

	public double getVelocity() {
		return velocity;
	}

	public double getHeight() {
		return height;
	}

	public double getDeltaheight() {
		return deltaheight;
	}
};