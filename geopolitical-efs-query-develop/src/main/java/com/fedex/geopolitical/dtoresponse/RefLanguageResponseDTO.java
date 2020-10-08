package com.fedex.geopolitical.dtoresponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RefLanguageResponseDTO {
	private String languageCode;
	private String languageName;
	private String nativeScriptLanguageName;
	private String nativeScriptCode;
	private List<LocaleResponseDTO> locales;


}
