/*package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.GeopoliticalRelationshipDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.GeopoliticalRelationshipResponseDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.GeoplRltsp;
import com.fedex.geopolitical.repository.GeoplRltspRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

@Service
public class GeopoliticalRelationshipResponseSeriveImpl implements SearchService<GeopoliticalRelationshipDTO, GeopoliticalRelationshipResponseDTO, GeoplRltsp> {

	@Autowired
	GeoplRltspRepository geopoliticalRelationshipRepository;

	@Override
	public QueryServiceResponseDTO search(GeopoliticalRelationshipDTO geopoliticalRelationshipDTO) throws ParseException {
		List<GeoplRltsp> geoplRltsps = new ArrayList<>();
		
		if (geopoliticalRelationshipDTO.getRelationshipTypeCode() != null) {
			
			geoplRltsps=getData(geopoliticalRelationshipDTO);
			
		}
		else {
			geoplRltsps = geopoliticalRelationshipRepository.findAllByEffAndExpDate(CommonUtility.getCurrenctDate(), CommonUtility.getDefaultExpirationDate());
		}
		return prepareReponse(map(geoplRltsps));
		
	}
	
	private List<GeoplRltsp> getData(GeopoliticalRelationshipDTO geopoliticalRelationshipDTO){
		List<GeoplRltsp> geoplRltsps= null;
		if(geopoliticalRelationshipDTO.getRelationshipTypeCode()!=null && geopoliticalRelationshipDTO.getFromGeopoliticalId()==null 
				&& geopoliticalRelationshipDTO.getToGeopoliticalId()==null){
			geoplRltsps=fetchOnRltspTypeCodeAndTargetDateAndEndDate(geopoliticalRelationshipDTO.getRelationshipTypeCode()
					,geopoliticalRelationshipDTO.getEffectiveDate(),geopoliticalRelationshipDTO.getExpirationDate());
		}
		else if(geopoliticalRelationshipDTO.getFromGeopoliticalId()!=null){
			if(geopoliticalRelationshipDTO.getToGeopoliticalId()!=null){
				geoplRltsps=fetchOnIdAndEndDate(geopoliticalRelationshipDTO.getFromGeopoliticalId(),
						geopoliticalRelationshipDTO.getToGeopoliticalId(),geopoliticalRelationshipDTO.getRelationshipTypeCode()
						,geopoliticalRelationshipDTO.getEffectiveDate(),geopoliticalRelationshipDTO.getExpirationDate());
			}
			else{
				geoplRltsps=fetchOnFromIdAndCode(geopoliticalRelationshipDTO.getFromGeopoliticalId(),geopoliticalRelationshipDTO.getRelationshipTypeCode()
						,geopoliticalRelationshipDTO.getEffectiveDate(),geopoliticalRelationshipDTO.getExpirationDate());
			}
		}
		else if(geopoliticalRelationshipDTO.getToGeopoliticalId()!=null && geopoliticalRelationshipDTO.getFromGeopoliticalId()==null){
			geoplRltsps=fetchOnToIdAndCode(geopoliticalRelationshipDTO.getToGeopoliticalId(),geopoliticalRelationshipDTO.getRelationshipTypeCode()
					,geopoliticalRelationshipDTO.getEffectiveDate(),geopoliticalRelationshipDTO.getExpirationDate());
		}
		return geoplRltsps;
	}
	
	private List<GeoplRltsp> fetchOnFromIdAndCode(String fromId, String code, Date target, Date end){
		List<GeoplRltsp> geoplRltsps=geopoliticalRelationshipRepository.findByGeoplComptIdAndCode(fromId,code,target,end);
		return geoplRltsps;
	}

	private List<GeoplRltsp> fetchOnToIdAndCode(String toId,String code, Date target, Date end){
		List<GeoplRltsp> geoplRltsps=geopoliticalRelationshipRepository.findByGeoplRelatedComptIdAndCode(toId,code,target,end);
		return geoplRltsps;
	}
	
	private List<GeoplRltsp> fetchOnIdAndEndDate(String fromId,String toId,String code, Date target, Date end){
		List<GeoplRltsp> geoplRltsps=geopoliticalRelationshipRepository.findByIDAndEndDate(fromId,toId,code,target,end);
		return geoplRltsps;
	}
	
	private List<GeoplRltsp> fetchOnRltspTypeCodeAndTargetDateAndEndDate(String code, Date target, Date end){
		List<GeoplRltsp> geoplRltsps=geopoliticalRelationshipRepository.findByRltspTypeCodeAndTargetDateAndEndDate(code,target,end);
		return geoplRltsps;
	}

	@Override
	public QueryServiceResponseDTO prepareReponse(List<GeopoliticalRelationshipResponseDTO> geoplRltspResponses) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(geoplRltspResponses);
		if (CollectionUtils.isEmpty(geoplRltspResponses)) {
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
	public List<GeopoliticalRelationshipResponseDTO> map(List<GeoplRltsp> geoplRltsps) throws ParseException {
		List<GeopoliticalRelationshipResponseDTO> geoplRltspResponses = new ArrayList<>();
		if (!CollectionUtils.isEmpty(geoplRltsps)) {
			for (GeoplRltsp geoplRltsp : geoplRltsps) {
				GeopoliticalRelationshipResponseDTO geoplRltspResponseDTO = new GeopoliticalRelationshipResponseDTO();
				geoplRltspResponseDTO.setFromGeopoliticalId(geoplRltsp.getId().getGeoplComptId());
				geoplRltspResponseDTO.setToGeopoliticalId(geoplRltsp.getId().getReltdGeoplComptId());
				geoplRltspResponseDTO.setGeopoliticalRelationshipTypeCode(geoplRltsp.getId().getGeoplRltspTypeCd());
				geoplRltspResponseDTO.setEffectiveDate(geoplRltsp.getId().getEffectiveDate());
				geoplRltspResponseDTO.setExpirationDate(geoplRltsp.getExpirationDate());
				
				geoplRltspResponses.add(geoplRltspResponseDTO);
			}
		}
		return geoplRltspResponses;
	}
	
}
*/