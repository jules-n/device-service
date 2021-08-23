// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/documentation.proto

package com.google.api;

public interface DocumentationOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.api.Documentation)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * A short summary of what the service does. Can only be provided by
   * plain text.
   * </pre>
   *
   * <code>string summary = 1;</code>
   */
  java.lang.String getSummary();
  /**
   * <pre>
   * A short summary of what the service does. Can only be provided by
   * plain text.
   * </pre>
   *
   * <code>string summary = 1;</code>
   */
  com.google.protobuf.ByteString
      getSummaryBytes();

  /**
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  java.util.List<com.google.api.Page> 
      getPagesList();
  /**
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  com.google.api.Page getPages(int index);
  /**
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  int getPagesCount();
  /**
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  java.util.List<? extends com.google.api.PageOrBuilder> 
      getPagesOrBuilderList();
  /**
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  com.google.api.PageOrBuilder getPagesOrBuilder(
      int index);

  /**
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  java.util.List<com.google.api.DocumentationRule> 
      getRulesList();
  /**
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  com.google.api.DocumentationRule getRules(int index);
  /**
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  int getRulesCount();
  /**
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  java.util.List<? extends com.google.api.DocumentationRuleOrBuilder> 
      getRulesOrBuilderList();
  /**
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  com.google.api.DocumentationRuleOrBuilder getRulesOrBuilder(
      int index);

  /**
   * <pre>
   * The URL to the root of documentation.
   * </pre>
   *
   * <code>string documentation_root_url = 4;</code>
   */
  java.lang.String getDocumentationRootUrl();
  /**
   * <pre>
   * The URL to the root of documentation.
   * </pre>
   *
   * <code>string documentation_root_url = 4;</code>
   */
  com.google.protobuf.ByteString
      getDocumentationRootUrlBytes();

  /**
   * <pre>
   * Declares a single overview page. For example:
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   overview: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * This is a shortcut for the following declaration (using pages style):
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   pages:
   *   - name: Overview
   *     content: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * Note: you cannot specify both `overview` field and `pages` field.
   * </pre>
   *
   * <code>string overview = 2;</code>
   */
  java.lang.String getOverview();
  /**
   * <pre>
   * Declares a single overview page. For example:
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   overview: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * This is a shortcut for the following declaration (using pages style):
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   pages:
   *   - name: Overview
   *     content: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * Note: you cannot specify both `overview` field and `pages` field.
   * </pre>
   *
   * <code>string overview = 2;</code>
   */
  com.google.protobuf.ByteString
      getOverviewBytes();
}
