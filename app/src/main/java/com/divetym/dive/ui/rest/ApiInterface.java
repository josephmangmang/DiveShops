package com.divetym.dive.ui.rest;

import com.divetym.dive.ui.models.Course;
import com.divetym.dive.ui.models.DailyTrip;

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
    Call register(@Field("email") String email, @Field("password") String password, @Field("type") String type);

    /**
     * Diver and Dive Shop login
     *
     * @param email
     * @param password
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Call login(@Field("email") String email, @Field("password") String password, @Field("type") String type);

    /**
     * Get a list of Course
     *
     * @param offset
     * @param sort
     * @param orderBy
     * @return
     */
    @GET("courses")
    Call<List<Course>> getCourses(@Query("offset") int offset, @Query("sort") String sort, @Query("order") String orderBy);

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
    Call getSites(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius, @Query("offset") int offset);

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
    Call getDiveShops(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius,
                      @Query("offset") int offset);

    /**
     * Get dive shop informations
     *
     * @param shopUid
     * @return
     */
    @GET("diveshops/{shopUid}")
    Call getDiveShop(@Path("shopUid") String shopUid);

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
    Call getDiveShopCourses(@Path("shopUid") String shopUid, @Query("offset") int offset, @Query("sort") String sort,
                            @Query("order") String orderBy);

    /**
     * Update dive shop courses
     *
     * @param shopUid
     * @param shopCourseId
     * @param price
     * @return
     */
    @PUT("diveshops/{shopUid}/courses/{shopCourseId}")
    Call updateDiveShopCourse(@Path("shopUid") String shopUid, @Path("shopCourseId") int shopCourseId, @Query("price") double price);

    /**
     * Add course on dive shop
     *
     * @param courseId
     * @param price
     * @return
     */
    @POST("diveshops/{shopUid}/courses")
    Call addDiveShopCourse(@Field("course_id") int courseId, @Field("price") double price);

    /**
     * Add new boat
     *
     * @param name
     * @return
     */
    @POST("diveshops/{shopUid}/boats")
    Call addDiveShopBoat(@Path("shopUid") @Field("name") String name);

    /**
     * Get a list of boats
     *
     * @param offset
     * @return
     */
    @GET("diveshops/{shopUid}/boats")
    Call getDiveShopBoats(@Path("shopUid") String shopUid, @Query("offset") int offset);

    /**
     * Update boat
     *
     * @param shopUid
     * @param boatId
     * @param name
     * @return
     */
    @PUT("diveshops/{shopUid}/boats/{boatId}")
    Call updateDiveShopBoat(@Path("shopUid") String shopUid, @Path("boatId") int boatId, @Field("name") String name);

    /**
     * Delete boat
     *
     * @param shopUid
     * @param boatId
     * @return
     */
    @DELETE("diveshops/{shopUid}/boats/{boatId}")
    Call deleteDiveShopBoat(@Path("shopUid") String shopUid, @Path("boatId") int boatId);

    /**
     * Get list of Dive Shop Dive Trips
     *
     * @param shopUid
     * @param startDate
     * @param endDate
     * @return
     */
    @GET("diveshops/{shopUid}/trips")
    Call getDiveShopTrips(@Path("shopUid") String shopUid, @Query("start_date") String startDate, @Query("end_date") String endDate);

    /**
     * Add new Daily Trip
     *
     * @param shopUid
     * @param dailyTrip
     * @return
     */
    @POST("diveshops/{shopUid}/trips")
    Call addDailyTrip(@Path("shopUid") String shopUid, @Body DailyTrip dailyTrip);

    /**
     * Update selected dive trip
     *
     * @param shopUid
     * @param tripId
     * @param groupSize
     * @param numberOfDives
     * @param date
     * @param price
     * @param priceNote
     * @return
     */
    @PUT("diveshops/{shopUid}/trips/{tripId}")
    Call updateDailyTrip(@Path("shopUid") String shopUid, @Path("tripId") int tripId,
                         @Field("group_size") int groupSize, @Field("number_of_dive") int numberOfDives,
                         @Field("date") String date, @Field("price") double price, @Field("price_note") String priceNote);


}