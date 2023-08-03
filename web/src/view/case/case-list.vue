<template>
    <div class="container" v-if="!showEdit">
      <div class="header">
        <div class="newBtn"><el-button type="primary" @click="handleCreate">新 增</el-button></div>
        <div class="search">
          <el-select v-model="projectId" placeholder="请选择工程" clearable filterable>
            <el-option v-for="item in projects" :key="item.value" :label="item.name" :value="item.id"/>
          </el-select>
          <el-input placeholder="请输入用例名称查询" v-model="name" clearable></el-input>
        </div>
      </div>
      <el-main v-if="loading" v-loading = "loading" element-loading-text="Loading..." element-loading-background="#F9FAFB" style="height: 600px;"/>
      <div class="list" v-else>
        <div class="case" v-for="(item,index) in casesRes">
          <div class="line line-name">
            <div class="name">{{item.name}}</div>
            <div class="last-run">2023-3-31 10:56:25  李淳罡</div>
          </div>
          <div class="line">
            <div class="progress">
              <el-progress :text-inside="true" :stroke-width="23" :percentage="70" color="#0f59a4" striped striped-flow :duration="10"/>
            </div>
            <div class="line-icon">
              <i class="iconfont icon-stop"></i>
              <i class="iconfont icon-config"></i>
            </div>
          </div>
          <div class="line">
            <div class="line-icon">
              <i class="iconfont icon-clear"></i>
              <i class="iconfont icon-start"></i>
              <i class="iconfont icon-debug"></i>
              <i class="iconfont icon-modify"></i>
              <i class="iconfont icon-remove"></i>
              <i class="iconfont icon-history"></i>
            </div>
          </div>
        </div>
      </div>

    </div>
  </template>
  
  <script>
    import Utils from 'lin/util/util'
    import { onMounted, ref ,watch } from 'vue'
    import { get,_delete } from '@/lin/plugin/axios'
  
    export default {
      components: {

      },
      setup() {
        const showEdit = ref(false)
        const name = ref('')
        const cases = ref([])
        const loading = ref(false)
        const editcaseId = ref(null)
        const projects = ref([])
        const projectId = ref('')
        const casesRes = ref([])
  
        onMounted(() => {
            getProjects()
            getCases()
        })

        const getProjects = async () => {
          let res
          try {
            res = await get('/v1/project/all', { showBackend: true })
            projects.value = res
          } catch (error) {
            projects.value = []
          }
        }

        const getCases = async () => {
          loading.value = true
          let res
          try {
            res = await get('/v1/case', { showBackend: true })
            cases.value = res
          } catch (error) {
            cases.value = []
          }
          searchCases()
        }

        const searchCases = () => {
          loading.value = true
          casesRes.value = []
          for (let i = 0; i < cases.value.length; i++) {
            if (cases.value[i].name.includes(name.value, { ignoreCase: true }) && (cases.value[i].project == projectId.value || projectId.value=='')) {
              casesRes.value.push(cases.value[i])
            }
          }
          loading.value = false
        }
        
        const handleCreate = () => {
          showEdit.value = true
          editcaseId.value = null
        }

        const _debounce =Utils.debounce(()=>{
          searchCases()
        }, 800)
  
        watch(name, () => {
          _debounce()
        })

        watch(projectId, () => {
          _debounce()
        })
  
        return {
          cases,
          loading,
          editcaseId,
          projects,
          projectId,
          name,
          showEdit,
          handleCreate,
          casesRes,
          searchCases,
      }
  
      },
    }
  </script>
  
  <style lang="scss" scoped>
  .container {
    padding: 0 30px;
  
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin: 20px 0;
  
      .search {
        display: flex;
        align-items: center;
        height: 59px;
        line-height: 59px;
        color: $parent-title-color;
        font-size: 16px;
        font-weight: 500;
        .el-select {
          margin-right: 20px;
          width: 350px;
        }
      }
    }

    .list {
      display: flex;
      flex-wrap: wrap;
    }
    .case:hover {
      background-color: rgb(219, 234, 247);
      cursor: pointer;
    }
    .case {
      width: calc(50% - 32px);
      margin: 15px;
      border: 1px solid #1177b0;
      border-radius: 5px;
      background-color: aliceblue;
      box-shadow: 0px 0px 12px rgba(0, 0, 0, .12);

      .line {
        height: 45px;
        line-height: 45px;
        align-items: center;
        position: relative;
        .progress {
          width: 75%;
          transform: translate(0, -50%);  
          position: absolute;  
          top: 50%;
          margin-left: 8px;
          .el-progress {
            width: 100%;
          }
        }
        .line-icon {
          transform: translate(0, -50%);  
          position: absolute;
          top: 50%;
          right: 0;
          .iconfont {
            margin-right: 25px;
            font-size: 1.5rem;
          }
        }
      }
      .line-name {
        display: flex;
        justify-content: space-between;
        .name {
          margin: 0 0 0 8px;
          font-size: 20px;
        }
        .last-run {
          margin: 0 8px 0 0;
          font-size: 10px;
        }
      }
    }
  }
  </style>