<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="规则号" prop="ruleno">
        <el-input v-model="queryParams.ruleno" placeholder="请输入规则号" clearable size="small" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="规则名称" prop="rulename">
        <el-input v-model="queryParams.rulename" placeholder="请输入规则名称" clearable size="small" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="优先级" prop="priority">
        <el-input v-model="queryParams.priority" placeholder="请输入优先级" clearable size="small" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="起始时间">
        <el-date-picker v-model="dateRangeStartdate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker v-model="dateRangeEnddate" size="small" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['ruleManage::createRule']">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list" row-key="id">
      <el-table-column label="规则主键" align="center" prop="id" />

      <el-table-column label="规则号" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link  :to="{ path: '/ruleManage/ruleFactor', query: { ruleno: scope.row.ruleno } }"  class="link-type">
            <span>{{ scope.row.ruleno }}</span>
          </router-link>
        </template>
      </el-table-column>

      <el-table-column label="规则名称" align="center" prop="rulename" />
      <el-table-column label="规则类型" align="center" prop="ruletype" />
      <el-table-column label="优先级" align="center" prop="priority" />
      <el-table-column label="起始时间" align="center" prop="startdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startdate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="enddate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.enddate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status"  :formatter="statusFormat"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['ruleManage::updateRule']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['ruleManage::deleteRule']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="规则号" prop="ruleno"  >
          <el-input v-model="form.ruleno" :disabled="true" />
        </el-form-item>
        <el-form-item label="规则名称" prop="rulename">
          <el-input v-model="form.rulename" placeholder="请输入规则名称" />
        </el-form-item>
        <el-form-item label="规则类型" prop="ruletype">
          <el-input v-model="form.ruletype" placeholder="请输入规则类型" />
        </el-form-item>
        <el-form-item label="优先级" prop="priority">
          <el-input v-model="form.priority" placeholder="请输入优先级" />
        </el-form-item>
        <el-form-item label="起始时间" prop="startdate">
          <el-date-picker clearable size="small" v-model="form.startdate" type="date" value-format="yyyy-MM-dd" placeholder="选择起始时间" />
        </el-form-item>
        <el-form-item label="结束时间" prop="enddate">
          <el-date-picker clearable size="small" v-model="form.enddate" type="date" value-format="yyyy-MM-dd" placeholder="选择结束时间" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createRule, updateRule, deleteRule, getRule, getPage, exportExcel,getRuleNo } from "@/api/ruleManage/rule";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import {SysCommonStatusEnum} from '@/utils/constants'
import { getDictDataLabel, getDictDatas, DICT_TYPE } from '@/utils/dict'


export default {
  name: "rule",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 规则管理列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeStartdate: [],
      dateRangeEnddate: [],
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        ruleno: null,
        rulename: null,
        ruletype: null,
        priority: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeStartdate, 'startdate');
      this.addBeginAndEndTime(params, this.dateRangeEnddate, 'enddate');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getPage(params).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 字典状态字典翻译
    statusFormat(row, column) {
      return getDictDataLabel(DICT_TYPE.SYS_COMMON_STATUS, row.status)
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        ruleno: undefined,
        rulename: undefined,
        ruletype: undefined,
        priority: undefined,
        startdate: undefined,
        enddate: undefined,
        status: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRangeStartdate = [];
      this.dateRangeEnddate = [];
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getRuleNo().then(response => {
        this.form.ruleno = response.data.ruleno;
      });
      this.open = true;
      this.title = "添加规则管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getRule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改规则管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateRule(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createRule(this.form).then(response => {
          this.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$confirm('是否确认删除规则管理编号为"' + id + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return deleteRule(id);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.addBeginAndEndTime(params, this.dateRangeStartdate, 'startdate');
      this.addBeginAndEndTime(params, this.dateRangeEnddate, 'enddate');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行导出
      this.$confirm('是否确认导出所有规则管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportExcel(params);
      }).then(response => {
        this.downloadExcel(response, '规则管理.xls');
      })
    }
  }
};
</script>
