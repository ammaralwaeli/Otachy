package com.srit.otachy.database.models;

public class UserCategories {
    private CategotyModel category;

    public CategotyModel getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "UserCategories{" +
                "category=" + category.toString() +
                '}';
    }
}
