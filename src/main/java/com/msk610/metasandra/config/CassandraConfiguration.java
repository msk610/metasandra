package com.msk610.metasandra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

/**
 * CassandraConfiguration class configures cassandra settings and init scripts
 * This class utilizes settings from application.properties to connect to cassandra
 * Keyspace and schema specifications can be set here
 *
 * @author MD Kabir
 */

@Configuration
@PropertySource(value = { "classpath:application.properties" })
@EnableCassandraRepositories(basePackages = { "com.msk610.metasandra" })
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Autowired
    private Environment environment;


    @Override
    protected String getKeyspaceName() {
        return environment.getProperty("spring.data.cassandra.keyspace-name");
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName()).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        return Arrays.asList(specification);
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    public String getContactPoints() {
        return environment.getProperty("spring.data.cassandra.contact-points");
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] { "com.msk610.metasandra.models" };
    }
}
