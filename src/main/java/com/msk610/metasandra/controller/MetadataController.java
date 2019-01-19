package com.msk610.metasandra.controller;

import com.msk610.metasandra.models.Metadata;
import com.msk610.metasandra.repository.MetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Metadata controller for {@link Metadata} instances.
 * This class utilizes {@link MetadataRepository} to make crud operations.
 * This class provides rest api methods to make crud operations for metadatas.
 *
 * @author MD Kabir
 */
@RestController
@RequestMapping("/api")
public class MetadataController {

    @Autowired
    private MetadataRepository metaRepo;

    private static final Logger logger = LoggerFactory.getLogger(MetadataController.class);

    /**
     * GET Request Method to retrieve metadata(s) from a given namespace.
     *
     * @param namespace
     * @return
     */
    @GetMapping("/metadatas/{namespace}")
    public List<Metadata> getMetadatasForNamespace(@PathVariable String namespace) {
        List<Metadata> datas =  metaRepo.findMetaForNamespace(namespace);
        logger.info("Found " + String.valueOf(datas.size()) + "entries for " + namespace);
        return datas;
    }

    /**
     * POST Request Method to create a new metadata entry.
     *
     * @param data
     * @return
     */
    @PostMapping("/metadata")
    public Metadata createMetadata(@Valid @RequestBody Metadata data) {
        logger.info("Created entry: " + data.getLabels().toString() + "for " + data.getNamespace());
        return metaRepo.save(data);
    }

    /**
     * POST Request Method to query for metadata using key value pair labels.
     *
     * @param labels
     * @return
     */
    @PostMapping("/metadata/labels")
    public Map<String, List<Metadata>> getMetadataByLabels(@Valid @RequestBody  Map<String,String> labels) {
        logger.info("Received label query: " + labels.toString());
        Map<String, Boolean> unique = new HashMap<>();
        Map<String, List<Metadata>> response = new HashMap<>();
        for (String key : labels.keySet()) {
            List<Metadata> data = metaRepo.findMetaForKV(key, labels.get(key));
            for( Metadata d : data) {
                if (!unique.getOrDefault(d.getId().toString(), false)) {
                    if (response.getOrDefault(d.getNamespace(), null) == null) {
                        response.put(d.getNamespace(), new ArrayList<>());
                    }
                    response.get(d.getNamespace()).add(d);
                    unique.put(d.getId().toString(), true);
                }
            }
        }
        for (String key : response.keySet()) {
            logger.info("Query Found " + String.valueOf(response.get(key).size()) + "entries for " + key);
        }
        return response;
    }

    /**
     * DELETE Request Method to delete a metadata given id.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/metadata/{id}")
    public Metadata removeMetadata(@PathVariable UUID id) {
        Metadata dataToRemove = metaRepo.findMetaForID(id);
        logger.info("Removing metadata: " + id.toString());
        metaRepo.delete(dataToRemove);
        return dataToRemove;
    }

    /**
     * PUT Request Method to update a metadata.
     *
     * @param updated
     * @return
     */
    @PutMapping("/metadata/{id}")
    public Metadata updateMetadata(@PathVariable UUID id, @Valid @RequestBody Metadata updated) {
        Metadata dataToUpdate = metaRepo.findMetaForID(id);
        logger.info("Updating metadata for: " + id.toString());
        dataToUpdate.setLabels(updated.getLabels());
        dataToUpdate.setNamespace(updated.getNamespace());
        metaRepo.save(dataToUpdate);
        return dataToUpdate;
    }

}
