package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.StProvStdDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.dtoresponse.StProvStdResponseDTO;
import com.fedex.geopolitical.model.StProvStd;
import com.fedex.geopolitical.repository.StProvStdRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class StProvStdServiceImpl implements SearchService<StProvStdDTO, StProvStdResponseDTO, StProvStd> {

	@Autowired
	StProvStdRepository stProvStdRepository;

	@Override
	public QueryServiceResponseDTO search(StProvStdDTO stProvStdDTO) throws ParseException {
		List<StProvStd> stProvStd = new ArrayList<>();
		if (null != stProvStdDTO) {
			if (stProvStdDTO.getGeopoliticalId() != null) {
				stProvStd = stProvStdRepository.findByGeopoliticalId(stProvStdDTO.getGeopoliticalId(),
						stProvStdDTO.getEffectiveDate(), stProvStdDTO.getExpirationDate());
			} else if (stProvStdDTO.getStateProvinceCode() == null && stProvStdDTO.getOrganizationStandardCode() == null
					&& stProvStdDTO.getCountryCode() == null) {
				stProvStd = stProvStdRepository.findAllByEffAndExpDate(CommonUtility.getCurrenctDate(),
						CommonUtility.getDefaultExpirationDate());
			} else {
				stProvStd = getData(stProvStdDTO);
			}

		} else {
			stProvStd = stProvStdRepository.findAllByEffAndExpDate(CommonUtility.getCurrenctDate(),
					CommonUtility.getDefaultExpirationDate());
		}
		return prepareReponse(map(stProvStd));
	}

	public QueryServiceResponseDTO search(String geopoliticalId, Date target, Date end) throws ParseException {
		List<StProvStd> stProvStd = new ArrayList<>();
		stProvStd = stProvStdRepository.findByGeopoliticalId(geopoliticalId, target, end);
		return prepareReponse(map(stProvStd));
	}

	private List<StProvStd> getData(StProvStdDTO stProvStdDTO) {
		List<StProvStd> provStd = null;
		if (stProvStdDTO.getStateProvinceCode() != null && stProvStdDTO.getOrganizationStandardCode() != null
				&& stProvStdDTO.getEffectiveDate() != null && stProvStdDTO.getExpirationDate() != null) {
			provStd = fetchByStprovCdAndorgStdCdAndEffectAndTargetDate(stProvStdDTO.getStateProvinceCode(),
					stProvStdDTO.getOrganizationStandardCode(), stProvStdDTO.getEffectiveDate(),
					stProvStdDTO.getExpirationDate());
		}
		if (stProvStdDTO.getStateProvinceCode() != null && stProvStdDTO.getCountryCode() != null) {
			provStd = fetchBystProvCdAndCountryCd(stProvStdDTO.getStateProvinceCode(), stProvStdDTO.getCountryCode(),
					stProvStdDTO.getEffectiveDate(), stProvStdDTO.getExpirationDate());
		}
		if (stProvStdDTO.getCountryCode() != null && stProvStdDTO.getEffectiveDate() != null
				&& stProvStdDTO.getExpirationDate() != null) {
			provStd = fetchByCountryCdAndTrgtAndEndDate(stProvStdDTO.getCountryCode(), stProvStdDTO.getEffectiveDate(),
					stProvStdDTO.getExpirationDate());
		}
		return provStd;
	}

	private List<StProvStd> fetchByStprovCdAndorgStdCdAndEffectAndTargetDate(String provCd, String orgCd, Date effect,
			Date target) {
		List<StProvStd> stProvStd = stProvStdRepository.findByStprovCdAndorgStdCdAndEffectAndTargetDate(provCd, orgCd,
				effect, target);
		return stProvStd;
	}

	private List<StProvStd> fetchBystProvCdAndCountryCd(String stProvCd, String cntryCd, Date target, Date end) {
		List<StProvStd> stProvStd = stProvStdRepository.findBystProvCdAndCountryCd(stProvCd, cntryCd, target, end);
		return stProvStd;
	}

	private List<StProvStd> fetchByCountryCdAndTrgtAndEndDate(String cntryCd, Date effect, Date target) {
		List<StProvStd> stProvStd = stProvStdRepository.findByCountryCdAndTrgtAndEndDate(cntryCd, effect, target);
		return stProvStd;
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<StProvStdResponseDTO> stProvStdResponse) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(stProvStdResponse);
		if (CollectionUtils.isEmpty(stProvStdResponse)) {
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

	@Override
	public List<StProvStdResponseDTO> map(List<StProvStd> stProvStd) throws ParseException {
		List<StProvStdResponseDTO> geoplRltspResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(stProvStd)) {
			for (StProvStd stprov : stProvStd) {
				StProvStdResponseDTO stprovStdResponseDTO = new StProvStdResponseDTO();
				stprovStdResponseDTO.setGeopoliticalId(String.valueOf(stprov.getId().getGeopoliticalId()));
				stprovStdResponseDTO.setOrganizationStandardName(stprov.getGeoplOrgStd().getOrgStdNm());
				stprovStdResponseDTO.setStateProvinceCode(stprov.getStProvCd());
				stprovStdResponseDTO.setStateProvinceName(stprov.getStProvNm());
				stprovStdResponseDTO.setEffectiveDate(stprov.getId().getEffectiveDate());
				stprovStdResponseDTO.setExpirationDate(stprov.getExpirationDate());
				geoplRltspResponses.add(stprovStdResponseDTO);
			}
		}
		return geoplRltspResponses;
	}

}
