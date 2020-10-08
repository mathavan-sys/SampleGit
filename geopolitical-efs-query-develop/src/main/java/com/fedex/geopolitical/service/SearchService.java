package com.fedex.geopolitical.service;

import java.text.ParseException;
import java.util.List;

import com.fedex.geopolitical.dtoresponse.QueryServiceResponseDTO;

public interface SearchService<T, U, V> {
	
	public QueryServiceResponseDTO search(T t) throws ParseException;
	public QueryServiceResponseDTO prepareReponse(List<U> u);
	public List<U> map(List<V> v) throws ParseException;
	
}
