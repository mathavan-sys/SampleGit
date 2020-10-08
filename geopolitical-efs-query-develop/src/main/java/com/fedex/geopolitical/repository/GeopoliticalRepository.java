package com.fedex.geopolitical.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.Geopolitical;
import com.fedex.geopolitical.model.GeopoliticalPK;


@Repository
public interface GeopoliticalRepository extends JpaRepository<Geopolitical,GeopoliticalPK> {

	@Query("select u from Geopolitical u where u.id.geopoliticalId=?1 order by u.id.effectiveDate DESC")
	Optional<List<Geopolitical>> findFirstByGeopoliticalIdOrderByEffectiveDateDesc(String geopoliticalId, Pageable pageable);
	
}
