package model;

import java.io.InputStream;

public interface StoreInformationDAO {
	public abstract StoreInformationBean select();
	public abstract StoreInformationBean select(String storeName);
	public abstract StoreInformationBean insert(StoreInformationBean bean,InputStream is,long size);
	public abstract StoreInformationBean update();
	public abstract boolean delete(String storeName);
}
