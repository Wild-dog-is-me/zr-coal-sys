import request from '@/utils/request'

// 查询竞价采购列表
export function listBid(query) {
  return request({
    url: '/manage/bid/list',
    method: 'get',
    params: query
  })
}

// 查询竞价采购详细
export function getBid(id) {
  return request({
    url: '/manage/bid/' + id,
    method: 'get'
  })
}

// 新增竞价采购
export function addBid(data) {
  return request({
    url: '/manage/bid',
    method: 'post',
    data: data
  })
}

// 修改竞价采购
export function updateBid(data) {
  return request({
    url: '/manage/bid',
    method: 'put',
    data: data
  })
}

// 删除竞价采购
export function delBid(id) {
  return request({
    url: '/manage/bid/' + id,
    method: 'delete'
  })
}
