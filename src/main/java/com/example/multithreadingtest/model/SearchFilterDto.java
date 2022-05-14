package com.example.multithreadingtest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * SearchFilterDto.
 *
 * @Description Reporter filter dto class.
 * @Date 02/08/2021 11:14
 * @Created by Qinxiu Wang
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_DEFAULT)

public class SearchFilterDto {

  private String concessionCode;

  private String countryCode;

  private String regionalCode;

  private String aspm;

  private int fiscalYear;

  private int month;

  private List<String> productCodes;

  private List<String> productGroups;

  private int isCumulative;

  private int sinceMonth;

  private int upToMonth;

  private int languageId;

  private double oilValue;

  private double tireValue;

  private double paintingValue;

  private double remanValue;
}
