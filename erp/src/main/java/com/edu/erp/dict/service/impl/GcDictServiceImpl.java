package com.edu.erp.dict.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.erp.dao.DictDataDao;
import com.edu.erp.dao.DictTypeDao;
import com.edu.erp.dict.service.GcDictService;
import com.edu.erp.model.TpDictData;
import com.edu.erp.model.TpDictType;
import com.github.pagehelper.Page;

@Service("gcDictService")
public class GcDictServiceImpl implements GcDictService {
    private static Map<String, List<TpDictData>> dictDataMap = new HashMap<String, List<TpDictData>>();

    @Resource(name = "dictTypeDao")
    private DictTypeDao dictTypeDao;

    @Resource(name = "dictDataDao")
    private DictDataDao dictDataDao;

    /**
     * 字典类型分页查询
     * 
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public Page<TpDictType> typePage(Page<TpDictType> page) throws Exception {
        return dictTypeDao.page(page);
    }

    /**
     * 字典数据分页查询
     * 
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public Page<TpDictData> dataPage(Page<TpDictData> page) throws Exception {
        return dictDataDao.page(page);
    }

    /**
     * 根据条件查询List<T>
     * 
     * @param param
     *            动态参数
     * @return
     * @throws Exception
     */
    @Override
    public List<TpDictData> dataList(Map<String, Object> param) throws Exception {
        return dictDataDao.selectList(param);
    }

    /**
     * 新增
     * 
     * @param pojo
     * @throws Exception
     */
    @Override
    public void toAdd(TpDictData pojo) throws Exception {
        Integer ret = dictDataDao.toAdd(pojo);
        if (ret < 1)
            throw new RuntimeException("toAdd_error");
    }

    /**
     * 根据ID修改
     * 
     * @param pojo
     *            部门IDS
     * @throws Exception
     */
    @Override
    public void toUpdate(TpDictData pojo) throws Exception {
        Integer ret = dictDataDao.toUpdate(pojo);
        if (ret < 1)
            throw new RuntimeException("toUpdate_error");
    }

    /**
     * 根据IDS字符串改变状态
     * 
     * @param ids
     * @param status
     * @throws Exception
     */
    @Override
    public void toChangeStatus(String ids, Integer status) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        param.put("status", status);
        dictDataDao.toChangeStatus(param);
    }

    @Override
    public List<Map> getBranchsByUser(Map<String, Object> param) throws Exception {
        return dictDataDao.getBranchsByUser(param);
    }

    @Override
    public List<Map> getBusByUser(Map<String, Object> param) throws Exception {
        return dictDataDao.getBusByUser(param);
    }

    @Override
    public List<Map<String, Object>> getPosts(Map<String, Object> params) throws Exception {
        return dictDataDao.getPosts(params);
    }

    @Override
    public List<TpDictData> selectByDictTypeCode(String dictTypeCode) throws Exception {
        if (StringUtils.isNotEmpty(dictTypeCode)) {
            if (dictDataMap.containsKey(dictTypeCode)) {
                return dictDataMap.get(dictTypeCode);
            }
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("dictTypeCodes", Arrays.asList(dictTypeCode.split(",")));
            List<TpDictData> dictDataList = dataList(param);
            dictDataMap.put(dictTypeCode, dictDataList);
            return dictDataList;
        }
        return null;
    }

    @Override
    public Map<String, List<TpDictData>> selectByDictTypeCodes(String dictTypeCodes) throws Exception {
        if (StringUtils.isNotEmpty(dictTypeCodes)) {
            Map<String, Object> param = new HashMap<String, Object>();
            List<String> dictTypeCodeList = Arrays.asList(dictTypeCodes.split(","));
            Map<String, List<TpDictData>> resultMap = new HashMap<String, List<TpDictData>>();
            List<String> paramList = new ArrayList<String>();
            for (String dictTypeCode : dictTypeCodeList) {
                if (dictDataMap.containsKey(dictTypeCode)) {
                    resultMap.put(dictTypeCode, dictDataMap.get(dictTypeCode));
                } else {
                    paramList.add(dictTypeCode);
                }
            }

            if (!CollectionUtils.isEmpty(paramList)) {
                param.put("dictTypeCodes", paramList);
                List<TpDictData> dataList = dataList(param);
                if (!CollectionUtils.isEmpty(dataList)) {
                    for (TpDictData dictData : dataList) {
                        String key = dictData.getDict_type_code();
                        if (resultMap.containsKey(key)) {
                            resultMap.get(key).add(dictData);
                        } else {
                            List<TpDictData> dictDataList = new ArrayList<TpDictData>();
                            dictDataList.add(dictData);
                            resultMap.put(key, dictDataList);
                        }
                    }
                    dictDataMap.putAll(resultMap);
                }
            }
            return resultMap;
        }
        return null;
    }

    @Override
    public List<TpDictData> selectDictData(Map<String, Object> paramMap) throws Exception {
        Assert.notNull(paramMap.get("typeCode"), "字典类型查询条件为空，查询失败！");
        String key = paramMap.get("typeCode").toString()
                + (paramMap.get("subTypeCode") == null ? StringUtils.EMPTY : paramMap.get("subTypeCode").toString());
        if (Constants.YES.equals(paramMap.get("needProductLineCdtn"))) {
            key += paramMap.get("product_line").toString();
        }
        if (!CollectionUtils.isEmpty(dictDataMap.get(key))) {
            return dictDataMap.get(key);
        }
        
        List<TpDictData> dictDataList = this.dataList(paramMap);
        if (!CollectionUtils.isEmpty(dictDataList)) {
            dictDataMap.put(key, dictDataList);
        }
        
        return dictDataList;
    }

}
