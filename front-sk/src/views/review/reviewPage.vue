<template>

    <div>
        <div>
            <b-container>
                <!-- 리뷰 제목 -->
                <b-row style="text-align: left">
                    <b-col style="margin-right: 50px;">
                        <!-- 리뷰 제목 -->
                        <v-chip class="ma-2" outlined  color="#ff7f00" style="text-align: left;">
                            <v-icon>{{mdiFormatTitle}}</v-icon>itle
                        </v-chip>
                        <v-text-field v-model="reviewTitle" label="Review Title" :counter="20"></v-text-field>

                        <div class="error-text" v-if="error.title">
                            {{error.title}}
                        </div>



                        <!-- 음식점 주소 등록 -->
                        <v-chip class="ma-2" outlined  color="#ff7f00" style="text-align: left">
                            <v-icon>{{mdiPasta }}</v-icon>음식점이름
                        </v-chip><span>
                                <v-btn class="ma-2" id="show-btn" @click="$bvModal.show('bv-modal-example_adr')"
                                    style="color: #ff7f00 !important; background:#ff7f00 ;">음식점 이름/주소 등록
                                </v-btn>
                            </span>
                        <v-text-field v-model="store_name" label="store name" required>
                            <!-- <v-text-field v-model="store_name" :rules="nameRules" label="store name" required> -->
                        </v-text-field>

                         <v-chip class="ma-2" outlined  color="#ff7f00" style="text-align: center;">
                            <v-icon>{{mdiMapMarker}}</v-icon>음식점주소
                        </v-chip>
                        <v-text-field v-model="store_address" label="store address" required>
                            <!-- <v-text-field v-model="store_address" :rules="nameRules" label="store address" required> -->
                        </v-text-field>



                        <b-modal ref="my-modal" style="text-align: center; margin-bottom: 10px;"
                            id="bv-modal-example_adr" class="modalStore" hide-footer>
                            <template v-slot:modal-title>음식점 주소 등록하기</template>
                            <div class="d-block text-center">
                                <v-icon>{{icons.noodles}}</v-icon>음식점을 검색해주세요
                                검색 완료한 후 enter를 눌러주세요!
                                <b-form-input id="address_search" @keyup.enter="fetchAdr" type="text" v-model="address">
                                </b-form-input>
                                <b-table striped hover :items="maps" @row-clicked=" clickEvent"></b-table>
                            </div>
                            <div style="text-align: center;">
                                <v-btn block color="#ff7f00" @click="$bvModal.hide('bv-modal-example_adr')">EXIT</v-btn>
                            </div>
                        </b-modal>
                        <br>


                        <!-- 해시 태그 모달 -->
                        <div id="reviewHashtag">
                            
                         <v-chip class="ma-2" outlined  color="#ff7f00" >
                            <v-icon>{{mdiPoundBoxOutline}}</v-icon>해쉬태그
                        </v-chip><span>
                                    <v-btn @click="$bvModal.show('bv-modal-example')" id="show-btn" class="ma-2" dark
                                        style="color: #ff7f00 !important; background:#ff7f00 ;">
                                        <v-icon left>{{icons.pencil}}</v-icon> hashtag
                                    </v-btn>
                                </span>
                            <span>
                                <v-combobox v-model="allTags" chips clearable label="Your hashtags" multiple>
                                </v-combobox>
                            </span>

                        </div>
                        <b-modal style="text-align: center; margin-bottom: 10px;" id="bv-modal-example" hide-footer>
                            <template v-slot:modal-title>HashTag</template>
                            <div class="d-block text-center">
                                <span>
                                    <v-combobox v-model="allTags" chips clearable label="Your hashtags" multiple solo>
                                    </v-combobox>
                                </span>

                                <v-divider></v-divider>
                                음식종류
                                <v-divider></v-divider>
                                <v-chip draggable @click="check_area(24, '한식')">한식</v-chip>
                                <v-chip draggable @click="check_area(25, '양식')">양식</v-chip>
                                <v-chip draggable @click="check_area(26, '중식')">중식</v-chip>
                                <v-chip draggable @click="check_area(27, '일식')">일식</v-chip>
                                <v-chip draggable @click="check_area(28, '카페')">카페</v-chip>
                                <v-chip draggable @click="check_area(29, '밥집')">밥집</v-chip>
                                <v-chip draggable @click="check_area(30, '술집')">술집</v-chip>
                                <v-chip draggable @click="check_area(31, '고깃집')">고깃집</v-chip>
                                <v-chip draggable @click="check_area(32, '아시안')">아시안</v-chip>
                                <v-chip draggable @click="check_area( 43, '퓨전')">퓨전</v-chip>
                                <v-chip draggable @click="check_area( 48, '디저트')">디저트</v-chip>

                                


                                <v-divider></v-divider>
                                위치(강북)
                                <v-divider></v-divider>
                                <v-chip draggable @click="check_area( 8, '종로')">종로</v-chip>
                                <v-chip draggable @click="check_area( 9, '삼청동')">삼청동</v-chip>
                                <v-chip draggable @click="check_area( 10, '서촌')">서촌</v-chip>
                                <v-chip draggable @click="check_area( 11, '왕십리')">왕십리</v-chip>
                                <v-chip draggable @click="check_area( 12, '혜화')">혜화</v-chip>
                                <v-chip draggable @click="check_area(13, '상수')">상수</v-chip>
                                <v-chip draggable @click="check_area( 14, '한남동')">한남동</v-chip>
                                <v-chip draggable @click="check_area( 15, '이태원')">이태원</v-chip>
                                <v-chip draggable @click="check_area( 16, '홍대')">홍대</v-chip>
                                <v-chip draggable @click="check_area( 17, '광화문')">광화문</v-chip>
                                <v-chip draggable @click="check_area( 18, '여의도')">여의도</v-chip>
                                <v-chip draggable @click="check_area( 19, '연남동')">연남동</v-chip>
                                <v-chip draggable @click="check_area( 20, '합정')">합정</v-chip>
                                <v-chip draggable @click="check_area( 21, '경리단길')">경리단길</v-chip>
                                <v-chip draggable @click="check_area( 22, '명동')">명동</v-chip>
                                <v-chip draggable @click="check_area(23, '신촌')">신촌</v-chip>
                                <v-chip draggable @click="check_area(33, '대학로')">대학로</v-chip>
                                
                                <v-chip draggable @click="check_area(34, '마포')">마포</v-chip>
                                <v-chip draggable @click="check_area(35, '용산')">용산</v-chip>
                                <v-chip draggable @click="check_area(36, '서대문구')">서대문구</v-chip>
                                <v-chip draggable @click="check_area(37, '중구')">중구</v-chip>
                                <v-chip draggable @click="check_area(38, '성동구')">성동구</v-chip>
                                <v-chip draggable @click="check_area(39, '동대문')">동대문</v-chip>
                                <v-chip draggable @click="check_area(40, '은평구')">은평구</v-chip>
                                <v-chip draggable @click="check_area(41, '노원')">노원</v-chip>
                                <v-chip draggable @click="check_area(42, '뚝섬')">뚝섬</v-chip>
                                <v-chip draggable @click="check_area(45, '홍대')">홍대</v-chip>



                                <br>
                                <v-divider></v-divider>
                                위치 (강남)
                                <v-divider></v-divider>
                                <v-chip draggable @click="check_area(0, '강남')">강남</v-chip>
                                <v-chip draggable @click="check_area( 1, '잠실')">잠실</v-chip>
                                <v-chip draggable @click="check_area( 2, '사당')">사당</v-chip>
                                <v-chip draggable @click="check_area( 3, '코엑스')">코엑스</v-chip>
                                <v-chip draggable @click="check_area( 4, '고속터미널')">고속터미널</v-chip>
                                <v-chip draggable @click="check_area( 5, '가로수길')">가로수길</v-chip>
                                <v-chip draggable @click="check_area( 6  , '서래마을')">서래마을</v-chip>
                                <v-chip draggable @click="check_area( 44, '역삼')">역삼</v-chip>
                                <v-chip draggable @click="check_area( 46, '압구정')">압구정</v-chip>
                                <v-chip draggable @click="check_area( 47, '신사')">신사</v-chip>
                                <v-chip draggable @click="check_area( 49, '방배')">방배</v-chip>

                                <v-divider></v-divider>

                                누구와
                                <v-divider></v-divider>

                                <v-chip draggable @click="check_with( 0, '혼자')">혼자</v-chip>
                                <v-chip draggable @click="check_with(1, '친구')">친구</v-chip>
                                <v-chip draggable @click="check_with(2, '연인')">연인</v-chip>
                                <v-chip draggable @click="check_with( 3, '직장동료')">직장동료</v-chip>
                                <v-chip draggable @click="check_with(4, '가족')">가족</v-chip>

                                <v-divider></v-divider>
                                연령별
                                <v-divider></v-divider>

                                <v-chip draggable @click="check_age(0, '10대')">10대</v-chip>
                                <v-chip draggable @click="check_age( 1, '20대')">20대</v-chip>
                                <v-chip draggable @click="check_age( 2, '30대')">30대</v-chip>
                                <v-chip draggable @click="check_age( 3, '40450대')">4050대</v-chip>
                                <v-chip draggable @click="check_age(4, '그이상')">그이상</v-chip>


                                <v-divider></v-divider>
                                분위기
                                <v-divider></v-divider>

                                <v-chip draggable @click="check_atmosphere( 0, '시끌벅적한')">시끌벅적한</v-chip>
                                <v-chip draggable @click="check_atmosphere( 1, '차분한')">차분한</v-chip>
                                <v-chip draggable @click="check_atmosphere( 2, '소소한')">소소한</v-chip>
                                <v-chip draggable @click="check_atmosphere( 3, '고급스러운')">고급스러운</v-chip>
                                <v-chip draggable @click="check_atmosphere( 4, '데이트')">데이트</v-chip>




                                <v-divider></v-divider>
                                편의시설
                                <v-divider></v-divider>

                                <v-chip draggable @click="check_facility( 0, '단체석')">단체석</v-chip>
                                <v-chip draggable @click="check_facility( 1, '주차장')">주차장</v-chip>
                                <v-chip draggable @click="check_facility( 2, '개별룸')">개별룸</v-chip>
                                <v-chip draggable @click="check_facility( 50, '흡연실')">흡연실</v-chip>


                            </div>
                            <hr>
                            <div>
                                <v-btn block color="#ff7f00" @click="$bvModal.hide('bv-modal-example')">등록</v-btn>
                            </div>
                        </b-modal> <br>

                          <v-chip class="ma-2" outlined  color="#ff7f00" >
                            <v-icon>{{ mdiContentSaveAll}}</v-icon>사진업로드
                        </v-chip>
                        <div class="uploadProfile">
                            <v-file-input @change="getProfileForm" label="File input" filled
                                :prepend-icon="icons.camera" name="fileToUpload" id="fileToUpload"
                                accept=".gif, .jpg, .png"></v-file-input>
                            <!--change을 통해서 파일의 변화를 감지 -->
                            <!-- <v-btn @click="upload" class="ma-2" dark
                                style="color: #ff7f00 !important; background:#ff7f00; left: 350px;">
                                <v-icon left>{{icons.camera}}</v-icon>이미지업로드
                            </v-btn> -->
                        </div> <br>

                    </b-col>

                    <!-- 평점 -->
                    <b-col style="text-align: left">
 
                         <v-chip class="ma-2" outlined  color="#ff7f00" >
                            <v-icon>{{mdiEmoticonTongueOutline}}</v-icon>taste
                        </v-chip>
                        <star-rating v-model="flavor" :border-width="4" border-color="#d8d8d8" :rounded-corners="true"
                            style="text-align: center !important;"
                            :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]">
                        </star-rating> <br>
                            <v-chip class="ma-2" outlined  color="#ff7f00" >
                            <v-icon>{{mdiCurrencyUsd}}</v-icon>price
                        </v-chip>
                        <star-rating v-model="price" :border-width="4" border-color="#d8d8d8" :rounded-corners="true"
                            :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]">
                        </star-rating> <br>
                            <v-chip class="ma-2" outlined  color="#ff7f00" >
                            <v-icon>{{mdiEmoticonWinkOutline}}</v-icon>kind
                        </v-chip>
                        <star-rating v-model="kindness" :border-width="4" border-color="#d8d8d8" :rounded-corners="true"
                            :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]">
                        </star-rating> <br>
                          <h4>장점</h4>
                         <v-textarea
          label="장점 300자 이내"
          auto-grow
          outlined
          :counter="300"
          rows="3"
          v-model = "props"
          row-height="30"
          shaped
        ></v-textarea>
                         
            <div class="error-text" v-if="error.props">
                        {{error.props}}
            </div>
            <h4>단점</h4>
                               <v-textarea
          label="단점 300자 이내"
          auto-grow
          outlined
          v-model = "cons"
          :counter="300"
          rows="3"
          row-height="30"
          shaped
        ></v-textarea>
   
            <div class="error-text" v-if="error.cons">
                        {{error.cons}}
            </div>
            <h5>그래서 이 음식점의 총평을 평점으로 매긴다면?</h5>
             <star-rating v-model="rating" :border-width="4" border-color="#d8d8d8" :rounded-corners="true"
                    :star-points="[23,2, 14,17, 0,19, 10,34, 7,50, 23,43, 38,50, 36,34, 46,19, 31,17]"></star-rating>
           
              <div ref="finalSubmit">
              <v-btn depressed large name="submit" id="submit" @click="reviewConfirm(store_num)" style="color: #ff7f00 !important; background:#ff7f00 ;"
              value="review 등록" >리뷰 등록</v-btn>
              </div>
              </b-col>
               </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>
    import UserApi from '../../apis/UserApi'
    import StoreApi from '../../apis/StoreApi'
    import ReviewApi from '../../apis/ReviewApi'
    import PV from 'password-validator'
    import ImgurApi from '../../apis/ImgurApi'
    import StarRating from 'vue-star-rating'
    import {
        mdiPencil,
        mdiCamera,
        mdiNoodles,
        mdiFormatTitle,
        mdiPasta,
        mdiMapMarker,
        mdiPoundBoxOutline,
        mdiContentSaveAll,
        mdiEmoticonTongueOutline,
        mdiCurrencyUsd,
        mdiEmoticonWinkOutline,
        mdiEmoticonOutline,
        mdiEmoticonSadOutline,
        mdiCloudUpload
    } from '@mdi/js'

    import {
        jump
    } from '../../../public/js/animejs';



    export default {

        created() {

            this.titleSchema
                .is().min(0)
                .is().max(20)

            this.propsSchema
                .is().min(0)
                .is().max(300)
            this.consSchema
                .is().min(0)
                .is().max(300)

        },
        mounted() {
            this.gojump();
        },
        watch: {
            reviewTitle: function (v) {
                this.checkForm();
            },
            props: function (v) {
                this.checkForm_props();
            },
            cons: function (v) {
                this.checkForm_cons();
            },
            googleStorePlaceView: function () {

                let list = [...this.maps]
                list = [...this.$store.state.googleStorePlaceView]
                this.maps = [...list]
                console.log('har')


            }
        },
        computed: {
            googleStorePlaceView() {
                return this.$store.state.googleStorePlaceView;
            }
        },



        components: {
            StarRating
        },
        methods: {
            check_area(n, name) {

                if (!this.hashtags.locations[n]) {
                    // 반대값으로 넣기
                    this.hashtags.locations[n] = !this.hashtags.locations[n]
                    let list = [...this.area]
                    list.push(name)
                    this.area = [...list]
                    let list2 = [...this.allTags]
                    list2.push(name)
                    this.allTags = [...list2]
                    console.log('area!!!!!!!!!!!!!!')
                    console.log(this.area)
                } else {
                    var pos = this.area.indexOf(name)
                    this.hashtags.locations[n] = !this.hashtags.locations[n]
                    let list = [...this.area]
                    list.splice(pos, 1)
                    this.area = [...list]
                    var pos2 = this.allTags.indexOf(name)
                    let list2 = [...this.allTags]
                    list2.splice(pos2, 1)
                    this.allTags = [...list2]
                }
            },



            gojump() {
                jump(this.$refs.finalSubmit);
            },

            check_atmosphere(n, name) {
                if (!this.hashtags.moods[n]) {
                    this.hashtags.moods[n] = !this.hashtags.moods[n]
                    let list = [...this.area]
                    list.push(name)
                    this.area = [...list]
                    let list2 = [...this.allTags]
                    list2.push(name)
                    this.allTags = [...list2]
                } else {
                    this.hashtags.moods[n] = !this.hashtags.moods[n]
                    let list = [...this.area]
                    this.area = [...list]
                    var pos2 = this.allTags.indexOf(name)
                    let list2 = [...this.allTags]
                    list2.splice(pos2, 1)
                    this.allTags = [...list2]
                }
            },
            check_with(n, name) {
                if (!this.hashtags.withWho[n]) {
                    this.hashtags.withWho[n] = !this.hashtags.withWho[n]
                    let list = [...this.area]
                    list.push(name)
                    this.area = [...list]
                    let list2 = [...this.allTags]
                    list2.push(name)
                    this.allTags = [...list2]
                } else {
                    this.hashtags.withWho[n] = !this.hashtags.withWho[n]
                    let list = [...this.area]
                    this.area = [...list]
                    var pos2 = this.allTags.indexOf(name)
                    let list2 = [...this.allTags]
                    list2.splice(pos2, 1)
                    this.allTags = [...list2]
                }
            },
            check_age(n, name){
                if (!this.hashtags.ages[n]) {
                    this.hashtags.ages[n] = !this.hashtags.ages[n]
                    let list = [...this.area]
                    list.push(name)
                    this.area = [...list]
                    let list2 = [...this.allTags]
                    list2.push(name)
                    this.allTags = [...list2]
                } else {
                    this.hashtags.ages[n] = !this.hashtags.ages[n]
                    let list = [...this.area]
                    this.area = [...list]
                    var pos2 = this.allTags.indexOf(name)
                    let list2 = [...this.allTags]
                    list2.splice(pos2, 1)
                    this.allTags = [...list2]
                }
            },
            check_facility(n, name) {
                if (!this.hashtags.facility[n]) {
                    this.hashtags.facility[n] = !this.hashtags.facility[n]
                    let list = [...this.area]
                    list.push(name)
                    this.area = [...list]
                    let list2 = [...this.allTags]
                    list2.push(name)
                    this.allTags = [...list2]
                } else {
                    this.hashtags.facility[n] = !this.hashtags.facility[n]
                    let list = [...this.area]
                    this.area = [...list]
                    var pos2 = this.allTags.indexOf(name)
                    let list2 = [...this.allTags]
                    list2.splice(pos2, 1)
                    this.allTags = [...list2]
                }
            },

            upload() {
                ImgurApi.uploadProfile(this.selectedImage, res => {
                    this.imageUrl = res.data.link
                    console.log(this.imageUrl)

                }, error => {
                    alert('Imgur 업로드 실패!')
                })

            },

            getProfileForm(e) {
                this.selectedImage = e;
                this.upload()

            },
            // setRating(rating) {
            //     this.rating = "You have Selected: " + rating + " stars";
            //     console.log(this.rating);
            // },
            fetchAdr() {
                console.log(this.address);
                //검색시
                this
                    .$store
                    .dispatch('FETCH_ADR', this.address)



                console.log(this.$store.state.googleStorePlace);
                console.log(this.$store.state.googleStorePlaceView);
                // if (this.eye == false) this.eye = true;
                // else this.eye = false

                //값 init
                this.map = this.$store.state.googleStorePlaceView


                console.log(this.$store.state.googleStorePlace);


            },
            reviewConfirm(num) {
                let hashtag = this.area.toString();
                if (this.isSubmit) {
                    console.log(this.store_num);
                     this.$store.dispatch('storeHashtags', num)
                    var data = {
                        'hashtag': hashtag,
                        'picture': this.imageUrl,
                        'kindness': this.kindness,
                        'price': this.price,
                        'taste': this.flavor,
                        'total': this.rating,
                        'store': {
                            'num': this.store_num, //this.store_num,
                        },
                        'str': this.props,
                        'title': this.reviewTitle,
                        'user': {
                            'id': Number(sessionStorage.getItem("userid")),
                        },
                        'weak': this.cons,
                    }
                    console.log(data);
                    ReviewApi.requestAddReview(data, res => {
                        this.$alert("리뷰 등록 되셨습니다.", "success", "success", );

                        console.log("reviewPage 등록 성공");

                        this.$store.state.directiveStoreDetail = this.store_num;
                        
                        // this.$router.push({name : "storeDetail", params : {id  : this.store_num}})
                    })
                    console.log('all complete');

                } else {
                    this.$alert("리뷰를 작성해주세요.", "Warning", "warning", );
                    //alert('리뷰를 작성해주세요~~')
                }
            },
            clickEvent(recode, index) {
                // 'record' will be the row data from items
                // `index` will be the visible row number (available in the v-model 'shownItems')

                /**modal창닫고 table index infomation fetch**/
                this.$bvModal.hide('bv-modal-example_adr');
                this.$alert("등록 되셨습니다.", "success", "success", );
                this.store_address = this.$store.state.googleStorePlace[index].address;
                this.store_name = this.$store.state.googleStorePlace[index].sname;
                this.address = "";

                let list = [...this.maps]
                list = []
                this.maps = [...list]


                StoreApi.requestAddPlace(this.$store.state.googleStorePlace[index], res => {

                    this.store_num = res.data.object.num;
                    this.store_pic = res.data.object.img;
                    console.log('res 위');
                    console.log(res);


                })
            },
            checkForm() {
                //리뷰 제목(0자이상 10자 이하)
                //장점, 단점 (0자 이상 20자 이하)
                if (this.reviewTitle.length >= 0 && !this.titleSchema.validate(this.reviewTitle))
                    this.error.title = '리뷰제목은 0 글자 이상 20글자 이하 이어야 합니다.'
                else
                    this.error.title = false;
            },
            checkForm_props() {
                if (this.props.length >= 0 && !this.propsSchema.validate(this.props))
                    this.error.props = '장점은 0글자 이상 300글자 이하 이어야 합니다.'
                else
                    this.error.props = false;
            },
            checkForm_cons() {
                if (this.cons.length >= 0 && !this.consSchema.validate(this.cons))
                    this.error.cons = '단점은 0글자 이상 300글자 이하 이어야 합니다.'
                else
                    this.error.cons = false;


                let isSubmit = true;
                Object.values(this.error).map(v => {
                    if (v) isSubmit = false;
                })
                this.isSubmit = isSubmit;
            },
        },
        data: () => ({
            allTags: [],
            area: [],
            atmosphere: [],
            mageUrl: '',
            selectedImage: '',
            rating: "",
            cons: '',
            props: '',
            flavor: '',
            price: '',
            kindness: '',
            maps: [],
            address: '',
            eye: false,
            isSubmit: true, //form 완료시 toggle
            reviewTitle: '',
            store_num: '',
            store_name: '',
            user_email: 'ssafy@ssafy.com', //temp
            titleSchema: new PV(),
            consSchema: new PV(),
            propsSchema: new PV(),
            hashtags: {
                locations: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0
                ],
                withWho: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0
                ],
                ages: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0
                ],
                moods: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0
                ],
                facility: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0
                ]
            },
            error: {
                title: false,
                props: false,
                cons: false,
            },
            store_pic: '',
            icons: {
                pencil: mdiPencil,
                camera: mdiCamera,
                noodles: mdiNoodles
            },
            store_address: '',
            //icons
            mdiFormatTitle,
            mdiPasta ,
            mdiMapMarker,
            mdiPoundBoxOutline,
            mdiContentSaveAll,
            mdiEmoticonTongueOutline,
            mdiCurrencyUsd,
            mdiEmoticonWinkOutline,
            mdiEmoticonOutline,
            mdiEmoticonSadOutline,
            mdiCloudUpload
        })
    }
</script>

<style scoped>
    h4 {
        padding-bottom: 20px;
        margin-top: 10px;
        margin-right: 10px;
        font-weight: bold;
    }

    span {
        margin-left: 10px;
    }

    #show-btn:hover {
        -webkit-transform: scale(1.2);
        -moz-transform: scale(1.2);
        -ms-transform: scale(1.2);
        -o-transform: scale(1.2);
        transform: scale(1.2);
        -webkit-transition: .3s;
        -moz-transition: .3s;
        -ms-transition: .3s;
        -o-transition: .3s;
        transition: .3s;
        background: white !important;
        color: #ff7f00 !important;
        outline-color: #ff7f00 !important;
    }

    v-chip:hover {
        -webkit-transform: scale(1.2);
        -moz-transform: scale(1.2);
        -ms-transform: scale(1.2);
        -o-transform: scale(1.2);
        transform: scale(1.2);
        -webkit-transition: .3s;
        -moz-transition: .3s;
        -ms-transition: .3s;
        -o-transition: .3s;
        transition: .3s;
    }
    v-chip {
        margin-bottom: 10px !important;
    }
</style>