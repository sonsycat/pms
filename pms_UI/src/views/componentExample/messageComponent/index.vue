<template>
  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">预览</el-menu-item>
      <el-menu-item index="2">查看源代码</el-menu-item>
    </el-menu>
    <div v-show="activeIndex==1">
      <div class="app-container" style="width: 30%;float: left">
        <el-row>
          <el-col>
            <h4>Message消息提示:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button :plain="true" @click="open1">消息</el-button>
            <el-button :plain="true" @click="open2">成功</el-button>
            <el-button :plain="true" @click="open3">警告</el-button>
            <el-button :plain="true" @click="open4">错误</el-button>
            <el-button :plain="true" @click="openCenter">文字居中</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>MessageBox弹框:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button @click="open5">消息提示</el-button>
            <el-button @click="open6">确认消息</el-button>
            <el-button @click="open7">提交内容</el-button>
            <el-button @click="open8">居中布局</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>MessageBox弹框:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button plain @click="open9">可自动关闭</el-button>
            <el-button plain @click="open10">不会自动关闭</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>MessageBox弹框(带有倾向性):</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button plain @click="open11">成功</el-button>
            <el-button plain @click="open12">警告</el-button>
            <el-button plain @click="open13">消息</el-button>
            <el-button plain @click="open14">错误</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>MessageBox弹框(自定义弹出位置):</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button plain @click="open15">右上角</el-button>
            <el-button plain @click="open16">右下角</el-button>
            <el-button plain @click="open17">左下角</el-button>
            <el-button plain @click="open18">左上角</el-button>
          </el-col>
        </el-row>
      </div>
      <div class="app-container" style="width: 25%;float: left">
        <el-row>
          <el-col>
            <h4>Tooltip 文字提示:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div class="box">
              <div class="top">
                <el-tooltip class="item" effect="dark" content="Top Left 提示文字" placement="top-start">
                  <el-button>上左</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Top Center 提示文字" placement="top">
                  <el-button>上边</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Top Right 提示文字" placement="top-end">
                  <el-button>上右</el-button>
                </el-tooltip>
              </div>
              <div class="left">
                <el-tooltip class="item" effect="dark" content="Left Top 提示文字" placement="left-start">
                  <el-button>左上</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Left Center 提示文字" placement="left">
                  <el-button>左边</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Left Bottom 提示文字" placement="left-end">
                  <el-button>左下</el-button>
                </el-tooltip>
              </div>
              <div class="right">
                <el-tooltip class="item" effect="dark" content="Right Top 提示文字" placement="right-start">
                  <el-button>右上</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Right Center 提示文字" placement="right">
                  <el-button>右边</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Right Bottom 提示文字" placement="right-end">
                  <el-button>右下</el-button>
                </el-tooltip>
              </div>
              <div class="bottom">
                <el-tooltip class="item" effect="dark" content="Bottom Left 提示文字" placement="bottom-start">
                  <el-button>下左</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Bottom Center 提示文字" placement="bottom">
                  <el-button>下边</el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="Bottom Right 提示文字" placement="bottom-end">
                  <el-button>下右</el-button>
                </el-tooltip>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>Popover 弹出框:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-popover placement="top-start" title="标题" width="200" trigger="hover"
                        content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
              <el-button slot="reference">hover 激活</el-button>
            </el-popover>
            <el-popover placement="bottom" title="标题" width="200" trigger="click"
                        content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
              <el-button slot="reference">click 激活</el-button>
            </el-popover>
            <el-popover ref="popover" placement="right" title="标题" width="200" trigger="focus"
                        content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">
            </el-popover>
            <el-button v-popover:popover>focus 激活</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>PopConfirm 气泡确认框:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-popconfirm title="这是一段内容确定删除吗？">
              <el-button slot="reference">基础用法</el-button>
            </el-popconfirm>
            <el-popconfirm confirm-button-text='好的' cancel-button-text='不用了' icon="el-icon-info" icon-color="red"
                           title="这是一段内容确定删除吗？">
              <el-button slot="reference">自定义</el-button>
            </el-popconfirm>
          </el-col>
        </el-row>
      </div>
      <div class="app-container" style="width: 45%;float: left">
        <el-row>
          <el-col>
            <h4>Button 按钮-基础用法:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-row>
              <el-button>默认按钮</el-button>
              <el-button type="primary">主要按钮</el-button>
              <el-button type="success">成功按钮</el-button>
              <el-button type="info">信息按钮</el-button>
              <el-button type="warning">警告按钮</el-button>
              <el-button type="danger">危险按钮</el-button>
            </el-row>
            <el-row>
              <el-button plain>朴素按钮</el-button>
              <el-button type="primary" plain>主要按钮</el-button>
              <el-button type="success" plain>成功按钮</el-button>
              <el-button type="info" plain>信息按钮</el-button>
              <el-button type="warning" plain>警告按钮</el-button>
              <el-button type="danger" plain>危险按钮</el-button>
            </el-row>
            <el-row>
              <el-button round>圆角按钮</el-button>
              <el-button type="primary" round>主要按钮</el-button>
              <el-button type="success" round>成功按钮</el-button>
              <el-button type="info" round>信息按钮</el-button>
              <el-button type="warning" round>警告按钮</el-button>
              <el-button type="danger" round>危险按钮</el-button>
            </el-row>
            <el-row>
              <el-button icon="el-icon-search" circle></el-button>
              <el-button type="primary" icon="el-icon-edit" circle></el-button>
              <el-button type="success" icon="el-icon-check" circle></el-button>
              <el-button type="info" icon="el-icon-message" circle></el-button>
              <el-button type="warning" icon="el-icon-star-off" circle></el-button>
              <el-button type="danger" icon="el-icon-delete" circle></el-button>
            </el-row>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>Button 按钮-禁用状态:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-row>
              <el-button disabled>默认按钮</el-button>
              <el-button type="primary" disabled>主要按钮</el-button>
              <el-button type="success" disabled>成功按钮</el-button>
              <el-button type="info" disabled>信息按钮</el-button>
              <el-button type="warning" disabled>警告按钮</el-button>
              <el-button type="danger" disabled>危险按钮</el-button>
            </el-row>
            <el-row>
              <el-button plain disabled>朴素按钮</el-button>
              <el-button type="primary" plain disabled>主要按钮</el-button>
              <el-button type="success" plain disabled>成功按钮</el-button>
              <el-button type="info" plain disabled>信息按钮</el-button>
              <el-button type="warning" plain disabled>警告按钮</el-button>
              <el-button type="danger" plain disabled>危险按钮</el-button>
            </el-row>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>Button 按钮-图标按钮:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button type="primary" icon="el-icon-edit"></el-button>
            <el-button type="primary" icon="el-icon-share"></el-button>
            <el-button type="primary" icon="el-icon-delete"></el-button>
            <el-button type="primary" icon="el-icon-search">搜索</el-button>
            <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <h4>Button 按钮-按钮组:</h4>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-button-group>
              <el-button type="primary" icon="el-icon-arrow-left">上一页</el-button>
              <el-button type="primary">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
            </el-button-group>
            <el-button-group>
              <el-button type="primary" icon="el-icon-edit"></el-button>
              <el-button type="primary" icon="el-icon-share"></el-button>
              <el-button type="primary" icon="el-icon-delete"></el-button>
            </el-button-group>
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-show="activeIndex==2">
      <pre style="background-color: #dadada">{{code}}</pre>
    </div>
  </div>
</template>


<script>

  export default {
    name: 'MessageComponent',
    components: {},
    data() {
      return {
        // 导航栏切换index
        activeIndex: '1',
        // 查看源代码code
        code: ''
      }
    },
    created() {
      // 初始化源代码
      this.initCode()
    },
    methods: {
      open1() {
        this.$message('这是一条消息提示')
      },
      open2() {
        this.$message({
          message: '恭喜你，这是一条成功消息',
          type: 'success'
        })
      },
      open3() {
        this.$message({
          message: '警告，这是一条警告消息',
          type: 'warning'
        })
      },
      open4() {
        this.$message.error('错误，这是一条错误消息')
      },
      openCenter() {
        this.$message({
          message: '居中的文字',
          center: true
        })
      },
      open5() {
        this.$alert('这是一段内容', '标题名称', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info',
              message: `action: ${action}`
            })
          }
        })
      },
      open6() {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      open7() {
        this.$prompt('请输入邮箱', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
          inputErrorMessage: '邮箱格式不正确'
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '你的邮箱是: ' + value
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          })
        })
      },
      open8() {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center: true
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      open9() {
        const h = this.$createElement

        this.$notify({
          title: '标题名称',
          message: h('i', { style: 'color: teal' }, '这是一条会自动关闭的消息')
        })
      },
      open10() {
        this.$notify({
          title: '提示',
          message: '这是一条不会自动关闭的消息',
          duration: 0
        })
      },
      open11() {
        this.$notify({
          title: '成功',
          message: '这是一条成功的提示消息',
          type: 'success'
        })
      },
      open12() {
        this.$notify({
          title: '警告',
          message: '这是一条警告的提示消息',
          type: 'warning'
        })
      },
      open13() {
        this.$notify.info({
          title: '消息',
          message: '这是一条消息的提示消息'
        })
      },
      open14() {
        this.$notify.error({
          title: '错误',
          message: '这是一条错误的提示消息'
        })
      },
      open15() {
        this.$notify({
          title: '自定义位置',
          message: '右上角弹出的消息'
        })
      },
      open16() {
        this.$notify({
          title: '自定义位置',
          message: '右下角弹出的消息',
          position: 'bottom-right'
        })
      },
      open17() {
        this.$notify({
          title: '自定义位置',
          message: '左下角弹出的消息',
          position: 'bottom-left'
        })
      },
      open18() {
        this.$notify({
          title: '自定义位置',
          message: '左上角弹出的消息',
          position: 'top-left'
        })
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
          '    <div class="app-container" style="width: 30%;float: left">\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Message消息提示:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button :plain="true" @click="open1">消息</el-button>\n' +
          '          <el-button :plain="true" @click="open2">成功</el-button>\n' +
          '          <el-button :plain="true" @click="open3">警告</el-button>\n' +
          '          <el-button :plain="true" @click="open4">错误</el-button>\n' +
          '          <el-button :plain="true" @click="openCenter">文字居中</el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>MessageBox弹框:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button @click="open5">消息提示</el-button>\n' +
          '          <el-button @click="open6">确认消息</el-button>\n' +
          '          <el-button @click="open7">提交内容</el-button>\n' +
          '          <el-button @click="open8">居中布局</el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>MessageBox弹框:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button plain @click="open9">可自动关闭</el-button>\n' +
          '          <el-button plain @click="open10">不会自动关闭</el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>MessageBox弹框(带有倾向性):</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button plain @click="open11">成功</el-button>\n' +
          '          <el-button plain @click="open12">警告</el-button>\n' +
          '          <el-button plain @click="open13">消息</el-button>\n' +
          '          <el-button plain @click="open14">错误</el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>MessageBox弹框(自定义弹出位置):</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button plain @click="open15">右上角</el-button>\n' +
          '          <el-button plain @click="open16">右下角</el-button>\n' +
          '          <el-button plain @click="open17">左下角</el-button>\n' +
          '          <el-button plain @click="open18">左上角</el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '    </div>\n' +
          '    <div class="app-container" style="width: 25%;float: left">\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Tooltip 文字提示:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <div class="box">\n' +
          '            <div class="top">\n' +
          '              <el-tooltip class="item" effect="dark" content="Top Left 提示文字" placement="top-start">\n' +
          '                <el-button>上左</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Top Center 提示文字" placement="top">\n' +
          '                <el-button>上边</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Top Right 提示文字" placement="top-end">\n' +
          '                <el-button>上右</el-button>\n' +
          '              </el-tooltip>\n' +
          '            </div>\n' +
          '            <div class="left">\n' +
          '              <el-tooltip class="item" effect="dark" content="Left Top 提示文字" placement="left-start">\n' +
          '                <el-button>左上</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Left Center 提示文字" placement="left">\n' +
          '                <el-button>左边</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Left Bottom 提示文字" placement="left-end">\n' +
          '                <el-button>左下</el-button>\n' +
          '              </el-tooltip>\n' +
          '            </div>\n' +
          '            <div class="right">\n' +
          '              <el-tooltip class="item" effect="dark" content="Right Top 提示文字" placement="right-start">\n' +
          '                <el-button>右上</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Right Center 提示文字" placement="right">\n' +
          '                <el-button>右边</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Right Bottom 提示文字" placement="right-end">\n' +
          '                <el-button>右下</el-button>\n' +
          '              </el-tooltip>\n' +
          '            </div>\n' +
          '            <div class="bottom">\n' +
          '              <el-tooltip class="item" effect="dark" content="Bottom Left 提示文字" placement="bottom-start">\n' +
          '                <el-button>下左</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Bottom Center 提示文字" placement="bottom">\n' +
          '                <el-button>下边</el-button>\n' +
          '              </el-tooltip>\n' +
          '              <el-tooltip class="item" effect="dark" content="Bottom Right 提示文字" placement="bottom-end">\n' +
          '                <el-button>下右</el-button>\n' +
          '              </el-tooltip>\n' +
          '            </div>\n' +
          '          </div>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Popover 弹出框:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-popover placement="top-start" title="标题" width="200" trigger="hover"\n' +
          '                      content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">\n' +
          '            <el-button slot="reference">hover 激活</el-button>\n' +
          '          </el-popover>\n' +
          '          <el-popover placement="bottom" title="标题" width="200" trigger="click"\n' +
          '                      content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">\n' +
          '            <el-button slot="reference">click 激活</el-button>\n' +
          '          </el-popover>\n' +
          '          <el-popover ref="popover" placement="right" title="标题" width="200" trigger="focus"\n' +
          '                      content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">\n' +
          '          </el-popover>\n' +
          '          <el-button v-popover:popover>focus 激活</el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>PopConfirm 气泡确认框:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-popconfirm title="这是一段内容确定删除吗？">\n' +
          '            <el-button slot="reference">基础用法</el-button>\n' +
          '          </el-popconfirm>\n' +
          '          <el-popconfirm confirm-button-text=\'好的\' cancel-button-text=\'不用了\' icon="el-icon-info" icon-color="red"\n' +
          '                         title="这是一段内容确定删除吗？">\n' +
          '            <el-button slot="reference">自定义</el-button>\n' +
          '          </el-popconfirm>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '    </div>\n' +
          '    <div class="app-container" style="width: 45%;float: left">\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Button 按钮-基础用法:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-row>\n' +
          '            <el-button>默认按钮</el-button>\n' +
          '            <el-button type="primary">主要按钮</el-button>\n' +
          '            <el-button type="success">成功按钮</el-button>\n' +
          '            <el-button type="info">信息按钮</el-button>\n' +
          '            <el-button type="warning">警告按钮</el-button>\n' +
          '            <el-button type="danger">危险按钮</el-button>\n' +
          '          </el-row>\n' +
          '          <el-row>\n' +
          '            <el-button plain>朴素按钮</el-button>\n' +
          '            <el-button type="primary" plain>主要按钮</el-button>\n' +
          '            <el-button type="success" plain>成功按钮</el-button>\n' +
          '            <el-button type="info" plain>信息按钮</el-button>\n' +
          '            <el-button type="warning" plain>警告按钮</el-button>\n' +
          '            <el-button type="danger" plain>危险按钮</el-button>\n' +
          '          </el-row>\n' +
          '          <el-row>\n' +
          '            <el-button round>圆角按钮</el-button>\n' +
          '            <el-button type="primary" round>主要按钮</el-button>\n' +
          '            <el-button type="success" round>成功按钮</el-button>\n' +
          '            <el-button type="info" round>信息按钮</el-button>\n' +
          '            <el-button type="warning" round>警告按钮</el-button>\n' +
          '            <el-button type="danger" round>危险按钮</el-button>\n' +
          '          </el-row>\n' +
          '          <el-row>\n' +
          '            <el-button icon="el-icon-search" circle></el-button>\n' +
          '            <el-button type="primary" icon="el-icon-edit" circle></el-button>\n' +
          '            <el-button type="success" icon="el-icon-check" circle></el-button>\n' +
          '            <el-button type="info" icon="el-icon-message" circle></el-button>\n' +
          '            <el-button type="warning" icon="el-icon-star-off" circle></el-button>\n' +
          '            <el-button type="danger" icon="el-icon-delete" circle></el-button>\n' +
          '          </el-row>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Button 按钮-禁用状态:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-row>\n' +
          '            <el-button disabled>默认按钮</el-button>\n' +
          '            <el-button type="primary" disabled>主要按钮</el-button>\n' +
          '            <el-button type="success" disabled>成功按钮</el-button>\n' +
          '            <el-button type="info" disabled>信息按钮</el-button>\n' +
          '            <el-button type="warning" disabled>警告按钮</el-button>\n' +
          '            <el-button type="danger" disabled>危险按钮</el-button>\n' +
          '          </el-row>\n' +
          '          <el-row>\n' +
          '            <el-button plain disabled>朴素按钮</el-button>\n' +
          '            <el-button type="primary" plain disabled>主要按钮</el-button>\n' +
          '            <el-button type="success" plain disabled>成功按钮</el-button>\n' +
          '            <el-button type="info" plain disabled>信息按钮</el-button>\n' +
          '            <el-button type="warning" plain disabled>警告按钮</el-button>\n' +
          '            <el-button type="danger" plain disabled>危险按钮</el-button>\n' +
          '          </el-row>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Button 按钮-图标按钮:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button type="primary" icon="el-icon-edit"></el-button>\n' +
          '          <el-button type="primary" icon="el-icon-share"></el-button>\n' +
          '          <el-button type="primary" icon="el-icon-delete"></el-button>\n' +
          '          <el-button type="primary" icon="el-icon-search">搜索</el-button>\n' +
          '          <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <h4>Button 按钮-按钮组:</h4>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '      <el-row>\n' +
          '        <el-col>\n' +
          '          <el-button-group>\n' +
          '            <el-button type="primary" icon="el-icon-arrow-left">上一页</el-button>\n' +
          '            <el-button type="primary">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>\n' +
          '          </el-button-group>\n' +
          '          <el-button-group>\n' +
          '            <el-button type="primary" icon="el-icon-edit"></el-button>\n' +
          '            <el-button type="primary" icon="el-icon-share"></el-button>\n' +
          '            <el-button type="primary" icon="el-icon-delete"></el-button>\n' +
          '          </el-button-group>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '    </div>\n' +
          '  </div>\n' +
          '</template>\n' +
          '\n' +
          '\n' +
          '<script>\n' +
          '\n' +
          '  export default {\n' +
          '    name: \'MessageComponent\',\n' +
          '    components: {},\n' +
          '    data() {\n' +
          '      return {\n' +
          '\t  }\n' +
          '    },\n' +
          '    created() {\n' +
          '\t},\n' +
          '    methods: {\n' +
          '      open1() {\n' +
          '        this.$message(\'这是一条消息提示\')\n' +
          '      },\n' +
          '      open2() {\n' +
          '        this.$message({\n' +
          '          message: \'恭喜你，这是一条成功消息\',\n' +
          '          type: \'success\'\n' +
          '        })\n' +
          '      },\n' +
          '      open3() {\n' +
          '        this.$message({\n' +
          '          message: \'警告，这是一条警告消息\',\n' +
          '          type: \'warning\'\n' +
          '        })\n' +
          '      },\n' +
          '      open4() {\n' +
          '        this.$message.error(\'错误，这是一条错误消息\')\n' +
          '      },\n' +
          '      openCenter() {\n' +
          '        this.$message({\n' +
          '          message: \'居中的文字\',\n' +
          '          center: true\n' +
          '        })\n' +
          '      },\n' +
          '      open5() {\n' +
          '        this.$alert(\'这是一段内容\', \'标题名称\', {\n' +
          '          confirmButtonText: \'确定\',\n' +
          '          callback: action => {\n' +
          '            this.$message({\n' +
          '              type: \'info\',\n' +
          '              message: `action: ${action}`\n' +
          '            })\n' +
          '          }\n' +
          '        })\n' +
          '      },\n' +
          '      open6() {\n' +
          '        this.$confirm(\'此操作将永久删除该文件, 是否继续?\', \'提示\', {\n' +
          '          confirmButtonText: \'确定\',\n' +
          '          cancelButtonText: \'取消\',\n' +
          '          type: \'warning\'\n' +
          '        }).then(() => {\n' +
          '          this.$message({\n' +
          '            type: \'success\',\n' +
          '            message: \'删除成功!\'\n' +
          '          })\n' +
          '        }).catch(() => {\n' +
          '          this.$message({\n' +
          '            type: \'info\',\n' +
          '            message: \'已取消删除\'\n' +
          '          })\n' +
          '        })\n' +
          '      },\n' +
          '      open7() {\n' +
          '        this.$prompt(\'请输入邮箱\', \'提示\', {\n' +
          '          confirmButtonText: \'确定\',\n' +
          '          cancelButtonText: \'取消\',\n' +
          '          inputPattern: /[\\w!#$%&\'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&\'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?/,\n' +
          '          inputErrorMessage: \'邮箱格式不正确\'\n' +
          '        }).then(({ value }) => {\n' +
          '          this.$message({\n' +
          '            type: \'success\',\n' +
          '            message: \'你的邮箱是: \' + value\n' +
          '          })\n' +
          '        }).catch(() => {\n' +
          '          this.$message({\n' +
          '            type: \'info\',\n' +
          '            message: \'取消输入\'\n' +
          '          })\n' +
          '        })\n' +
          '      },\n' +
          '      open8() {\n' +
          '        this.$confirm(\'此操作将永久删除该文件, 是否继续?\', \'提示\', {\n' +
          '          confirmButtonText: \'确定\',\n' +
          '          cancelButtonText: \'取消\',\n' +
          '          type: \'warning\',\n' +
          '          center: true\n' +
          '        }).then(() => {\n' +
          '          this.$message({\n' +
          '            type: \'success\',\n' +
          '            message: \'删除成功!\'\n' +
          '          })\n' +
          '        }).catch(() => {\n' +
          '          this.$message({\n' +
          '            type: \'info\',\n' +
          '            message: \'已取消删除\'\n' +
          '          })\n' +
          '        })\n' +
          '      },\n' +
          '      open9() {\n' +
          '        const h = this.$createElement\n' +
          '\n' +
          '        this.$notify({\n' +
          '          title: \'标题名称\',\n' +
          '          message: h(\'i\', { style: \'color: teal\' }, \'这是一条会自动关闭的消息\')\n' +
          '        })\n' +
          '      },\n' +
          '      open10() {\n' +
          '        this.$notify({\n' +
          '          title: \'提示\',\n' +
          '          message: \'这是一条不会自动关闭的消息\',\n' +
          '          duration: 0\n' +
          '        })\n' +
          '      },\n' +
          '      open11() {\n' +
          '        this.$notify({\n' +
          '          title: \'成功\',\n' +
          '          message: \'这是一条成功的提示消息\',\n' +
          '          type: \'success\'\n' +
          '        })\n' +
          '      },\n' +
          '      open12() {\n' +
          '        this.$notify({\n' +
          '          title: \'警告\',\n' +
          '          message: \'这是一条警告的提示消息\',\n' +
          '          type: \'warning\'\n' +
          '        })\n' +
          '      },\n' +
          '      open13() {\n' +
          '        this.$notify.info({\n' +
          '          title: \'消息\',\n' +
          '          message: \'这是一条消息的提示消息\'\n' +
          '        })\n' +
          '      },\n' +
          '      open14() {\n' +
          '        this.$notify.error({\n' +
          '          title: \'错误\',\n' +
          '          message: \'这是一条错误的提示消息\'\n' +
          '        })\n' +
          '      },\n' +
          '      open15() {\n' +
          '        this.$notify({\n' +
          '          title: \'自定义位置\',\n' +
          '          message: \'右上角弹出的消息\'\n' +
          '        })\n' +
          '      },\n' +
          '      open16() {\n' +
          '        this.$notify({\n' +
          '          title: \'自定义位置\',\n' +
          '          message: \'右下角弹出的消息\',\n' +
          '          position: \'bottom-right\'\n' +
          '        })\n' +
          '      },\n' +
          '      open17() {\n' +
          '        this.$notify({\n' +
          '          title: \'自定义位置\',\n' +
          '          message: \'左下角弹出的消息\',\n' +
          '          position: \'bottom-left\'\n' +
          '        })\n' +
          '      },\n' +
          '      open18() {\n' +
          '        this.$notify({\n' +
          '          title: \'自定义位置\',\n' +
          '          message: \'左上角弹出的消息\',\n' +
          '          position: \'top-left\'\n' +
          '        })\n' +
          '      }\n' +
          '    }\n' +
          '  }\n' +
          '<\/script>\n' +
          '<style>\n' +
          '  .box {\n' +
          '    width: 400px;\n' +
          '    .top {\n' +
          '      text-align: center;\n' +
          '    }\n' +
          '    .left {\n' +
          '      float: left;\n' +
          '      width: 60px;\n' +
          '    }\n' +
          '    .right {\n' +
          '      float: right;\n' +
          '      width: 60px;\n' +
          '    }\n' +
          '    .bottom {\n' +
          '      clear: both;\n' +
          '      text-align: center;\n' +
          '    }\n' +
          '    .item {\n' +
          '      margin: 4px;\n' +
          '    }\n' +
          '    .left .el-tooltip__popper,\n' +
          '    .right .el-tooltip__popper {\n' +
          '      padding: 8px 10px;\n' +
          '    }\n' +
          '  }\n' +
          '</style>'
      }
    }
  }
</script>

<style>
  .box {
    width: 400px;
    .top {
      text-align: center;
    }
    .left {
      float: left;
      width: 60px;
    }
    .right {
      float: right;
      width: 60px;
    }
    .bottom {
      clear: both;
      text-align: center;
    }
    .item {
      margin: 4px;
    }
    .left .el-tooltip__popper,
    .right .el-tooltip__popper {
      padding: 8px 10px;
    }
  }
</style>
