package com.zr.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.zr.manage.domain.NoticeInfo;
import com.zr.manage.service.INoticeInfoService;
import com.zr.common.utils.poi.ExcelUtil;
import com.zr.common.core.page.TableDataInfo;

/**
 * 公告信息Controller
 *
 * @author zr
 * @date 2024-09-08
 */
@RestController
@RequestMapping("/manage/notice")
public class NoticeInfoController extends BaseController
{
    @Autowired
    private INoticeInfoService noticeInfoService;

    /**
     * 查询公告信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(NoticeInfo noticeInfo)
    {
        startPage();
        List<NoticeInfo> list = noticeInfoService.selectNoticeInfoList(noticeInfo);
        return getDataTable(list);
    }

    /**
     * 导出公告信息列表
     */
    @Log(title = "公告信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NoticeInfo noticeInfo)
    {
        List<NoticeInfo> list = noticeInfoService.selectNoticeInfoList(noticeInfo);
        ExcelUtil<NoticeInfo> util = new ExcelUtil<NoticeInfo>(NoticeInfo.class);
        util.exportExcel(response, list, "公告信息数据");
    }

    /**
     * 获取公告信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(noticeInfoService.selectNoticeInfoById(id));
    }

    /**
     * 新增公告信息
     */
    @Log(title = "公告信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NoticeInfo noticeInfo)
    {
        return toAjax(noticeInfoService.insertNoticeInfo(noticeInfo));
    }

    /**
     * 修改公告信息
     */
    @Log(title = "公告信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NoticeInfo noticeInfo)
    {
        return toAjax(noticeInfoService.updateNoticeInfo(noticeInfo));
    }

    /**
     * 删除公告信息
     */
    @Log(title = "公告信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(noticeInfoService.deleteNoticeInfoByIds(ids));
    }
}
