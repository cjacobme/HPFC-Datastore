package cj.software.cassandra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@ApplicationScoped
public class SessionProducer
{
	private Logger logger = LogManager.getFormatterLogger();

	@Inject
	private ClusterProducer clusterProducer;

	private Session session;

	public SessionProducer()
	{
	}

	@Produces
	@ApplicationScoped
	public Session getSession()
	{
		if (this.session == null)
		{
			String lKeyspaceName = System.getProperty("keyspace");
			if (lKeyspaceName == null)
			{
				throw new IllegalStateException("System Property \"keyspace\" not set");
			}
			this.logger.info("open session on keyspace \"%s\"...", lKeyspaceName);
			Cluster lCluster = this.clusterProducer.getCluster();
			this.session = lCluster.connect(lKeyspaceName);
			this.logger.debug("Session established");
		}
		return this.session;
	}
}
