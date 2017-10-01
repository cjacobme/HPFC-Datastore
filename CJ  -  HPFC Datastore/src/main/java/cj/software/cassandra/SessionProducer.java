package cj.software.cassandra;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@ApplicationScoped
@Singleton
@Startup
@DependsOn("ClusterProducer")
public class SessionProducer
{
	@Inject
	private ClusterProducer clusterProducer;

	private Session session;

	public SessionProducer()
	{
	}

	@PostConstruct
	public void setup()
	{
		getSession();
	}

	public Session getSession()
	{
		if (this.session == null)
		{
			String lKeyspaceName = System.getProperty("keyspace");
			if (lKeyspaceName == null)
			{
				throw new IllegalStateException("System Property \"keyspace\" not set");
			}
			Cluster lCluster = this.clusterProducer.getCluster();
			this.session = lCluster.connect(lKeyspaceName);
		}
		return this.session;
	}
}
