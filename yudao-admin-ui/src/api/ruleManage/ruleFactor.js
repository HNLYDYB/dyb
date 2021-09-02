import request from '@/utils/request'

// 创建规则使用因子
export function create(data) {
  return request({
    url: '/ruleManage/ruleFactor/create',
    method: 'post',
    data: data
  })
}

// 更新规则使用因子
export function update(data) {
  return request({
    url: '/ruleManage/ruleFactor/update',
    method: 'put',
    data: data
  })
}

// 删除规则使用因子
export function del(id) {
  return request({
    url: '/ruleManage/ruleFactor/delete?id=' + id,
    method: 'delete'
  })
}

// 获得规则使用因子
export function get(id) {
  return request({
    url: '/ruleManage/ruleFactor/get?id=' + id,
    method: 'get'
  })
}



// 获得规则使用因子分页
export function getPage(query) {
  return request({
    url: '/ruleManage/ruleFactor/page',
    method: 'get',
    params: query
  })
}

// 导出规则使用因子 Excel
export function exportExcel(query) {
  return request({
    url: '/ruleManage/ruleFactor/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
