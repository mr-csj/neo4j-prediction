package com.test.data.repository;

import com.test.data.domain.Playoff;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayoffRepository extends GraphRepository<Playoff> {
    //一个球队可参与的比赛
    @Query("MATCH (p:Playoff) WHERE Not (p)<-[:WIN]-() RETURN p UNION " +
            "MATCH (p:Playoff)<-[:WIN]-(t:Team)  WITH count(t) as count, COLLECT(t.name) AS team, p " +
            "WHERE count < 2 AND NOT {name} IN team RETURN p")
    Iterable<Playoff> findPlayoffByTeamName(@Param("name") String name);

    //还没有球队参与的比赛
    @Query("MATCH (p:Playoff) WHERE Not (p)<-[:WIN]-() RETURN p")
    Iterable<Playoff> findPlayoff();

    //分页比赛列表
    @Query("MATCH (p:Playoff) WHERE p.year =~ ('(?i).*'+{year}+'.*') " +
            "RETURN p ORDER BY p.year DESC SKIP {skip} LIMIT {limit}")
    Set<Playoff> findPlayoff(@Param("year") String year, @Param("skip") Integer  skip, @Param("limit") Integer  limit);

    //比赛总数
    @Query("MATCH (p:Playoff) WHERE p.year =~ ('(?i).*'+{year}+'.*') " +
            "RETURN COUNT(p) AS count")
    Integer findPlayoffCount(@Param("year") String year);
}
