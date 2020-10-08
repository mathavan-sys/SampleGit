package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.GeoplOrgStd;

@Repository
public interface GeoplOrgStdRepository extends JpaRepository<GeoplOrgStd, String>{
	Optional<GeoplOrgStd> findByOrgStdCd(String orgStdCd);
	
	Optional<GeoplOrgStd> findByOrgStdNm(String orgStdNm);
	
	@Query("select g from GeoplOrgStd g where g.orgStdCd<>?1 and g.orgStdNm=?2")
	Optional<GeoplOrgStd> findByOrgStdNmAndNotOrgStdCd(String orgStdCd, String orgStdNm);
}
