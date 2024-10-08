import request from '@/utils/request'

// 查询订单信息列表
export function listOrder(query) {
  return request({
    url: '/manage/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单信息详细
export function getOrder(id) {
  return request({
    url: '/manage/order/' + id,
    method: 'get'
  })
}

// 新增订单信息
export function addOrder(data) {
  return request({
    url: '/manage/order',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateOrder(data) {
  return request({
    url: '/manage/order',
    method: 'put',
    data: data
  })
}

// 删除订单信息
export function delOrder(id) {
  return request({
    url: '/manage/order/' + id,
    method: 'delete'
  })
}

export function queryCoalList() {
  return request({
    url: '/manage/order/queryCoal',
    method: 'get'
  })
}

export function deliverOrder(id) {
  return request({
    url: '/manage/order/deliver?id=' + id,
    method: "get"
  })
}

export function revOrder(id) {
  return request({
    url: '/manage/order/rev?id=' + id,
    method: "get"
  })
}

export function payFinishOrder(id) {
  return request({
    url: '/manage/order/payFinish?id=' + id,
    method: "get"
  })
}

export function partPay(data){
  return request({
    url: '/manage/order/payDivide',
    method: "post",
    data: data,
  })
}

