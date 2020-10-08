package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.LanguageDTO;
import com.fedex.geopolitical.dto.LocaleDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.CountryResponseDTO;
import com.fedex.geopolitical.dtoresponse.LocaleResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.dtoresponse.RefLanguageResponseDTO;
import com.fedex.geopolitical.dtoresponse.TrnslDowResponseDTO;
import com.fedex.geopolitical.dtoresponse.TrnslMthOfYrResoponseDTO;
import com.fedex.geopolitical.model.Country;
import com.fedex.geopolitical.model.Locale;
import com.fedex.geopolitical.model.RefLanguage;
import com.fedex.geopolitical.model.TrnslDow;
import com.fedex.geopolitical.model.TrnslMthOfYr;
import com.fedex.geopolitical.repository.CountryRepository;
import com.fedex.geopolitical.repository.LocaleRepository;
import com.fedex.geopolitical.repository.RefLanguageRepository;
import com.fedex.geopolitical.repository.TrnslDowRepository;
import com.fedex.geopolitical.repository.TrnslMthOfYrRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

import lombok.Setter;

@Service
@Setter
public class RefLanguageServiceImpl implements SearchService<LanguageDTO, RefLanguageResponseDTO, RefLanguage> {

	@Autowired
	RefLanguageRepository refLanguageRepository;

	@Autowired
	TrnslDowRepository trnslDowRepository;

	@Autowired
	TrnslMthOfYrRepository trnslMthOfYrRepository;

	@Autowired
	LocaleRepository localeRepository;

	@Autowired
	CountryRepository countryRepository;
	LocaleDTO localedto = new LocaleDTO();

	@Override
	public QueryServiceResponseDTO search(LanguageDTO languageDTO) throws ParseException {
		List<RefLanguage> refLanguages = new ArrayList<>();
		List<String> filterBy = new ArrayList<>();
		if (languageDTO.getLocaleCd() != null) {
			Optional<List<Locale>> locales = localeRepository.findByLoclcode(languageDTO.getLocaleCd());
			if (locales.isPresent()) {
				for (Locale locale : locales.get()) {
					Optional<RefLanguage> refLanguage = refLanguageRepository.findByLangCd(locale.getLangCd());
					if (refLanguage.isPresent()) {
						filterBy.add(languageDTO.getLocaleCd());
						refLanguages.add(refLanguage.get());
					}
				}
			}
		} else if (languageDTO.getCountryCd() != null) {
			Optional<List<Country>> countries = countryRepository.findByCountryCd(languageDTO.getCountryCd());
			if (countries.isPresent()) {
				for (Country country : countries.get()) {
					Optional<List<Locale>> locales = localeRepository
							.findByGeopoliticalId(country.getId().getGeopoliticalId());
					if (locales.isPresent()) {
						for (Locale locale : locales.get()) {
							Optional<RefLanguage> refLanguage = refLanguageRepository.findByLangCd(locale.getLangCd());
							if (refLanguage.isPresent()) {
								filterBy.add(locale.getId().getLoclcode());
								refLanguages.add(refLanguage.get());
							}
						}
					}
				}
			}
		} else if (languageDTO.getLangCd() != null) {

			Optional<RefLanguage> refLanguage = refLanguageRepository.findByLangCd(languageDTO.getLangCd());
			if (refLanguage.isPresent()) {
				refLanguages.add(refLanguage.get());
			}
		} else {
			refLanguages = refLanguageRepository.findAll();
		}

		return prepareReponse(map(refLanguages, filterBy));
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<RefLanguageResponseDTO> refLanguageResponseDTO) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(refLanguageResponseDTO);
		if (CollectionUtils.isEmpty(refLanguageResponseDTO)) {
			message.setInternalMessage(GenericConstants.NO_RECORDS);
		} else {
			message.setInternalMessage(GenericConstants.CONSTANT_INTERNAL_MSG);
		}
		CommonUtility.populateSuccess_Status_StatusCd_TS(message,metaResponse);
		
		metaResponse.setVersion(GenericConstants.META_VERSION);
		metaResponse.setMessage(message);
		queryServiceResponse.setMeta(metaResponse);
		return queryServiceResponse;
	}


	public List<RefLanguageResponseDTO> map(List<RefLanguage> refLanguages, List<String> filterBy)
			throws ParseException {
		List<RefLanguageResponseDTO> refLanguageResponses = new ArrayList<>();

		if (!CollectionUtils.isEmpty(refLanguages)) {

			for (RefLanguage refLanguage : refLanguages) {
				RefLanguageResponseDTO refLanguageResponseDTO = new RefLanguageResponseDTO();

				List<LocaleResponseDTO> localeSet = new ArrayList<>();
				List<Locale> locales = new ArrayList<>();
				if (!filterBy.isEmpty()) {
					List<Locale> localesTemp = refLanguage.getLocales();
					for (Locale loc : localesTemp) {
						if (filterBy.contains(loc.getId().getLoclcode())
								&& loc.getLangCd().equals(refLanguage.getLangCd())) {
							locales.add(loc);
						}
					}
				} else {
					locales = refLanguage.getLocales();
				}
				for (Locale loc : locales) {					
					LocaleResponseDTO locs = new LocaleResponseDTO();
					locs.setLocaleCode(loc.getId().getLoclcode());

					List<Country> count = new ArrayList<>();
					count = countryRepository.findByGeoplId(loc.getGeopoliticalId());
					locs.setCountryCode(count.get(0).getCountryCd());

					List<TrnslDowResponseDTO> trnslDowSet = new ArrayList<>();
					List<TrnslMthOfYrResoponseDTO> trnslMoySet = new ArrayList<>();
					List<TrnslDow> dows = new ArrayList<>();
					List<TrnslMthOfYr> moys = new ArrayList<>();

					dows = trnslDowRepository.findByLoclcode(locs.getLocaleCode());
					for (TrnslDow dow : dows) {
						TrnslDowResponseDTO trnslDow = new TrnslDowResponseDTO();
						trnslDow.setTranslatedDayOfWeekName(dow.getTrnslDowNm());
						trnslDow.setDayOfWeekNumber(String.valueOf(dow.getId().getDowNbr()));
						trnslDowSet.add(trnslDow);
					}
					Collections.sort(trnslDowSet);

					moys = trnslMthOfYrRepository.findByLoclcode(locs.getLocaleCode());
					for (TrnslMthOfYr moy : moys) {
						TrnslMthOfYrResoponseDTO tnslMoy = new TrnslMthOfYrResoponseDTO();
						tnslMoy.setMonthOfYearNumber(String.valueOf(moy.getId().getMthOfYrNbr()));
						tnslMoy.setTranslatedMonthOfYearName(moy.getTrnslMthOfYrNm());
						trnslMoySet.add(tnslMoy);
					}
					Collections.sort(trnslMoySet);

					if (loc.getRefScript() != null) {
						locs.setScriptCode(loc.getRefScript().getScrptCd());
					}
					locs.setCldrVersionDate(loc.getCldrVersionDate());
					locs.setCldrVersionNumber(loc.getCldrVersionNumber());
					locs.setDateFullFormatDescription(loc.getDateFullFormatDescription());
					locs.setDateLongFormatDescription(loc.getDateLongFormatDescription());
					locs.setDateShortFormatDescription(loc.getDateShortFormatDescription());
					locs.setDateMediumFormatDescription(loc.getDateMediumFormatDescription());
					locs.setEffectiveDate(loc.getId().getEffectiveDate());
					locs.setExpirationDate(loc.getExpirationDate());

					locs.setTranslatedDOWs(trnslDowSet);
					locs.setTranslatedMOYs(trnslMoySet);
					localeSet.add(locs);
					
				}
				refLanguageResponseDTO.setLanguageCode(refLanguage.getLangCd());
				refLanguageResponseDTO.setLanguageName(refLanguage.getEngLanguageNm());
				refLanguageResponseDTO.setNativeScriptLanguageName(refLanguage.getNativeScriptLanguageNm());
				refLanguageResponseDTO.setNativeScriptCode(refLanguage.getNativeScrptCd());

				refLanguageResponseDTO.setLocales(localeSet);
				refLanguageResponses.add(refLanguageResponseDTO);
			}
		}
		return refLanguageResponses;
	}

	@Override
	public List<RefLanguageResponseDTO> map(List<RefLanguage> v) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
