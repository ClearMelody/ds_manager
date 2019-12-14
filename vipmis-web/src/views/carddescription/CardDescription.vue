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
        editorOption: {
          placeholder: 'Please enter it here...',
          modules:{
            toolbar:{
              container: [
                ['bold', 'italic', 'underline', 'strike'],// 加粗，斜体，下划线，删除线
                ['blockquote'],// 引用
                [{ 'header': 1 }, { 'header': 2 }],// 标题，键值对的形式；1、2表示字体大小
                // eslint-disable-next-line standard/object-curly-even-spacing
                [{ 'list': 'ordered'}, { 'list': 'bullet' }],//列表
                // eslint-disable-next-line standard/object-curly-even-spacing
                [{ 'indent': '-1'}, { 'indent': '+1' }],// 缩进
                [{ 'direction': 'rtl' }],// 文本方向
                [{ 'size': ['small', false, 'large', 'huge'] }],// 字体大小
                [{ 'header': [1, 2, 3, 4, 5, 6, false] }],//几级标题
                [{ 'color': [] }, { 'background': [] }],// 字体颜色，字体背景颜色
                [{ 'font': [] }],//字体
                [{ 'align': [] }],//对齐方式
                ['clean'],//清除字体样式
              ]
            }
          }
        }
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
