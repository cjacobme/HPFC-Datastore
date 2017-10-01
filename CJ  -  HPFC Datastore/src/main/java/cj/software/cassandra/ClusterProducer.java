package cj.software.cassandra;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

import com.datastax.driver.core.Cluster;

@ApplicationScoped
@Singleton
@Startup
public class ClusterProducer
{
	private Cluster cluster;

	public ClusterProducer()
	{
	}

	@PostConstruct
	public void setup()
	{
		getCluster();
	}

	public Cluster getCluster()
	{
		if (this.cluster == null)
		{
			String lHostname = System.getProperty("host");
			if (lHostname == null)
			{
				throw new IllegalStateException("System Property \"host\" not set");
			}
			this.cluster = Cluster.builder().addContactPoint(lHostname).build();
		}
		return this.cluster;
	}
}
