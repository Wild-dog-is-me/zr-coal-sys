package com.zr.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zr.common.utils.SecurityUtils;
import com.zr.manage.controller.vo.CheckInfoVO;
import com.zr.manage.convert.CheckInfoConvert;
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
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.service.ICheckInfoService;
import com.zr.common.utils.poi.ExcelUtil;
import com.zr.common.core.page.TableDataInfo;

/**
 * 账单信息Controller
 *
 * @author zr
 * @date 2024-09-08
 */
@RestController
@RequestMapping("/manage/check")
public class CheckInfoController extends BaseController
{
    @Autowired
    private ICheckInfoService checkInfoService;

    /**
     * 查询账单信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CheckInfo checkInfo)
    {
        startPage();
        boolean admin = SecurityUtils.getLoginUser().equals("admin");
        if (!admin) {
            checkInfo.setCreateBy(SecurityUtils.getUsername());
        }
        List<CheckInfo> list = checkInfoService.selectCheckInfoList(checkInfo);
        List<CheckInfoVO> checkInfoVOS = CheckInfoConvert.convertVO(list);
        return getDataTable(checkInfoVOS);
    }

    /**
     * 导出账单信息列表
     */
    @Log(title = "账单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CheckInfo checkInfo)
    {
        List<CheckInfo> list = checkInfoService.selectCheckInfoList(checkInfo);
        ExcelUtil<CheckInfo> util = new ExcelUtil<CheckInfo>(CheckInfo.class);
        util.exportExcel(response, list, "账单信息数据");
    }

    /**
     * 获取账单信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(checkInfoService.selectCheckInfoById(id));
    }

    /**
     * 新增账单信息
     */
    @Log(title = "账单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckInfo checkInfo)
    {
        return toAjax(checkInfoService.insertCheckInfo(checkInfo));
    }

    /**
     * 修改账单信息
     */
    @Log(title = "账单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckInfo checkInfo)
    {
        return toAjax(checkInfoService.updateCheckInfo(checkInfo));
    }

    /**
     * 删除账单信息
     */
    @Log(title = "账单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkInfoService.deleteCheckInfoByIds(ids));
    }
}
