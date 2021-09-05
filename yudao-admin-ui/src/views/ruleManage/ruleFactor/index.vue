<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="因子代码" prop="factorcode">
        <el-input v-model="queryParams.factorcode" placeholder="请输入因子代码" clearable size="small" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="因子名称" prop="factorname">
        <el-input v-model="queryParams.factorname" placeholder="请输入因子名称" clearable size="small" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="规则号" align="center" prop="ruleno" />
      <el-table-column label="规则类型" align="center" prop="ruletype" />
      <el-table-column label="因子代码" align="center" prop="factorcode" />
      <el-table-column label="因子名称" align="center" prop="factorname" />
      <el-table-column label="因子逻辑" align="center" prop="judgetype" />
      <el-table-column label="命中值代码" align="center" prop="factorvaluecode" />
      <el-table-column label="命中值逻辑" align="center" prop="factorvaluename" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="规则主键" prop="ruleid"  :hidden="true">
          <el-input v-model="form.ruleid"  />
        </el-form-item>
        <el-form-item label="规则号" prop="ruleno">
          <el-input v-model="form.ruleno" placeholder="请输入规则号"  :disabled="true" />
        </el-form-item>

        <el-form-item label="因子类型" prop="factortype">
          <el-select v-model="form.factortype" placeholder="请选择因子类型">
            <el-option v-for="dict in  this.getDictDatas(DICT_TYPE.RULE_FACTOR_YPE)"
                       :key="dict.label" :label="dict.value">
              {{dict.label}}</el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="命中逻辑" prop="judgetype">
          <el-select v-model="form.judgetype" placeholder="请选择命中逻辑">
            <el-option v-for="dict in  this.getDictDatas(DICT_TYPE.RULE_FACTOR_JUDGETYPE)"
                       :key="dict.label" :label="dict.value">
              {{dict.label}}</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="因子代码" prop="factorcode">
          <el-input v-model="form.factorcode" placeholder="请输入因子代码" />
        </el-form-item>
        <el-form-item label="因子名称" prop="factorname">
          <el-input v-model="form.factorname" placeholder="请输入因子名称" />
        </el-form-item>
        <el-form-item label="命中值代码" prop="factorvaluecode">
          <el-input v-model="form.factorvaluecode" placeholder="请输入命中值代码" />
        </el-form-item>
        <el-form-item label="命中值逻辑" prop="factorvaluename">
          <el-input v-model="form.factorvaluename" placeholder="请输入命中值逻辑" />
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
import { create, update, del, get, getPage } from "@/api/ruleManage/ruleFactor";
import { getRulebyNo } from "@/api/ruleManage/rule";
import { getDictDataLabel, getDictDatas, DICT_TYPE } from '@/utils/dict'
export default {
  name: "ruleFactor",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 规则使用因子列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeCreateTime: [],
      mainPageData: {
        ruleid: null,
        ruleno: null,
      },
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        ruleid: null,
        ruleno: null,
        ruletype: null,
        factortype:null,
        factorcode: null,
        factorname: null,
        judgetype: null,
        factorvaluecode: null,
        factorvaluename: null,
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
    const ruleno = this.$route.query && this.$route.query.ruleno;
    //新增时弹出框为父页面的
    this.initRuleFactor(ruleno);
    //绑定规则号，进行查询
    this.queryParams.ruleno=ruleno;
    this.getList();
  },
  methods: {
    /** 初始化规则因子 */
    initRuleFactor(ruleno) {
      getRulebyNo(ruleno).then(response => {
        this.mainPageData.ruleno = response.data.ruleno;
        this.mainPageData.ruleid = response.data.id;
      });
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getPage(params).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
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
        ruleid: undefined,
        ruleno: undefined,
        ruletype: undefined,
        factortype: undefined,
        factorcode: undefined,
        factorname: undefined,
        judgetype: undefined,
        factorvaluecode: undefined,
        factorvaluename: undefined,
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
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加规则因子";
      this.form = {
        ruleid: this.mainPageData.ruleid,
        ruleno: this.mainPageData.ruleno,
      };
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      get(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改规则因子";
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
          update(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        create(this.form).then(response => {
          this.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$confirm('是否确认删除规则使用因子编号为"' + id + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return del(id);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    }
  }
};
</script>
