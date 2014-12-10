package model;

import java.io.InputStream;
import java.util.List;

public interface MemberDAO {
	public abstract MemberBean select(String username);
	public abstract List<MemberBean> select();
	public abstract MemberBean insert(MemberBean bean,InputStream is,long size);
	public abstract MemberBean update(MemberBean bean,InputStream is,long size);
	public abstract boolean delete(String username);
	
}
