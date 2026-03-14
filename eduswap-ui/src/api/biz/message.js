import request from '@/utils/request'

// 查询消息列表
export function listMessage(query) {
  return request({
    url: '/biz/message/list',
    method: 'get',
    params: query
  })
}

// 查询消息详细
export function getMessage(msgId) {
  return request({
    url: '/biz/message/' + msgId,
    method: 'get'
  })
}

// 新增消息
export function addMessage(data) {
  return request({
    url: '/biz/message',
    method: 'post',
    data: data
  })
}

// 修改消息
export function updateMessage(data) {
  return request({
    url: '/biz/message',
    method: 'put',
    data: data
  })
}

// 删除消息
export function delMessage(msgId) {
  return request({
    url: '/biz/message/' + msgId,
    method: 'delete'
  })
}
