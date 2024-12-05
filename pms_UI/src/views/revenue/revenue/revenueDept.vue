<template>
    <!-- 科室设置 -->
  <el-dialog custom-class="el-button--primary" :title="title" :close-on-click-modal="false" @close="handleDialogClose" :visible.sync="openDialog" width="50%" append-to-body>
      <el-form ref="queryForm" :model="queryParams" :rules="rules" label-width="80px">
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item label="项目编码" prop="revenueCode">
              <el-input v-model="queryParams.revenueCode" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="项目名称" prop="revenueName">
              <el-input v-model="queryParams.revenueName" placeholder="请输入项目名称"  maxlength="100" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-tabs v-model="activeName"  @tab-click="handleClick" >
                <el-tab-pane label="门诊" name="outPatient">
                    <el-col :span="24">
                        <div class="hello">
                            <div class="transfer">
                                <div class="transfer_left">
                                    <div class="transfer_left_top">
                                        <p>
                                            <label for="">待选科室</label>
                                            <span>{{num(selectionRowoutpatientLeft)}}/{{form.outpatientServiceLeft.length}}</span>
                                        </p>
                                        <el-select v-model="queryParams.deptCode1" @change="changeDept" @click="changeDept" clearable automatic-dropdown="" :filterable="true" :remote="true" placeholder="请选择科室" size="small">
                                            <el-option
                                            v-for="item in deptDatas"
                                            :key="item.dictValue"
                                            :label="item.dictLabel"
                                            :value="item.dictLabel"
                                            ></el-option>
                                        </el-select>
                                    </div>
                                    <div class="checkbox_left">
                                        <el-form  ref="outPatientLeft" :model="form">
                                            <el-table ref="multipleTable" height="410" :style="{width: '100%'}" :show-overflow-tooltip="true" tooltip-effect="dark" stripe v-loading="loading" :data="form.outpatientServiceLeft" @selection-change="handleSelectionOutpatientChange" @row-click="rowClick">
                                                <el-table-column type="selection" width="55" align="center" fixed />
                                                <el-table-column label="科室名称" align="center" prop="dictLabel" />
                                            </el-table>
                                        </el-form>
                                    </div>
                                </div>
                                <div class="transfer_main">
                                    <el-button-group>
                                        <el-button type="primary" size="small" icon="el-icon-arrow-right" @click="outpatientToright" :disabled="patToRightBool"></el-button>
                                        <el-button type="primary"  size="small" @click="outpatientToleft" icon="el-icon-arrow-left" :disabled="patToLeftBool"></el-button>
                                    </el-button-group>
                                </div>
                                <div class="transfer_right">
                                    <div class="transfer_right_top">
                                        <p>
                                            <label for="">已选科室</label> 
                                            <span>{{num1(selectionRowOutpatientRight)}}/{{form.outpatientServiceRight.length}}</span>
                                        </p>                        
                                    </div>
                                    <div class="checkbox_left">
                                        <el-form  ref="outPatientRight" :model="form">
                                            <el-table ref="multipleTableRight" v-loading="loading1" :style="{width: '100%'}" height="450" tooltip-effect="dark" stripe :data="form.outpatientServiceRight" @selection-change="handleSelectionChangeRight" @row-click="rowClickPatRight">
                                                <el-table-column type="selection" width="55" align="center" fixed />
                                                <el-table-column label="科室名称" align="center" prop="dictLabel" />
                                                <el-table-column label="分摊比例" align="center" prop="ratio">
                                                    <template slot-scope="scope">
                                                        <el-input-number v-model="scope.row.ratio" :min="0" :max="100" :precision="2" />
                                                    </template>
                                                </el-table-column>
                                            </el-table>
                                        </el-form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </el-col>
                </el-tab-pane>
                <!-- 住院 -->
                <el-tab-pane label="住院" name="hospital">
                    <el-col :span="24">
                        <div class="hello">
                            <div class="transfer">
                                <div class="transfer_left">
                                    <div class="transfer_left_top">
                                        <p>
                                            <label for="">待选科室</label>
                                            <span>{{num2(selectionRowhospitalLeft)}}/{{form.hospitalServiceLeft.length}}</span>
                                        </p>
                                        <el-select v-model="queryParams.deptCode2" @change="changeDept" clearable automatic-dropdown="" :filterable="true" :remote="true" placeholder="请选择科室" size="small">
                                            <el-option
                                            v-for="item in deptDatasHos"
                                            :key="item.dictValue"
                                            :label="item.dictLabel"
                                            :value="item.dictLabel"
                                            ></el-option>
                                        </el-select>
                                    </div>
                                    <div class="checkbox_left">
                                        <el-table ref="multipleTableHos" height="410" :style="{width: '100%'}" :show-overflow-tooltip="true" tooltip-effect="dark" stripe v-loading="loading2" :data="form.hospitalServiceLeft"  @selection-change="handleSelectionHospitalChange" @row-click="rowClickHos">
                                            <el-table-column type="selection" width="55" align="center" fixed />
                                            <el-table-column label="科室名称" align="center" prop="dictLabel" />
                                        </el-table>
                                    </div>
                                </div>
                                <div class="transfer_main">
                                    <el-button-group>
                                        <el-button type="primary" size="small" icon="el-icon-arrow-right" @click="hospitalToright" :disabled="hosToRightBool"></el-button>
                                        <el-button type="primary"  size="small" @click="hospitalToLeft" icon="el-icon-arrow-left" :disabled="hosToLeftBool"></el-button>
                                    </el-button-group>
                                </div>
                                <div class="transfer_right">
                                    <div class="transfer_right_top">
                                        <p>
                                            <label for="">已选科室</label> 
                                            <span>{{num3(selectionRowhospitalRight)}}/{{form.hospitalServiceRight.length}}</span>
                                        </p>                        
                                    </div>
                                    <div class="checkbox_left">
                                        <el-table v-loading="loading3" ref="multipleTableRightHos" :style="{width: '100%'}" height="450" tooltip-effect="dark" stripe :data="form.hospitalServiceRight" @selection-change="handleSelectionHospitalChangeRight" @row-click="rowClickHosRight">
                                            <el-table-column type="selection" width="55" align="center" fixed />
                                            <el-table-column label="科室名称" align="center" prop="dictLabel" />
                                            <el-table-column label="分摊比例" align="center" prop="ratio">
                                                <template slot-scope="scope">
                                                    <el-input-number v-model="scope.row.ratio" :min="0" :max="100" :precision="2" />
                                                </template>
                                            </el-table-column>
                                        </el-table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </el-col>
                </el-tab-pane>
            </el-tabs>
          </el-col>                
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormDept()" >保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>

import { getRevenueDept,getDeptList,addDeptList } from "@/api/revenue/revenue";


export default {
    name: 'revenueDept',
    props:{
        // 传参为form表单 方便数据回显渲染
        queryParams: {
            type: [Object, Array]
        },
        open: {
            type: Boolean,
            default: false
        },
        id: {
            type: [String, Number],
            default: ""
        },
    },
    watch:{
         open: {
            handler(val) {
                this.openDialog = val;
            },
            deep: true
        }
    },
    data() {
        return {
            title:'收入核算方案-第三方科室',        
            openDialog: this.open,  
            loading:false,
            loading1:false,
            loading2:false,
            loading3:false,
            clickCount:0,
            activeName: "outPatient", 
            patToRightBool:true,
            patToLeftBool:true,
            hosToRightBool:true,
            hosToLeftBool:true,
            form:{
                outpatientServiceLeft:[],//门诊左侧数据
                outpatientServiceRight:[],//门诊右侧数据
                hospitalServiceLeft:[],//住院左侧数据
                hospitalServiceRight:[],//住院右侧数据
            },        
            rules:{},
            deptDatas:[],//门诊科室列表数据
            deptDatasLength:0,//门诊科室列表数据用于比较全选数据
            changeDeptList1:[],
            changeDeptList:[],
            deptDatasHos:[],//门诊科室列表数据
            changeHosDeptList:[],
            changeHosDeptList1:[],
            outpatientRightLength:[],
            hospitalRightLength:[],
            selectionRowoutpatientLeft:[],//门诊左侧复选框数据
            selectionRowOutpatientRight:[],//门诊右侧复选框数据
            selectionRowhospitalLeft:[],//住院左侧复选框数据
            selectionRowhospitalRight:[],//住院右侧复选框数据
        };
    },
    computed:{
        num(){//计算属性传递参数，根据传入的数组返回符合条件的长度
		  return data =>  this.form.outpatientServiceLeft&&this.selectionRowoutpatientLeft.length
        },
        num1(){//计算属性传递参数，根据传入的数组返回符合条件的长度
            return data =>  this.form.hospitalServiceLeft&&this.selectionRowOutpatientRight.length
        },
        num2(){//计算属性传递参数，根据传入的数组返回符合条件的长度
            return data =>  this.form.hospitalServiceLeft&&this.selectionRowhospitalLeft.length
        },
        num3(){//计算属性传递参数，根据传入的数组返回符合条件的长度
            return data =>  this.form.hospitalServiceLeft&&this.selectionRowhospitalRight.length
        }
    },
    created(){
         this.getDetail(1)        
         this.getTreeDept({ compCode: this.$store.state.user.accountInfo.deptId ,clinicType:1})
    },
    mounted() {
    },
    methods: {
        //科室查询下拉框事件
        changeDept(e){
            if(this.activeName=='outPatient'){
                let data
                if(e){
                    data = { compCode: this.$store.state.user.accountInfo.deptId ,clinicType:1,deptName:e};
                }else{
                    data = { compCode: this.$store.state.user.accountInfo.deptId ,clinicType:1};      
                }
                this.getTreeDept(data)
            }
            if(this.activeName=='hospital'){
               let data
                if(e){
                    data = { compCode: this.$store.state.user.accountInfo.deptId ,clinicType:2,deptName:e};
                }else{
                    data = { compCode: this.$store.state.user.accountInfo.deptId ,clinicType:2};      
                } 
                this.getTreeDept(data)                       
            }
        },
        //tab切换事件
       async handleClick(tab){
            if(tab.label=="住院"){
                this.changeHosDeptList=[]
                this.changeHosDeptList1=[]
                // this.form.hospitalServiceLeft=[]                
                this.activeName='hospital'
                if(this.clickCount==0){
                 await this.getDetail(2)  
                }
                this.clickCount+=1
                await this.getTreeDept({ compCode: this.$store.state.user.accountInfo.deptId ,clinicType:2})
            }
            if(tab.label=='门诊'){
                // this.form.outpatientServiceLeft=[]
                this.changeDeptList1=[]
                this.changeDeptList=[]
                this.activeName='outPatient'   
               await this.getTreeDept({ compCode: this.$store.state.user.accountInfo.deptId ,clinicType:1}) 
            }
        },
        //获取门诊及住院右侧列表数据
        async getDetail(type){
            let params={
                compCode:this.queryParams.compCode,
                revenueCode:this.queryParams.revenueCode,
                deptType:type
            }
            if(type==1){
                this.loading1=true
                let arr=[]
                await getRevenueDept(params).then(response => {       
                    for(let i=0;i<response.total;i++){
                        arr.indexOf(response.rows[i])===-1?arr.push({ratio:response.rows[i].ratio,dictValue:response.rows[i].deptCode,dictLabel:response.rows[i].deptName}):arr
                    } 
                    this.form.outpatientServiceRight = JSON.parse(JSON.stringify(arr));
                    this.outpatientRightLength=response.total
                    this.loading1=false 
                });
                return
            }
            if(type==2){
                this.loading3=true
                let arr=[]
               await getRevenueDept(params).then(response => {
                   for(let i=0;i<response.total;i++){
                        arr.indexOf(response.rows[i])===-1?arr.push({ratio:response.rows[i].ratio,dictValue:response.rows[i].deptCode,dictLabel:response.rows[i].deptName}):arr
                    } 
                    this.form.hospitalServiceRight = JSON.parse(JSON.stringify(arr));
                    this.hospitalRightLength=response.total 
                    this.loading3=false                                                       
                });
                return
            }
             
        },
        //获取科室列表及门诊和住院左侧数据
        async getTreeDept(data) {
             this.loading=true
             this.loading2=true
             if(this.clickCount==0){
                await this.getDetail(2)
            }
            this.clickCount+=1
            await getDeptList(data).then(response => {
                if (response.code == 200) {
                    this.deptDatasLength=response.data.length
                    if(data.clinicType==1){
                        // this.form.outpatientServiceLeft=[]
                        this.changeDeptList1=[]
                        this.changeDeptList=[]
                        this.deptDatas=JSON.parse(JSON.stringify(response.data.map(ite=>{
                            ite.ratio=0
                            return ite
                        })));
                        if(this.outpatientRightLength!==0 || this.form.outpatientServiceRight.length==1){
                            this.changeDeptList1=JSON.parse(JSON.stringify(response.data.map(ite=>{
                            ite.ratio=0
                            return ite
                        })));
                            for(let i in this.form.outpatientServiceRight){
                                    for(let k in this.changeDeptList1){
                                    if(this.form.outpatientServiceRight[i].dictValue == this.changeDeptList1[k].dictValue){
                                        this.changeDeptList1.splice(k,1)
                                    }
                                }
                            }
                           this.form.outpatientServiceLeft=JSON.parse(JSON.stringify(this.changeDeptList1))
                           this.loading=false
                           return
                        }else if(this.outpatientRightLength>1){
                            this.changeDeptList=JSON.parse(JSON.stringify(response.data.map(ite=>{
                            ite.ratio=0
                            return ite
                        })));
                            for(let i in this.form.outpatientServiceRight){
                                    for(let k in this.changeDeptList){
                                    if(this.form.outpatientServiceRight[i].dictValue == this.changeDeptList[k].dictValue){
                                        this.changeDeptList.splice(k,1)
                                    }
                                }
                            }
                           this.form.outpatientServiceLeft=JSON.parse(JSON.stringify(this.changeDeptList))
                           this.loading=false
                           return
                        }else if(this.outpatientRightLength==0&&this.form.outpatientServiceRight.length==0){
                            this.form.outpatientServiceLeft=JSON.parse(JSON.stringify(this.deptDatas))
                            this.loading=false
                            return
                        }
                    }
                    if(data.clinicType==2){
                        this.changeHosDeptList=[]
                        this.changeHosDeptList1=[]
                        // this.form.hospitalServiceLeft=[]
                        this.deptDatasHos=JSON.parse(JSON.stringify(response.data.map(ite=>{
                            ite.ratio=0
                            return ite
                        })));
                        if(this.hospitalRightLength!==0 || this.form.hospitalServiceRight.length==1){
                             this.changeHosDeptList1=JSON.parse(JSON.stringify(response.data.map(ite=>{
                            ite.ratio=0
                            return ite
                        })));
                            for(let i in this.form.hospitalServiceRight){
                                    for(let k in this.changeHosDeptList1){
                                    if(this.form.hospitalServiceRight[i].dictValue == this.changeHosDeptList1[k].dictValue){
                                        this.changeHosDeptList1.splice(k,1)
                                    }
                                }
                            }
                            this.form.hospitalServiceLeft=JSON.parse(JSON.stringify(this.changeHosDeptList1))
                            this.loading2=false        
                            return                   
                        }else if(this.form.hospitalServiceRight.length>1){              
                            this.changeHosDeptList=JSON.parse(JSON.stringify(response.data.map(ite=>{
                            ite.ratio=0
                            return ite
                        })));
                            for(let i in this.form.hospitalServiceRight){
                                    for(let k in this.changeHosDeptList){
                                    if(this.form.hospitalServiceRight[i].dictValue == this.changeHosDeptList[k].dictValue){
                                        this.changeHosDeptList.splice(k,1)
                                    }
                                }
                            }
                            this.loading2=false
                            this.form.hospitalServiceLeft=JSON.parse(JSON.stringify(this.changeHosDeptList))
                            return                                              
                        }else if(this.hospitalRightLength==0 && this.form.hospitalServiceRight.length==0){
                            this.form.hospitalServiceLeft=JSON.parse(JSON.stringify(this.deptDatasHos))
                            this.loading2=false
                            return                                               
                        }
                        
                    }

                } else {
                    this.msgError(response.msg);
                }
            });
            
            
        },
        hospitalToright(){
            //将过滤后的数组需要添加到右边的数组中
            for(let i = 0;i<this.selectionRowhospitalLeft.length;i++){
                 this.form.hospitalServiceRight.indexOf(this.selectionRowhospitalLeft[i]) === -1 ?  this.form.hospitalServiceRight.push(this.selectionRowhospitalLeft[i]) :  this.form.hospitalServiceRight
            };        
            //剩余左边数据
            for(let i=0;i<this.form.hospitalServiceLeft.length;i++){
                 this.form.hospitalServiceRight.forEach(ite=>{
                    if(this.form.hospitalServiceLeft[i]&&this.form.hospitalServiceLeft[i].dictValue==ite.dictValue){        
                          this.form.hospitalServiceLeft.splice(i,1)
                          i-=1
                          i<0?0:i
                    }
                })
            }
        },
        hospitalToLeft(){
            for(let i = 0;i<this.selectionRowhospitalRight.length;i++){
                this.form.hospitalServiceLeft.indexOf(this.selectionRowhospitalRight[i]) === -1 ?  this.form.hospitalServiceLeft.push(this.selectionRowhospitalRight[i]) :  this.form.hospitalServiceLeft
            };
            //剩余右边数据
            for(let i=0;i<this.form.hospitalServiceRight.length;i++){
                 this.form.hospitalServiceLeft.forEach(ite=>{
                    if(this.form.hospitalServiceRight[i]&&this.form.hospitalServiceRight[i].dictValue==ite.dictValue){     
                          this.form.hospitalServiceRight.splice(i,1)
                          i-=1
                          i<0?0:i
                    }
                })
            }
        },
        outpatientToright(){
          //将过滤后的数组需要添加到右边的数组中
            for(let i = 0;i<this.selectionRowoutpatientLeft.length;i++){
                 this.form.outpatientServiceRight.indexOf(this.selectionRowoutpatientLeft[i]) === -1 ?  this.form.outpatientServiceRight.push(this.selectionRowoutpatientLeft[i]) :  this.form.outpatientServiceRight
            };    
            //剩余左边数据
            for(let i=0;i<this.form.outpatientServiceLeft.length;i++){
                 this.form.outpatientServiceRight.forEach(ite=>{
                    if(this.form.outpatientServiceLeft[i]&&this.form.outpatientServiceLeft[i].dictValue==ite.dictValue){
                          this.form.outpatientServiceLeft.splice(i,1)
                          i-=1
                          i<0?0:i
                    }
                })
            }
		},
        //将右边数据放到左边
        outpatientToleft(){
            for(let i = 0;i<this.selectionRowOutpatientRight.length;i++){
                 this.form.outpatientServiceLeft.indexOf(this.selectionRowOutpatientRight[i]) === -1 ?  this.form.outpatientServiceLeft.push(this.selectionRowOutpatientRight[i]) :  this.form.outpatientServiceLeft
            };
            //剩余右边数据
            for(let i=0;i<this.form.outpatientServiceRight.length;i++){
                 this.form.outpatientServiceLeft.forEach(ite=>{
                    if(this.form.outpatientServiceRight[i]&&this.form.outpatientServiceRight[i].dictValue==ite.dictValue){         
                          this.form.outpatientServiceRight.splice(i,1)
                          i-=1
                          i<0?i=0:i
                    }
                })
            }
        },
        // 门诊多选框选中数据
        handleSelectionOutpatientChange(selection) {
            this.selectionRowoutpatientLeft=selection.map(ite=>{
                ite.ratio=0
                return ite
            });
            if(selection.length!==0){
                this.patToRightBool=false;
            }else{
                this.patToRightBool=true;
            }
        },
        //门诊右侧多选框数据
        handleSelectionChangeRight(selection){
            this.selectionRowOutpatientRight=selection;
            if(selection.length!==0){
                this.patToLeftBool=false;
            }else{
                this.patToLeftBool=true;
            }
        },
        //住院左侧多选框
        handleSelectionHospitalChange(selection){
            this.selectionRowhospitalLeft=selection.map(ite=>{
                ite.ratio=0
                return ite
            });
            if(selection.length!==0){
                this.hosToRightBool=false;
            }else{
                this.hosToRightBool=true;
            }
        },
        //住院右侧多选框
        handleSelectionHospitalChangeRight(selection){
            this.selectionRowhospitalRight=selection;
            if(selection.length!==0){
                this.hosToLeftBool=false;
            }else{
               this.hosToLeftBool=true;
            }
        },
        rowClick(row, column, event) {
            let refsElTable = this.$refs.multipleTable;
            let findRow = this.selectionRowoutpatientLeft.find(c => c.rowIndex == row.rowIndex);
            if (findRow && this.selectionRowoutpatientLeft.length == 1) {
                refsElTable.toggleRowSelection(row, false);
                return;
            }
            refsElTable.clearSelection();
            refsElTable.toggleRowSelection(row);
        },
        rowClickPatRight(row, column, event){
            let refsElTable = this.$refs.multipleTableRight;
            let findRow = this.selectionRowOutpatientRight.find(c => c.rowIndex == row.rowIndex);
            if (findRow && this.selectionRowOutpatientRight.length == 1) {
                refsElTable.toggleRowSelection(row, false);
                return;
            }
            refsElTable.clearSelection();
            refsElTable.toggleRowSelection(row);
        },
        rowClickHos(row, column, event){
            let refsElTable = this.$refs.multipleTableHos;
            let findRow = this.selectionRowhospitalLeft.find(c => c.rowIndex == row.rowIndex);
            if (findRow && this.selectionRowhospitalLeft.length == 1) {
                refsElTable.toggleRowSelection(row, false);
                return;
            }
            refsElTable.clearSelection();
            refsElTable.toggleRowSelection(row);
        },
        rowClickHosRight(row, column, event){
            let refsElTable = this.$refs.multipleTableRightHos;
            let findRow = this.selectionRowhospitalRight.find(c => c.rowIndex == row.rowIndex);
            if (findRow && this.selectionRowhospitalRight.length == 1) {
                refsElTable.toggleRowSelection(row, false);
                return;
            }
            refsElTable.clearSelection();
            refsElTable.toggleRowSelection(row);
        },
        reset(){
            this.form={
                outpatientServiceLeft:[],//门诊左侧数据
                outpatientServiceRight:[],//门诊右侧数据
                hospitalServiceLeft:[],//住院左侧数据
                hospitalServiceRight:[],//住院右侧数据
            };
            this.clickCount=0
        },
        /** 科室提交按钮 */
        submitFormDept() {
            this.$refs["queryForm"].validate(valid => {
                if (valid) {
                let params,arr=[],arr1=[],sum=0,sum1=0;
                    if(this.form.outpatientServiceRight.length==0 && this.form.hospitalServiceRight==0){
                        params={
                            revenueCode:this.queryParams.revenueCode,
                            pmsRevenueDepts:[],
                            compCode:this.queryParams.compCode,
                        }
                        let saveLoading = this.saveLoading();
                            addDeptList(params).then(response => {
                                this.msgSuccess("收入核算科室设置成功");
                                this.$emit("success", false);
                                saveLoading.close();
                        });
                    }
                    if(this.form.outpatientServiceRight.length==0 && this.form.hospitalServiceRight!=0){
                        this.form.hospitalServiceRight.forEach(ite=>{
                            sum1+=Number(ite.ratio)                        
                            arr1.push({
                                deptName:ite.dictLabel,
                                deptCode:ite.dictValue,
                                ratio:ite.ratio
                            })
                        })
                        params={
                            revenueCode:this.queryParams.revenueCode,
                            pmsRevenueDepts:arr1,
                            compCode:this.queryParams.compCode,
                        }
                        if(sum1>100){
                            this.msgError("住院已选科室分摊比例的总和应小于或等于100")
                            return
                        }else{
                            let saveLoading = this.saveLoading();
                            addDeptList(params).then(response => {
                                this.msgSuccess("收入核算科室设置成功");
                                this.$emit("success", false);
                                saveLoading.close();
                            });
                        }
                    }
                    if(this.form.outpatientServiceRight.length!=0 && this.form.hospitalServiceRight==0){
                        this.form.outpatientServiceRight.forEach(ite=>{
                            sum+=Number(ite.ratio)
                            arr.push({
                                deptName:ite.dictLabel,
                                deptCode:ite.dictValue,
                                ratio:ite.ratio
                            })
                        })
                        params={
                            revenueCode:this.queryParams.revenueCode,
                            pmsRevenueDepts:arr,
                            compCode:this.queryParams.compCode,
                        }
                        if(sum>100){
                            this.msgError("门诊已选科室分摊比例的总和应小于或等于100")
                            return
                        }else{
                            let saveLoading = this.saveLoading();
                            addDeptList(params).then(response => {
                                this.msgSuccess("收入核算科室设置成功");
                                this.$emit("success", false);
                                saveLoading.close();
                            });
                        }
                    }
                    if(this.form.outpatientServiceRight.length!=0 && this.form.hospitalServiceRight.length!=0){
                        this.form.outpatientServiceRight.forEach(ite=>{
                            sum+=Number(ite.ratio)
                            arr.push({
                                deptName:ite.dictLabel,
                                deptCode:ite.dictValue,
                                ratio:ite.ratio
                            })
                        })
                        this.form.hospitalServiceRight.forEach(ite=>{
                            sum1+=Number(ite.ratio)                        
                            arr1.push({
                                deptName:ite.dictLabel,
                                deptCode:ite.dictValue,
                                ratio:ite.ratio
                            })
                        })
                        params={
                            revenueCode:this.queryParams.revenueCode,
                            pmsRevenueDepts:[...arr1,...arr],
                            compCode:this.queryParams.compCode,
                        }
                        if(sum>100||sum1>100){
                            this.msgError("门诊及住院已选科室分摊比例的总和应分别小于或等于100")
                            return
                        }else{
                            let saveLoading = this.saveLoading();
                            addDeptList(params).then(response => {
                                this.msgSuccess("收入核算科室设置成功");
                                this.$emit("success", false);
                                saveLoading.close();
                            });
                        }
                    }
                    
                }
            });
        },
      // 弹框关闭
        handleDialogClose() {
            this.$emit("cancel", false);
            this.reset()
        },
        // 取消
        cancel() {
            this.$emit("cancel", false);
            this.reset()
        },
    },
};
</script>

<style lang="scss" scoped>
::v-deep .el-dialog{
    .el-form-item__label{
        width: 80px !important;
    }
    .el-form-item__content{
        margin-left: 80px !important
    }
    .el-tabs__item.is-top{
        font-size:16px;
    }
}
.transfer{
    width: 100%;
    .transfer_left{
        border: 1px solid #cccccc;
        border-radius: 2%;
        width: 45%;
        float: left;
        overflow: hidden;
        height:500px;
        .transfer_left_top{
            padding: 0 10px;
            p{
                position: relative;
                label{
                    font-size: 16px;
                    font-weight: normal;
                    margin-left: 10px
                }
                span{
                position: absolute;
                right: 10px
            }
            }
        }
    }
    .transfer_main{
        width: 10%;
        float: left;
        line-height: 500px;
        text-align: center;
        position: relative;
       ::v-deep .el-button-group .el-button--primary:first-child{
           position: absolute;
           top:40%;
           left: 50%;
           transform: translate(-50%,-50%);
           border: 0;
           border-radius: 4px;
        }
        ::v-deep .el-button-group .el-button--primary:last-child{
           position: absolute;
           top:50%;
           left: 50%;
           transform: translate(-50%,-50%);
           border: 0;
           border-radius: 4px;

        }
    }
    .transfer_right{
        border: 1px solid #cccccc;
        border-radius: 2%;
        width: 45%;
        height: 500px;
        overflow: hidden;
        float: right;
        .transfer_right_top{
            padding: 0 10px;            
            p{
                position: relative;
                label{
                    font-weight: normal;
                    font-size: 16px; 
                    margin-left: 10px
                }
                span{
                    position: absolute;
                    right: 10px
                }
            }
        }
    }
    .checkbox_left{
        margin-top: 10px;
        padding:0 4px;
        overflow: auto;
        min-height:300px;
    }
}
</style>