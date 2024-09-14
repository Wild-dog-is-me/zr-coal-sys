import request from '@/utils/request'

// 查询煤炭信息列表
export function listCoal(query) {
  return request({
    url: '/manage/coal/list',
    method: 'get',
    params: query
  })
}

// 查询煤炭信息详细
export function getCoal(id) {
  return request({
    url: '/manage/coal/' + id,
    method: 'get'
  })
}

// 新增煤炭信息
export function addCoal(data) {
  return request({
    url: '/manage/coal',
    method: 'post',
    data: data
  })
}

// 修改煤炭信息
export function updateCoal(data) {
  return request({
    url: '/manage/coal',
    method: 'put',
    data: data
  })
}

// 删除煤炭信息
export function delCoal(id) {
  return request({
    url: '/manage/coal/' + id,
    method: 'delete'
  })
}

// 删除煤炭信息
export function listSupplierList() {
  return request({
    url: '/manage/coal/supplierList',
    method: 'get'
  })
}

// 采购煤炭
export function purchaseOrder(data) {
  return request({
    url: '/manage/coal/purchase',
    method: 'post',
    data: data
  })
}

// 销售煤炭
export function saleOrder(data) {
  return request({
    url: 'manage/coal/sale',
    method: 'post',
    data: data
  })
}
