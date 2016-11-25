package com.test.data.repository;

import com.test.data.domain.West;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WestTeamRepository extends GraphRepository<West> {
    @Query("MATCH (t:W) WHERE t.name =~ ('(?i).*'+{name}+'.*') " +
            "RETURN t ORDER BY t.name SKIP {skip} LIMIT {limit}")
    Set<West> findWest(@Param("name") String name, @Param("skip") Integer  skip, @Param("limit") Integer  limit);

    @Query("MATCH (t:W) " +
            "WHERE t.name =~ ('(?i).*'+{name}+'.*') " +
            "RETURN COUNT(t) as count")
    Integer findWestCount(@Param("name") String name);
}
