package cj.software.cassandra;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

@ApplicationScoped
public class MappingManagerProducer
{
	private Logger logger = LogManager.getFormatterLogger();

	private MappingManager mappingManager;

	@Inject
	private Session session;

	@Produces
	public MappingManager createMappingManager()
	{
		if (this.mappingManager == null)
		{
			this.logger.info("create the mapping manager instance...");
			this.mappingManager = new MappingManager(this.session);
		}
		return this.mappingManager;
	}
}
