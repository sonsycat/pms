package com.zjry.pms.table.mapper;

import com.zjry.pms.table.domain.*;

import java.util.List;

public interface PmsTableMapper {

    List<PmsTableOpcRes> selectListOpcList(PmsTableOpcQuery pmsTableOpcQuery);

    List<PmsTableBihRes> selectListBihList(PmsTableBihQuery pmsTableBihQuery);

    List<PmsTableDeptbeRes> listDeptbeList(PmsTableDeptbeQuery pmsTableDeptbeQuery);

    List<PmsTableHsdeptRes> listHsdeptList(PmsTableHsdeptQuery pmsTableHsdeptQuery);

    List<PmsTableDeptarRes> listDeptarList(PmsTableDeptarQuery pmsTableDeptarQuery);

    List<PmsTableIncsumRes> listIncsumList(PmsTableIncsumQuery pmsTableIncsumQuery);

    List<PmsTableDocRes> listDocList(PmsTableDocQuery pmsTableDocQuery);

    List<PmsTableCostRes> listCostList(PmsTableCostQuery pmsTableCostQuery);

}
