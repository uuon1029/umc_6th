package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.Request.AccountDupRequest
import com.example.umc_6th.Retrofit.Request.FindIdRequest
import com.example.umc_6th.Retrofit.Request.FindPwdRequest
import com.example.umc_6th.Retrofit.Request.LoginRequest
import com.example.umc_6th.Retrofit.Request.NickNameDupRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import retrofit2.http.*
import retrofit2.Call

interface RetrofitService {

    //########GET##############

    // 닉네임 중복 확인
    @HTTP(method = "GET", path = "/user/nickname-dup", hasBody = true)
    fun getNickNameDup(

    ): Call<Boolean>

    // 아이디 중복 확인
    @HTTP(method = "GET", path = "/user/account-dup", hasBody = true)
    fun getAccountDup(

    ): Call<Boolean>

    // 아이디 찾기
    @HTTP(method = "GET", path = "/user/find-id", hasBody = true)
    fun getFindId(

    ): Call<FindAccountResponse>

    // 비밀번호 찾기
    @HTTP(method = "GET", path = "/user/find-pwd", hasBody = true)
    fun getFindPwd(

    ): Call<FindPwdResponse>

    // 활동내역 전체 조회
    @GET("/user/history?paging={page}")
    fun getHistory(
        @Path(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 내가 쓴 글 전체 조회
    @GET("/user/myboards?paging={page}")
    fun getMyBoards(
        @Path(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 댓글단 글 전체 조회
    @GET("/user/mycomments?paging={page}")
    fun getMyComments(
        @Path(value = "page") page: Int
    ): Call<myCommentsResponse>

    // 좋아요한 글 전체 조회
    @GET("/user/mylikes?paging={page}")
    fun getMyLikes(
        @Path(value = "page") page: Int
    ): Call<myLikesResponse>

    // 활동내역 검색 조회
    @GET("/user/history?paging={page}")//수정 필요
    fun getHistorySearch(
        @Path(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 내가 쓴 글 검색 조회
    @GET("/user/myboards?paging={page}")//수정 필요
    fun getMyBoardsSearch(
        @Path(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 댓글단 글 검색 조회
    @GET("/user/mycomments?paging={page}")//수정 필요
    fun getMyCommentsSearch(
        @Path(value = "page") page: Int
    ): Call<myCommentsResponse>

    // 좋아요한 글 검색 조회
    @GET("/user/mylikes?paging={page}")//수정 필요
    fun getMyLikesSeach(
        @Path(value = "page") page: Int
    ): Call<myLikesResponse>

    // 타인 프로필 조회
    @GET("/user/{id}/find")
    fun getUserProfile(
        @Path(value = "id") id: Int
    ): Call<FindProfileResponse>

    // 타인 작성한 글 리스트 조회
    @GET("/user/{id}/boards?page={page}")
    fun getUserProfileBoards(
        @Path(value = "id") id: Int,
        @Path(value = "page") page: Int
    ): Call<FindProfileBoardsResponse>

    // 타인 댓글단 글 리스트 조회
    @GET("/user/{id}/comments?page={page}")
    fun getUserProfileComments(
        @Path(value = "id") id: Int,
        @Path(value = "page") page: Int
    ): Call<FindProfileCommentsResponse>

    // 커뮤니티 화면 게시판들 조회
    @GET("/board/main")
    fun getBoardMain(): Call<BoardMainResponse>

    // 전공 게시판 목록 조회
    @GET("/board/{major_id}/major?paging={page}")
    fun getBoardMajor(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "page") page: Int
    ): Call<BoardMajorListResponse>

    // 핫한 게시판 목록 조회
    @GET("/board/hot/{major_id}?paging={page}")
    fun getBoardHot(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "page") page: Int
    ): Call<BoardHotResponse>

    // 전체 게시판 목록 조회
    @GET("/board/list?paging={page}")
    fun getBoardAll(
        @Path(value = "page") page: Int
    ): Call<BoardAllListResponse>

    // 게시물 조회 (내용, 댓글, 대댓글 등)
    @GET("/board/{board_id}?paging={page}")
    fun getBoard(
        @Path(value = "board_id") board_id: Int,
        @Path(value = "page") page: Int
    ): Call<BoardViewResponse>

    // 게시물 댓글 수정
    @GET("/pin/detail/{pin_id}")
    fun getBoardEditPin(
        @Path(value = "pin_id") pin_id: Int,
    ): Call<CommentFindReponse>

    // 게시물 대댓글 수정
    @GET("/comment/detail/{comment_id}")
    fun getBoardEditComment(
        @Path(value = "comment_id") comment_id: Int,
    ): Call<CommentFindReponse>

    // 전공 게시판 제목 검색
    @GET("/board/search-major/{major_id}/title/{search_keyWord}&paging={page}")
    fun getBoardMajorSearchTitle(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 제목 검색
    @GET("/board/search-hot/title/{search_keyWord}&paging={page}")
    fun getBoardHotSearchTitle(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 제목 검색
    @GET("/board/search-all/title/{search_keyWord}&paging={page}")
    fun getBoardAllSearchTitle(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 내용 검색
    @GET("/board/search-major/{major_id}/content/{search_keyWord}&paging={page}")
    fun getBoardMajorSearchContent(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 내용 검색
    @GET("/board/search-hot/content/{search_keyWord}&paging={page}")
    fun getBoardHotSearchContent(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 내용 검색
    @GET("/board/search-all/content/{search_keyWord}&paging={page}")
    fun getBoardAllSearchContent(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 제목+내용 검색
    @GET("/board/search-major/{major_id}/title-content/{search_keyWord}&paging={page}")
    fun getBoardMajorSearch(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 제목+내용 검색
    @GET("/board/search-hot/title-content/{search_keyWord}&paging={page}")
    fun getBoardHotSearch(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 제목+내용 검색
    @GET("/board/search-all/title-content/{search_keyWord}&paging={page}")
    fun getBoardAllSearch(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 작성자 검색
    @GET("/board/search-major/{major_id}/nickname/{search_keyWord}&paging={page}")
    fun getBoardMajorSearchUser(
        @Path("major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 핫한 게시판 작성자 검색
    @GET("/board/search-hot/nickname/{search_keyWord}&paging={page}")
    fun getBoardHotSearchUser(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 작성자 검색
    @GET("/board/search-all/nickname/{search_keyWord}&paging={page}")
    fun getBoardAllSearchUser(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Path(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 공지 목록 조회
    @GET("/notice/paging?page={page}")
    fun getAnnouncementsList(
        @Path(value = "page") page: Int
    ): Call<NoticeListResponse>

    // 공지 게시물 조회
    @GET("/notice/{notice_id}")
    fun getAnnouncement(
        @Path(value = "notice_id") notice_id: Int
    ): Call<NoticeDetailResponse>

    //#############POST#############

    // 회원 가입
    @POST("/user/join")
    fun postSignUp(
        @Body request: SignupRequest
    ): Call<SignupResponse>

    // 로그인
    @POST("/user/login")
    fun postLogin(
        @Body request: LoginRequest
    ): Call<LoginResponse>


}