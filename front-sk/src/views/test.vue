<template>
  <div>
      {{access_token}} <br>
      {{userid}}
      {{jwt}}
  </div>
</template>

<script>
export default {
    data(){
        return {
            access_token:'',
            userid:'',
            jwt:'',
        }
    },
    mounted(){
        this.access_token = this.$route.params.access_token
        this.userid = this.$route.params.userid
        this.jwt = this.$route.params.jwt
        
        sessionStorage.setItem("userToken", this.jwt);
        sessionStorage.setItem("userid", this.userid)
        this.$router.push({ name: 'main' })
        this.$store.state.userid = sessionStorage.getItem("userid");

        // sessionStorage.setItem("userToken", this.access_token);

     
    }

}
</script>

<style>

</style>