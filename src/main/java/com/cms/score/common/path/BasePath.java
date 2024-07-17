package com.cms.score.common.path;

public interface BasePath {
    
    String BASE_API ="/api";

    // product management
    String BASE_PRODUCT = BASE_API + "/products";
    String BASE_PRODUCT_TYPE = BASE_API + "/product-types";
    String BASE_PRODUCT_TARGET = BASE_API + "/product-targets";
    String BASE_PRODUCT_PLAN = BASE_API + "/product-plans";
    String BASE_PRODUCT_TARGET_PLANNING = BASE_API + "/product-papers";
    String BASE_KKP = BASE_API + "/kkp";

    // branch management
    String BASE_BRANCH = BASE_API + "/branchs";
    String BASE_BRANCH_TARGET = BASE_API + "/branch-targets";
    String BASE_BRANCH_TARGET_PLANNING = BASE_API + "/branch-papers";
    String BASE_BRANCH_KKC = BASE_API + "/kkc";

    String BASE_COLLECTORS = BASE_API + "/collectors";
    String BASE_COLLECTOR_KKK = BASE_API + "/kkk";

}
