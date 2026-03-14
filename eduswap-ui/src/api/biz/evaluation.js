import request from '@/utils/request'

// 查询评价列表
export function listEvaluation(query) {
  return request({
    url: '/biz/evaluation/list',
    method: 'get',
    params: query
  })
}

// 查询评价详细
export function getEvaluation(evalId) {
  return request({
    url: '/biz/evaluation/' + evalId,
    method: 'get'
  })
}

// 新增评价
export function addEvaluation(data) {
  return request({
    url: '/biz/evaluation',
    method: 'post',
    data: data
  })
}

// 修改评价
export function updateEvaluation(data) {
  return request({
    url: '/biz/evaluation',
    method: 'put',
    data: data
  })
}

// 删除评价
export function delEvaluation(evalId) {
  return request({
    url: '/biz/evaluation/' + evalId,
    method: 'delete'
  })
}
