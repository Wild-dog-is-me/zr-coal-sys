import request from '@/utils/request'

// 查询供应商列表
export function listSupplier(query) {
  return request({
    url: '/manage/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商详细
export function getSupplier(id) {
  return request({
    url: '/manage/supplier/' + id,
    method: 'get'
  })
}

// 新增供应商
export function addSupplier(data) {
  return request({
    url: '/manage/supplier',
    method: 'post',
    data: data
  })
}

// 修改供应商
export function updateSupplier(data) {
  return request({
    url: '/manage/supplier',
    method: 'put',
    data: data
  })
}

// 删除供应商
export function delSupplier(id) {
  return request({
    url: '/manage/supplier/' + id,
    method: 'delete'
  })
}
