<template>
  <div>
    <!-- 新增编辑 -->
    <el-dialog class="mainDialog" :title="title" :visible.sync="openDialog" width="1000px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary"  @close="handleDialogClose">
      <el-tabs v-model="activeName" @tab-click="handleClick" >
        <el-tab-pane label="人员信息" name="first">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="8">
                <orgSearch size="mini" label="单位" :queryParams="form"  ></orgSearch>
              </el-col>
              <el-col :span="8">
                <el-form-item label="科室名称" prop="deptCode">
                  <el-select v-model="form.deptCode" clearable  class="common" >
                    <el-option
                      v-for="item in deptList"
                      :key="item.deptRoomId"
                      :label="item.deptRoomName"
                      :value="item.deptRoomId">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="工号" prop="empCode">
                  <el-input v-model="form.empCode" placeholder="请输入工号" :disabled="id?true:false"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="人员姓名" prop="empName">
                  <el-input v-model="form.empName" placeholder="人员姓名" @blur="getPym"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="拼音码" prop="spell">
                  <el-input v-model="form.spell" :placeholder="pym" disabled />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="证件类型" prop="cardType">
                  <el-select v-model="form.cardType" placeholder="请输入证件类型" class="common">
                    <el-option
                      v-for="dict in cardTypeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>

                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="身份证号" prop="idNumber">
                  <el-input v-model="form.idNumber" placeholder="请输入身份证号" @blur="form.cardType == '01'?idUserInfo():''"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="gender">
                  <el-select v-model="form.gender" placeholder="请选择性别" class="common"  >
                    <el-option
                      v-for="dict in genderOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出生日期" prop="birthday" class="common">
                  <el-date-picker clearable size="small"

                                  v-model="form.birthday"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择出生日期" class="common" >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="婚姻状况" prop="maritalStatus">
                  <el-select v-model="form.maritalStatus" placeholder="请选择婚姻状况" class="common" >
                    <el-option
                      v-for="dict in maritalStatusOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="民族" prop="nationName">
                  <el-select v-model="form.nationName" placeholder="请选择民族" class="common" >
                    <el-option
                      v-for="dict in nationNameOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="政治面貌" prop="politicalName">
                  <el-select v-model="form.politicalName" placeholder="请选择政治面貌" class="common" >
                    <el-option
                      v-for="dict in politicalNameOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系方式" prop="telephone">
                  <el-input v-model="form.telephone" placeholder="请输入联系方式"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="电子邮箱" prop="email">
                  <el-input v-model="form.email" placeholder="请输入电子邮箱"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="户口性质" prop="householdType">
                  <el-select v-model="form.householdType" placeholder="请选择户口性质" class="common">
                    <el-option
                      v-for="dict in householdTypeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="家庭住址" prop="homeAdd">
                  <el-input v-model="form.homeAdd" placeholder="请输入家庭住址"   maxlength="255"
  show-word-limit/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="籍贯" prop="homeTown">
                  <el-input v-model="form.homeTown"  placeholder="请输入籍贯"   maxlength="255"
  show-word-limit/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="人员类别" prop="staffsort">
                  <el-select v-model="form.staffsort" placeholder="请选择人员类别" class="common">
                    <el-option
                      v-for="dict in staffsortOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否在岗" prop="isPost">
                  <el-select v-model="form.isPost" placeholder="请选择人员状态" class="common">
                    <el-option
                      v-for="dict in isPostOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="parseInt(dict.dictValue)"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="人员分类" prop="kindCode">
                  <el-select v-model="form.kindCode" placeholder="请选择人员分类" class="common">
                    <el-option
                      v-for="dict in kindCodeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="岗位名称" prop="postCode">
                  <el-select v-model="form.postCode" placeholder="请选择岗位名称" class="common">
                    <el-option
                      v-for="dict in postNameOptions"
                      :key="dict.postCode"
                      :label="dict.postName"
                      :value="dict.postCode"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="来院时间" prop="inhospitalDate">
                  <el-date-picker clearable size="mini"
                                  v-model="form.inhospitalDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择来院时间" class="common">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职称" prop="titleCode">
                  <el-select v-model="form.titleCode" placeholder="请选择职称" class="common">
                    <el-option
                      v-for="dict in titleCodeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职称级别" prop="titleLevel">
                  <el-select v-model="form.titleLevel" placeholder="请选择职称级别" class="common">
                    <el-option
                      v-for="dict in titleLevelOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职称系列" prop="titleKindCode">
                  <el-select v-model="form.titleKindCode" placeholder="请选择职称系列" class="common">
                    <el-option
                      v-for="dict in titleKindCodeOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="学历" prop="eduCode">
                  <el-select v-model="form.eduCode" placeholder="请选择学历" class="common">
                    <el-option
                      v-for="dict in eduNameOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="学位" prop="degreeCode">
                  <el-select v-model="form.degreeCode" placeholder="请选择学位" class="common">
                    <el-option
                      v-for="dict in academicOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="毕业院校" prop="graduateAcademy">
                  <el-input v-model="form.graduateAcademy" placeholder="请输入毕业院校"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所学专业" prop="studySpecsort">
                  <el-input v-model="form.studySpecsort" placeholder="请输入所学专业"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="行政职务" prop="duty">
                  <el-select v-model="form.duty" placeholder="请选择行政职务" class="common">
                    <el-option
                      v-for="dict in dutyOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="行政职务时间" prop="dutydate">
                  <el-date-picker clearable size="small"
                                  v-model="form.dutydate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择行政职务时间" class="common">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="行政职级" prop="dutyLevel">
                  <el-select v-model="form.dutyLevel" placeholder="请选择行政职级" class="common">
                    <el-option
                      v-for="dict in dutyLevelOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="附件" prop="annex">
                  <el-input v-model="form.annex" placeholder="请输入附件"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>

      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listSysEmp,
  getSysEmp,
  delSysEmp,
  addSysEmp,
  updateSysEmp,
  generateEmpCode,
  exportSysEmp
} from "@/api/basic/sysEmp";

import orgSearch from "@/views/components/orgSearch";
import { treeRoom, getEmpList } from "@/api/until";
import pinyin from "js-pinyin";
//import formItem from "@/views/components/formItem";

export default {
  name: "add",
  components: {
    orgSearch
  },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    proclnsIds: {
      type: [String, Number],
      default: ""
    }
  },
  data() {
    return {
      empCode: "",
      openDialog: this.open,
      play: false,
      activeName: "first",
      title: "新增",
      // 表单参数
      form: {
        compName: null,
        compCode: null
      },
      deptList: [],
      // 表单校验
      rules: {
        duty: [
          { required: true, message: "行政职务不能为空", trigger: "change" }
        ],
        titleCode: [
          { required: true, message: "职称不能为空", trigger: "change" }
        ],
        empCode: [{ required: true, message: "工号不能为空", trigger: "blur" }],
        empName: [
          { required: true, message: "人员姓名不能为空", trigger: "blur" }
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
        birthday: [
          { required: true, message: "出生日期不能为空", trigger: "blur" }
        ],
        idNumber: [
          { required: true, message: "身份证号不能为空", trigger: "blur" },
          { validator: this.validID, trigger: "blur" }
        ],
        homeTown: [
          { required: true, message: "籍贯不能为空", trigger: "blur" }
        ],
        studySpecsort: [
          { required: true, message: "所学专业不能为空", trigger: "blur" }
        ],
        nationName: [
          { required: true, message: "民族不能为空", trigger: "change" }
        ],
        eduName: [
          { required: true, message: "学历不能为空", trigger: "change" }
        ],
        isPost: [
          { required: true, message: "是否在岗不能为空", trigger: "change" }
        ],
        kindCode: [
          { required: true, message: "人员分类不能为空", trigger: "change" }
        ],
        inhospitalDate: [
          { required: true, message: "来院时间不能为空", trigger: "blur" }
        ],
        staffsort: [
          { required: true, message: "人员类别不能为空", trigger: "change" }
        ],
        maritalStatus: [
          { required: true, message: "婚姻状况不能为空", trigger: "change" }
        ],
        degreeCode: [
          { required: true, message: "学位不能为空", trigger: "change" }
        ],
        graduateAcademy: [
          { required: true, message: "毕业院校不能为空", trigger: "blur" }
        ],
        householdType: [
          { required: true, message: "户口性质不能为空", trigger: "change" }
        ],
        homeAdd: [
          { max: 100, message: "长度不能大于100个字符", trigger: "blur" },
          { required: true, message: "家庭住址不能为空", trigger: "blur" }
        ],
        cardType: [
          { required: true, message: "证件类型不能为空", trigger: "change" }
        ],
        compName: [
          { required: true, message: "单位不能为空", trigger: "change" },
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        compCode: [
          { required: true, message: "单位不能为空", trigger: "change" },
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室不能为空", trigger: "change" }
        ],
        postCode: [
          { required: true, message: "岗位名称不能为空", trigger: "change" }
        ],
        eduCode: [
          { required: true, message: "学历不能为空", trigger: "change" }
        ],
        telephone: [
          { required: true, message: "联系方式不能为空", trigger: "change" }
        ]
      },
      sexOptions: [],
      // 证件类型
      cardTypeOptions: [],
      // 性别字典
      genderOptions: [],
      // 婚姻状况字典
      maritalStatusOptions: [],
      // 民族字典
      nationNameOptions: [],
      //政治面貌
      politicalNameOptions: [],
      // 户口性质字典
      householdTypeOptions: [],
      // 人员类别字典
      staffsortOptions: [],
      // 人员状态字典
      isPostOptions: [],
      // 人员分类字典
      kindCodeOptions: [],
      // 岗位名称字典
      postNameOptions: [],
      // 职称字典
      titleCodeOptions: [],
      // 职称级别字典
      titleLevelOptions: [],
      // 职称系列字典
      titleKindCodeOptions: [],
      // 学历字典
      eduNameOptions: [],
      // 学位字典
      academicOptions: [],
      // 行政职务字典
      dutyOptions: [],
      // 行政职级字典
      dutyLevelOptions: [],
      // 卫生专业分类字典
      santspecSortOptions: [],
      // 开户行字典
      bankOptions: [],
      // 人员属性字典
      personsTypeOptions: [],
      // 是否军人字典
      isArmyOptions: [],
      // 军衔级别字典
      soldierRankOptions: [],
      // 技术职务字典
      jobOptions: [],
      // 技术级字典
      techincClassOptions: [],
      // 岗位分类字典
      jobtypeOptions: [],
      // 岗位级别字典
      joblevelOptions: [],
      //职称获得方式字典
      titleGetOptions: [],
      //健康状况
      healthOptions: [],
      //血型字典
      bloodTypeOptions: [],
      // 是否轮转字典
      isInternOptions: [],
      // 是否聘任字典
      ifAppointOptions: [],
      // 留学归国字典
      ifAbroadOptions: [],

      // 港澳台侨胞字典
      overseaschineseOptions: [],
      // 家庭关系字典
      relationOptions: [],
      // 民族字典
      nationOptions: [],
      // 职称名称字典
      titleNameOptions: [],
      // 职称级别字典
      titleGradeCodeOptions: [],
      // 获得方式字典
      acquireWayCodeOptions: [],

      // 原职务字典
      outPostNameOptions: [],
      // 现职务字典
      inPostNameOptions: [],
      // 是否认证字典
      isAttOptions: [],
      pym: "",
      multiple2: true,
      // 家庭成员信息表格数据
      homeinfoList: [],
      // 非多个禁用
      multiple3: true,
      multiple4: true,
      multiple5: true,
      multiple6: true,
      multiple7: true,
      // 工作经历信息表格数据
      resumeinfoList: [],
      // 学历变动表格数据
      degreeschangeList: [],
      // 学位变动表格数据
      academicchangeList: [],
      // 职称变动表格数据
      posttitlechangeList: [],
      // 职务调动表格数据
      deptchangeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empCode: null
      },
      // 用户字典
      userOptions: [],
      // 下级节点办理人
      nextAssignee: []
    };
  },
  watch: {
    "form.compCode": {
      handler(val) {
        this.getTreeDept(val);
      },
      deep: true,
      immediate: true
    },
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.play = true;
          this.id = val;
          this.title = "人员基本信息 / 修改";

          this.getDetailInfo();
        } else {
          // 新增获取工号
          this.generateEmpCode();
          this.id = "";
          this.play = false;
          this.pym = "系统自动生成";
          this.title = "人员基本信息 / 新增";
        }
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    this.initDic();
    let that = this;
    getEmpList(
      that,
      this.$store.state.user.name,
      this.proclnsIds,
      this.userOptions,
      this.btn1,
      this.btn2,
      this.btn3,
      this.btn4,
      this.btn5
    );
  },

  methods: {
    successSubmit() {
      this.$emit("success", false);
    },
    // 获取工号
    generateEmpCode() {
      generateEmpCode().then(res => {
        console.log(res);
        this.form.empCode = res;
        let codeLength = res.length;
      });
    },
    //获取科室列表
    getTreeDept(deptId) {
      let data = {
        deptId: deptId
      };
      this.formDeptList = [];
      treeRoom(data).then(response => {
        if (response.code == 200) {
          this.deptList = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    getDetailInfo() {
      const loading = this.dataLoading()                   
      getSysEmp(this.id).then(response => {
        if (response.code == 200) {
          this.form = response.data;
          this.empCode = response.data.empCode;
          this.queryParams.empCode = this.empCode;
          loading.close()                          
        } else {
          this.msgError(response.msg);
        }
      }).catch(()=>{
        loading.close();            
      })
    },
    handleClick(tab, event) {
      if (tab.label == "人员信息详情") {
        let data = { empCode: this.empCode };
        getEmpDetail(data).then(response => {
          if (response.code == 200) {
            if (response.data == undefined) {
              this.empDetailFlag = false;
            } else {
              this.empDetailFlag = true;
              this.form10 = response.data;
            }
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    initDic() {
      this.getDicts("DE02.01.025.00").then(response => {
        this.nationOptions = response.data;
      });
      this.getDicts("GBT4761.2008").then(response => {
        this.relationOptions = response.data;
      });
      this.getDicts("sys_user_sex").then(response => {
        this.sexOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.overseaschineseOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.ifAbroadOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.ifAppointOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.isInternOptions = response.data;
      });

      this.getDicts("CV04.50.005").then(response => {
        this.bloodTypeOptions = response.data;
      });

      this.getDicts("DE05.10.034.00").then(response => {
        this.healthOptions = response.data;
      });

      this.getDicts("hr_post_classification").then(response => {
        this.titleGetOptions = response.data;
      });

      this.getDicts("hr_staff_post_level").then(response => {
        this.joblevelOptions = response.data;
      });
      this.getDicts("hr_personnel_title_way").then(response => {
        this.jobtypeOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.techincClassOptions = response.data;
      });
      this.getDicts("GBT8561.2001").then(response => {
        this.jobOptions = response.data;
      });
      // 证件类型
      this.getDicts("CV02.01.101").then(response => {
        this.cardTypeOptions = response.data;
      });
      this.getDicts("sys_user_sex").then(response => {
        this.genderOptions = response.data;
      });
      this.getDicts("GBT2261.2.2003").then(response => {
        this.maritalStatusOptions = response.data;
      });
      this.getDicts("DE02.01.025.00").then(response => {
        this.nationNameOptions = response.data;
      });
      this.getDicts("hr_politics_face").then(response => {
        this.politicalNameOptions = response.data;
      });
      this.getDicts("MYD0052").then(response => {
        this.householdTypeOptions = response.data;
      });
      this.getDicts("hr_personnel_category").then(response => {
        this.staffsortOptions = response.data;
      });
      this.getDicts("hr_personnel_state").then(response => {
        this.isPostOptions = response.data;
      });
      this.getDicts("hr_personnel_classification").then(response => {
        this.kindCodeOptions = response.data;
      });
      this.getPostInfo().then(response => {
        this.postNameOptions = response.data;
      });
      this.getDicts("DE02.01.049.00").then(response => {
        this.titleCodeOptions = response.data;
      });
      this.getDicts("hr_personnel_title_level").then(response => {
        this.titleLevelOptions = response.data;
      });
      this.getDicts("hr_personnel_title_series").then(response => {
        this.titleKindCodeOptions = response.data;
      });
      this.getDicts("GBT4658.1984").then(response => {
        this.eduNameOptions = response.data;
      });
      this.getDicts("GBT6864.2003").then(response => {
        this.academicOptions = response.data;
      });
      this.getDicts("hr_personnel_post").then(response => {
        this.dutyOptions = response.data;
      });
      this.getDicts("hr_administration_rank").then(response => {
        this.dutyLevelOptions = response.data;
      });
      this.getDicts("WS218.2002").then(response => {
        this.santspecSortOptions = response.data;
      });
      this.getDicts("hr_bank_deposit").then(response => {
        this.bankOptions = response.data;
      });
      this.getDicts("hr_personnel_attribute").then(response => {
        this.personsTypeOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.isArmyOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.soldierRankOptions = response.data;
      });
    },
    // 职称系类字典翻译
    titleKindCodeFormat(row, column) {
      return this.selectDictLabel(this.titleKindCodeOptions, row.titleKindCode);
    },
    // 学历字典翻译
    eduNameFormat(row, column) {
      return this.selectDictLabel(this.eduNameOptions, row.eduName);
    },
    // 学位字典翻译
    academicFormat(row, column) {
      return this.selectDictLabel(this.academicOptions, row.academic);
    },
    // 性别字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // 家庭关系字典翻译
    relationFormat(row, column) {
      return this.selectDictLabel(this.relationOptions, row.relation);
    },
    // 民族字典翻译
    nationFormat(row, column) {
      return this.selectDictLabel(this.nationOptions, row.nation);
    },
    // 职称名称字典翻译
    titleNameFormat(row, column) {
      return this.selectDictLabel(this.titleNameOptions, row.titleName);
    },
    // 职称级别字典翻译
    titleGradeCodeFormat(row, column) {
      return this.selectDictLabel(
        this.titleGradeCodeOptions,
        row.titleGradeCode
      );
    },
    // 获得方式字典翻译
    acquireWayCodeFormat(row, column) {
      return this.selectDictLabel(
        this.acquireWayCodeOptions,
        row.acquireWayCode
      );
    },
    // 原职务字典翻译
    outPostNameFormat(row, column) {
      return this.selectDictLabel(this.outPostNameOptions, row.outPostName);
    },
    // 现职务字典翻译
    inPostNameFormat(row, column) {
      return this.selectDictLabel(this.inPostNameOptions, row.inPostName);
    },
    // 是否认证字典翻译
    isAttFormat(row, column) {
      return this.selectDictLabel(this.isAttOptions, row.isAtt);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compName: null,
        compCode: null,
        empCode: null,
        empName: null,
        formerName: null,
        gender: null,
        birthday: null,
        idNumber: null,
        homeAdd: null,
        homePost: null,
        photoUrl: null,
        homeTown: null,
        studySpecsort: null,
        bankAccount: null,
        postCode: null,
        titleCode: null,
        addMark: null,
        spell: null,
        isStock: null,
        sysUserId: null,
        empDesc: null,
        nationName: null,
        politicalName: null,
        eduName: null,
        deptCode: null,
        isPost: null,
        kindCode: null,
        isStop: null,
        isSyn: null,
        isIntern: null,
        isArmy: null,
        bonusFactor: null,
        deptType: null,
        inhospitalDate: null,
        job: null,
        jobDate: null,
        staffsort: null,
        beenrolledinDate: null,
        workdate: null,
        duty: null,
        dutydate: null,
        techincClass: null,
        technicClassDate: null,
        civilserviceClass: null,
        civilserviceClassDate: null,
        santspecSort: null,
        rootspecSort: null,
        inputUser: null,
        inputDate: null,
        userDate: null,
        deptGroup: null,
        homeplace: null,
        maritalStatus: null,
        edu1: null,
        graduateAcademy: null,
        rank: null,
        markUser: null,
        userId: null,
        jobTitle: null,
        titleDate: null,
        expert: null,
        fileNo: null,
        gl: null,
        politicalTime: null,
        titleLevel: null,
        language: null,
        languageLevel: null,
        wyzhshshj: null,
        ifReservecadres: null,
        practice: null,
        practiceData: null,
        household: null,
        householdType: null,
        telephone: null,
        email: null,
        health: null,
        overseaschinese: null,
        titleGet: null,
        titleTime: null,
        ifAppoint: null,
        appointDate: null,
        ifAbroad: null,
        dutyLevel: null,
        rzhfsh: null,
        jobtype: null,
        joblevel: null,
        joblevelname: null,
        bank: null,
        bangkCard: null,
        jhcyDate: null,
        jhAge: null,
        jdAge: null,
        inhospitalAge: null,
        jdhospitalAge: null,
        personsType: null,
        ispayment: null,
        staffHeight: null,
        staffWeight: null,
        bloodType: null,
        jobdate: null,
        isbraid: null,
        titleKindCode: null,
        deptName: null,
        postName: null,
        age: null,
        branchCourts: null,
        personsOther: null,
        personsOther1: null,
        staffid: null,
        partyBranch: null,
        source: null,
        socialNumber: null,
        salaryScale: null,
        studyOverDate: null,
        dateOfGradetitle: null,
        dimissionType: null,
        workid: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSysEmp(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.$emit("success");
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addSysEmp(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("新增成功");
                this.$emit("success");
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    idUserInfo() {
      let info = this.getFromIdCard(this.form.idNumber);
      let sexObj = {};
      this.genderOptions.forEach(el => {
        sexObj[el.dictLabel] = el.dictValue;
      });
      this.form.birthday = info.birthday;
      this.form.gender = sexObj[info.sex];
    },
    // 获取拼音码
    getPym() {
      // 全是字母的情况下,拼音码等于所有字母
      if (/^[a-zA-Z]*$/.test(this.form.empName)) {
        this.form.spell = this.form.empName;
        return;
      }
      // 有汉字的情况下,转为首字母
      let fullName = pinyin.getCamelChars(this.form.empName).toUpperCase();
      let pym = "";
      for (var i = 0; i < fullName.length; i++) {
        var c = fullName.charAt(i);
        if (/^[A-Z]+$/.test(c)) {
          pym += c;
        }
      }
      this.form.spell = pym;
    },
    // 取消按钮
    cancel() {
      this.$emit("cancel");
    },
    handleDialogClose() {
      this.$emit("cancel");
    }
  }
};
</script>

<style scoped lang="scss">
.common {
  width: 100%;
}
.mainDialog {
  ::v-deep .el-dialog {
    height: 80%;
    .el-dialog__body {
      height: calc(100% - 44px);
      overflow: auto;
    }
  }
}
</style>
