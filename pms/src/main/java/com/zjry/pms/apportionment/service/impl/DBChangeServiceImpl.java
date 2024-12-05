package com.zjry.pms.apportionment.service.impl;



import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.service.DBChangeService;

import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.DateUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.project.domain.CostProjectNum;
import com.zjry.pms.revenue.domain.PmsRevenue;
import com.zjry.pms.revenue.domain.PmsRevenueNum;
import com.zjry.pms.revenue.service.IPmsRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Author : syw
 * @CreateTime : 2022/05/11
 * @Description :
 **/

@Service
public class DBChangeServiceImpl implements DBChangeService {
    @Autowired
    private IPmsRevenueService pmsRevenueService;

    @Override
    public List<CostApportionmentDictionaryDetail> changeDb(CostDatabase dataSource,String sql,String level,String ratioCode) throws Exception {
    	List<CostApportionmentDictionaryDetail>list = new ArrayList<CostApportionmentDictionaryDetail>();
        Connection conn = null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        String driveClass=null;

        String url = dataSource.getUrl();
        String username = dataSource.getUserName();
        String password = dataSource.getPassWord();


        if ("mysql".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.mysql.cj.jdbc.Driver";
        } else if ("gp".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "org.postgresql.Driver";
        } else if ("sqlserver2000".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if ("sqlserver".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if ("hana".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.sap.db.jdbc.Driver";
        } else if ("oracle".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "oracle.jdbc.driver.OracleDriver";
        }

         try{
                Class.forName(driveClass);
                conn = DriverManager.getConnection(url,username,password);
                pst= conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while(rs.next()){
                	CostApportionmentDictionaryDetail costApportionmentDictionaryDetail = new CostApportionmentDictionaryDetail();
                	costApportionmentDictionaryDetail.setRatioCode(ratioCode);
                	costApportionmentDictionaryDetail.setDeptCode(rs.getString("DEPT_CODE"));
                	costApportionmentDictionaryDetail.setDeptName(rs.getString("DEPT_NAME"));
                	costApportionmentDictionaryDetail.setLevelType(level);
                	costApportionmentDictionaryDetail.setUseRatio(rs.getString("USE_RATIO"));
                	costApportionmentDictionaryDetail.setNy(rs.getString("NY"));
                	list.add(costApportionmentDictionaryDetail);
    			}
              //关闭资源
                try{
                    if(rs!=null){
                        rs.close();
                    }
                    if(pst!=null){
                        pst.close();
                    }
                    if(conn!=null){
                        conn.close();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
         }catch (Exception e){
             e.printStackTrace();
         }finally {
             //关闭资源
             try{
                 if(rs!=null){
                     rs.close();
                 }
                 if(pst!=null){
                     pst.close();
                 }
                 if(conn!=null){
                     conn.close();
                 }
             }catch (Exception ex){
                 ex.printStackTrace();
             }

         }
        return list;

     }

    @Override
    public List<CostProjectNum> cPNDb(CostDatabase dataSource, String sql, java.util.Date nowDate, Long compCode) throws Exception {
        List<CostProjectNum>list = new ArrayList<CostProjectNum>();
        Connection conn = null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        String driveClass=null;

        String url = dataSource.getUrl();
        String username = dataSource.getUserName();
        String password = dataSource.getPassWord();


        if ("mysql".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.mysql.cj.jdbc.Driver";
        } else if ("gp".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "org.postgresql.Driver";
        } else if ("sqlserver2000".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if ("sqlserver".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if ("hana".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.sap.db.jdbc.Driver";
        } else if ("oracle".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "oracle.jdbc.driver.OracleDriver";
        }

        try{
            Class.forName(driveClass);
            conn = DriverManager.getConnection(url,username,password);
            sql = sql+"where NOW_DATE ='"+ DateUtils.formatDate(nowDate,null) +"' and COMP_CODE ='"+compCode+"'";
            pst= conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while(rs.next()){
                CostProjectNum cpn = new CostProjectNum();
                cpn.setMoney(Double.parseDouble(rs.getString("Money")));
                cpn.setDeptCode(Long.parseLong(rs.getString("DEPT_CODE")));
                cpn.setProjectCode(rs.getString("PROJECT_CODE"));
                cpn.setNowDate(nowDate);
                cpn.setCompCode(compCode);
                list.add(cpn);
            }
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        return list;
    }

    @Override
    public List<PmsRevenueNum> cPRNdb(CostDatabase dataSource, String sql, Date nowDate, Long compCode) throws Exception {
        List<PmsRevenueNum>list = new ArrayList<PmsRevenueNum>();
        Connection conn = null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        String driveClass=null;

        String url = dataSource.getUrl();
        String username = dataSource.getUserName();
        String password = dataSource.getPassWord();


        if ("mysql".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.mysql.cj.jdbc.Driver";
        } else if ("gp".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "org.postgresql.Driver";
        } else if ("sqlserver2000".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if ("sqlserver".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if ("hana".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.sap.db.jdbc.Driver";
        } else if ("oracle".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "oracle.jdbc.driver.OracleDriver";
        }

        try{
            Class.forName(driveClass);
            conn = DriverManager.getConnection(url,username,password);
            sql = sql+" and to_char ( 处方时间, 'yyyy-MM' )='"+DateUtils.formatDate(nowDate,"yyyy-MM")+"'";
            pst= conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                PmsRevenueNum cpn = new PmsRevenueNum();
                // 根据HIS收入类别关联收入核算方案
                PmsRevenue pmsRevenue = new PmsRevenue();
                pmsRevenue.setCompCode(Long.parseLong(rs.getString("COMP_CODE")));
                pmsRevenue.setHisRevenumTypede(rs.getString("REVENUE_CODE"));
                List<PmsRevenue> listHis = pmsRevenueService.selectPmsRevenueList(pmsRevenue);
                if (listHis.size() > 0){
                    cpn.setRevenueCode(listHis.get(0).getRevenueCode());
                } else{
                    continue;
                }

                cpn.setCompCode(Long.parseLong(rs.getString("COMP_CODE")));
                cpn.setReceiveNum(new BigDecimal(rs.getString("RECEIVE_NUM")));
                cpn.setActualNum(new BigDecimal(rs.getString("ACTUAL_NUM")));
                cpn.setBillDept(Long.parseLong(rs.getString("BILL_DEPT")));
                cpn.setExecDept(Long.parseLong(rs.getString("EXEC_DEPT")));
               // cpn.setCareDept(Long.parseLong(rs.getString("CARE_DEPT")));
                cpn.setBillDoc(rs.getString("BILL_DOC"));
                cpn.setOccureTime(DateUtils.parseDate1(rs.getString("OCCURE_TIME")));
                cpn.setRevenueType(rs.getString("REVENUE_TYPE"));
                cpn.setSettleMark(rs.getString("SETTLE_MARK"));
                list.add(cpn);
            }
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        return list;
    }

    @Override
    public List<PmsEvaluateIndex> cPmsEvaIndexdb(CostDatabase dataSource, PmsEvaluateIndex pmsEvaluateIndex) throws Exception {
        List<PmsEvaluateIndex>list = new ArrayList<PmsEvaluateIndex>();
        Connection conn = null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        String driveClass=null;

        String url = dataSource.getUrl();
        String username = dataSource.getUserName();
        String password = dataSource.getPassWord();


        if ("mysql".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.mysql.cj.jdbc.Driver";
        } else if ("gp".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "org.postgresql.Driver";
        } else if ("sqlserver2000".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if ("sqlserver".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if ("hana".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.sap.db.jdbc.Driver";
        } else if ("oracle".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "oracle.jdbc.driver.OracleDriver";
        }

        try{
            Class.forName(driveClass);
            conn = DriverManager.getConnection(url,username,password);
            String sql = pmsEvaluateIndex.getIndexSql();
            if(Constants.IS_CHANGE_YES.equals(pmsEvaluateIndex.getIsSection())){
                sql = sql+" and to_char ( 处方时间, 'yyyy-MM-dd' ) between'"+DateUtils.formatDate(pmsEvaluateIndex.getSectionStart(),"yyyy-MM-dd")+"'  and  '" +DateUtils.formatDate(pmsEvaluateIndex.getSectionEnd(),"yyyy-MM-dd")+"'";
            }

            pst= conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                PmsEvaluateIndex cpn = new PmsEvaluateIndex();
                cpn.setCompCode(Long.parseLong(rs.getString("COMP_CODE")));
                cpn.setBscOneName(rs.getString("REVENUE_CODE"));
                cpn.setBscSecondName(rs.getString("RECEIVE_NUM"));
                cpn.setDeptType(rs.getString("ACTUAL_NUM"));
                cpn.setIndexName(rs.getString("BILL_DEPT"));
                cpn.setSectionStart(DateUtils.parseDate1(rs.getString("OCCURE_TIME")));
                list.add(cpn);
            }
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        return list;
    }
    @Override
    public List<Map<String,Object>> getDataFromDb(CostDatabase dataSource, String sql, List<String> returnParam)throws Exception{
        List<Map<String,Object>> resultList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        String driveClass=null;

        String url = dataSource.getUrl();
        String username = dataSource.getUserName();
        String password = dataSource.getPassWord();

        if ("mysql".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.mysql.cj.jdbc.Driver";
        } else if ("gp".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "org.postgresql.Driver";
        } else if ("sqlserver2000".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if ("sqlserver".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        } else if ("hana".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "com.sap.db.jdbc.Driver";
        } else if ("oracle".equalsIgnoreCase(dataSource.getType())) {
            driveClass = "oracle.jdbc.driver.OracleDriver";
        }

        try{
            Class.forName(driveClass);
            conn = DriverManager.getConnection(url,username,password);
            pst= conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Map<String,Object> map = new HashMap<>();
                for(String param : returnParam){
                    map.put(param, rs.getObject(param));
                }
                resultList.add(map);
            }
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            //关闭资源
            try{
                if(rs!=null){
                    rs.close();
                }
                if(pst!=null){
                    pst.close();
                }
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return resultList;
    }
}



