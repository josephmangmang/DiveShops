package com.divetym.dive.rest;

import com.divetym.dive.common.SortOption;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.BoatListResponse;
import com.divetym.dive.models.response.BoatResponse;
import com.divetym.dive.models.response.CourseListResponse;
import com.divetym.dive.models.response.DailyTripListResponse;
import com.divetym.dive.models.response.DailyTripResponse;
import com.divetym.dive.models.response.DiveShopCourseListResponse;
import com.divetym.dive.models.response.DiveShopCourseResponse;
import com.divetym.dive.models.response.DiveShopListResponse;
import com.divetym.dive.models.response.DiveShopResponse;
import com.divetym.dive.models.response.DiveSiteListResponse;
import com.divetym.dive.models.response.GuideListResponse;
import com.divetym.dive.models.response.GuideResponse;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.models.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kali_root on 3/28/2017.
 */

public interface ApiInterface {
    /**
     * Diver and Dive Shop registration
     *
     * @param email
     * @param password
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Call<Response> register(@Field("email") String email, @Field("password") String password, @Field("type") String type);

    /**
     * Diver and Dive Shop login
     *
     * @param email
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> login(@Field("email") String email, @Field("password") String password);

    /**
     * Get a list of Course
     *
     * @param offset
     * @param sort
     * @param orderBy
     * @return
     */
    @GET("courses")
    Call<CourseListResponse> getCourses(@Query("offset") int offset, @Query("sort") String sort, @Query("order") String orderBy);

    /**
     * Get a list of Dive Site base on location
     *
     * @param lat
     * @param lng
     * @param radius
     * @param offset
     * @return
     */
    @GET("sites")
    Call<DiveSiteListResponse> getSites(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius, @Query("offset") int offset);

    /**
     * Get a list of Dive Site base on name
     *
     * @param searchName
     * @param offset
     * @return
     */
    @GET("sites")
    Call<DiveSiteListResponse> getSites(@Query("q") String searchName, @Query("offset") int offset);

    /**
     * Get a list of Dive Shop
     *
     * @param lat
     * @param lng
     * @param radius
     * @param offset
     * @return
     */
    @GET("diveshops")
    Call<DiveShopListResponse> getDiveShops(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius,
                                            @Query("offset") int offset);

    /**
     * Get dive shop informations
     *
     * @param shopUid
     * @return
     */
    @GET("diveshops/{shopUid}")
    Call<DiveShopResponse> getDiveShop(@Path("shopUid") String shopUid);

    /**
     * Get dive shop by uid
     *
     * @param uid
     * @return
     */
    @GET("users/dive_shop/{uid}")
    Call<DiveShopResponse> getDiveShopByUid(@Path("uid") String uid);

    /**
     * Get dive shop courses
     *
     * @param shopUid
     * @param offset
     * @param sort
     * @param orderBy
     * @return
     */
    @GET("diveshops/{shopUid}/courses")
    Call<DiveShopCourseListResponse> getDiveShopCourses(@Path("shopUid") String shopUid, @Query("offset") int offset, @Query("sort") String sort,
                                                        @Query("order") String orderBy);

    @GET("diveshops/{shopUid}/courses")
    Call<DiveShopCourseListResponse> getDiveShopCourses(@Path("shopUid") String shopUid, @Query("offset") int offset,
                                                        @Query("order") String orderBy);

    @GET("diveshops/{shopUid}/courses")
    Call<DiveShopCourseListResponse> getDiveShopCourses(@Path("shopUid") String shopUid, @Query("offset") int offset);

    /**
     * Update dive shop courses
     *
     * @param shopUid
     * @param shopCourseId
     * @param price
     * @return
     */
    @PUT("diveshops/{shopUid}/courses/{shopCourseId}")
    Call<DiveShopCourseResponse> updateDiveShopCourse(@Path("shopUid") String shopUid, @Path("shopCourseId") int shopCourseId, @Query("price") double price);

    /**
     * Add course on dive shop
     *
     * @param courseId
     * @param price
     * @return
     */
    @POST("diveshops/{shopUid}/courses")
    Call<DiveShopCourseResponse> addDiveShopCourse(@Field("course_id") int courseId, @Field("price") double price);

    /**
     * Add new boat
     *
     * @param name
     * @return
     */
    @POST("diveshops/{shopUid}/boats")
    Call<BoatResponse> addDiveShopBoat(@Path("shopUid") @Field("name") String name);

    /**
     * Get a list of boats
     *
     * @param offset
     * @return
     */
    @GET("diveshops/{shopUid}/boats")
    Call<BoatListResponse> getDiveShopBoats(@Path("shopUid") String shopUid, @Query("offset") int offset);

    /**
     * Get a list of boats base on name
     *
     * @param shopUid
     * @param offset
     * @param searchName
     * @return
     */
    @GET("diveshops/{shopUid}/boats")
    Call<BoatListResponse> getDiveShopBoats(@Path("shopUid") String shopUid, @Query("offset") int offset, @Query("q") String searchName);

    /**
     * Update boat
     *
     * @param shopUid
     * @param boatId
     * @param name
     * @return
     */
    @PUT("diveshops/{shopUid}/boats/{boatId}")
    Call<BoatResponse> updateDiveShopBoat(@Path("shopUid") String shopUid, @Path("boatId") int boatId, @Field("name") String name);

    /**
     * Delete boat
     *
     * @param shopUid
     * @param boatId
     * @return
     */
    @DELETE("diveshops/{shopUid}/boats/{boatId}")
    Call<Response> deleteDiveShopBoat(@Path("shopUid") String shopUid, @Path("boatId") int boatId);

    /**
     * Get list of Dive Shop Dive Trips
     *
     * @param shopUid
     * @param startDate
     * @param endDate
     * @return
     */
    @GET("diveshops/{shopUid}/trips")
    Call<DailyTripListResponse> getDiveShopTrips(@Path("shopUid") String shopUid, @Query("start_date") String startDate, @Query("end_date") String endDate);

    /**
     * Get list of Dive Shop Dive Trips
     *
     * @param shopUid
     * @param diveSiteId
     * @param startDate
     * @param endDate
     * @return
     */
    @GET("diveshops/{shopUid}/trips")
    Call<DailyTripListResponse> getDiveShopTrips(
            @Path("shopUid") String shopUid, @Query("dive_site_id") int diveSiteId,
            @Query("start_date") String startDate, @Query("end_date") String endDate);

    /**
     * Get list of Dive Shop Dive Trips
     *
     * @param shopUid
     * @param diveSiteId
     * @param startDate
     * @param endDate
     * @param offset
     * @return
     */
    @GET("diveshops/{shopUid}/trips")
    Call<DailyTripListResponse> getDiveShopTrips(
            @Path("shopUid") String shopUid, @Query("dive_site_id") int diveSiteId,
            @Query("start_date") String startDate, @Query("end_date") String endDate,
            @Query("offset") int offset);

    /**
     * Get list of Dive Shop Dive Trips
     *
     * @param shopUid
     * @param diveSiteId
     * @param startDate
     * @param endDate
     * @param offset
     * @param sort
     * @param orderBy
     * @return
     */
    @GET("diveshops/{shopUid}/trips")
    Call<DailyTripListResponse> getDiveShopTrips(
            @Path("shopUid") String shopUid, @Query("dive_site_id") int diveSiteId,
            @Query("start_date") String startDate, @Query("end_date") String endDate,
            @Query("offset") int offset, @Query("sort") String sort, @Query("order") String orderBy);

    /**
     * Add new Daily Trip
     *
     * @param shopUid
     * @param dailyTrip
     * @return
     */
    @POST("diveshops/{shopUid}/trips")
    Call<DailyTripResponse> addDailyTrip(@Path("shopUid") String shopUid, @Body DailyTrip dailyTrip);

    /**
     * Update selected dive trip
     *
     * @param shopUid
     * @param tripId
     * @param newDailyTrip
     * @return
     */
    @PUT("diveshops/{shopUid}/trips/{tripId}")
    Call<DailyTripResponse> updateDailyTrip(@Path("shopUid") String shopUid, @Path("tripId") int tripId,
                                            @Body DailyTrip newDailyTrip);

    /**
     * @param shopUid
     * @param dailyTripIds
     * @return
     */
    @DELETE("diveshops/{shopUid}/trips")
    Call<Response> deleteDiveShopTrips(@Path("shopUid") String shopUid, @Query("daily_trip_ids[]") List<Integer> dailyTripIds);

    /**
     * Get list of diveshop guides
     *
     * @param shopUid
     * @param name
     * @param offset
     * @return
     */
    @GET("diveshops/{shopUid}/guides")
    Call<GuideListResponse> getGuides(@Path("shopUid") String shopUid, @Query("q") String name, @Query("offset") int offset);

    /**
     * Get list of diveshop guides
     *
     * @param shopUid
     * @param offset
     * @return
     */
    @GET("diveshops/{shopUid}/guides")
    Call<GuideListResponse> getGuides(@Path("shopUid") String shopUid, @Query("offset") int offset);

    /**
     * Get guide
     *
     * @param shopUid
     * @param guideId
     * @return
     */
    @GET("diveshops/{shopUid}/guides/{guideId}")
    Call<GuideResponse> getGuide(@Path("shopUid") String shopUid, @Path("guideId") int guideId);

    /**
     * Add new Guide
     *
     * @param shopUid
     * @param name
     * @return
     */
    @POST("diveshops/{shopUid}/guides")
    Call<GuideResponse> addGuide(@Path("shopUid") String shopUid, @Query("name") String name);

    /**
     * Update Guide
     *
     * @param shopUid
     * @param name
     * @return
     */
    @PUT("diveshops/{shopUid}/guides/{guideId}")
    Call<GuideResponse> updateGuide(@Path("shopUid") String shopUid, @Query("name") String name);

    /**
     * Delete Guide
     *
     * @param shopUid
     * @return
     */
    @DELETE("diveshops/{shopUid}/guides/{guideId}")
    Call<Response> deleteGuide(@Path("shopUid") String shopUid);
}
