import request from '@/utils/request'

// 查询煤矿信息列表
export function listCoal(query) {
  return request({
    url: '/manage/coal/list',
    method: 'get',
    params: query
  })
}

// 查询煤矿信息详细
export function getCoal(id) {
  return request({
    url: '/manage/coal/' + id,
    method: 'get'
  })
}

// 新增煤矿信息
export function addCoal(data) {
  return request({
    url: '/manage/coal',
    method: 'post',
    data: data
  })
}

// 修改煤矿信息
export function updateCoal(data) {
  return request({
    url: '/manage/coal',
    method: 'put',
    data: data
  })
}

// 删除煤矿信息
export function delCoal(id) {
  return request({
    url: '/manage/coal/' + id,
    method: 'delete'
  })
}

// 删除煤矿信息
export function listSupplierList() {
  return request({
    url: '/manage/coal/supplierList',
    method: 'get'
  })
}

// 采购煤矿
export function purchaseOrder(data) {
  return request({
    url: '/manage/coal/purchase',
    method: 'post',
    data: data
  })
}

// 销售煤矿
export function saleOrder(data) {
  return request({
    url: 'manage/coal/sale',
    method: 'post',
    data: data
  })
}
