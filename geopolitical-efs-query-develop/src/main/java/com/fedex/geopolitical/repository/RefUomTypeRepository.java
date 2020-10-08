package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.RefUomType;

@Repository
public interface RefUomTypeRepository extends JpaRepository<RefUomType, String>{
	Optional<RefUomType> findByUomTypeCd(String uomTypeCd);
	
	Optional<RefUomType> findByUomTypeNm(String uomTypeNm);
	
	Optional<RefUomType> findByUomTypeDesc(String uomTypeDesc);
	
	@Query("select u from RefUomType u where u.uomTypeCd<>?1 and u.uomTypeNm=?2")
	Optional<RefUomType> findByUomTypeNmAndNotUomTypeCd(String uomTypeCd, String uomTypeNm);
	
	@Query("select u from RefUomType u where u.uomTypeCd<>?1 and u.uomTypeDesc=?2")
	Optional<RefUomType> findByUomTypeDescAndNotUomTypeCd(String uomTypeCd, String uomTypeDesc);
}
