package bowl.model;

import bowl.observers.ILaneObserver;

public interface ILaneServer extends java.rmi.Remote {
	public void subscribe(ILaneObserver toAdd) throws java.rmi.RemoteException;
};

