package com.example.demo.entity;

import com.example.demo.util.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewAppletPageElementDefaultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    public ViewAppletPageElementDefaultExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPageIdIsNull() {
            addCriterion("page_id is null");
            return (Criteria) this;
        }

        public Criteria andPageIdIsNotNull() {
            addCriterion("page_id is not null");
            return (Criteria) this;
        }

        public Criteria andPageIdEqualTo(Integer value) {
            addCriterion("page_id =", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotEqualTo(Integer value) {
            addCriterion("page_id <>", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdGreaterThan(Integer value) {
            addCriterion("page_id >", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_id >=", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdLessThan(Integer value) {
            addCriterion("page_id <", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdLessThanOrEqualTo(Integer value) {
            addCriterion("page_id <=", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdIn(List<Integer> values) {
            addCriterion("page_id in", values, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotIn(List<Integer> values) {
            addCriterion("page_id not in", values, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdBetween(Integer value1, Integer value2) {
            addCriterion("page_id between", value1, value2, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("page_id not between", value1, value2, "pageId");
            return (Criteria) this;
        }

        public Criteria andElementLogoIsNull() {
            addCriterion("element_logo is null");
            return (Criteria) this;
        }

        public Criteria andElementLogoIsNotNull() {
            addCriterion("element_logo is not null");
            return (Criteria) this;
        }

        public Criteria andElementLogoEqualTo(String value) {
            addCriterion("element_logo =", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoNotEqualTo(String value) {
            addCriterion("element_logo <>", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoGreaterThan(String value) {
            addCriterion("element_logo >", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoGreaterThanOrEqualTo(String value) {
            addCriterion("element_logo >=", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoLessThan(String value) {
            addCriterion("element_logo <", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoLessThanOrEqualTo(String value) {
            addCriterion("element_logo <=", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoLike(String value) {
            addCriterion("element_logo like", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoNotLike(String value) {
            addCriterion("element_logo not like", value, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoIn(List<String> values) {
            addCriterion("element_logo in", values, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoNotIn(List<String> values) {
            addCriterion("element_logo not in", values, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoBetween(String value1, String value2) {
            addCriterion("element_logo between", value1, value2, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementLogoNotBetween(String value1, String value2) {
            addCriterion("element_logo not between", value1, value2, "elementLogo");
            return (Criteria) this;
        }

        public Criteria andElementIconIsNull() {
            addCriterion("element_icon is null");
            return (Criteria) this;
        }

        public Criteria andElementIconIsNotNull() {
            addCriterion("element_icon is not null");
            return (Criteria) this;
        }

        public Criteria andElementIconEqualTo(String value) {
            addCriterion("element_icon =", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconNotEqualTo(String value) {
            addCriterion("element_icon <>", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconGreaterThan(String value) {
            addCriterion("element_icon >", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconGreaterThanOrEqualTo(String value) {
            addCriterion("element_icon >=", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconLessThan(String value) {
            addCriterion("element_icon <", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconLessThanOrEqualTo(String value) {
            addCriterion("element_icon <=", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconLike(String value) {
            addCriterion("element_icon like", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconNotLike(String value) {
            addCriterion("element_icon not like", value, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconIn(List<String> values) {
            addCriterion("element_icon in", values, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconNotIn(List<String> values) {
            addCriterion("element_icon not in", values, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconBetween(String value1, String value2) {
            addCriterion("element_icon between", value1, value2, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementIconNotBetween(String value1, String value2) {
            addCriterion("element_icon not between", value1, value2, "elementIcon");
            return (Criteria) this;
        }

        public Criteria andElementNameIsNull() {
            addCriterion("element_name is null");
            return (Criteria) this;
        }

        public Criteria andElementNameIsNotNull() {
            addCriterion("element_name is not null");
            return (Criteria) this;
        }

        public Criteria andElementNameEqualTo(String value) {
            addCriterion("element_name =", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameNotEqualTo(String value) {
            addCriterion("element_name <>", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameGreaterThan(String value) {
            addCriterion("element_name >", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameGreaterThanOrEqualTo(String value) {
            addCriterion("element_name >=", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameLessThan(String value) {
            addCriterion("element_name <", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameLessThanOrEqualTo(String value) {
            addCriterion("element_name <=", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameLike(String value) {
            addCriterion("element_name like", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameNotLike(String value) {
            addCriterion("element_name not like", value, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameIn(List<String> values) {
            addCriterion("element_name in", values, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameNotIn(List<String> values) {
            addCriterion("element_name not in", values, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameBetween(String value1, String value2) {
            addCriterion("element_name between", value1, value2, "elementName");
            return (Criteria) this;
        }

        public Criteria andElementNameNotBetween(String value1, String value2) {
            addCriterion("element_name not between", value1, value2, "elementName");
            return (Criteria) this;
        }

        public Criteria andContentIdIsNull() {
            addCriterion("content_id is null");
            return (Criteria) this;
        }

        public Criteria andContentIdIsNotNull() {
            addCriterion("content_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentIdEqualTo(Integer value) {
            addCriterion("content_id =", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotEqualTo(Integer value) {
            addCriterion("content_id <>", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdGreaterThan(Integer value) {
            addCriterion("content_id >", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_id >=", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLessThan(Integer value) {
            addCriterion("content_id <", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLessThanOrEqualTo(Integer value) {
            addCriterion("content_id <=", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdIn(List<Integer> values) {
            addCriterion("content_id in", values, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotIn(List<Integer> values) {
            addCriterion("content_id not in", values, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdBetween(Integer value1, Integer value2) {
            addCriterion("content_id between", value1, value2, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("content_id not between", value1, value2, "contentId");
            return (Criteria) this;
        }

        public Criteria andElementJsonIsNull() {
            addCriterion("element_json is null");
            return (Criteria) this;
        }

        public Criteria andElementJsonIsNotNull() {
            addCriterion("element_json is not null");
            return (Criteria) this;
        }

        public Criteria andElementJsonEqualTo(String value) {
            addCriterion("element_json =", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonNotEqualTo(String value) {
            addCriterion("element_json <>", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonGreaterThan(String value) {
            addCriterion("element_json >", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonGreaterThanOrEqualTo(String value) {
            addCriterion("element_json >=", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonLessThan(String value) {
            addCriterion("element_json <", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonLessThanOrEqualTo(String value) {
            addCriterion("element_json <=", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonLike(String value) {
            addCriterion("element_json like", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonNotLike(String value) {
            addCriterion("element_json not like", value, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonIn(List<String> values) {
            addCriterion("element_json in", values, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonNotIn(List<String> values) {
            addCriterion("element_json not in", values, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonBetween(String value1, String value2) {
            addCriterion("element_json between", value1, value2, "elementJson");
            return (Criteria) this;
        }

        public Criteria andElementJsonNotBetween(String value1, String value2) {
            addCriterion("element_json not between", value1, value2, "elementJson");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andElementStatusIsNull() {
            addCriterion("element_status is null");
            return (Criteria) this;
        }

        public Criteria andElementStatusIsNotNull() {
            addCriterion("element_status is not null");
            return (Criteria) this;
        }

        public Criteria andElementStatusEqualTo(Boolean value) {
            addCriterion("element_status =", value, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusNotEqualTo(Boolean value) {
            addCriterion("element_status <>", value, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusGreaterThan(Boolean value) {
            addCriterion("element_status >", value, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("element_status >=", value, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusLessThan(Boolean value) {
            addCriterion("element_status <", value, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("element_status <=", value, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusIn(List<Boolean> values) {
            addCriterion("element_status in", values, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusNotIn(List<Boolean> values) {
            addCriterion("element_status not in", values, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("element_status between", value1, value2, "elementStatus");
            return (Criteria) this;
        }

        public Criteria andElementStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("element_status not between", value1, value2, "elementStatus");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }
    }
}
