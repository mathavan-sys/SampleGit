package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.RefScript;

@Repository
public interface RefScriptRepository extends JpaRepository<RefScript, String> {
	Optional<RefScript> findByScrptCd(String scrptCd);
	
	Optional<RefScript> findByScrptNm(String scrptNm);
	
	Optional<RefScript> findByScrptDesc(String scrptDesc);
	
	@Query("select s from RefScript s where s.scrptCd<>?1 and s.scrptNm=?2")
	Optional<RefScript> findByScrptNmAndNotScrptCd(String scrptCd, String scrptNm);
	
	@Query("select s from RefScript s where s.scrptCd<>?1 and s.scrptDesc=?2")
	Optional<RefScript> findByScrptDescAndNotScrptCd(String scrptCd, String scrptDesc);
}
