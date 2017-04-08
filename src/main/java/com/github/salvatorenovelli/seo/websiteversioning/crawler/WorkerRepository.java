package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<WorkerDTO, String> {
}
