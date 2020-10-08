package com.fedex.geopolitical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.TrnslMthOfYr;
import com.fedex.geopolitical.model.TrnslMthOfYrPK;

@Repository
public interface TrnslMthOfYrRepository extends JpaRepository<TrnslMthOfYr, TrnslMthOfYrPK>{

	@Query("select m from TrnslMthOfYr m where m.id.loclcode=?1")
	List<TrnslMthOfYr> findByLoclcode(String loclcode);

	@Query("select m from TrnslMthOfYr m where m.id.loclcode like ?1%")
	List<TrnslMthOfYr> findByLocaleCodeStartsWith(String loclcode);
}
