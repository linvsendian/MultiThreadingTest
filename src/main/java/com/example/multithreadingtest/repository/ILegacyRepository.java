package com.example.multithreadingtest.repository;

import com.example.multithreadingtest.model.IProductIncentiveMapper;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ILegacyRepository.
 *
 * @Description ILegacyRepository
 * @Date 14/02/2022 21:08
 * @Created by Qinxiu Wang
 */
public interface ILegacyRepository extends JpaRepository<EmptyEntity, String> {

  //region query
  String PRODUCT_INCENTIVE_QUERY_HEADER = ""
      + "SELECT\n"
      + "\tVCODHOST AS concessionCode,\n"
      + "    VRAZONSOCIAL as businessName,\n"
      + "    VNOMBRECOMERCIAL as commercialName,\n"
      + "    vcode_producto AS productCode,\n"
      + "    vdesc as DescriptionEs,\n"
      + "    vdesc_pt DescriptionPt, \n"
      + "    vpais AS country,\n"
      + "    vregional AS regional,\n"
      + "    vaspm AS aspm,\n"
      + "    SUM(incentivo) AS incentive\n"
      + "FROM (\n"
      + "    \tSELECT DISTINCT \n"
      + "            c.vcodhost,\n"
      + "            c.VRAZONSOCIAL,\n"
      + "            c.VNOMBRECOMERCIAL,\n"
      + "            c.vpais,\n"
      + "            c.vregional,\n"
      + "            c.VASPM,\n"
      + "            p.vcode_producto,\n"
      + "            p.vdesc,\n"
      + "            p.vdesc_pt,\n"
      + "            f.vorden,\n"
      + "            (CASE WHEN f.dinit < TO_DATE(:sinceDate, 'DD/MM/YYYY')\n"
      + "            THEN TO_DATE(:sinceDate, 'DD/MM/YYYY')\n"
      + "            ELSE f.dinit END) AS desde,\n"
      + "      (CASE WHEN vcode_producto like 'BV' THEN\n"
      + "          smpv_getformula(p.vcode_producto, SUBSTR(c.vcodhost, 3, 4),\n"
      + "            (CASE WHEN f.dinit < TO_DATE(:sinceDate,'DD/MM/YYYY')\n"
      + "              THEN TO_DATE(:sinceDate, 'DD/MM/YYYY')\n"
      + "              ELSE f.dinit END),\n"
      + "            (CASE WHEN f.dfin > TO_DATE(:upToDate, 'DD/MM/YYYY')\n"
      + "              THEN TO_DATE(:upToDate, 'DD/MM/YYYY')\n"
      + "              ELSE NVL(f.dfin, to_date(:upToDate, 'DD/MM/YYYY')) END),\n"
      + "              0,\n"
      + "              ADD_MONTHS(TO_DATE(:sinceDate, 'DD/MM/YYYY'), :discountMonth),\n"
      + "              :idUser)\n"
      + "        ELSE\n"
      + "          smpv_getformula(p.vcode_producto, SUBSTR(c.vcodhost, 3, 4),\n"
      + "            (CASE WHEN f.dinit < TO_DATE(:sinceDate,'DD/MM/YYYY')\n"
      + "              THEN TO_DATE(:sinceDate, 'DD/MM/YYYY')\n"
      + "              ELSE f.dinit END),\n"
      + "            (CASE WHEN f.dfin > TO_DATE(:upToDate, 'DD/MM/YYYY')\n"
      + "              THEN TO_DATE(:upToDate, 'DD/MM/YYYY')\n"
      + "              ELSE NVL(f.dfin, to_date(:upToDate, 'DD/MM/YYYY')) END),\n"
      + "              0, TO_DATE(:estimatedDate, 'DD/MM/YYYY'), :idUser)\n"
      + "      END) AS incentivo\n"
      + "    FROM smpv_productos p, rn_entidad c, smpv_formulas f\n"
      + "    WHERE p.bvisible = 1\n"
      + "    AND besbono = 1\n"
      + "    AND f.vcodeprod = p.vcode_producto\n"
      + "    AND f.dinit < TO_DATE(:upToDate, 'DD/MM/YYYY')\n"
      + "    AND NVL(f.dfin, TO_DATE(:upToDate, 'DD/MM/YYYY'))\n"
      + "        > TO_DATE(:sinceDate, 'DD/MM/YYYY')\n"
      + "    AND c.vcode in (\n"
      + "        SELECT DISTINCT re.VCODHOST\n"
      + "        FROM RN_ENTIDAD re \n"
      + "        WHERE  re.nidentidad = re.nprincipal\n"
      + "        AND (re.dfechabaja IS NULL\n"
      + "                OR re.DFECHABAJA >= TO_DATE('01/04/' || "
      + "to_char(sysdate, 'YYYY'), 'DD/MM/YYYY'))\n"
      + "        AND (re.dfechabaja IS NULL\n"
      + "                OR re.DFECHABAJA >= TO_DATE('01/04/' || "
      + "to_char(sysdate, 'YYYY'), 'DD/MM/YYYY'))";

  String PRODUCT_INCENTIVE_QUERY_FOOTER = ""
      + "    )\n"
      + "    AND (c.dfechabaja IS NULL OR c.dfechabaja >= TO_DATE('01/04/'\n"
      + "        || TO_CHAR(ADD_MONTHS(TO_DATE(:sinceDate, 'DD/MM/YYYY'),\n"
      + "                    -3), 'YYYY'), 'DD/MM/YYYY'))\n"
      + "    AND c.nidentidad = c.nprincipal\n"
      + "    AND p.vcode_producto IN (:productCodes)\n"
      + "    ORDER BY f.vorden\n"
      + ")\n"
      + "GROUP BY vcode_producto, vorden, vdesc, vdesc_pt,vpais,\n"
      + "        vregional,vaspm,VCODHOST,VRAZONSOCIAL,VNOMBRECOMERCIAL \n"
      + "ORDER BY vorden";
//endregion

  @Query(value = PRODUCT_INCENTIVE_QUERY_HEADER
      + "AND UPPER(re.vaspm) = UPPER(:aspm)"
      + PRODUCT_INCENTIVE_QUERY_FOOTER, nativeQuery = true)
  List<IProductIncentiveMapper> findProductIncentiveMapperByAspm(String sinceDate,
      String upToDate, String estimatedDate, String aspm, int idUser, int discountMonth,
      List<String> productCodes);
}
