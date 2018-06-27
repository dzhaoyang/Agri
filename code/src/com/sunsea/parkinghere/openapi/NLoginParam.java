package com.sunsea.parkinghere.openapi;

import com.sunsea.parkinghere.framework.data.IDataObj;

public interface NLoginParam extends IDataObj {
	/**
	 * @return the account
	 */
	public String getAccount();

	/**
	 * @param account
	 *          the account to set
	 */
	public void setAccount(String account);

	/**
	 * @return the password
	 */
	public String getPassword();

	/**
	 * @param password
	 *          the password to set
	 */
	public void setPassword(String password);
}
