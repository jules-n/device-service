// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/type/datetime.proto

package com.google.type;

public interface DateTimeOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.type.DateTime)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Optional. Year of date. Must be from 1 to 9999, or 0 if specifying a
   * datetime without a year.
   * </pre>
   *
   * <code>int32 year = 1;</code>
   */
  int getYear();

  /**
   * <pre>
   * Required. Month of year. Must be from 1 to 12.
   * </pre>
   *
   * <code>int32 month = 2;</code>
   */
  int getMonth();

  /**
   * <pre>
   * Required. Day of month. Must be from 1 to 31 and valid for the year and
   * month.
   * </pre>
   *
   * <code>int32 day = 3;</code>
   */
  int getDay();

  /**
   * <pre>
   * Required. Hours of day in 24 hour format. Should be from 0 to 23. An API
   * may choose to allow the value "24:00:00" for scenarios like business
   * closing time.
   * </pre>
   *
   * <code>int32 hours = 4;</code>
   */
  int getHours();

  /**
   * <pre>
   * Required. Minutes of hour of day. Must be from 0 to 59.
   * </pre>
   *
   * <code>int32 minutes = 5;</code>
   */
  int getMinutes();

  /**
   * <pre>
   * Required. Seconds of minutes of the time. Must normally be from 0 to 59. An
   * API may allow the value 60 if it allows leap-seconds.
   * </pre>
   *
   * <code>int32 seconds = 6;</code>
   */
  int getSeconds();

  /**
   * <pre>
   * Required. Fractions of seconds in nanoseconds. Must be from 0 to
   * 999,999,999.
   * </pre>
   *
   * <code>int32 nanos = 7;</code>
   */
  int getNanos();

  /**
   * <pre>
   * UTC offset. Must be whole seconds, between -18 hours and +18 hours.
   * For example, a UTC offset of -4:00 would be represented as
   * { seconds: -14400 }.
   * </pre>
   *
   * <code>.google.protobuf.Duration utc_offset = 8;</code>
   */
  com.google.protobuf.Duration getUtcOffset();
  /**
   * <pre>
   * UTC offset. Must be whole seconds, between -18 hours and +18 hours.
   * For example, a UTC offset of -4:00 would be represented as
   * { seconds: -14400 }.
   * </pre>
   *
   * <code>.google.protobuf.Duration utc_offset = 8;</code>
   */
  com.google.protobuf.DurationOrBuilder getUtcOffsetOrBuilder();

  /**
   * <pre>
   * Time zone.
   * </pre>
   *
   * <code>.google.type.TimeZone time_zone = 9;</code>
   */
  com.google.type.TimeZone getTimeZone();
  /**
   * <pre>
   * Time zone.
   * </pre>
   *
   * <code>.google.type.TimeZone time_zone = 9;</code>
   */
  com.google.type.TimeZoneOrBuilder getTimeZoneOrBuilder();

  public com.google.type.DateTime.TimeOffsetCase getTimeOffsetCase();
}
