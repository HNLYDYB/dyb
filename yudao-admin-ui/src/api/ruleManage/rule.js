import request from '@/utils/request'

// 创建规则管理
export function createRule(data) {
  return request({
    url: '/ruleManage/rule/createRule',
    method: 'post',
    data: data
  })
}

// 更新规则管理
export function updateRule(data) {
  return request({
    url: '/ruleManage/rule/updateRule',
    method: 'put',
    data: data
  })
}

// 删除规则管理
export function deleteRule(id) {
  return request({
    url: '/ruleManage/rule/deleteRule?id=' + id,
    method: 'delete'
  })
}

// 获得规则管理
export function getRule(id) {
  return request({
    url: '/ruleManage/rule/getRule?id=' + id,
    method: 'get'
  })
}

// 获得规则管理分页
export function getPage(query) {
  return request({
    url: '/ruleManage/rule/page',
    method: 'get',
    params: query
  })
}

// 导出规则管理 Excel
export function exportExcel(query) {
  return request({
    url: '/ruleManage/rule/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
