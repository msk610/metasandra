package com.msk610.metasandra.repository;

import com.msk610.metasandra.models.Metadata;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;


/**
 * Metadata repository interface for {@link Metadata} instances.
 * The interface is used to retrieve single or collection of metadata.
 *
 * @author MD Kabir
 */
public interface MetadataRepository extends CassandraRepository<Metadata, UUID> {

    /**
     * Method to retrieve metadata(s) from a given namespace using
     * {@link Query} value.
     *
     * @param namespace
     * @return
     */
    @Query(value = "SELECT * from metadatas WHERE namespace = ?0")
    public List<Metadata> findMetaForNamespace(String namespace);

    /**
     * Method to retrieve metadata given ID using
     * {@link Query} value.
     *
     * @param id
     * @return
     */
    @Query("SELECT * from metadatas WHERE id = ?0 ALLOW FILTERING")
    public Metadata findMetaForID(UUID id);

    /**
     * Method to retrieve metadata given key value pair
     * using {@link Query} value.
     *
     * @param key
     * @param val
     * @return
     */
    @Query("SELECT * from metadatas WHERE labels[?0] = ?1 ALLOW FILTERING")
    public List<Metadata> findMetaForKV(String key, String val);

}
