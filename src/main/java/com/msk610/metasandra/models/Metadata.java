package com.msk610.metasandra.models;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

/**
 * Metadata class to model schema used to store metadata
 *
 * @author MD Kabir
 */
@Table(value = "metadatas")
public class Metadata {

    @PrimaryKeyColumn(name = "id", ordinal = 2, type=PrimaryKeyType.PARTITIONED)
    private UUID id = UUID.randomUUID();

    @PrimaryKeyColumn(name="transaction", ordinal=1, type=PrimaryKeyType.CLUSTERED, ordering=Ordering.DESCENDING)
    private UUID transaction = UUIDs.timeBased();

    @PrimaryKeyColumn(name = "namespace", ordinal = 2, type=PrimaryKeyType.PARTITIONED)
    @Indexed("namespace")
    private String namespace;

    @Column("labels")
    Map<String, String> labels;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTransaction() {
        return transaction;
    }

    public void setTransaction(UUID transaction) {
        this.transaction = transaction;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

}
