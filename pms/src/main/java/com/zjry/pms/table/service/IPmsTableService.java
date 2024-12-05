package com.zjry.pms.table.service;

import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.table.domain.*;

import java.util.List;

/**
 * 分摊计算Service接口
 * 
 * @author yzh
 * @date 2022-05-17
 */
public interface IPmsTableService {


    List<PmsTableOpcRes> selectListOpcList(PmsTableOpcQuery pmsTableOpcQuery);

    List<PmsTableBihRes> selectListBihList(PmsTableBihQuery pmsTableBihQuery);

    List<PmsTableDeptbeRes> listDeptbeList(PmsTableDeptbeQuery pmsTableDeptbeQuery);

    AjaxResult exportDetailDeptbe(PmsTableDeptbeQuery pmsTableDeptbeQuery, String name);

    List<PmsTableHsdeptRes> listHsdeptList(PmsTableHsdeptQuery pmsTableHsdeptQuery);

    AjaxResult exportDetailHsdept(PmsTableHsdeptQuery pmsTableHsdeptQuery, String name);

    List<PmsTableDeptarRes> listDeptarList(PmsTableDeptarQuery pmsTableDeptarQuery);

    AjaxResult exportDetailDeptar(PmsTableDeptarQuery tableDeptarQuery, String name);

    List<PmsTableIncsumRes> listIncsumList(PmsTableIncsumQuery pmsTableIncsumQuery);

    List<PmsTableDocRes> listDocList(PmsTableDocQuery pmsTableDocQuery);

    List<PmsTableCostRes> listCostList(PmsTableCostQuery pmsTableCostQuery);
}
