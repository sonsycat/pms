<template>
  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">预览</el-menu-item>
      <el-menu-item index="2">查看源代码</el-menu-item>
    </el-menu>

    <div v-show="activeIndex==1">
      <div class="app-container" ref="preview">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-row>
            <el-col :span="12">
              <el-form-item label="活动名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="级联查询" prop="casQuery">
                <el-cascader v-model="ruleForm.casQuery"
                             :options="casOptions"
                             @change="handleChange"></el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="活动区域" prop="region">
                <el-select v-model="ruleForm.region" placeholder="请选择活动区域">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="活动时间" required>
                <el-col :span="5">
                  <el-form-item prop="date1">
                    <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1"
                                    style="width: 100%;"></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="5">
                  <el-form-item prop="date2">
                    <el-time-picker placeholder="选择时间" v-model="ruleForm.date2" style="width: 100%;"></el-time-picker>
                  </el-form-item>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label="即时配送" prop="delivery">
                <el-switch v-model="ruleForm.delivery"></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="特殊资源" prop="resource">
                <el-radio-group v-model="ruleForm.resource">
                  <el-radio label="线上品牌商赞助"></el-radio>
                  <el-radio label="线下场地免费"></el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="活动性质" prop="type">
                <el-checkbox-group v-model="ruleForm.type">
                  <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
                  <el-checkbox label="地推活动" name="type"></el-checkbox>
                  <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                  <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="活动形式" prop="desc">
                <el-input type="textarea" v-model="ruleForm.desc"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="文件上传" prop="fileUrl">
                <FileUpload ref="fileUpload" :show-file-list="true" v-model="ruleForm.fileUrl"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="图像上传" prop="imageUrl">
                <ImageUpload ref="imageUpload" v-model="ruleForm.imageUrl"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="富文本" prop="editor">
                <Editor ref="imageUpload" v-model="ruleForm.editor"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </div>
      </div>
    </div>
    <div v-show="activeIndex==2">
      <pre style="background-color: #dadada">{{code}}</pre>
    </div>
  </div>
</template>


<script>
  import FileUpload from '@/components/FileUpload'
  import ImageUpload from '@/components/ImageUpload'
  import Editor from '@/components/Editor'
  import { getCasOptions } from '@/api/demo/componentExample'

  export default {
    name: 'FormComponent',
    components: { FileUpload, ImageUpload, Editor },
    data() {
      return {
        // 导航栏切换index
        activeIndex: '1',
        // 查看源代码code
        code: '',
        ruleForm: {
          name: '',
          region: '',
          casQuery: [],
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: '',
          fileUrl: '',
          imageUrl: '',
          editor: ''
        },
        casOptions: [],
        rules: {
          name: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          region: [
            { required: true, message: '请选择活动区域', trigger: 'change' }
          ],
          casQuery: [
            { required: true, message: '请选择级联查询', trigger: 'change' }
          ],
          date1: [
            { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
          ],
          date2: [
            { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
          ],
          type: [
            { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
          ],
          resource: [
            { required: true, message: '请选择活动资源', trigger: 'change' }
          ],
          desc: [
            { required: true, message: '请填写活动形式', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      // 初始化源代码
      this.initCode()
      // 获取级联数据
      this.getCasOptions()
    },
    methods: {
      getCasOptions() {
        getCasOptions().then(response => {
          this.casOptions = response.data
        })
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!')
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      handleChange(value) {
        console.log(value)
      },
      // 导航切换
      handleSelect(key, keyPath) {
        this.activeIndex = key
      },
      // 初始化源代码
      initCode() {
        this.code =
          '<template>\n' +
          '  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">\n' +
          '    <div class="app-container" ref="preview">\n' +
          '      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">\n' +
          '        <el-row>\n' +
          '          <el-col :span="12">\n' +
          '            <el-form-item label="活动名称" prop="name">\n' +
          '              <el-input v-model="ruleForm.name"></el-input>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="6">\n' +
          '            <el-form-item label="级联查询" prop="casQuery">\n' +
          '              <el-cascader v-model="ruleForm.casQuery"\n' +
          '                           :options="casOptions"\n' +
          '                           @change="handleChange"></el-cascader>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="6">\n' +
          '            <el-form-item label="活动区域" prop="region">\n' +
          '              <el-select v-model="ruleForm.region" placeholder="请选择活动区域">\n' +
          '                <el-option label="区域一" value="shanghai"></el-option>\n' +
          '                <el-option label="区域二" value="beijing"></el-option>\n' +
          '              </el-select>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="12">\n' +
          '            <el-form-item label="活动时间" required>\n' +
          '              <el-col :span="5">\n' +
          '                <el-form-item prop="date1">\n' +
          '                  <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1"\n' +
          '                                  style="width: 100%;"></el-date-picker>\n' +
          '                </el-form-item>\n' +
          '              </el-col>\n' +
          '              <el-col class="line" :span="2">-</el-col>\n' +
          '              <el-col :span="5">\n' +
          '                <el-form-item prop="date2">\n' +
          '                  <el-time-picker placeholder="选择时间" v-model="ruleForm.date2" style="width: 100%;"></el-time-picker>\n' +
          '                </el-form-item>\n' +
          '              </el-col>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="4">\n' +
          '            <el-form-item label="即时配送" prop="delivery">\n' +
          '              <el-switch v-model="ruleForm.delivery"></el-switch>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="8">\n' +
          '            <el-form-item label="特殊资源" prop="resource">\n' +
          '              <el-radio-group v-model="ruleForm.resource">\n' +
          '                <el-radio label="线上品牌商赞助"></el-radio>\n' +
          '                <el-radio label="线下场地免费"></el-radio>\n' +
          '              </el-radio-group>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="12">\n' +
          '            <el-form-item label="活动性质" prop="type">\n' +
          '              <el-checkbox-group v-model="ruleForm.type">\n' +
          '                <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>\n' +
          '                <el-checkbox label="地推活动" name="type"></el-checkbox>\n' +
          '                <el-checkbox label="线下主题活动" name="type"></el-checkbox>\n' +
          '                <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>\n' +
          '              </el-checkbox-group>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="12">\n' +
          '            <el-form-item label="活动形式" prop="desc">\n' +
          '              <el-input type="textarea" v-model="ruleForm.desc"></el-input>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="12">\n' +
          '            <el-form-item label="文件上传" prop="fileUrl">\n' +
          '              <FileUpload ref="fileUpload" :show-file-list="true" v-model="ruleForm.fileUrl"/>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="12">\n' +
          '            <el-form-item label="图像上传" prop="imageUrl">\n' +
          '              <ImageUpload ref="imageUpload" v-model="ruleForm.imageUrl"/>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '          <el-col :span="24">\n' +
          '            <el-form-item label="富文本" prop="editor">\n' +
          '              <Editor ref="imageUpload" v-model="ruleForm.editor"/>\n' +
          '            </el-form-item>\n' +
          '          </el-col>\n' +
          '        </el-row>\n' +
          '      </el-form>\n' +
          '      <div slot="footer" class="dialog-footer" style="text-align: center">\n' +
          '        <el-button type="primary" @click="submitForm(\'ruleForm\')">立即创建</el-button>\n' +
          '        <el-button @click="resetForm(\'ruleForm\')">重置</el-button>\n' +
          '      </div>\n' +
          '    </div>\n' +
          '  </div>\n' +
          '</template>\n' +
          '\n' +
          '\n' +
          '<script>\n' +
          '  import FileUpload from \'@/components/FileUpload\'\n' +
          '  import ImageUpload from \'@/components/ImageUpload\'\n' +
          '  import Editor from \'@/components/Editor\'\n' +
          '  import { getCasOptions } from \'@/api/demo/componentExample\'\n' +
          '\n' +
          '  export default {\n' +
          '    name: \'FormComponent\',\n' +
          '    components: { FileUpload, ImageUpload, Editor },\n' +
          '    data() {\n' +
          '      return {\n' +
          '        ruleForm: {\n' +
          '          name: \'\',\n' +
          '          region: \'\',\n' +
          '          casQuery: [],\n' +
          '          date1: \'\',\n' +
          '          date2: \'\',\n' +
          '          delivery: false,\n' +
          '          type: [],\n' +
          '          resource: \'\',\n' +
          '          desc: \'\',\n' +
          '          fileUrl: \'\',\n' +
          '          imageUrl: \'\',\n' +
          '          editor: \'\'\n' +
          '        },\n' +
          '        casOptions: [],\n' +
          '        rules: {\n' +
          '          name: [\n' +
          '            { required: true, message: \'请输入活动名称\', trigger: \'blur\' },\n' +
          '            { min: 3, max: 5, message: \'长度在 3 到 5 个字符\', trigger: \'blur\' }\n' +
          '          ],\n' +
          '          region: [\n' +
          '            { required: true, message: \'请选择活动区域\', trigger: \'change\' }\n' +
          '          ],\n' +
          '          casQuery: [\n' +
          '            { required: true, message: \'请选择级联查询\', trigger: \'change\' }\n' +
          '          ],\n' +
          '          date1: [\n' +
          '            { type: \'date\', required: true, message: \'请选择日期\', trigger: \'change\' }\n' +
          '          ],\n' +
          '          date2: [\n' +
          '            { type: \'date\', required: true, message: \'请选择时间\', trigger: \'change\' }\n' +
          '          ],\n' +
          '          type: [\n' +
          '            { type: \'array\', required: true, message: \'请至少选择一个活动性质\', trigger: \'change\' }\n' +
          '          ],\n' +
          '          resource: [\n' +
          '            { required: true, message: \'请选择活动资源\', trigger: \'change\' }\n' +
          '          ],\n' +
          '          desc: [\n' +
          '            { required: true, message: \'请填写活动形式\', trigger: \'blur\' }\n' +
          '          ]\n' +
          '        }\n' +
          '      }\n' +
          '    },\n' +
          '    created() {\n' +
          '      // 获取级联数据\n' +
          '      this.getCasOptions()\n' +
          '    },\n' +
          '    methods: {\n' +
          '      getCasOptions() {\n' +
          '        getCasOptions().then(response => {\n' +
          '          this.casOptions = response.data\n' +
          '        })\n' +
          '      },\n' +
          '      submitForm(formName) {\n' +
          '        this.$refs[formName].validate((valid) => {\n' +
          '          if (valid) {\n' +
          '            alert(\'submit!\')\n' +
          '          } else {\n' +
          '            console.log(\'error submit!!\')\n' +
          '            return false\n' +
          '          }\n' +
          '        })\n' +
          '      },\n' +
          '      resetForm(formName) {\n' +
          '        this.$refs[formName].resetFields()\n' +
          '      },\n' +
          '      handleChange(value) {\n' +
          '        console.log(value)\n' +
          '      }\n' +
          '    }\n' +
          '  }\n' +
          '<\/script>\n'
      }
    }
  }
</script>
