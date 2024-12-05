package com.zjry.pms.apportionment.service;

import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.project.domain.CostProjectNum;
import com.zjry.pms.revenue.domain.PmsRevenueNum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author : syw
 * @CreateTime : 2022/05/11
 * @Description :
 **/

public interface DBChangeService {

	/**
	 * 
	 * @param dataSource
	 * @param sql
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public List<CostApportionmentDictionaryDetail> changeDb(CostDatabase dataSource,String sql,String level,String code) throws Exception;

	List<CostProjectNum> cPNDb(CostDatabase dataSource, String sql, Date nowDate, Long compCode)throws Exception;

    List<PmsRevenueNum> cPRNdb(CostDatabase costDatabase, String numSql, Date nowDate, Long compCode)throws Exception;

	List<PmsEvaluateIndex> cPmsEvaIndexdb(CostDatabase costDatabase, PmsEvaluateIndex pmsEvaluateIndex)throws Exception;

	List<Map<String,Object>> getDataFromDb(CostDatabase costDatabase, String sql, List<String> returnParam)throws Exception;
}
