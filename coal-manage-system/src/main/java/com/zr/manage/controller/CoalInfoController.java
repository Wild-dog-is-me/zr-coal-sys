package com.zr.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zr.manage.controller.vo.CoalInfoVO;
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
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.service.ICoalInfoService;
import com.zr.common.utils.poi.ExcelUtil;
import com.zr.common.core.page.TableDataInfo;

/**
 * 煤矿信息Controller
 *
 * @author zr
 * @date 2024-09-08
 */
@RestController
@RequestMapping("/manage/coal")
public class CoalInfoController extends BaseController
{
    @Autowired
    private ICoalInfoService coalInfoService;

    /**
     * 查询煤矿信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CoalInfo coalInfo)
    {
        startPage();
        List<CoalInfoVO> list = coalInfoService.selectCoalInfoList(coalInfo);
        return getDataTable(list);
    }

    /**
     * 导出煤矿信息列表
     */
    @Log(title = "煤矿信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoalInfo coalInfo)
    {
        List<CoalInfo> list = coalInfoService.selectCoalInfoList(coalInfo);
        ExcelUtil<CoalInfo> util = new ExcelUtil<CoalInfo>(CoalInfo.class);
        util.exportExcel(response, list, "煤矿信息数据");
    }

    /**
     * 获取煤矿信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(coalInfoService.selectCoalInfoById(id));
    }

    /**
     * 新增煤矿信息
     */
    @Log(title = "煤矿信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoalInfo coalInfo)
    {
        return toAjax(coalInfoService.insertCoalInfo(coalInfo));
    }

    /**
     * 修改煤矿信息
     */
    @Log(title = "煤矿信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoalInfo coalInfo)
    {
        return toAjax(coalInfoService.updateCoalInfo(coalInfo));
    }

    /**
     * 删除煤矿信息
     */
    @Log(title = "煤矿信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(coalInfoService.deleteCoalInfoByIds(ids));
    }
}
