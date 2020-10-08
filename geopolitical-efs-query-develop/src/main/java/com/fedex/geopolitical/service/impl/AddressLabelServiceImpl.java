package com.fedex.geopolitical.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fedex.geopolitical.constants.GenericConstants;
import com.fedex.geopolitical.dto.AddressLabelDTO;
import com.fedex.geopolitical.dto.AddressServiceMapperDTO;
import com.fedex.geopolitical.dtometa.MessageDTO;
import com.fedex.geopolitical.dtometa.MetaDTO;
import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;
import com.fedex.geopolitical.model.AddressLabel;
import com.fedex.geopolitical.repository.AddressLabelRepository;
import com.fedex.geopolitical.service.SearchService;
import com.fedex.geopolitical.utility.CommonUtility;
import com.fedex.geopolitical.utility.ResponseStatus;

import lombok.Setter;

@Service
@Setter
public class AddressLabelServiceImpl implements SearchService<AddressServiceMapperDTO, Object, AddressLabel> {

	@Autowired
	AddressLabelRepository addressRepository;

	@Override
	public QueryServiceResponseDTO search(AddressServiceMapperDTO addressServiceMapperDTO) throws ParseException {
		List<AddressLabel> addressList = new ArrayList<>();
		putDefaultEffectiveAndExpirationDate(addressServiceMapperDTO);

		if (addressServiceMapperDTO.getGeopoliticalId() != null && addressServiceMapperDTO.getLocaleCode() != null
				&& (addressServiceMapperDTO.getAddressLineNumber() != 0)
				&& StringUtils.isNotEmpty(String.valueOf(addressServiceMapperDTO.getAddressLineNumber()))) {
			addressList = getAddressByGeopoliticalIdLocaleCodeAndLineNumber(addressServiceMapperDTO);
		} else if (addressServiceMapperDTO.getGeopoliticalId() != null
				&& addressServiceMapperDTO.getLocaleCode() != null) {
			addressList = getAddressByGeopoliticalIdLocaleCode(addressServiceMapperDTO);

		} else if (addressServiceMapperDTO.getGeopoliticalId() != null) {
			addressList = getAddressByGeopoliticalId(addressServiceMapperDTO);
		} else {
			addressList = getAllAddressLabel(addressList, addressServiceMapperDTO);
		}

		return prepareReponse(map(addressList));
	}

	private void putDefaultEffectiveAndExpirationDate(AddressServiceMapperDTO addressServiceMapperDTO) {
		if (addressServiceMapperDTO.getEffectiveDate() == null) {
			addressServiceMapperDTO.setEffectiveDate(CommonUtility.getCurrenctDate());
		}
		if (addressServiceMapperDTO.getExpirationDate() == null) {
			addressServiceMapperDTO.setExpirationDate(CommonUtility.getDefaultExpirationDate());
		}
	}

	private List<AddressLabel> getAllAddressLabel(List<AddressLabel> addressList,
			AddressServiceMapperDTO addressServiceMapperDTO) {

		Optional<List<AddressLabel>> addressOpt = addressRepository
				.findAllByDate(addressServiceMapperDTO.getEffectiveDate(), addressServiceMapperDTO.getExpirationDate());

		if (addressOpt.isPresent()) {
			addressList = addressOpt.get();
		}
		return addressList;
	}

	private List<AddressLabel> getAddressByGeopoliticalId(AddressServiceMapperDTO addressServiceMapperDTO) {
		List<AddressLabel> addressLabel = new ArrayList<>();
		Optional<List<AddressLabel>> addressOpt = addressRepository.findByGeopoliticalId(
				addressServiceMapperDTO.getGeopoliticalId(), addressServiceMapperDTO.getEffectiveDate(),
				addressServiceMapperDTO.getExpirationDate());

		if (addressOpt.isPresent()) {
			addressLabel = addressOpt.get();
		}
		return addressLabel;
	}

	private List<AddressLabel> getAddressByGeopoliticalIdLocaleCode(AddressServiceMapperDTO addressServiceMapperDTO) {
		List<AddressLabel> address = new ArrayList<>();
		Optional<List<AddressLabel>> addressOpt = addressRepository.findByGeopoliticalIdAndLocale(
				addressServiceMapperDTO.getGeopoliticalId(), addressServiceMapperDTO.getLocaleCode(),
				addressServiceMapperDTO.getEffectiveDate(), addressServiceMapperDTO.getExpirationDate());

		if (addressOpt.isPresent()) {
			address = addressOpt.get();
		}
		return address;
	}

	@SuppressWarnings("unchecked") // passing results as list to make it
									// compatible with the BaseService
	@Override
	public QueryServiceResponseDTO prepareReponse(List<Object> u) {
		MetaDTO metaResponse = new MetaDTO();
		QueryServiceResponseDTO queryServiceResponse = new QueryServiceResponseDTO();
		MessageDTO message = new MessageDTO();
		queryServiceResponse.setData(u.get(0));
		if (u.get(0) instanceof HashMap)
			if (CollectionUtils.isEmpty((HashMap<String, List<AddressLabelDTO>>) u.get(0))) {
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
	public List<Object> map(List<AddressLabel> addressList) throws ParseException {

		HashMap<String, List<AddressLabelDTO>> addressMap = new HashMap<>();
		for (AddressLabel addressLabel : addressList) {
			AddressLabelDTO addressLabelDTO = new AddressLabelDTO();
			addressLabelDTO.setGeopoliticalId(addressLabel.getId().getGeopoliticalId());
			addressLabelDTO.setAddressLineNumber(addressLabel.getId().getAddressLineNumber());
			addressLabelDTO.setBrandAddressLineDescription(addressLabel.getBrandAddressLineLabelDescription());

			addressLabelDTO.setApplicable(
					addressLabel.getApplicableFlag() != null && addressLabel.getApplicableFlag().equalsIgnoreCase("1"));
			if (addressMap.get(addressLabel.getId().getLoclcode()) == null
					|| addressMap.get(addressLabel.getId().getLoclcode()).isEmpty()) {
				List<AddressLabelDTO> newList = new ArrayList<AddressLabelDTO>();
				newList.add(addressLabelDTO);
				addressMap.put(addressLabel.getId().getLoclcode(), newList);
			} else {
				List<AddressLabelDTO> reuseList = addressMap.get(addressLabel.getId().getLoclcode());
				reuseList.add(addressLabelDTO);
				addressMap.put(addressLabel.getId().getLoclcode(), reuseList);
			}
		}

		// created to make the design align to the SearchService
		List<Object> resultObj = new ArrayList<>();
		resultObj.add(addressMap);
		return resultObj;
	}

	private List<AddressLabel> getAddressByGeopoliticalIdLocaleCodeAndLineNumber(
			AddressServiceMapperDTO addressServiceMapperDTO) {
		List<AddressLabel> address = new ArrayList<>();
		Optional<List<AddressLabel>> addressOpt = addressRepository.findByGeopoliticalIdAndLocaleAndAddressLineNumber(
				addressServiceMapperDTO.getGeopoliticalId(), addressServiceMapperDTO.getLocaleCode(),
				addressServiceMapperDTO.getEffectiveDate(), addressServiceMapperDTO.getExpirationDate(),
				addressServiceMapperDTO.getAddressLineNumber());

		if (addressOpt.isPresent()) {
			address = addressOpt.get();
		}
		return address;
	}

}
