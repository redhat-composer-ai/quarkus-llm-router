package com.redhat.composer.initialize;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

import org.jboss.logging.Logger;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.snapshot.CreateRepositoryRequest;
import co.elastic.clients.elasticsearch.snapshot.SharedFileSystemRepository;
import co.elastic.clients.elasticsearch.snapshot.SharedFileSystemRepositorySettings;
import co.elastic.clients.util.ApiTypeHelper;

/**
 * This class is responsible for loading snapshot data into ElasticSearch on startup.
 * It creates a repository and restores a snapshot from a specified location.
 */
@ApplicationScoped
@IfBuildProfile("local")
@IfBuildProperty(name = "quarkus.elasticsearch.devservices.image-name",
                stringValue = "quay.io/redhat-composer-ai/elasticsearch-snapshot:latest")
public class ElasticSearchSnapshotLoader {
  Logger logger = Logger.getLogger(ElasticSearchSnapshotLoader.class);

  @Inject
  private ElasticsearchClient esClient;

  @ConfigProperty(name = "elasticsearch.loader.repo.name", defaultValue = "rh_doc_repo")
  private String repoName;

  @ConfigProperty(name = "elasticsearch.loader.snapshot.name", defaultValue = "20250201_rh_snapshot")
  private String snapshotName;

  @ConfigProperty(
      name = "elasticsearch.loader.snapshot.location",
      defaultValue = "/usr/share/elasticsearch/data/snapshots"
  )
  private String snapshotLocation;

  @Startup
  void init() {
    logger.info("Loading snapshot data for ElasticSearch...");
    try {
      createRepository();
      restoreSnapshot();
    } catch (Throwable e) {
      logger.error("Unable to load snapshot data for ElasticSearch...", e);
    }
  }

  private void createRepository() throws ElasticsearchException, IOException {
    logger.info("Creating new repository for snapshot...");
    SharedFileSystemRepositorySettings settings = SharedFileSystemRepositorySettings.of(r ->
                                                    r.location(snapshotLocation)
                                                    .compress(true));

    SharedFileSystemRepository fileSystemRepo = SharedFileSystemRepository.of(r ->
                                                    r.settings(settings));

    CreateRepositoryRequest request = CreateRepositoryRequest.of(r ->
                                        r.name(repoName)
                                        .repository(s -> s.fs(fileSystemRepo)));

    esClient.snapshot().createRepository(request);
  }

  private void restoreSnapshot() throws Throwable, IOException {
    logger.info(String.format("Restoring snapshot - Repo: %s, Snapshot: %s", repoName, snapshotLocation));

    // https://github.com/elastic/elasticsearch-java/issues/891.  Resolved in ES 8.15.x
    ApiTypeHelper.DANGEROUS_disableRequiredPropertiesCheck(true);
    esClient.snapshot().restore(r -> r
                                .repository(repoName)
                                .snapshot(snapshotName));
    ApiTypeHelper.DANGEROUS_disableRequiredPropertiesCheck(false);
  }

}
