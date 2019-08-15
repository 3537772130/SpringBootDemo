package com.example.demo.entity;

import com.example.demo.util.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    public MenuInfoExample() {
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

        public Criteria andMenuNameIsNull() {
            addCriterion("menu_name is null");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNotNull() {
            addCriterion("menu_name is not null");
            return (Criteria) this;
        }

        public Criteria andMenuNameEqualTo(String value) {
            addCriterion("menu_name =", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotEqualTo(String value) {
            addCriterion("menu_name <>", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThan(String value) {
            addCriterion("menu_name >", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("menu_name >=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThan(String value) {
            addCriterion("menu_name <", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThanOrEqualTo(String value) {
            addCriterion("menu_name <=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLike(String value) {
            addCriterion("menu_name like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotLike(String value) {
            addCriterion("menu_name not like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameIn(List<String> values) {
            addCriterion("menu_name in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotIn(List<String> values) {
            addCriterion("menu_name not in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameBetween(String value1, String value2) {
            addCriterion("menu_name between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotBetween(String value1, String value2) {
            addCriterion("menu_name not between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleIsNull() {
            addCriterion("menu_simple is null");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleIsNotNull() {
            addCriterion("menu_simple is not null");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleEqualTo(String value) {
            addCriterion("menu_simple =", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleNotEqualTo(String value) {
            addCriterion("menu_simple <>", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleGreaterThan(String value) {
            addCriterion("menu_simple >", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleGreaterThanOrEqualTo(String value) {
            addCriterion("menu_simple >=", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleLessThan(String value) {
            addCriterion("menu_simple <", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleLessThanOrEqualTo(String value) {
            addCriterion("menu_simple <=", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleLike(String value) {
            addCriterion("menu_simple like", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleNotLike(String value) {
            addCriterion("menu_simple not like", value, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleIn(List<String> values) {
            addCriterion("menu_simple in", values, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleNotIn(List<String> values) {
            addCriterion("menu_simple not in", values, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleBetween(String value1, String value2) {
            addCriterion("menu_simple between", value1, value2, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuSimpleNotBetween(String value1, String value2) {
            addCriterion("menu_simple not between", value1, value2, "menuSimple");
            return (Criteria) this;
        }

        public Criteria andMenuLevelIsNull() {
            addCriterion("menu_level is null");
            return (Criteria) this;
        }

        public Criteria andMenuLevelIsNotNull() {
            addCriterion("menu_level is not null");
            return (Criteria) this;
        }

        public Criteria andMenuLevelEqualTo(Integer value) {
            addCriterion("menu_level =", value, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelNotEqualTo(Integer value) {
            addCriterion("menu_level <>", value, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelGreaterThan(Integer value) {
            addCriterion("menu_level >", value, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_level >=", value, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelLessThan(Integer value) {
            addCriterion("menu_level <", value, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelLessThanOrEqualTo(Integer value) {
            addCriterion("menu_level <=", value, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelIn(List<Integer> values) {
            addCriterion("menu_level in", values, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelNotIn(List<Integer> values) {
            addCriterion("menu_level not in", values, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelBetween(Integer value1, Integer value2) {
            addCriterion("menu_level between", value1, value2, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_level not between", value1, value2, "menuLevel");
            return (Criteria) this;
        }

        public Criteria andMenuPathIsNull() {
            addCriterion("menu_path is null");
            return (Criteria) this;
        }

        public Criteria andMenuPathIsNotNull() {
            addCriterion("menu_path is not null");
            return (Criteria) this;
        }

        public Criteria andMenuPathEqualTo(String value) {
            addCriterion("menu_path =", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathNotEqualTo(String value) {
            addCriterion("menu_path <>", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathGreaterThan(String value) {
            addCriterion("menu_path >", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathGreaterThanOrEqualTo(String value) {
            addCriterion("menu_path >=", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathLessThan(String value) {
            addCriterion("menu_path <", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathLessThanOrEqualTo(String value) {
            addCriterion("menu_path <=", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathLike(String value) {
            addCriterion("menu_path like", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathNotLike(String value) {
            addCriterion("menu_path not like", value, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathIn(List<String> values) {
            addCriterion("menu_path in", values, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathNotIn(List<String> values) {
            addCriterion("menu_path not in", values, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathBetween(String value1, String value2) {
            addCriterion("menu_path between", value1, value2, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuPathNotBetween(String value1, String value2) {
            addCriterion("menu_path not between", value1, value2, "menuPath");
            return (Criteria) this;
        }

        public Criteria andMenuSrcIsNull() {
            addCriterion("menu_src is null");
            return (Criteria) this;
        }

        public Criteria andMenuSrcIsNotNull() {
            addCriterion("menu_src is not null");
            return (Criteria) this;
        }

        public Criteria andMenuSrcEqualTo(String value) {
            addCriterion("menu_src =", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcNotEqualTo(String value) {
            addCriterion("menu_src <>", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcGreaterThan(String value) {
            addCriterion("menu_src >", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcGreaterThanOrEqualTo(String value) {
            addCriterion("menu_src >=", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcLessThan(String value) {
            addCriterion("menu_src <", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcLessThanOrEqualTo(String value) {
            addCriterion("menu_src <=", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcLike(String value) {
            addCriterion("menu_src like", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcNotLike(String value) {
            addCriterion("menu_src not like", value, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcIn(List<String> values) {
            addCriterion("menu_src in", values, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcNotIn(List<String> values) {
            addCriterion("menu_src not in", values, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcBetween(String value1, String value2) {
            addCriterion("menu_src between", value1, value2, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuSrcNotBetween(String value1, String value2) {
            addCriterion("menu_src not between", value1, value2, "menuSrc");
            return (Criteria) this;
        }

        public Criteria andMenuIndexIsNull() {
            addCriterion("menu_index is null");
            return (Criteria) this;
        }

        public Criteria andMenuIndexIsNotNull() {
            addCriterion("menu_index is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIndexEqualTo(Integer value) {
            addCriterion("menu_index =", value, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexNotEqualTo(Integer value) {
            addCriterion("menu_index <>", value, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexGreaterThan(Integer value) {
            addCriterion("menu_index >", value, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_index >=", value, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexLessThan(Integer value) {
            addCriterion("menu_index <", value, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexLessThanOrEqualTo(Integer value) {
            addCriterion("menu_index <=", value, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexIn(List<Integer> values) {
            addCriterion("menu_index in", values, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexNotIn(List<Integer> values) {
            addCriterion("menu_index not in", values, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexBetween(Integer value1, Integer value2) {
            addCriterion("menu_index between", value1, value2, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andMenuIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_index not between", value1, value2, "menuIndex");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("status not between", value1, value2, "status");
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
