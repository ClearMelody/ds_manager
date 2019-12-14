<template>
  <div>
    <quill-editor ref="text" v-model="content" class="myQuillEditor" :options="editorOption"/>
    <el-button type="primary" :loading="loading" @click="submit">提交</el-button>
  </div>
</template>

<script>
  import API from "../../api/api_card_description"
  import {quillEditor} from 'vue-quill-editor'
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'

  export default {
    name: "CardDescription",
    components: {
      quillEditor
    },
    data() {
      return {
        loading: false,
        content: '',
        editorOption: {}
      }
    },
    methods: {
      submit() {
        let _that = this;
        _that.loading = true;
        API.addCardDescription({content: _that.content}).then(res => {
          _that.loading = false;
          if (!res) {
          }
        }).catch(() => {
          _that.loading = false;
        });
      },
      initContent() {
        let _that = this;
        API.getCardDescription().then(res => {
          if (!res) {
            return;
          }
          console.log(res);
          _that.content = res.content;
        }).catch(() => {});
      }
    },
    mounted() {
      let _that = this;
      _that.initContent();
    }
  }
</script>

<style scoped>

</style>
