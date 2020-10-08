package com.fedex.geopolitical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fedex.geopolitical.model.RefLanguage;

@Repository
public interface RefLanguageRepository extends JpaRepository<RefLanguage, String>{
	Optional<RefLanguage> findByLangCd(String langCd);
	
	@Query("select l from RefLanguage l where l.langCd<>?1 and l.engLanguageNm=?2")
	Optional<RefLanguage> findByLangNmAndNotLangCd(String langCd, String engLanguageNm);

}
