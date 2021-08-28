import request from '@/utils/request'

// 创建因子配置
export function create(data) {
  return request({
    url: '/ruleManage/factor/create',
    method: 'post',
    data: data
  })
}

// 更新因子配置
export function update(data) {
  return request({
    url: '/ruleManage/factor/update',
    method: 'put',
    data: data
  })
}

// 删除因子配置
export function deletefactor(id) {
  return request({
    url: '/ruleManage/factor/delete?id=' + id,
    method: 'delete'
  })
}

// 获得因子配置
export function get(id) {
  return request({
    url: '/ruleManage/factor/get?id=' + id,
    method: 'get'
  })
}

// 获得因子配置分页
export function getPage(query) {
  return request({
    url: '/ruleManage/factor/page',
    method: 'get',
    params: query
  })
}

// 导出因子配置 Excel
export function exportExcel(query) {
  return request({
    url: '/ruleManage/factor/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}


// 获取岗位精简信息列表
export function getJudgetypeList() {
  return request({
    url: '/ruleManage/factor/getJudgetypeList',
    method: 'get'
  })
}
