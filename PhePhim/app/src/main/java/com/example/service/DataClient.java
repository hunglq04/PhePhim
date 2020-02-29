package com.example.service;

import android.widget.Spinner;

import com.example.model.BaiViet;
import com.example.model.BinhLuanBaiViet;
import com.example.model.BinhLuanPhim;
import com.example.model.Phim;
import com.example.model.User;

import java.util.List;
import java.util.ListIterator;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {

    @Multipart
    @POST("uploadImage.php")
    Call<String> uploadImage(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("registerAccount.php")
    Call<String> registerAccount(@Field("uid") String uid,
                                 @Field("name") String name,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("checkLogin.php")
    Call<List<User>> checkLogin(@Field("uid") String email,
                                @Field("password") String password);

    @GET("deleteAccount.php")
    Call<String> deleteUser(@Query("id") String id,
                            @Query("avatar") String avatar);

    @GET("getPhimHot.php")
    Call<List<Phim>> getPhimHot(@Query("tenPhim") String tenPhim);

    @GET("getBaiViet.php")
    Call<List<BaiViet>> getBaiViet(@Query("maBaiViet") String maBaiViet);

    @GET("getBaiVietByTheLoai.php")
    Call<List<BaiViet>> getBaiVietByTheLoai(@Query("maTheLoai") String maTheLoai);

    @GET("searchPhim.php")
    Call<List<Phim>> searchPhim(@Query("tenPhim") String tenPhim,
                                @Query("theLoai") int maTheLoai);

    @GET("getBinhLuanPhim.php")
    Call<List<BinhLuanPhim>> getBinhLuanPhim (@Query("maPhim") String maPhim,
                                              @Query("sapXep") int sapXep);

    @FormUrlEncoded
    @POST("insertBinhLuanPhim.php")
    Call<String> insertBinhLuanPhim (@Field("maPhim") String maPhim,
                                                 @Field("email") String email,
                                                 @Field("noiDung") String noiDung,
                                                 @Field("diemPhim") int diemPhim);

    @GET("getBinhLuanBaiViet.php")
    Call<List<BinhLuanBaiViet>> getBinhLuanBaiViet (@Query("maBaiViet") String maBaiVet,
                                                    @Query("sapXep") int sapXep);

    @FormUrlEncoded
    @POST("insertBinhLuanBaiViet.php")
    Call<String> insertBinhLuanBaiViet (@Field("maBaiViet") String maBaiViet,
                                     @Field("email") String email,
                                     @Field("noiDung") String noiDung);

    @GET("getBinhLuanPhimOfUser.php")
    Call<List<BinhLuanPhim>> getBinhLuanPhimOfUser (@Query("email") String email,
                                              @Query("maPhim") String maPhim);

    @FormUrlEncoded
    @POST("updateUserName.php")
    Call<String> updateUserName (@Field("email") String email,
                                     @Field("ten") String ten);

    @FormUrlEncoded
    @POST("updateUserImageUrl.php")
    Call<String> updateUserImage (@Field("email") String email,
                                 @Field("imageUrl") String imageUrl);

    @GET("getAllBinhLuanPhimOfUser.php")
    Call<List<BinhLuanPhim>> getAllBinhLuanPhim (@Query("email") String email);

    @GET("getAllBaiVietOfUser.php")
    Call<List<BaiViet>> getAllBaiVietOfUser(@Query("email") String email);

    @GET("getLuuBaiVietOfUser.php")
    Call<List<BaiViet>> getLuuBaiVietOfUser(@Query("email") String email);

    @FormUrlEncoded
    @POST("updateBinhLuanPhimOfUser.php")
    Call<String> updateBinhLuanPhimOfUser (@Field("email") String email,
                                           @Field("maPhim") String maPhim,
                                           @Field("noiDung") String noiDung);

    @GET("deleteBinhLuanPhim.php")
    Call<String> deleteBinhLuanPhim (@Query("email") String email,
                                     @Query("maPhim") String maPhim);

    @FormUrlEncoded
    @POST("insertBaiViet.php")
    Call<String> insertBaiViet (@Field("email") String email,
                                @Field("tieuDe") String tieuDe,
                                @Field("noiDung")String noiDung,
                                @Field("theLoai") int maTheLoai,
                                @Field("anh") String anh);

    @FormUrlEncoded
    @POST("checkLuuBaiViet.php")
    Call<String> checkLuuBaiViet (@Field("maBaiViet") String maBaiViet,
                                   @Field("email") String email);

    @FormUrlEncoded
    @POST("insertLuuBaiViet.php")
    Call<String> insertLuuBaiViet (@Field("maBaiViet") String maBaiViet,
                                   @Field("email") String email);

    @FormUrlEncoded
    @POST("deleteLuuBaiViet.php")
    Call<String> deleteLuuBaiViet (@Field("maBaiViet") String maBaiViet,
                                   @Field("email") String email);
}
