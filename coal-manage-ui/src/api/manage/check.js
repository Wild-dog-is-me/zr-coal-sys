import request from '@/utils/request'

// 查询账单信息列表
export function listCheck(query) {
  return request({
    url: '/manage/check/list',
    method: 'get',
    params: query
  })
}

// 查询账单信息详细
export function getCheck(id) {
  return request({
    url: '/manage/check/' + id,
    method: 'get'
  })
}

// 新增账单信息
export function addCheck(data) {
  return request({
    url: '/manage/check',
    method: 'post',
    data: data
  })
}

// 修改账单信息
export function updateCheck(data) {
  return request({
    url: '/manage/check',
    method: 'put',
    data: data
  })
}

// 删除账单信息
export function delCheck(id) {
  return request({
    url: '/manage/check/' + id,
    method: 'delete'
  })
}
