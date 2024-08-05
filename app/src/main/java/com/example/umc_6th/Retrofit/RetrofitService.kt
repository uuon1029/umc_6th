package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.Request.AccountDupRequest
import com.example.umc_6th.Retrofit.Request.BoardModifyRequest
import com.example.umc_6th.Retrofit.Request.BoardRegisterRequest
import com.example.umc_6th.Retrofit.Request.CommentModifyRequest
import com.example.umc_6th.Retrofit.Request.CommentRegisterRequest
import com.example.umc_6th.Retrofit.Request.CommentReportRequest
import com.example.umc_6th.Retrofit.Request.FindIdRequest
import com.example.umc_6th.Retrofit.Request.FindPwdRequest
import com.example.umc_6th.Retrofit.Request.IdRestoreRequest
import com.example.umc_6th.Retrofit.Request.LoginRequest
import com.example.umc_6th.Retrofit.Request.MajorRestoreRequest
import com.example.umc_6th.Retrofit.Request.NickNameDupRequest
import com.example.umc_6th.Retrofit.Request.NickNameRestoreRequest
import com.example.umc_6th.Retrofit.Request.PicRestoreRequest
import com.example.umc_6th.Retrofit.Request.PinModifyRequest
import com.example.umc_6th.Retrofit.Request.PwdRestoreRequest
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.Response.CommentDeleteReponse
import com.example.umc_6th.Retrofit.Response.CommentLikeReponse
import com.example.umc_6th.Retrofit.Response.CommentRegisterResponse
import com.example.umc_6th.Retrofit.Response.RegisterFavoriteExampleResponse
import okhttp3.RequestBody
import org.w3c.dom.Comment
import retrofit2.http.*
import retrofit2.Call

interface RetrofitService {

    //########GET##############

    // 닉네임 중복 확인
    @GET("/user/nickname-dup") //수정 필요
    fun getNickNameDup(): Call<Boolean>

    // 아이디 중복 확인
    @GET("/user/account-dup") //수정 필요
    fun getAccountDup(): Call<Boolean>

    // 아이디 찾기
    @GET("/user/find-id") // 수정 필요
    fun getFindId(
    ): Call<FindAccountResponse>

    // 비밀번호 찾기
    @GET("/user/find-pwd") // 수정 필요
    fun getFindPwd(): Call<FindPwdResponse>

    // 활동내역 전체 조회
    @GET("/user/history?paging={page}")
    fun getHistory(
        @Query(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 내가 쓴 글 전체 조회
    @GET("/user/myboards?paging={page}")
    fun getMyBoards(
        @Query(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 댓글단 글 전체 조회
    @GET("/user/mycomments?paging={page}")
    fun getMyComments(
        @Query(value = "page") page: Int
    ): Call<myCommentsResponse>

    // 좋아요한 글 전체 조회
    @GET("/user/mylikes?paging={page}")
    fun getMyLikes(
        @Query(value = "page") page: Int
    ): Call<myLikesResponse>

    // 활동내역 검색 조회
    @GET("/user/history?paging={page}")//수정 필요
    fun getHistorySearch(
        @Query(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 내가 쓴 글 검색 조회
    @GET("/user/myboards?paging={page}")//수정 필요
    fun getMyBoardsSearch(
        @Query(value = "page") page: Int
    ): Call<myBoardsResponse>

    // 댓글단 글 검색 조회
    @GET("/user/mycomments?paging={page}")//수정 필요
    fun getMyCommentsSearch(
        @Query(value = "page") page: Int
    ): Call<myCommentsResponse>

    // 좋아요한 글 검색 조회
    @GET("/user/mylikes?paging={page}")//수정 필요
    fun getMyLikesSeach(
        @Query(value = "page") page: Int
    ): Call<myLikesResponse>

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
    @GET("/board/search-major/{major_id}/title/{search_keyWord}&paging={page}")
    fun getBoardMajorSearchTitle(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 제목 검색
    @GET("/board/search-hot/title/{search_keyWord}&paging={page}")
    fun getBoardHotSearchTitle(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 제목 검색
    @GET("/board/search-all/title/{search_keyWord}&paging={page}")
    fun getBoardAllSearchTitle(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 내용 검색
    @GET("/board/search-major/{major_id}/content/{search_keyWord}&paging={page}")
    fun getBoardMajorSearchContent(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 내용 검색
    @GET("/board/search-hot/content/{search_keyWord}&paging={page}")
    fun getBoardHotSearchContent(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 내용 검색
    @GET("/board/search-all/content/{search_keyWord}&paging={page}")
    fun getBoardAllSearchContent(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 제목+내용 검색
    @GET("/board/search-major/{major_id}/title-content/{search_keyWord}&paging={page}")
    fun getBoardMajorSearch(
        @Path(value = "major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchMajorResponse>

    // 핫한 게시판 제목+내용 검색
    @GET("/board/search-hot/title-content/{search_keyWord}&paging={page}")
    fun getBoardHotSearch(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 제목+내용 검색
    @GET("/board/search-all/title-content/{search_keyWord}&paging={page}")
    fun getBoardAllSearch(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 전공 게시판 작성자 검색
    @GET("/board/search-major/{major_id}/nickname/{search_keyWord}&paging={page}")
    fun getBoardMajorSearchUser(
        @Path("major_id") major_id: Int,
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchAllResponse>

    // 핫한 게시판 작성자 검색
    @GET("/board/search-hot/nickname/{search_keyWord}&paging={page}")
    fun getBoardHotSearchUser(
        @Path(value = "search_keyWord") search_keyWord: String,
        @Query(value = "page") page: Int
    ): Call<BoardSearchHotResponse>

    // 전체 게시판 작성자 검색
    @GET("/board/search-all/nickname/{search_keyWord}&paging={page}")
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
    @POST("/board/register")
    fun postBoardRegister(
        @Header("authorization") authorization: String?,
        @Body request: BoardRegisterRequest
    )

    // 게시글 신고
    @POST("/board/report/{board_id}")
    fun postBoardReport(
        @Path("board_id") board_id: Int,
        @Body request: BoardRegisterRequest
    )

    // 댓글 등록
    @POST("/pin/{board_id}/register")
    fun postPinRegister(
        @Path("board_id") board_id: Int,
        @Body request: CommentRegisterRequest
    ):Call<CommentRegisterResponse>

    // 댓글 신고
    @POST("/pin/report/{pin_id}")
    fun postPinReport(
        @Path("pin_id") pin_id: Int,
        @Body request: CommentRegisterRequest
    )

    // 댓글 좋아요
    @POST("/pin/like/{pin_id}")
    fun postPinLike(
        @Path("pin_id") pin_id: Int
    ):Call<CommentLikeReponse>

    // 대댓글 등록
    @POST("/comment/{pin_id}/register")
    fun postCommentRegister(
        @Path("pin_id") pin_id: Int,
        @Body request: CommentRegisterRequest
    ):Call<CommentRegisterResponse>

    // 대댓글 신고
    @POST("/comment/report/{comment_id}")
    fun postCommentReport(
        @Path("comment_id") comment_id: Int,
        @Body request: CommentReportRequest
    )

    // 대댓글 좋아요 //
    @POST("/comment/like/{comment_id}")
    fun postCommentLike(
        @Path("comment_id") comment_id: Int,
    ):Call<CommentLikeReponse>

    // 즐겨찾기 등록
    @POST("/major/{example_id}")
    fun postBookmark(
        @Path("example_id") example_id: Int
    ):Call<RegisterFavoriteExampleResponse>

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
    @PATCH("/user/pic-restore")
    fun patchPicRestore(
        @Body request: PicRestoreRequest
    )

    // 게시글 수정
    @PATCH("/board/{board_id}")
    fun patchEditBoard(
        @Path("board_id") board_id: Int,
        @Body request: BoardModifyRequest
    )

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

    //######Delete########

    // 게시글 삭제
    @DELETE("/board/{board_id}")
    fun deleteBoard(
        @Path("board_id")board_id: Int
    )

    // 댓글 삭제
    @DELETE("/pin/{pin_id}")
    fun deletePin(
        @Path("pin_id")pin_id: Int
    ): Call<CommentDeleteReponse>

    // 대댓글 삭제
    @DELETE("/comment/{comment_id}")
    fun deleteComment(
        @Path("comment_id")comment_id: Int
    ): Call<CommentDeleteReponse>

    // 즐겨찾기 삭제
//    @DELETE("/major/{favorite-id}")
//    fun deleteBookmark(
//
//    )

}