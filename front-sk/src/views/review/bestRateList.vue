<template>

    <div>
        <br>
        <h2 style="color:orange"><span class="iconify" data-icon="bx:bx-trending-up" data-inline="false"></span> Best
            Rate</h2>
        <b-container class="bv-example-row">
            <b-row>
                <div v-if="bestReviews.length==0">
                    <h2> 해당 리뷰가 존재하지 않습니다. </h2>
                </div>
                <b-col v-for="(bestReview, i) in bestReviews" v-bind:key="i+10">

                  
                        <v-card id="review-image" :loading="loading_card" class="mx-auto my-12 mr-2" max-width="350" width="350" height="450"
                            @click="storeDetail(bestReview.id)">
                            <v-img height="250" :src="bestReview.src">
                                <!--      -->
                            </v-img>
                            <v-card-title>{{bestReview.title}}</v-card-title>

                            <v-card-text>

                                <div>{{bestReview.black_text_content.slice(0, 30)}}...</div>
                                <!-- <div>{{bestReview.address}}</div> -->

                                <v-chip-group active-class="deep-purple accent-4 white--text" column="column">
                                    <div v-for="(tags, i) in bestReview.tag.slice(0, 4)" v-bind:key="i+20">
                                        <v-chip>{{tags}}</v-chip>
                                    </div>
                                </v-chip-group>
                            </v-card-text>

                        </v-card>
                </b-col>
            </b-row>

        </b-container>
    </div>
</template>

<script>
    import ReviewApi from '../../apis/ReviewApi.js';
    export default {
        created() {

            ReviewApi.requestfetchScoreList().then(
                response => {
                    var rateList = new Array();
                    console.log('Review');
                    console.log(response);
                    for (let i = 0; i < response.data.object.length; i++) {
                        var item = {};
                        if (response.data.object[i].picture)
                            item['src'] = response
                            .data
                            .object[i]
                            .picture;
                        else
                            item['src'] = "https://cdn.pixabay.com/photo/2016/12/26/17/28/food-1932466_1280.jpg"
                        item['title'] = response
                            .data
                            .object[i]
                            .title;
                        item['id'] = response.data.object[i].store.num;
                        let content = response
                            .data
                            .object[i]
                            .str + response
                            .data
                            .object[i]
                            .weak;
                        item['black_text_content'] = content;
                        item['address'] = response
                            .data
                            .object[i]
                            .store
                            .address;
                        let HashString = response
                            .data
                            .object[i]
                            .hashtag;
                        let HashSplit = HashString.split(',');
                        let HashList = [];
                        for (let z in HashSplit)
                            HashList.push(HashSplit[z]);
                        item['tag'] = HashList;
                        JSON.stringify(item);
                        rateList.push(item);
                    }

                    this.bestReviews = rateList;

                }
            )
        },
        data() {
            return {
                loading_card: false,
                selection_card: 1,
                bestReviews: [

                ],
            }

        },
        methods: {
            reserve() {
                this.loading = true

                setTimeout(() => (this.loading = false), 2000)
            },
            storeDetail(num) {
                this.$store.dispatch('storeHashtags', num)

            }

        },
        watch: {
            selection_card: function (v) {
                console.log(v);

            }
        }

    }
</script>

<style scoped>
    .container>div {
        padding-top: -20px;
        padding-top: 3px;
    }

    .v-card--reveal {
        align-items: center;
        bottom: 0;
        justify-content: center;
        opacity: 0.5;
        position: absolute;
        width: 100%;
    }

    #review-image {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1);
        -webkit-transition: .3s;
        -moz-transition: .3s;
        -ms-transition: .3s;
        -o-transition: .3s;
        transition: .3s;
    }

    #review-image:hover {
        -webkit-transform: scale(0.9);
        -moz-transform: scale(0.9);
        -ms-transform: scale(0.9);
        -o-transform: scale(0.9);
        transform: scale(0.9);
        overflow: hidden !important;
    }
</style>