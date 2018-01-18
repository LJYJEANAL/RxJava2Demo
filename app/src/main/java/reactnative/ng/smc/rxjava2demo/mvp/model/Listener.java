package reactnative.ng.smc.rxjava2demo.mvp.model;

public abstract class Listener<Status, Result> {

	public Listener() {
	}

	public abstract void onCallBack(Status status, Result reply);

	public void onSuccess(Result reply) {
	}

	public abstract void onFailed( String  reply);

}
