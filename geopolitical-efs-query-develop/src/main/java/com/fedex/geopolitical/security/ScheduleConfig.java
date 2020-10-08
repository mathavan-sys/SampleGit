package com.fedex.geopolitical.security;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * The Class ScheduleConfig.
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
	/** The Constant POOL_SIZE. */
	private static final int POOL_SIZE = 10;

	/** The authz dao. */
	@Autowired
	@Qualifier("com.fedex.framework.security.server.authz.AuthzDao")
	private CachedAuthzDao authzDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.scheduling.annotation.SchedulingConfigurer#
	 * configureTasks(org.springframework.scheduling.config.
	 * ScheduledTaskRegistrar)
	 */
	@Override
	public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
		/*
		 * Since the Cached Authz DAO object gets created in the context and
		 * ALSO returned from the factory, any Scheduled annotations would be
		 * interpreted twice. Due to this, we are scheduling here.
		 */
		taskRegistrar.addFixedDelayTask(new AuthZPutRunnable(authzDao), 3600000);
	}

	/**
	 * Task executor.
	 *
	 * @return the executor
	 */
	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(POOL_SIZE);
	}

}

/**
 * The Runnable class that calls authzDao.putAuthzData()
 */
class AuthZPutRunnable implements Runnable {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthZPutRunnable.class);
	/** The authZ DAO **/
	private final CachedAuthzDao authzDao;

	/**
	 * Constructor that has the AuthZ DAO to use
	 * 
	 * @param authzDao
	 *            The AuthZ DAO to use
	 */
	public AuthZPutRunnable(final CachedAuthzDao authzDao) {
		super();
		this.authzDao = authzDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		LOGGER.info("Loadtion Authz data");
		authzDao.putAuthzData();
	}
}
