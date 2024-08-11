package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.Request.BoardReportRequest
import com.example.umc_6th.Retrofit.Request.CommentModifyRequest
import com.example.umc_6th.Retrofit.Request.CommentRegisterRequest
import com.example.umc_6th.Retrofit.Request.CommentReportRequest
import com.example.umc_6th.Retrofit.Request.IdRestoreRequest
import com.example.umc_6th.Retrofit.Request.LoginRequest
import com.example.umc_6th.Retrofit.Request.MajorRestoreRequest
import com.example.umc_6th.Retrofit.Request.NickNameRestoreRequest
import com.example.umc_6th.Retrofit.Request.PinModifyRequest
import com.example.umc_6th.Retrofit.Request.PwdRestoreRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.Request.exampleRegisterRequest
import com.example.umc_6th.Retrofit.Request.majorExampleRequest
import com.example.umc_6th.Retrofit.Response.AgreementChangeResponse
import com.example.umc_6th.Retrofit.Response.BoardDeleteResponse
import com.example.umc_6th.Retrofit.Response.BoardLikeResponse
import com.example.umc_6th.Retrofit.Response.BoardReportResponse
import com.example.umc_6th.Retrofit.Response.CommentDeleteResponse
import com.example.umc_6th.Retrofit.Response.CommentLikeReponse
import com.example.umc_6th.Retrofit.Response.CommentRegisterResponse
import com.example.umc_6th.Retrofit.Response.CommentReportResponse
import com.example.umc_6th.Retrofit.Response.FAQListAllResponse
import com.example.umc_6th.Retrofit.Response.QNADetailResponse
import com.example.umc_6th.Retrofit.Response.QNAListResponse
import com.example.umc_6th.Retrofit.Response.RegisterFavoriteExampleResponse
import com.example.umc_6th.Retrofit.Response.ReissueResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import com.example.umc_6th.Retrofit.Response.CommonResponse
import com.example.umc_6th.Retrofit.Response.exampleRegisterResponse
import com.example.umc_6th.Retrofit.Response.getExampleResponse
import com.example.umc_6th.Retrofit.Response.LogoutResponse
import com.example.umc_6th.Retrofit.Response.ResultBooleanResponse
import retrofit2.http.*
import retrofit2.Call

interface RetrofitService {

    //########GET##############

    // 닉네임 중복 확인
    @GET("/user/nickname-dup") //수정 필요
    fun getNickNameDup(
        @Query("nickName") nickName : String
    ): Call<ResultBooleanResponse>

    // 아이디 중복 확인
    @GET("/user/account-dup") //수정 필요
    fun getAccountDup(
        @Query("account") account: String
    ): Call<ResultBooleanResponse>

    // 아이디 찾기
    @GET("/user/find-id") // 수정 필요
    fun getFindId(
    ): Call<FindAccountResponse>

    // 비밀번호 찾기
    @GET("/user/find-pwd") // 수정 필요
    fun getFindPwd(): Call<FindPwdResponse>

    // 활동내역 전체 조회
    @GET("/user/history")
    fun getHistory(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int
    ): Call<HistoryResponse>

    // 내가 쓴 글 전체 조회
    @GET("/user/myboards")
    fun getMyBoards(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int
    ): Call<HistoryResponse>

    // 댓글단 글 전체 조회
    @GET("/user/mycomments")
    fun getMyComments(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int
    ): Call<HistoryResponse>

    // 좋아요한 글 전체 조회
    @GET("/user/mylikes")
    fun getMyLikes(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int
    ): Call<HistoryResponse>

    // 활동내역 검색 조회
    @GET("/user/find/history")//수정 필요
    fun getHistorySearch(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<HistoryResponse>

    // 내가 쓴 글 검색 조회
    @GET("/user/find/myboards")//수정 필요
    fun getMyBoardsSearch(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<HistoryResponse>

    // 댓글단 글 검색 조회
    @GET("/user/find/mycomments")//수정 필요
    fun getMyCommentsSearch(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<HistoryResponse>

    // 좋아요한 글 검색 조회
    @GET("/user/find/mylikes")//수정 필요
    fun getMyLikesSeach(
        @Header("authorization") authorization : String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<HistoryResponse>

    // 타인 프로필 조회
    @GET("/user/{id}/find")
    fun getUserProfile(
        @Path(value = "id") id: Int
    ): Call<FindProfileResponse>

    // 타인 작성한 글 리스트 조회
    @GET("/user/{id}/boards")
    fun getUserProfileBoards(
        @Path(value = "id") id: Int,
        @Query(value = "page") page: Int
    ): Call<FindProfileBoardsResponse>

    // 타인 댓글단 글 리스트 조회
    @GET("/user/{id}/comments")
    fun getUserProfileComments(
        @Path(value = "id") id: Int,
        @Query(value = "page") page: Int
    ): Call<FindProfileCommentsResponse>

    // 커뮤니티 화면 게시판들 조회
    @GET("/board/main")
    fun getBoardMain(
        @Header("authorization") authorization : String?
    ): Call<BoardMainResponse>

    // 전공 게시판 목록 조회
    @GET("/board/{major_id}/major")
    fun getBoardMajor(
        @Path(value = "major_id") major_id: Int,
        @Query(value = "page") page: Int
    ): Call<BoardMajorListResponse>

    // 핫한 게시판 목록 조회
    @GET("/board/hot/{major_id}")
    fun getBoardHot(
        @Path(value = "major_id") major_id: Int,
        @Query(value = "page") page: Int
    ): Call<BoardHotResponse>

    // 전체 게시판 목록 조회
    @GET("/board/list")
    fun getBoardAll(
        @Query(value = "page") page: Int
    ): Call<BoardAllListResponse>

    // 게시물 조회 (내용, 댓글, 대댓글 등)
    @GET("/board/{board_id}")
    fun getBoard(
        @Path(value = "board_id") board_id: Int,
        @Query(value = "page") page: Int
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
    @GET("/board/search-major/{major_id}/title/{search_keyWord}")
    fun getBoardMajorSearchTitle(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 제목 검색
    @GET("/board/search-hot/title/{search_keyWord}")
    fun getBoardHotSearchTitle(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 제목 검색
    @GET("/board/search-all/title/{search_keyWord}")
    fun getBoardAllSearchTitle(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 내용 검색
    @GET("/board/search-major/{major_id}/content/{search_keyWord}")
    fun getBoardMajorSearchContent(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 내용 검색
    @GET("/board/search-hot/content/{search_keyWord}")
    fun getBoardHotSearchContent(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 내용 검색
    @GET("/board/search-all/content/{search_keyWord}")
    fun getBoardAllSearchContent(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 제목+내용 검색
    @GET("/board/search-major/{major_id}/title-content/{search_keyWord}")
    fun getBoardMajorSearch(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 제목+내용 검색
    @GET("/board/search-hot/title-content/{search_keyWord}")
    fun getBoardHotSearch(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 제목+내용 검색
    @GET("/board/search-all/title-content/{search_keyWord}")
    fun getBoardAllSearch(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 작성자 검색
    @GET("/board/search-major/{major_id}/nickname/{search_keyWord}")
    fun getBoardMajorSearchUser(
        @Path("major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 작성자 검색
    @GET("/board/search-hot/nickname/{search_keyWord}")
    fun getBoardHotSearchUser(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 작성자 검색
    @GET("/board/search-all/nickname/{search_keyWord}")
    fun getBoardAllSearchUser(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 공지 목록 조회
    @GET("/notice/paging")
    fun getAnnouncementsList(
        @Query(value = "page") page: Int
    ): Call<NoticeListResponse>

    // 공지 게시물 조회
    @GET("/notice/{notice_id}")
    fun getAnnouncement(
        @Path(value = "notice_id") notice_id: Int
    ): Call<NoticeDetailResponse>

    // 문의하기 문의 내역 조회
    @GET("/qna/my-list")
    fun getQNAList(
        @Query(value = "page") page: Int
    ): Call<QNAListResponse>

    // 문의하기 문의 세부내역 조회
    @GET("/qna/{qna-id}")
    fun getQNADetailList(
        @Path(value = "qna_id") qna_id: Int
    ): Call<QNADetailResponse>

    //자주 묻는 질문 전체 조회
    @GET("/faq/list-all")
    fun getFAQList(
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 검색어 조회
    @GET("/faq/list-word")
    fun getFAQSearchList(
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 커뮤니티 조회
    @GET("/faq/list-board")
    fun getFAQCommunityList(
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 문제 조회
    @GET("/faq/list-major")
    fun getFAQExampleList(
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 전체 검색
    @GET("/faq/find-all")
    fun getFAQSearchAll(
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 (검색어) 검색
    @GET("/faq/find-word")
    fun getFAQSearchWord(
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 (커뮤니티) 검색
    @GET("/faq/find-board")
    fun getFAQSearchBoard(
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 (문제) 리스트 검색
    @GET("/faq/find-major")
    fun getFAQSearchMajor(
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>


    // 알림 조회
    @GET("/user/alarm") // 수정 필요
    fun getAlarmSetting(
        @Header("authorization") authorization: String?
    )

    @GET("/major/myfavorite")
    fun getBookmarks(
        @Header("authorization") authorization: String?
    ): Call<FindAllFavoriteResponse>

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

    // 게시글 등록
    @Multipart
    @POST("/board/register")
    fun postBoardRegister(
        @Header("authorization") authorization: String?,
        //@Body request: BoardRegisterRequest
        @Part("request") request: RequestBody,

        @Part files: List<MultipartBody.Part>?
    ):  Call<ResponseBody>

    // 문의하기 등록
    @Multipart
    @POST("/qna/register")
    fun postQNARegister(
        @Header("authorization") authorization: String?,
        @Part("request") request: RequestBody,
        @Part files: List<MultipartBody.Part>?
    ): Call<ResponseBody>

    // 게시글 신고
    @POST("/board/report/{board_id}")
    fun postBoardReport(
        @Header("authorization") authorization: String?,
        @Path("board_id") board_id: Int,
        @Body request: BoardReportRequest
    ):Call<BoardReportResponse>

    // 게시글 좋아요 추가
    @POST("/board/like/{board_id}")
    fun postBoardLike(
        @Header("authorization") authorization : String?,
        @Path("board_id") board_id: Int,
    ):Call<BoardLikeResponse>

    // 댓글 등록
    @Multipart
    @POST("/pin/{board_id}/register")
    fun postPinRegister(
        @Header("authorization") authorization: String,
        @Path("board_id") board_id: Int,
        @Part request: MultipartBody.Part, // 텍스트 데이터
        @Part files: List<MultipartBody.Part> // 이미지 파일 배열
    ):Call<CommentRegisterResponse>

    // 댓글 신고
    @POST("/pin/report/{pin_id}")
    fun postPinReport(
        @Path("pin_id") pin_id: Int,
        @Body request: CommentRegisterRequest
    ):Call<CommentReportResponse>

    // 댓글 좋아요
    @POST("/pin/like/{pin_id}")
    fun postPinLike(
        @Header("authorization") authorization: String,
        @Path("pin_id") pin_id: Int
    ):Call<CommentLikeReponse>

    // 대댓글 등록
    @Multipart
    @POST("/comment/{pinId}/register")
    fun postCommentRegister(
        @Header("authorization") authorization: String,
        @Path("pinId") pinId: Int,
        @Part request: MultipartBody.Part, // 텍스트 데이터
        @Part files: List<MultipartBody.Part> // 이미지 파일 배열
    ):Call<CommentRegisterResponse>

    // 대댓글 신고
    @POST("/comment/report/{comment_id}")
    fun postCommentReport(
        @Path("comment_id") comment_id: Int,
        @Body request: CommentReportRequest
    ):Call<CommentReportResponse>

    // 대댓글 좋아요 //
    @POST("/comment/like/{comment_id}")
    fun postCommentLike(
        @Header("authorization") authorization: String,
        @Path("comment_id") comment_id: Int
    ):Call<CommentLikeReponse>

    // 즐겨찾기 등록
    @POST("/major/{example_id}")
    fun postBookmark(
        @Path("example_id") example_id: Int
    ):Call<RegisterFavoriteExampleResponse>

    // AccessToken 재발급
    @POST("/user/token-reissue")
    fun postGetAccessToken(
        @Header("Cookie") Cookie: String
    ):Call<ReissueResponse>

    // 전공 검색하기(GPT)
    @POST("/major/find")
    fun postMajorFind(
        @Header("authorization") authorization: String,
        @Body request: majorExampleRequest
    ):Call<getExampleResponse>

    @POST("/major/example")
    fun postMajorExample(
        @Body request: exampleRegisterRequest
    ):Call<exampleRegisterResponse>

    // 로그아웃
    @POST("/user/logout")
    fun postLogout(
        @Header("authorization") authorization: String
    ): Call<LogoutResponse>

    //########PATCH##########

    // 비밀번호 찾기-재설정
    @PATCH("/user/find-pwd-restore")
    fun patchFindPwdRestore(
        @Body request: PwdRestoreRequest
    )

    // 비밀번호 변경
    @PATCH("/user/pwd-restore")
    fun patchPwdRestore(
        @Body request: PwdRestoreRequest
    )

    // 아이디 변경
    @PATCH("/user/id-restore")
    fun patchIdRestore(
        @Body request: IdRestoreRequest
    )

    // 닉네임 변경
    @PATCH("/user/nickname-restore")
    fun patchNickRestore(
        @Body request: NickNameRestoreRequest
    )

    // 전공 변경
    @PATCH("/user/major-restore")
    fun patchMajorRestore(
        @Body request: MajorRestoreRequest
    )

    // 프로필 사진 변경
    @Multipart
    @PATCH("/user/pic-restore")
    fun patchPicRestore(
        //@Body request: PicRestoreRequest
        @Header("authorization") authorization : String?,
        @Part file: MultipartBody.Part

    ): Call<Void>

    // 게시글 수정
    @Multipart
    @PATCH("/board/{board_id}")
    fun patchEditBoard(
        @Header("authorization") authorization : String?,
        @Path("board_id") boardId: Int,
        @Part("request") requestBody: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseBody>

    // 댓글 수정
    @PATCH("/pin/update")
    fun patchEditPin(
        @Body request: PinModifyRequest
    ): Call<CommentFindReponse>

    // 대댓글 수정
    @PATCH("/comment/update")
    fun patchEditComment(
        @Body request: CommentModifyRequest
    ): Call<CommentFindReponse>

    // 전체 알림 켜기/끄기
    @PATCH("/user/all-change")
    fun patchNoticeAllChange(
        @Header("authorization") authorization : String?
    )

    // 댓글 알림 켜기/끄기
    @PATCH("/user/pin-change")
    fun patchNoticePinChange(
        @Header("authorization") authorization : String?
    ): Call<CommonResponse>

    // 대댓글 알림 켜기/끄기
    @PATCH("/user/event-change")
    fun patchNoticeCommentChange(
        @Header("authorization") authorization : String?
    ): Call<CommonResponse>

    // 이벤트 알림 켜기/끄기
    @PATCH("/user/event-change")
    fun patchNoticeEventChange(
        @Header("authorization") authorization : String?
    )

    // 인기글 알림 켜기/끄기
    @PATCH("/user/hot-change")
    fun patchNoticeHotChange(
        @Header("authorization") authorization : String?
    )

    // 좋아요 알림 켜기/끄기
    @PATCH("/user/like-change")
    fun patchNoticeLikeChange(
        @Header("authorization") authorization : String?
    )

    // 공지 알림 켜기/끄기
    @PATCH("/user/notice-change")
    fun patchNoticeNoticeChange(
        @Header("authorization") authorization : String?
    )

    // 활동 내역 켜기/끄기
    @PATCH("/user/agreement-change")
    fun patchHistoryOpen(
        @Header("authorization") authorization : String?
    ):Call<AgreementChangeResponse>

    //######Delete########

    // 게시글 삭제
    @DELETE("/board/{board_id}")
    fun deleteBoard(
        @Header("authorization") authorization : String?,
        @Path("board_id")board_id: Int
    ):Call<BoardDeleteResponse>

    // 게시글 좋아요 취소
    @DELETE("/board/like/{board_id}")
    fun deleteBoardLike(
        @Header("authorization") authorization : String?,
        @Path("board_id")board_id: Int
    ):Call<BoardLikeResponse>

    // 댓글 삭제
    @DELETE("/pin/{pin_id}")
    fun deletePin(
        @Path("pin_id")pin_id: Int
    ): Call<CommentDeleteResponse>

    // 대댓글 삭제
    @DELETE("/comment/{comment_id}")
    fun deleteComment(
        @Path("comment_id")comment_id: Int
    ): Call<CommentDeleteResponse>

    // 즐겨찾기 삭제
//    @DELETE("/major/{favorite-id}")
//    fun deleteBookmark(
//
//    )

}