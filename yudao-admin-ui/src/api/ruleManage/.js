import request from '@/utils/request'

// 创建规则管理
export function create(data) {
  return request({
    url: '/ruleManage//create',
    method: 'post',
    data: data
  })
}

// 更新规则管理
export function update(data) {
  return request({
    url: '/ruleManage//update',
    method: 'put',
    data: data
  })
}

// 删除规则管理
export function delete(id) {
  return request({
    url: '/ruleManage//delete?id=' + id,
    method: 'delete'
  })
}

// 获得规则管理
export function get(id) {
  return request({
    url: '/ruleManage//get?id=' + id,
    method: 'get'
  })
}

// 获得规则管理分页
export function getPage(query) {
  return request({
    url: '/ruleManage//page',
    method: 'get',
    params: query
  })
}

// 导出规则管理 Excel
export function exportExcel(query) {
  return request({
    url: '/ruleManage//export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
