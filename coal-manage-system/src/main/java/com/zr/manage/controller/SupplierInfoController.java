package com.zr.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zr.common.exception.base.BaseException;
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.mapper.CheckInfoMapper;
import com.zr.manage.mapper.CoalInfoMapper;
import com.zr.manage.mapper.SupplierInfoMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zr.common.annotation.Log;
import com.zr.common.core.controller.BaseController;
import com.zr.common.core.domain.AjaxResult;
import com.zr.common.enums.BusinessType;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.service.ISupplierInfoService;
import com.zr.common.utils.poi.ExcelUtil;
import com.zr.common.core.page.TableDataInfo;

/**
 * 供应商Controller
 *
 * @author zr
 * @date 2024-09-08
 */
@RestController
@RequestMapping("/manage/supplier")
public class SupplierInfoController extends BaseController {
    @Autowired
    private ISupplierInfoService supplierInfoService;
    @Autowired
    private CheckInfoMapper checkInfoMapper;
    @Autowired
    private CoalInfoMapper coalInfoMapper;
    @Autowired
    private SupplierInfoMapper supplierInfoMapper;

    /**
     * 查询供应商列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SupplierInfo supplierInfo) {
        startPage();
        List<SupplierInfo> list = supplierInfoService.selectSupplierInfoList(supplierInfo);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表
     */
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SupplierInfo supplierInfo) {
        List<SupplierInfo> list = supplierInfoService.selectSupplierInfoList(supplierInfo);
        ExcelUtil<SupplierInfo> util = new ExcelUtil<SupplierInfo>(SupplierInfo.class);
        util.exportExcel(response, list, "供应商数据");
    }

    /**
     * 获取供应商详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(supplierInfoService.selectSupplierInfoById(id));
    }

    /**
     * 新增供应商
     */
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplierInfo supplierInfo) {
        return toAjax(supplierInfoService.insertSupplierInfo(supplierInfo));
    }

    /**
     * 修改供应商
     */
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplierInfo supplierInfo) {
        return toAjax(supplierInfoService.updateSupplierInfo(supplierInfo));
    }

    /**
     * 删除供应商
     */
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        Long id = ids[0];
        LambdaQueryWrapper<CoalInfo> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(CoalInfo::getCoalSupplierId, id);
        List<CoalInfo> coalInfos = coalInfoMapper.selectList(lqw1);
        LambdaQueryWrapper<CheckInfo> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(CheckInfo::getCheckHolderUserId, id);
        List<CheckInfo> checkInfos = checkInfoMapper.selectList(lqw2);
        if (ObjectUtil.isNull(checkInfos) && ObjectUtil.isNull(coalInfos)) {
            return toAjax(supplierInfoMapper.deleteSupplierInfoByIds(ids));
        } else {
            throw new BaseException("当前供应商仍有商品或订单未处理");
        }
    }
}
