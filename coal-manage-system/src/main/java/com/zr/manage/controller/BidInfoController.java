package com.zr.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zr.manage.controller.vo.BidInfoVO;
import com.zr.manage.convert.BidInfoConvert;
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
import com.zr.manage.domain.BidInfo;
import com.zr.manage.service.IBidInfoService;
import com.zr.common.utils.poi.ExcelUtil;
import com.zr.common.core.page.TableDataInfo;

/**
 * 竞价采购Controller
 *
 * @author zr
 * @date 2024-09-08
 */
@RestController
@RequestMapping("/manage/bid")
public class BidInfoController extends BaseController
{
    @Autowired
    private IBidInfoService bidInfoService;

    /**
     * 查询竞价采购列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BidInfo bidInfo)
    {
        startPage();
        List<BidInfo> list = bidInfoService.selectBidInfoList(bidInfo);
        List<BidInfoVO> bidInfoVOList = BidInfoConvert.convertVO(list);
        return getDataTable(bidInfoVOList);
    }

    /**
     * 导出竞价采购列表
     */
    @Log(title = "竞价采购", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BidInfo bidInfo)
    {
        List<BidInfo> list = bidInfoService.selectBidInfoList(bidInfo);
        ExcelUtil<BidInfo> util = new ExcelUtil<BidInfo>(BidInfo.class);
        util.exportExcel(response, list, "竞价采购数据");
    }

    /**
     * 获取竞价采购详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bidInfoService.selectBidInfoById(id));
    }

    /**
     * 新增竞价采购
     */
    @Log(title = "竞价采购", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BidInfo bidInfo)
    {
        return toAjax(bidInfoService.insertBidInfo(bidInfo));
    }

    /**
     * 修改竞价采购
     */
    @Log(title = "竞价采购", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BidInfo bidInfo)
    {
        return toAjax(bidInfoService.updateBidInfo(bidInfo));
    }

    /**
     * 删除竞价采购
     */
    @Log(title = "竞价采购", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bidInfoService.deleteBidInfoByIds(ids));
    }
}
