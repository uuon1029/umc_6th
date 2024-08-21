package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.Request.BoardReportRequest
import com.example.umc_6th.Retrofit.Request.CommentModifyRequest
import com.example.umc_6th.Retrofit.Request.CommentReportRequest
import com.example.umc_6th.Retrofit.Request.IdRestoreRequest
import com.example.umc_6th.Retrofit.Request.LoginRequest
import com.example.umc_6th.Retrofit.Request.MajorRestoreRequest
import com.example.umc_6th.Retrofit.Request.NickNameRestoreRequest
import com.example.umc_6th.Retrofit.Request.PinModifyRequest
import com.example.umc_6th.Retrofit.Request.PwdRestoreRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.Request.SuspensionRequest
import com.example.umc_6th.Retrofit.Request.exampleRegisterRequest
import com.example.umc_6th.Retrofit.Request.majorExampleRequest
import com.example.umc_6th.Retrofit.Request.AnnouncementRegisterRequest
import com.example.umc_6th.Retrofit.Request.FAQRegisterRequest
import com.example.umc_6th.Retrofit.Request.QNACommentRequest
import com.example.umc_6th.Retrofit.Request.QNACommentModifyRequest
import com.example.umc_6th.Retrofit.Request.FAQModifyRequest
import com.example.umc_6th.Retrofit.Request.AnnouncementModifyRequest
import com.example.umc_6th.Retrofit.Request.PhoneAuthRequest

//Response
import com.example.umc_6th.Retrofit.Request.warnRequest
import com.example.umc_6th.Retrofit.Response.RootComplaintAllListResponse
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
import com.example.umc_6th.Retrofit.Response.DeleteFavoriteResponse
import com.example.umc_6th.Retrofit.Response.GetExampleByIdResponse
import com.example.umc_6th.Retrofit.Response.exampleRegisterResponse
import com.example.umc_6th.Retrofit.Response.getExampleResponse
import com.example.umc_6th.Retrofit.Response.LogoutResponse
import com.example.umc_6th.Retrofit.Response.MainPageRes
import com.example.umc_6th.Retrofit.Response.MajorAnswerResponse
import com.example.umc_6th.Retrofit.Response.PhoneAuthResponse
import com.example.umc_6th.Retrofit.Response.ResultBooleanResponse
import com.example.umc_6th.Retrofit.Response.RootBoardComplaintResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintBoardsResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintCommentResponse
import com.example.umc_6th.Retrofit.Response.RootComplaintDetailResponse
import com.example.umc_6th.Retrofit.Response.RootFAQDeleteResponse
import com.example.umc_6th.Retrofit.Response.RootFindDetailUserResponse
import com.example.umc_6th.Retrofit.Response.RootNoticeResponse
import com.example.umc_6th.Retrofit.Response.RootQNADeleteResponse
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.Retrofit.Response.RootUserFindReportBoards
import com.example.umc_6th.Retrofit.Response.RootUserFindReportCommentOrPin
import com.example.umc_6th.Retrofit.Response.UserGetMajorRes
import com.example.umc_6th.Retrofit.Response.UserMajorRestoreResponse
import com.example.umc_6th.Retrofit.Response.RootFindUsersResponse
import com.example.umc_6th.Retrofit.Response.RootQnAListResponse
import com.example.umc_6th.Retrofit.Response.SuspendResponse
import com.example.umc_6th.Retrofit.Response.WarnResponse
import com.example.umc_6th.Retrofit.Response.getExampleBoardResponse
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
        @Query("phone") phone : String
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
//        @Header("authorization") authorization : String?,
        @Path(value = "id") id: Int,
        @Query(value = "page") page: Int
    ): Call<FindProfileBoardsResponse>

    // 타인 댓글단 글 리스트 조회
    @GET("/user/{id}/comments")
    fun getUserProfileComments(
//        @Header("authorization") authorization : String?,
        @Path(value = "id") id: Int,
        @Query(value = "page") page: Int
    ): Call<FindProfileBoardsResponse>

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
        @Header("authorization") authorization: String?,
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
        @Header("authorization") authorization: String,
        @Query(value = "page") page: Int
    ): Call<NoticeListResponse>

    // 공지 게시물 조회
    @GET("/notice/{notice_id}")
    fun getAnnouncement(
        @Header("authorization") authorization: String,
        @Path(value = "notice_id") notice_id: Int
    ): Call<NoticeDetailResponse>

    // 문의하기 문의 내역 조회
    @GET("/qna/my-list")
    fun getQNAList(
        @Header("authorization") authorization: String,
        @Query(value = "page") page: Int
    ): Call<QNAListResponse>

    // 문의하기 문의 세부내역 조회
    @GET("/qna/{qna_id}")
    fun getQNADetailList(
        @Header("authorization") authorization: String,
        @Path(value = "qna_id") qna_id: Int
    ): Call<QNADetailResponse>

    //자주 묻는 질문 전체 조회
    @GET("/faq/list-all")
    fun getFAQList(
        @Header("authorization") authorization: String,
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 검색어 조회
    @GET("/faq/list-word")
    fun getFAQSearchList(
        @Header("authorization") authorization: String,
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 커뮤니티 조회
    @GET("/faq/list-board")
    fun getFAQCommunityList(
        @Header("authorization") authorization: String,
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 문제 조회
    @GET("/faq/list-major")
    fun getFAQExampleList(
        @Header("authorization") authorization: String,
        @Query(value ="page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 전체 검색
    @GET("/faq/find-all")
    fun getFAQSearchAll(
        @Header("authorization") authorization: String,
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 (검색어) 검색
    @GET("/faq/find-word")
    fun getFAQSearchWord(
        @Header("authorization") authorization: String,
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 (커뮤니티) 검색
    @GET("/faq/find-board")
    fun getFAQSearchBoard(
        @Header("authorization") authorization: String,
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>

    //자주 묻는 질문 (문제) 리스트 검색
    @GET("/faq/find-major")
    fun getFAQSearchMajor(
        @Header("authorization") authorization: String,
        @Query(value = "content") content: String,
        @Query(value = "page") page: Int
    ): Call<FAQListAllResponse>


    // 알림 조회
    @GET("/user/alarm") // 수정 필요
    fun getAlarmSetting(
        @Header("authorization") authorization: String?
    )

    // 즐겨찾기 조회
    @GET("/major/myfavorite")
    fun getBookmarks(
        @Header("authorization") authorization: String?
    ): Call<FindAllFavoriteResponse>

    // 즐겨찾기 상세 조회
    @GET("/major/{favorite_id}/detail")
    fun getBookmark(
        @Header("authorization") authorization: String?,
        @Path("favorite_id") favorite_id: Int
    ): Call<GetExampleByIdResponse>


    // 신고 내역 전체 조회
    @GET("/root/user/complaint/all")
    fun getAdminReportList(
        @Header("authorization") authorization: String?,
        @Query("page") page: Int
    ): Call<RootComplaintAllListResponse>

    // 신고 내역 게시글 조회
    @GET("/root/user/complaint/board?report/listboard")
    fun getAdminReportBoardList(
        @Header("authorization") authorization: String?,
        @Query("page") page: Int
    ): Call<RootComplaintBoardsResponse>

    // 신고 내역 댓글 조회
    @GET("/root/user/complaint/comment")
    fun getAdminReportCommentList(
        @Header("authorization") authorization: String?,
        @Query("page") page: Int
    ): Call<RootComplaintCommentResponse>

    // 문의 전체 리스트 + 페이징
    @GET("/root/qna/list-all")
    fun getRootQNAAllList(
        @Header("authorization") authorization: String?,
        @Query(value = "page") page: Int
    ): Call<RootQnAListResponse>

    // 문의 답변 완료 리스트 + 페이징
    @GET("/root/qna/list-answered")
    fun getRootQNAAnsweredList(
        @Header("authorization") authorization: String?,
        @Query(value = "page") page: Int
    ): Call<RootQnAListResponse>

    // 문의 대기중 리스트 + 페이징
    @GET("/root/qna/list-waiting")
    fun getRootQNAWaitingList(
        @Header("authorization") authorization: String?,
        @Query(value = "page") page: Int
    ): Call<RootQnAListResponse>

    // 문의 글 보기
    @GET("/root/qna/{qna_id}")
    fun getRootQNAView(
        @Header("authorization") authorization: String?,
        @Path(value = "qna_id") qna_id: Int
    ): Call<RootQNAViewResponse>

    // 관리자 유저 프로필
    @GET("/root/user/{userId}")
    fun getAdminProfile(
        @Header("authorization") authorization: String?,
        @Path("userId") userId : Int
    ): Call<RootFindDetailUserResponse>

    // 관리자 유저 신고 질문글
    @GET("/root/user/{userId}/boards")
    fun getAdminProfileBoard(
        @Header("authorization") authorization: String?,
        @Path("userId") userId : Int,
        @Query("page") page : Int
    ) : Call<RootUserFindReportBoards>

    //문의 전체 검색
    @GET("/root/qna/search/all")
    fun getAdminQNASearchAll(
        @Header("authorization") authorization: String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<RootQnAListResponse>

    //문의 답변 검색
    @GET("/root/qna/search/answered")
    fun getAdminQNASearchAnswered(
        @Header("authorization") authorization: String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<RootQnAListResponse>

    //문의 대기중 검색
    @GET("/root/qna/search/waiting")
    fun getAdminQNASearchWaiting(
        @Header("authorization") authorization: String?,
        @Query(value = "page") page: Int,
        @Query(value = "content") content: String
    ): Call<RootQnAListResponse>

    //회원 검색 (전체) + 페이징
    @GET("/root/user/find/all")
    fun getRootFindUsersAll(
        @Query(value = "keyword") keyword: String,
        @Query(value = "paging") paging : Int
    ): Call<RootFindUsersResponse>

    //회원 검색 (닉네임) + 페이징
    @GET("/root/user/find/nickname")
    fun getRootFindUsersNickname(
        @Query(value = "keyword") keyword: String,
        @Query(value = "paging") paging : Int
    ): Call<RootFindUsersResponse>
    //회원 검색 (실명) + 페이징
    @GET("/root/user/find/name")
    fun getRootFindUsersName(
        @Query(value = "keyword") keyword: String,
        @Query(value = "paging") paging : Int
    ): Call<RootFindUsersResponse>
    //회원 검색 (아이디) + 페이징
    @GET("/root/user/find/account")
    fun getRootFindUsersAccount(
        @Query(value = "keyword") keyword: String,
        @Query(value = "paging") paging : Int
    ): Call<RootFindUsersResponse>
    // 관리자 유저 신고 댓글
    @GET("/root/user/{userId}/pins")
    fun getAdminProfileComment(
        @Header("authorization") authorization: String?,
        @Path("userId") userId : Int,
        @Query("page") page : Int
    ) : Call<RootUserFindReportCommentOrPin>

    // 유저 전공 조회
    @GET("/user/find/major")
    fun getUserMajor(
        @Header("authorization") authorization: String?
    ): Call<UserGetMajorRes>

    // 예제 조회
    @GET("/major/{example_id}")
    fun getExample(
        @Path("example_id") example_id: Int
    ): Call<getExampleBoardResponse>

    //신고된 글 조회 + 신고 사유
    @GET("/root/user/complaint/board/{boardplaintId}")
    fun getAdminComplaintBoard(
        @Path("boardplaintId") boardplaintId : Int
    ): Call<RootBoardComplaintResponse>

    //신고된 댓글 조회 + 신고 사유
    @GET("/root/user/complaint/pin/{pincomplaintId}")
    fun getAdminComplaintPin(
        @Path("pincomplaintId") pincomplaintId : Int
    ): Call<RootComplaintDetailResponse>

    //신고된 대댓글 조회 + 신고 사유
    @GET("/root/user/complaint/comment/{commentplaintId}")
    fun getAdminComplaintComment(
        @Path("commentplaintId") commentplaintId : Int
    ): Call<RootComplaintDetailResponse>

    // 메인화면 최근 전공질문 올리기
    @GET("/major/main")
    fun getMainHomeBoard(
        @Header("authorization") authorization: String?
    ): Call<MainPageRes>

    // 메인화면 질문 보기
    @GET("/major/answer/{answerId}")
    fun getMajorAnswer(
        @Header("authorization") authorization: String?,
        @Path("answerId") answerId : Int
    ): Call<MajorAnswerResponse>

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
        @Header("authorization") authorization: String?,
        @Path("pin_id") pin_id: Int,
        @Body request: CommentReportRequest
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
        @Header("authorization") authorization: String?,
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
        @Header("authorization") authorization: String,
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

    //관리자 - 공지사항 등록
    @POST("/root/notice/register")
    fun postRootNoticeRegister(
        @Header("authorization") authorization: String,
        @Body request: AnnouncementRegisterRequest
    ): Call<Void>

    //관리자 - 자주 묻는 질문 등록
    @POST("/root/faq/register")
    fun postRootFAQRegister(
        @Header("authorization") authorization: String,
        @Body request: FAQRegisterRequest
    ): Call<Void>

    //관리자 - 문의 답변 등록
    @POST("/root/qna/{qna-id}")
    fun postRootQNAReplyRegister(
        @Header("authorization") authorization: String,
        @Path("qna_id")qnaId: Int,
        @Body request: QNACommentRequest
    ): Call<Void>

    // 관리자 / 경고 부여
    @POST("/root/user/warn")
    fun postAdminWarn(
        @Header("authorization") authorization: String,
        @Body request: warnRequest
    ): Call<WarnResponse>

    // 관리자 / 사용자 정지
    @POST("/root/user/suspension")
    fun postAdminSuspension(
        @Header("authorization") authorization: String,
        @Body request: SuspensionRequest
    ): Call<SuspendResponse>

    // 휴대폰 인증
    @POST("/user/phone-auth")
    fun postPhoneAuth(
        @Body request: PhoneAuthRequest
    ): Call<PhoneAuthResponse>


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
        @Header("authorization") authorization : String?,
        @Body request: MajorRestoreRequest
    ): Call<UserMajorRestoreResponse>

    // 프로필 사진 변경
    @Multipart
    @PATCH("/user/pic-restore")
    fun patchPicRestore(
        //@Body request: PicRestoreRequest
        @Header("authorization") authorization : String?,
        @Part file: MultipartBody.Part

    ): Call<Void>

    @PATCH("/user/pic-default")
    fun patchPicBase(
        @Header("authorization") authorization : String?
    ): Call<Void>

    // 게시글 수정
    @Multipart
    @PATCH("/board/{board_id}")
    fun patchEditBoard(
        @Header("authorization") authorization: String?,
        @Path("board_id") boardId: Int,
        @Part("request") requestBody: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseBody>

    // 댓글 수정
    @Multipart
    @PATCH("/pin/update")
    fun patchEditPin(
        @Header("authorization") authorization: String?,
        @Part("request") requestBody: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseBody>

    // 대댓글 수정
    @Multipart
    @PATCH("/comment/update")
    fun patchEditComment(
        @Header("authorization") authorization: String?,
        @Part("request") requestBody: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Call<ResponseBody>

    //관리자 - 문의 답변 수정
    @PATCH("/root/qna/{qna-id}")
    fun patchRootQNAReplyModify(
        @Header("authorization") authorization : String?,
        @Path("qna-id") qnaId: Int,
        @Body request: QNACommentModifyRequest
    ): Call<Void>

    //관리자 - 자주 묻는 질문 수정
    @PATCH("/root/faq/{faq-id}")
    fun patchRootFAQModify(
        @Header("authorization") authorization : String?,
        @Path("faq-id") faqId: Int,
        @Body request: FAQModifyRequest
    ): Call<Void>

    //관리자 - 공지사항 수정
    @PATCH("/root/notice/{noticeId}/update")
    fun patchRootNoticeModify(
        @Header("authorization") authorization : String?,
        @Path("noticeId") noticeId: Int,
        @Body request: AnnouncementModifyRequest
    ): Call<Void>

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
        @Header("authorization") authorization : String?,
        @Path("pin_id")pin_id: Int
    ): Call<CommentDeleteResponse>

    // 대댓글 삭제
    @DELETE("/comment/{comment_id}")
    fun deleteComment(
        @Header("authorization") authorization : String?,
        @Path("comment_id")comment_id: Int
    ):Call<CommentDeleteResponse>

    // 자주 묻는 질문 삭제
    @DELETE("/root/faq/{faq_id}")
    fun deleteFAQ(
        @Header("authorization") authorization : String?,
        @Path("faq_id")faq_id: Int
    ):Call<RootFAQDeleteResponse>

    // 문의 답변 삭제하기
    @DELETE("/root/qna")
    fun deleteRootQna(
        @Header("authorization") authorization : String?,
        @Path("qna_id")qna_id: Int
    ):Call<RootQNADeleteResponse>

    // 공지사항 삭제하기
    @DELETE("/root/notice/{noticeid}/delete")
    fun deleteRootNotice(
        @Header("authorization") authorization : String?,
        @Path("notice_id")notice_id: Int
    ):Call<RootNoticeResponse>

//     즐겨찾기 삭제
    @DELETE("/major/{favorite_id}")
    fun deleteBookmark(
        @Header("authorization") authorization : String?,
        @Path("favorite_id") favorite_id: Int
    ): Call<DeleteFavoriteResponse>

}