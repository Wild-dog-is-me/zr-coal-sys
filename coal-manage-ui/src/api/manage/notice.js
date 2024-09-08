import request from '@/utils/request'

// 查询公告信息列表
export function listNotice(query) {
  return request({
    url: '/manage/notice/list',
    method: 'get',
    params: query
  })
}

// 查询公告信息详细
export function getNotice(id) {
  return request({
    url: '/manage/notice/' + id,
    method: 'get'
  })
}

// 新增公告信息
export function addNotice(data) {
  return request({
    url: '/manage/notice',
    method: 'post',
    data: data
  })
}

// 修改公告信息
export function updateNotice(data) {
  return request({
    url: '/manage/notice',
    method: 'put',
    data: data
  })
}

// 删除公告信息
export function delNotice(id) {
  return request({
    url: '/manage/notice/' + id,
    method: 'delete'
  })
}
