package com.fedex.geopolitical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.TrnslDow;
import com.fedex.geopolitical.model.TrnslDowPK;

@Repository
public interface TrnslDowRepository extends JpaRepository<TrnslDow, TrnslDowPK> {

	@Query("select d from TrnslDow d where d.id.loclcode=?1")
	List<TrnslDow> findByLoclcode(String loclcode);

	@Query("select d from TrnslDow d where d.id.loclcode like ?1%")
	List<TrnslDow> findByLocaleCodeStartsWith(String loclcode);

}
