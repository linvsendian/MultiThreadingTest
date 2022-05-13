package com.example.multithreadingtest.model;

/**
 * IProductIncentiveMapper.
 *
 * @Description Object Interface for mapping usage( db <-> object).
 * @Date 16/11/2021 11:17
 * @Created by Qinxiu Wang
 */
public interface IProductIncentiveMapper {

  /**
   * Concession code with 6 digits.
   *
   * @return {@code String}
   */
  String getConcessionCode();

  /**
   * Business name of concession (razón social).
   *
   * @return {@code String}
   */
  String getBusinessName();

  /**
   * Commerical name of concession.
   *
   * @return {@code String}
   */
  String getCommercialName();

  /**
   * Product code.
   *
   * @return {@code String}
   */
  String getProductCode();

  /**
   * Product description in Spanish.
   *
   * @return {@code String}
   */
  String getDescriptionEs();

  /**
   * Product description in Portuguese.
   *
   * @return {@code String}
   */
  String getDescriptionPt();

  /**
   * Country {ESPAÑA,PORTUGAL}.
   *
   * @return {@code String]}
   */
  String getCountry();

  /**
   * Regional.
   *
   * @return {@code String}
   */
  String getRegional();

  /**
   * Aspm with format "XX - XXXX".
   *
   * @return {@code String}
   */
  String getAspm();

  /**
   * Incentive.
   *
   * @return {@code String}
   */
  @Deprecated
  Double getIncentive();


  /**
   * To String.
   *
   * @return {@code String}
   */
  default String toStringObj() {
    return "{"
        + "\t concessionCode: " + this.getConcessionCode()
        + "\t businessName: " + this.getBusinessName()
        + "\t commercialName: " + this.getCommercialName()
        + "\t productCode: " + this.getProductCode()
        + "\t descriptionEs: " + this.getDescriptionEs()
        + "\t descriptionPt: " + this.getDescriptionPt()
        + "\t country: " + this.getCountry()
        + "\t regional: " + this.getRegional()
        + "\t aspm: " + this.getAspm()
        + "}";
  }
}
