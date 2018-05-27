package com.emucoo.service.calendar.impl;

import com.emucoo.dto.modules.calendar.WorkTargetDelVO;
import com.emucoo.dto.modules.calendar.WorkTargetQueryVO;
import com.emucoo.dto.modules.calendar.WorkTargetVO;
import com.emucoo.mapper.TSaleTargetMapper;
import com.emucoo.mapper.TShopInfoMapper;
import com.emucoo.mapper.TWorkTargetMapper;
import com.emucoo.model.TSaleTarget;
import com.emucoo.model.TShopInfo;
import com.emucoo.model.TWorkTarget;
import com.emucoo.service.calendar.WorkTargetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工作目标
 * @author Jacy
 *
 */
@Transactional
@Service
public class WorkTargetServiceImpl implements WorkTargetService {

    @Resource
    private TWorkTargetMapper tWorkTargetMapper;

    @Resource
    private TSaleTargetMapper tSaleTargetMapper;

    @Resource
    private TShopInfoMapper tShopInfoMapper;
    /**
     * 查询用户工作目标
     * @param workTargetQueryVO
     * @return
     */
    @Override
   public WorkTargetVO getWorkTarget(WorkTargetQueryVO workTargetQueryVO){
        WorkTargetVO workTargetVO=null;
        Example example =new Example(TWorkTarget.class);
        example.createCriteria().andEqualTo("month",workTargetQueryVO.getMonth())
                .andEqualTo("createUserId",workTargetQueryVO.getUserID())
                .andEqualTo("isDel",false);
        TWorkTarget tWorkTarget=tWorkTargetMapper.selectOneByExample(example);
        if(null!=tWorkTarget){
            workTargetVO=new WorkTargetVO();
            workTargetVO.setWorkTargetID(tWorkTarget.getId());
            workTargetVO.setKpi(tWorkTarget.getKpi());
            workTargetVO.setMonth(tWorkTarget.getMonth());
            workTargetVO.setPurchaseDate(tWorkTarget.getPurchaseDate());
            workTargetVO.setWorkContent(tWorkTarget.getWorkContent());
            Example exampleSale=new Example(TSaleTarget.class);
            exampleSale.createCriteria().andEqualTo("workTargetId",tWorkTarget.getId());
            List<TSaleTarget> saleTargetList=tSaleTargetMapper.selectByExample(exampleSale);
            List<WorkTargetVO.SaleVO> list=null;
            if(null!=saleTargetList && saleTargetList.size()>0) {
                list=new ArrayList<>();
                WorkTargetVO.SaleVO saleVO=null;
                for (TSaleTarget tSaleTarget:saleTargetList){
                    saleVO=new WorkTargetVO.SaleVO();
                    saleVO.setSaleTargetID(tSaleTarget.getId());
                    saleVO.setActualAmount(tSaleTarget.getActualAmount());
                    saleVO.setTargetAmount(tSaleTarget.getTargetAmount());
                    saleVO.setShopId(tSaleTarget.getShopId());
                    TShopInfo shopInfo=tShopInfoMapper.selectByPrimaryKey(tSaleTarget.getShopId());
                    saleVO.setShopName(shopInfo.getShopName());
                    saleVO.setShopType(shopInfo.getType());
                    list.add(saleVO);
                }
                workTargetVO.setSaleList(list);
            }
        }
       return  workTargetVO;
   }

    /**
     * 添加工作目标
     * @param workTargetVO
     * @param currentUserId
     * @return
     */
    @Override
    @Transactional
    public void addWorkTarget(WorkTargetVO workTargetVO, Long currentUserId){
        TWorkTarget tWorkTarget=new TWorkTarget();
        tWorkTarget.setKpi(workTargetVO.getKpi());
        tWorkTarget.setMonth(workTargetVO.getMonth());
        tWorkTarget.setPurchaseDate(workTargetVO.getPurchaseDate());
        tWorkTarget.setWorkContent(workTargetVO.getWorkContent());
        tWorkTarget.setCreateTime(new Date());
        tWorkTarget.setCreateUserId(currentUserId);
        tWorkTarget.setIsDel(false);
        tWorkTargetMapper.insertUseGeneratedKeys(tWorkTarget);
        Long workTargetId=tWorkTarget.getId();
        List<WorkTargetVO.SaleVO> saleList=workTargetVO.getSaleList();
        if(null!=saleList && saleList.size()>0){
            TSaleTarget tSaleTarget=null;
            for (WorkTargetVO.SaleVO saleVO:saleList){
                tSaleTarget=new TSaleTarget();
                tSaleTarget.setActualAmount(saleVO.getActualAmount());
                tSaleTarget.setShopId(saleVO.getShopId());
                tSaleTarget.setTargetAmount(saleVO.getTargetAmount());
                tSaleTarget.setWorkTargetId(workTargetId);
                tSaleTargetMapper.insertSelective(tSaleTarget);
            }
        }

    }



    /**
     * 编辑工作目标
     * @param workTargetVO
     * @param currentUserId
     */
    @Override
    @Transactional
    public void editWorkTarget(WorkTargetVO workTargetVO, Long currentUserId){
        TWorkTarget tWorkTarget=new TWorkTarget();
        tWorkTarget.setId(workTargetVO.getWorkTargetID());
        tWorkTarget.setWorkContent(workTargetVO.getWorkContent());
        tWorkTarget.setPurchaseDate(workTargetVO.getPurchaseDate());
        tWorkTarget.setMonth(workTargetVO.getMonth());
        tWorkTarget.setKpi(workTargetVO.getKpi());
        tWorkTarget.setModifytime(new Date());
        tWorkTarget.setModifyUserId(currentUserId);
        tWorkTargetMapper.updateByPrimaryKeySelective(tWorkTarget);
        List<WorkTargetVO.SaleVO> saleList=workTargetVO.getSaleList();
        //先删除 之前关联的销售目标
        Example example=new Example(TSaleTarget.class);
        example.createCriteria().andEqualTo("workTargetId",workTargetVO.getWorkTargetID());
        tSaleTargetMapper.deleteByExample(example);
        if(null!=saleList && saleList.size()>0){
            TSaleTarget tSaleTarget=null;
            for (WorkTargetVO.SaleVO saleVO:saleList){
                tSaleTarget=new TSaleTarget();
                tSaleTarget.setActualAmount(saleVO.getActualAmount());
                tSaleTarget.setShopId(saleVO.getShopId());
                tSaleTarget.setTargetAmount(saleVO.getTargetAmount());
                tSaleTarget.setWorkTargetId(workTargetVO.getWorkTargetID());
                tSaleTargetMapper.insertSelective(tSaleTarget);
            }
        }
    }

    /**
     * 删除工作目标
     * @param workTargetDelVO
     */
    @Override
    @Transactional
    public void deleteWorkTarget(WorkTargetDelVO workTargetDelVO){
        Long workTargetId=workTargetDelVO.getWorkTargetId();
        TWorkTarget tWorkTarget=new TWorkTarget();
        tWorkTarget.setIsDel(true);
        tWorkTarget.setId(workTargetId);
        tWorkTargetMapper.updateByPrimaryKeySelective(tWorkTarget);
        //删除销售目标
        Example example=new Example(TSaleTarget.class);
        example.createCriteria().andEqualTo("workTargetId",workTargetId);
        tSaleTargetMapper.deleteByExample(example);
    }

}
