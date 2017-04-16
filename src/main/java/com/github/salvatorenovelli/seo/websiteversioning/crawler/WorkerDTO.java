package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkerDTO {
    @Id
    private String id;

    public WorkerDTO(Worker worker) {
        this.id = worker.getId();
    }
}
