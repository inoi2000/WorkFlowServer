package com.petproject.workflow.store;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FileKeyRepository extends CrudRepository<FileKey, UUID> {
}
