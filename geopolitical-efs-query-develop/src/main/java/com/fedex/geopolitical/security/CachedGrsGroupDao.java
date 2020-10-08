package com.fedex.geopolitical.security;

import java.util.List;

import com.fedex.framework.security.server.authorization.model.GroupDao;

/**
 * The Interface CachedGrsGroupDao.
 */
public interface CachedGrsGroupDao extends GroupDao {

	/**
	 * Put the group into cache.
	 * This will ONLY be called by the scheduled task in CachedAuthzDaoImpl.
	 *
	 * @param groupName the group name
	 * @return the list
	 */
	List<String> putGroup(String groupName);

}