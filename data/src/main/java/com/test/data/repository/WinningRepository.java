package com.test.data.repository;

import com.test.data.domain.Winning;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinningRepository extends GraphRepository<Winning> {
}
